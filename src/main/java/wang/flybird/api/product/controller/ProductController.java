package wang.flybird.api.product.controller;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;
import wang.flybird.api.security.JwtAuthenticationRequest;
import wang.flybird.config.annotation.SysLogAnnotation;
import wang.flybird.entity.custom.R;

@RestController
@RequestMapping(path = "/api/product")
@Api(value="用户产品信息接口")
public class ProductController {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
   	@SysLogAnnotation("新建产品信息")
    @RequestMapping(value = "/addproduct", method = RequestMethod.POST)
    @ApiOperation(value = "新建产品信息",notes = "")
	@ApiImplicitParams({
	   @ApiImplicitParam(name="authenticationRequest",value="认证信息",required=true,dataType="JwtAuthenticationRequest",paramType="body"),
	 })
    public R createAuthenticationToken(
    		@RequestBody JwtAuthenticationRequest authenticationRequest, 
    		HttpServletResponse response,
    		@ApiIgnore Device device) throws AuthenticationException {
         
   		return R.ok().put("userMsgList", "");
    }					

}
