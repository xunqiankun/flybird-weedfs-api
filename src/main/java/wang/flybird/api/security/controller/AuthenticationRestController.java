package wang.flybird.api.security.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.vdurmont.emoji.EmojiParser;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;
import wang.flybird.api.security.JwtAuthenticationRequest;
import wang.flybird.api.security.JwtTokenUtil;
import wang.flybird.api.security.JwtUser;
import wang.flybird.api.security.JwtUserFactory;
import wang.flybird.api.security.entity.WxssLoginInfo;
import wang.flybird.api.security.service.JwtAuthenticationResponse;
import wang.flybird.api.security.service.JwtUserDetailsServiceImpl;
import wang.flybird.config.annotation.SysLogAnnotation;
import wang.flybird.entity.FbAuthority;
import wang.flybird.entity.FbUser;
import wang.flybird.entity.FbUserAccount;
import wang.flybird.entity.FbUserAccountSession;
import wang.flybird.entity.FbUserAuthority;
import wang.flybird.entity.FbUserBase;
import wang.flybird.entity.SysConfig;
import wang.flybird.entity.custom.R;
import wang.flybird.entity.custom.WXAppUserInfo;
import wang.flybird.entity.repository.FbUserAccountRepository;
import wang.flybird.entity.repository.FbUserAccountSessionRepository;
import wang.flybird.entity.repository.FbUserAuthorityRepository;
import wang.flybird.entity.repository.FbUserBaseRepository;
import wang.flybird.entity.repository.FbUserRepository;
import wang.flybird.entity.repository.SysConfigRepository;
import wang.flybird.entity.repository.SysLogRepository;
import wang.flybird.utils.idwoker.IdWorker;
import wang.flybird.utils.net.CookieUtil;
import wang.flybird.utils.wechat.WXBizDataCrypt;
import weixin.popular.api.SnsAPI;
import weixin.popular.bean.sns.Jscode2sessionResult;
import weixin.popular.util.EmojiUtil;

@Transactional
@RestController
@RequestMapping(path = "/api/authentication")
@Api("权限认证接口")
public class AuthenticationRestController {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${jwt.header}")
    private String tokenHeader;
    
    @Value("${jwt.expiration}")
    private int expiration;
    
    @Autowired
    IdWorker idWorker;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsServiceImpl userDetailsService;
    
    @Autowired
    private SysConfigRepository sysConfigRepository;
    
    @Autowired
    private FbUserRepository fbUserRepository;
    
    @Autowired
    private FbUserBaseRepository fbUserBaseRepository;
    
    @Autowired
    private FbUserAuthorityRepository fbUserAuthorityRepository;
    
    @Autowired
    FbUserAccountRepository fbUserAccountRepository;
    
    @Autowired
    FbUserAccountSessionRepository fbUserAccountSessionRepository;

