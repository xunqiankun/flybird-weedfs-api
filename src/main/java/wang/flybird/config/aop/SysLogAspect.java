package wang.flybird.config.aop;

import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import wang.flybird.api.security.JwtTokenUtil;
import wang.flybird.config.annotation.SysLogAnnotation;
import wang.flybird.entity.FbOptLog;
import wang.flybird.entity.repository.SysLogRepository;
import wang.flybird.utils.idwoker.IdWorker;
import wang.flybird.utils.net.CookieUtil;
import wang.flybird.utils.net.HttpContextUtils;
import wang.flybird.utils.net.IPUtils;


/**
 * 系统日志，切面处理类
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017年3月8日 上午11:07:35
 */
@Aspect
@Component
public class SysLogAspect {
	
	@Value("${jwt.header}")
    private String tokenHeader;
	
	@Autowired
    private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private SysLogRepository sysLogRepository;
	
	@Autowired
	private IdWorker idWorker;
	
	@Pointcut("@annotation(wang.flybird.config.annotation.SysLogAnnotation)")
//	@Pointcut("execution(* com.zkn.learnspringboot.web.controller..*.*(..))")
	public void logPointCut() { 
		
	}
	
	@Before("logPointCut()")
	public void saveSysLog(JoinPoint joinPoint) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		
		FbOptLog fbOptLog = new FbOptLog();
		SysLogAnnotation sysLogAnnotation = method.getAnnotation(SysLogAnnotation.class);
		if(sysLogAnnotation != null){
			//注解上的描述 
			fbOptLog.setOperation(sysLogAnnotation.value());
		}
		
		//请求的方法名
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = signature.getName();
		fbOptLog.setMethod(className + "." + methodName + "()");
		
		//请求的参数
		Object[] args = joinPoint.getArgs();
		String params = JSON.toJSONString(args[0]);
		fbOptLog.setParams(params);
		
		//获取request
		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
		//设置IP地址
		fbOptLog.setIp(IPUtils.getIpAddr(request));
		
		//用户名
		Cookie cookie = CookieUtil.getCookie(request, this.tokenHeader);
    	String authToken = "";
    	if(cookie != null){
    		authToken = cookie.getValue();	
    	}
    	String username = jwtTokenUtil.getUsernameFromToken(authToken);
		
    	fbOptLog.setUsername(username);
		
    	fbOptLog.setCreateDate(new Date());
    	fbOptLog.setId(idWorker.getStrId());
		
		//保存系统日志
		sysLogRepository.save(fbOptLog);
	}
	
}
