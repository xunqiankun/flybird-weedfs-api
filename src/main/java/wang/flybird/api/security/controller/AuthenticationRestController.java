package wang.flybird.api.security.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;
import wang.flybird.annotation.SysLogAnnotation;
import wang.flybird.api.security.JwtAuthenticationRequest;
import wang.flybird.api.security.JwtTokenUtil;
import wang.flybird.api.security.JwtUser;
import wang.flybird.api.security.service.JwtAuthenticationResponse;
import wang.flybird.utils.net.CookieUtil;

@RestController
@RequestMapping(path = "/api/authentication")
@Api("权限认证接口")
public class AuthenticationRestController {

    @Value("${jwt.header}")
    private String tokenHeader;
    
    @Value("${jwt.expiration}")
    private int expiration;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

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
            final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
            token = jwtTokenUtil.generateToken(userDetails, device);
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
        
        
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);

        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
            String refreshedToken = jwtTokenUtil.refreshToken(token);
            CookieUtil.setCookie(response, this.tokenHeader, token, expiration);
            return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

}
