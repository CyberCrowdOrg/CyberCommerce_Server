package org.cybercrowd.mvp.annotation.aspectj;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import org.cybercrowd.mvp.annotation.LogV2;
import org.cybercrowd.mvp.dto.SysLog;
import org.cybercrowd.mvp.util.IPUtils;
import org.cybercrowd.mvp.util.SpringContextUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

@Aspect
@Component
public class LogV2Aspectj {

	private static final Logger log = LoggerFactory.getLogger(LogV2Aspectj.class);

	private static ThreadLocal<Date> startTime = new ThreadLocal<Date>();

	@Pointcut("@annotation(org.cybercrowd.mvp.annotation.LogV2)")
	public void logPointCut() {
	}

	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		long beginTime = System.currentTimeMillis();
		long time = System.currentTimeMillis() - beginTime;
		log.info("=====请求操作前==========>");
		SysLog saveLog = saveLog(null, point, time);
		// 执行方法
		Object result = point.proceed();
		// 执行时长(毫秒)
		time = System.currentTimeMillis() - beginTime;
		// 异步保存日志
		log.info("=====请求操作后==========>");
		saveLog(saveLog, point, time);
		log.info("返回结果：{}", JSONUtil.toJsonStr(result));

		return result;
	}

	SysLog saveLog(SysLog sysLog, ProceedingJoinPoint joinPoint, long time) throws InterruptedException {

		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();

		if (null == sysLog) {
			sysLog = new SysLog();
			LogV2 logV2 = method.getAnnotation(LogV2.class);

			// 注解上的描述,操作日志内容
			sysLog.setLogContent(logV2.value());
			// 请求的方法名
			String className = joinPoint.getTarget().getClass().getName();
			String methodName = signature.getName();
			sysLog.setMethod(className + "." + methodName + "()");

			// 获取request
			HttpServletRequest request = SpringContextUtils.getHttpServletRequest();

			// 请求方式
			String contentType = request.getHeader("content-type");
			sysLog.setRequestType(contentType);

			// 请求参数
			String params = getRequestParam(contentType, joinPoint, request);
			sysLog.setRequestParam(params);

			// 设置IP地址
			sysLog.setIp(IPUtils.getIpAddr(request));
			sysLog.setRequestUrl(request.getRequestURI());

			// 耗时
			sysLog.setCostTime(time);
			sysLog.setCreateTime(new Date());
		}

		log.info("======================>");
		log.info("请求内容类型：{}", sysLog.getRequestType());
		log.info("请求ip：{}", sysLog.getIp());
		log.info("请求描述：{}", sysLog.getLogContent());
		log.info("请求方法：{}", sysLog.getMethod());
		log.info("请求Url：{}", sysLog.getRequestUrl());
		log.info("请求参数：{}", sysLog.getRequestParam());

		log.info("请求响应时间：{} ms", sysLog.getCostTime());

		log.info("请求记录时间：{}", sysLog.getCreateTime().toLocaleString());
		log.info("======================>");

		// 保存系统日志
		// sysLogService.save(sysLog);

		return sysLog;

	}

	/**
	 * 拦截异常操作
	 * 
	 * @param joinPoint 切点
	 * @param e         异常
	 */
	@AfterThrowing(value = "logPointCut()", throwing = "e")
	public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
		handleExceptionLog(joinPoint, e, null);
	}

	void handleExceptionLog(JoinPoint joinPoint, Exception e, Object jsonResult) {

		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		SysLog sysLog = new SysLog();
		LogV2 logV2 = method.getAnnotation(LogV2.class);
		if (logV2 != null) {
			// 注解上的描述,操作日志内容
			sysLog.setLogContent(logV2.value());
		}

		// 请求的方法名
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = signature.getName();
		sysLog.setMethod(className + "." + methodName + "()");

		// 获取request
		HttpServletRequest request = SpringContextUtils.getHttpServletRequest();

		// 请求方式
		String contentType = request.getHeader("content-type");
		sysLog.setRequestType(contentType);

		// 请求参数
		String params = getRequestParam(contentType, joinPoint, request);
		sysLog.setRequestParam(params);

		// 设置IP地址
		sysLog.setIp(IPUtils.getIpAddr(request));
		sysLog.setRequestUrl(request.getRequestURI());

		// 耗时
		sysLog.setCreateTime(new Date());

		log.info("======请求异常拦截==========>");
		log.info("请求内容类型：{}", sysLog.getRequestType());
		log.info("请求ip：{}", sysLog.getIp());
		log.info("请求描述：{}", sysLog.getLogContent());
		log.info("请求方法：{}", sysLog.getMethod());
		log.info("请求Url：{}", sysLog.getRequestUrl());
		log.info("请求参数：{}", sysLog.getRequestParam());
		log.info("请求响应时间：{} ms", sysLog.getCostTime());
		log.info("请求记录时间：{}", sysLog.getCreateTime().toLocaleString());
		log.info("拦截到的异常：{}", e.getMessage());
		log.info("======================>");
		// 保存系统日志
		// sysLogService.save(sysLog);

	}

	/**
	 * 获取请求参数
	 * 
	 * @param contentType
	 * @param joinPoint
	 * @param request
	 * @return
	 */
	private String getRequestParam(String contentType, JoinPoint joinPoint, HttpServletRequest request) {

		String params = null;
		if (StrUtil.isBlank(contentType) || !contentType.contains("json")) {
			Map<String, String[]> map = request.getParameterMap();
			params = JSONUtil.toJsonStr(map);
		} else {
			Object[] args = joinPoint.getArgs();
			try {
				params = JSONObject.toJSONString(args);
			} catch (Exception e) {
				log.error("获取请求参数错误");
				params = "获取请求参数错误";
			}
		}
		return params;
	}

	/**
	 * 处理完请求后执行
	 *
	 * @param joinPoint 切点
	 */
	// @AfterReturning(pointcut = "logPointCut()", returning = "jsonResult")
	public void doAfterReturning(JoinPoint joinPoint, Object jsonResult) {
		handleAfterReturningLog(joinPoint, null, jsonResult);
	}

	private void handleAfterReturningLog(JoinPoint joinPoint, Object object, Object jsonResult) {

		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		SysLog sysLog = new SysLog();
		LogV2 logV2 = method.getAnnotation(LogV2.class);
		if (logV2 != null) {
			// 注解上的描述,操作日志内容
			sysLog.setLogContent(logV2.value());
		}

		// 请求的方法名
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = signature.getName();
		sysLog.setMethod(className + "." + methodName + "()");

		// 获取request
		HttpServletRequest request = SpringContextUtils.getHttpServletRequest();

		// 请求方式
		String contentType = request.getHeader("content-type");
		sysLog.setRequestType(contentType);

		// 请求参数
		String params = getRequestParam(contentType, joinPoint, request);
		sysLog.setRequestParam(params);

		// 设置IP地址
		sysLog.setIp(IPUtils.getIpAddr(request));
		sysLog.setRequestUrl(request.getRequestURI());

		// 耗时
		sysLog.setCreateTime(startTime.get());
		startTime.remove();

		log.info("======请求结果返回拦截==========>");
		log.info("请求内容类型：{}", sysLog.getRequestType());
		log.info("请求ip：{}", sysLog.getIp());
		log.info("请求描述：{}", sysLog.getLogContent());
		log.info("请求方法：{}", sysLog.getMethod());
		log.info("请求Url：{}", sysLog.getRequestUrl());
		log.info("请求参数：{}", sysLog.getRequestParam());
		log.info("请求响应时间：{} ms", sysLog.getCostTime());
		log.info("请求记录时间：{}", sysLog.getCreateTime().toLocaleString());
		log.info("请求返回结果：{}", jsonResult);
		log.info("======================>");
		// 保存系统日志
		// sysLogService.save(sysLog);

	}

	/*无异常运行的顺序是
	
	1、@Around
	
	2、@Before
	
	3、原方法
	
	4、@Around
	
	5、@After
	
	6、@AfterReturning
	
	当发生异常的时候@Around的方法执行后切入没有进行，但是@After的方法却执行了，所以原方法发生异常后顺序就是
	
	1、@Around
	
	2、@Brfore
	
	3、原方法
	
	4、@After
	
	5、@AfterThrowing*/

	// 方法执行开始之前
	// @Before("pointCut()")
	public void logStart(JoinPoint joinPoint) {
		System.out.println("方法执行开始之前");
	}

	// 方法执行开始之后
	// @After("pointCut()")
	public void logEnd(JoinPoint joinPoint) {
		System.out.println("方法执行开始之后");
	}

	// 当方法进行返回的时候，returning属性是指定方法参数中的result来接收返回参数，这样就可以修改返回参数
	// @AfterReturning(value = "pointCut()", returning = "result")
	public void logReturn(JoinPoint joinPoint, Object result) {
		System.out.println("除当方法进行返回的时候，returning属性是指定方法参数中的result来接收返回参数，这样就可以修改返回参数");
	}

	// 当方法执行异常的时候，throwding是指定方法参数中的e来接收异常参数，可以查看发生的什么异常
	// @AfterThrowing(value = "pointCut()", throwing = "e")
	public void logException(JoinPoint joinPoint, Exception e) {
		System.out.println("当方法执行异常的时候，throwding是指定方法参数中的e来接收异常参数，可以查看发生的什么异常");
	}

}
