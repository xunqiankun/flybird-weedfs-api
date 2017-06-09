package wang.flybird.api.userMsg.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import wang.flybird.api.security.JwtTokenUtil;
import wang.flybird.api.userMsg.entity.UserMsg;
import wang.flybird.api.userMsg.repository.UserMsgRepository;
import wang.flybird.config.annotation.SysLogAnnotation;
import wang.flybird.entity.FbMessage;
import wang.flybird.entity.custom.R;
import wang.flybird.utils.idwoker.IdWorker;

@RestController
@RequestMapping(path = "/api/usermsg")
@Api(value="用户消息接口")
public class UserMsgController {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Value("${jwt.header}")
    private String tokenHeader;
	
	@Autowired
    private IdWorker idWorker;
	
	@Autowired
    private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private UserMsgRepository userMsgRepository;
	
//	@SysLogAnnotation("获取用户最新MsgId")
	@RequestMapping(path = "/getUserMsgLastId", method = RequestMethod.POST)
	@ApiOperation(value = "获取用户最新MsgId",notes = "")
	@ApiImplicitParams({
	   @ApiImplicitParam(name = "authtoken", value = "token", required = true,paramType = "header"),
	 })
	public R getUserMsgLastId(HttpServletRequest request){
		String userMsgLastId = "";
		
//		String token = request.getHeader(tokenHeader);
		userMsgLastId = idWorker.getStrId();
		
		
		return R.ok().put("userMsgLastId", userMsgLastId);
	}
	
	@SysLogAnnotation("获取用户最新Msg")
	@RequestMapping(path = "/getUserLastMsg", method = RequestMethod.POST)
	@ApiOperation(value = "获取用户最新Msg",notes = "")
	@ApiImplicitParams({
	   @ApiImplicitParam(name = "authtoken", value = "token", required = true,paramType = "header"),
	   @ApiImplicitParam(name="userMsgLastId",value="客户端最新MsgId ",required=true,dataType="String",paramType="query"),
	 })
	public R getUserLastMsg(String userMsgLastId,HttpServletRequest request){
		
		String authToken = request.getHeader(tokenHeader);
		String userid = jwtTokenUtil.getUseridFromToken(authToken);
		List<UserMsg> userMsgList = userMsgRepository.findByIdAndReceiverid(userMsgLastId,userid);
				
		return R.ok().put("userMsgList", userMsgList);
	}
	
	

}