    @SysLogAnnotation("获取Token")
    @RequestMapping(value = "${jwt.route.authentication.path}", method = RequestMethod.POST)
    @ApiOperation(value = "获取Token",notes = "获取到的token以cookie形式返回")
	@ApiImplicitParams({
	   @ApiImplicitParam(name="authenticationRequest",value="认证信息",required=true,dataType="JwtAuthenticationRequest",paramType="body"),
	 })
    public ResponseEntity<?> createAuthenticationToken(
    		@RequestBody JwtAuthenticationRequest authenticationRequest, 
    		HttpServletResponse response,
    		@ApiIgnore Device device) throws AuthenticationException {
        String ret = "";
        String token = "";
    	try {
        	// Perform the security
            final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            // Reload password post-security so we can generate token
            String username = authenticationRequest.getUsername();
            final JwtUser jwtUser = userDetailsService.loadUserByUsername(username);
            token = jwtTokenUtil.generateToken(jwtUser, device);
            ret = "succeed";
		} catch (Exception e) {
			ret = "failure"; 
		}
    	Map<String, Object> att = new HashMap<String, Object>();
    	att.put("ret", ret);
    	//att.put("token", token);
        
    	CookieUtil.setCookie(response, this.tokenHeader, token, expiration);

        // Return the token
        return ResponseEntity.ok(att);
    }

//    @SysLogAnnotation("刷新Token")
    @RequestMapping(value = "${jwt.route.authentication.refresh}", method = RequestMethod.POST)
    @ApiOperation(value = "刷新Token",notes = "刷新Token以cookie形式返回")
    public ResponseEntity<?> refreshAndGetAuthenticationToken(
    		HttpServletRequest request,
    		HttpServletResponse response) {
//        String token = request.getHeader(tokenHeader);
        
        Cookie cookie = CookieUtil.getCookie(request, this.tokenHeader);
    	String token = "";
    	if(cookie != null){
    		token = cookie.getValue();	
    	}
        
        
        String userid = jwtTokenUtil.getUseridFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUserid(userid);

        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
            String refreshedToken = jwtTokenUtil.refreshToken(token);
            CookieUtil.setCookie(response, this.tokenHeader, token, expiration);
            return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    @SysLogAnnotation("微信小程序登录")
    @RequestMapping(value = "/doWeChatLogin", method = RequestMethod.POST)
    @ApiOperation(value = "微信小程序登录",notes = "登录成功返回Token,如果是新用户自动注册")
	@ApiImplicitParams({
	   @ApiImplicitParam(name="clientType",value="客户端类型 01 微信小程序 ",required=true,dataType="String",paramType="query"),
	   @ApiImplicitParam(name="accountType",value="账户类型 00 系统用户名帐号 10 微信帐号 20 手机号帐号 30 邮箱账户 ",required=true,dataType="String",paramType="query"),
	   @ApiImplicitParam(name="WxAppId",value="小程序ID",required=true,dataType="String",paramType="query"),
	   @ApiImplicitParam(name="WxCode",value="小程序登录凭证",required=true,dataType="String",paramType="query"),
	   @ApiImplicitParam(name="WxEncryptedData",value="包括敏感数据在内的完整用户信息的加密数据",required=true,dataType="String",paramType="query"),
	   @ApiImplicitParam(name="WxIv",value="加密算法的初始向量",required=true,dataType="String",paramType="query"),
	 })
    public R doWeChatLogin(/*String clientType,
    		String accountType,
    		String WxAppId,
    		String WxCode,
    		String WxEncryptedData,
    		String WxIv,*/
    		@RequestBody WxssLoginInfo wxssLoginInfo,
    		HttpServletResponse response,HttpServletRequest req ,
    		@ApiIgnore Device device){
    	
    	String clientType = wxssLoginInfo.getClientType();
		String accountType = wxssLoginInfo.getAccountType();
		String WxAppId = wxssLoginInfo.getWxAppId();
		String WxCode = wxssLoginInfo.getWxCode();
		String WxEncryptedData = wxssLoginInfo.getWxEncryptedData();
		String WxIv = wxssLoginInfo.getWxIv();
    	
       //根据WxAppId获取WxAppSecret 
    	System.out.println(req.getParameter("WxAppId"));
    	SysConfig sysConfig = sysConfigRepository.findByKey(WxAppId);
    	String WxAppSecret = sysConfig.getValue();
    	
        //根据WxAppId、WxAppSecret、WxCode、grant_type获取openId、session_key
    	Jscode2sessionResult Jscode2sessionResult = SnsAPI.jscode2session(WxAppId, WxAppSecret, WxCode);
    	
    	if(Jscode2sessionResult.isSuccess()){
    		String WxOpenid = Jscode2sessionResult.getOpenid();
    		String WxSessionKey = Jscode2sessionResult.getSession_key();
    		
    		WXAppUserInfo  wxAppUserInfo =  decryptWxUserInfo(WxEncryptedData,WxSessionKey,WxIv);
    		String token = "";
    		
    		wxAppUserInfo.setNickName(
    		EmojiParser.parseToAliases(wxAppUserInfo.getNickName())
    		);
    		
    		//根据clientType、accountType、WxAppId、WxOpenid判断用户是否存在
    		FbUserAccount fbUserAccount = fbUserAccountRepository.findByClientTypeAndAccountTypeAndWxAppidAndWxOpenid(clientType,accountType,WxAppId,WxOpenid);
    		if(fbUserAccount!=null&&!fbUserAccount.getId().equals("")){
    			//生成token
    			String fbUserAccountId = fbUserAccount.getId();
    			token = generateTokenByAccountId(fbUserAccountId,device);
    			int i = fbUserAccountSessionRepository.updateToken(token,fbUserAccountId);
    			
    		}else{
    			
    			
    			//新用户自动创建用户信息
    			FbUser fbUser = new FbUser();
    			FbUserBase fbUserBase = new FbUserBase();
    			FbUserAuthority fbUserAuthority = new FbUserAuthority();
    			fbUserAccount = new FbUserAccount();
    			FbUserAccountSession fbUserAccountSession = new FbUserAccountSession();
    			
    			//根据wxUnionId判断是否有同一用户的信息
    			String fbUserId = "";
    		    String wxUnionId = wxAppUserInfo.getUnionId();
    			if(wxUnionId!=null && !wxUnionId.equals("")){
    				FbUser FbUser = fbUserRepository.findByWxunionid(wxUnionId);
    				if(FbUser!=null && !FbUser.getId().equals("")){
    					fbUserId = FbUser.getId();
    				} 
    			}
    			
    			if(fbUserId.equals("")){
    				fbUser.setId(idWorker.getStrId());
    				fbUser.setWxunionid(wxUnionId);
    				fbUser.setEnabled(true);
    				List<FbAuthority> authorities = new ArrayList<FbAuthority>();
    				fbUser.setAuthorities(authorities);
    				fbUserRepository.save(fbUser);
    				
    				fbUserBase.setId(idWorker.getStrId());
    				fbUserBase.setFbUserId(fbUser.getId());
    				fbUserBase.setNickname(wxAppUserInfo.getNickName());
    				fbUserBase.setSex(wxAppUserInfo.getGender());
    				fbUserBase.setCity(wxAppUserInfo.getCity());
    				fbUserBase.setCountry(wxAppUserInfo.getCountry());
    				fbUserBase.setProvince(wxAppUserInfo.getProvince());
//    				fbUserBase.setLanguage(wxAppUserInfo.get);
    				fbUserBase.setHeadimgurl(wxAppUserInfo.getAvatarUrl());
    				fbUserBaseRepository.save(fbUserBase);
    				
    				fbUserAuthority.setId(idWorker.getStrId());
    				fbUserAuthority.setUserId(fbUser.getId());
    				fbUserAuthority.setAuthorityId("1");
    				fbUserAuthorityRepository.save(fbUserAuthority);
    				
    				fbUserAccount.setId(idWorker.getStrId());
    				fbUserAccount.setFbUserId(fbUser.getId());
    				fbUserAccount.setClientType(clientType);
    				fbUserAccount.setAccountType(accountType);
    				fbUserAccount.setWxAppid(WxAppId);
    				fbUserAccount.setWxOpenid(WxOpenid);
    				fbUserAccountRepository.save(fbUserAccount);
    				
    				fbUserAccountSession.setId(idWorker.getStrId());
    				fbUserAccountSession.setFbUserAccountId(fbUserAccount.getId());
    				JwtUser jwtUser = JwtUserFactory.create(fbUser);
    				token = jwtTokenUtil.generateToken(jwtUser, device);
    				fbUserAccountSession.setToken(token);
    				fbUserAccountSessionRepository.save(fbUserAccountSession);
    				
    			}else{
    				fbUserAccount.setId(idWorker.getStrId());
    				fbUserAccount.setFbUserId(fbUserId); //使用已存在的fbUserId
    				fbUserAccount.setClientType(clientType);
    				fbUserAccount.setAccountType(accountType);
    				fbUserAccount.setWxAppid(WxAppId);
    				fbUserAccount.setWxOpenid(WxOpenid);
    				fbUserAccountRepository.save(fbUserAccount);
    				
    				fbUserAccountSession.setId(idWorker.getStrId());
    				fbUserAccountSession.setFbUserAccountId(fbUserAccount.getId());
    				JwtUser jwtUser = JwtUserFactory.create(fbUser);
    				token = jwtTokenUtil.generateToken(jwtUser, device);
    				fbUserAccountSession.setToken(token);
    				fbUserAccountSessionRepository.save(fbUserAccountSession);
    			}
    			
    		}
    		
    		CookieUtil.setCookie(response, this.tokenHeader, token, expiration);
    		return R.ok().put("authtoken", token);
    	}else{
    		return R.error(403, "login error");
    	}
    	
    	//生成token并保存
	
    }
    
    private WXAppUserInfo decryptWxUserInfo(String WxEncryptedData, String WxSessionKey, String WxIv){
    	WXAppUserInfo wxAppUserInfo = null;
    	WXBizDataCrypt pc = new WXBizDataCrypt();
		byte[] content = Base64.decodeBase64(WxEncryptedData);
		byte[] keyByte = Base64.decodeBase64(WxSessionKey);
		byte[] ivByte = Base64.decodeBase64(WxIv);
		byte[] userInfoByte = pc.decrypt(content, keyByte, ivByte);
		if (null != userInfoByte && userInfoByte.length > 0) {
			try {
				String userInfo = new String(userInfoByte, "UTF-8");
				Gson gson = new Gson();
				wxAppUserInfo = gson.fromJson(userInfo, WXAppUserInfo.class);
			} catch (UnsupportedEncodingException e) {
				logger.error("",e.getCause());  
			}
		}
		
		return wxAppUserInfo;
    }
    
    private String generateTokenByAccountId(String accountId, Device device){
    	String token = "";
    	final JwtUser jwtUser = userDetailsService.loadUserByAccountId(accountId);
        token = jwtTokenUtil.generateToken(jwtUser, device);
        
        return token;
    }

}
