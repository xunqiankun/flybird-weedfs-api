package wang.flybird.aop;

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

import wang.flybird.annotation.SysLogAnnotation;
import wang.flybird.api.security.JwtTokenUtil;
import wang.flybird.entity.SysLog;
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
	
	@Pointcut("@annotation(wang.flybird.annotation.SysLogAnnotation)")
//	@Pointcut("execution(* com.zkn.learnspringboot.web.controller..*.*(..))")
	public void logPointCut() { 
		
	}
	
	@Before("logPointCut()")
	public void saveSysLog(JoinPoint joinPoint) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		
		SysLog sysLog = new SysLog();
		SysLogAnnotation sysLogAnnotation = method.getAnnotation(SysLogAnnotation.class);
		if(sysLogAnnotation != null){
			//注解上的描述 
			sysLog.setOperation(sysLogAnnotation.value());
		}
		
		//请求的方法名
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = signature.getName();
		sysLog.setMethod(className + "." + methodName + "()");
		
		//请求的参数
		Object[] args = joinPoint.getArgs();
		String params = JSON.toJSONString(args[0]);
		sysLog.setParams(params);
		
		//获取request
		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
		//设置IP地址
		sysLog.setIp(IPUtils.getIpAddr(request));
		
		//用户名
		Cookie cookie = CookieUtil.getCookie(request, this.tokenHeader);
    	String authToken = "";
    	if(cookie != null){
    		authToken = cookie.getValue();	
    	}
    	String username = jwtTokenUtil.getUsernameFromToken(authToken);
		
		sysLog.setUsername(username);
		
		sysLog.setCreateDate(new Date());
		sysLog.setId(String.valueOf(idWorker.getId()));
		
		//保存系统日志
		sysLogRepository.save(sysLog);
	}
	
}
