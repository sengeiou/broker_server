package com.xyauto.interact.broker.server.config;

import java.util.Objects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.xyauto.interact.broker.server.util.FastJsonUtils;

@Component
@Aspect
public class LogAspect {
	private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

	/**
	 * 定义一个切入点. 解释下：
	 *
	 * ~ 第一个 * 代表任意修饰符及任意返回值. ~ 第二个 * 定义在web包或者子包 ~ 第三个 * 任意方法 ~ .. 匹配任意数量的参数.
	 */
	@Pointcut("execution(* com.xyauto.interact.broker.server.service..*.*(..))")
	public void logPointcut() {
	}

	@org.aspectj.lang.annotation.Around("logPointcut()")
	public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
		long start = System.currentTimeMillis();
		try {
			Object result = joinPoint.proceed();
			long end = System.currentTimeMillis();
			logger.info("【调用方法】 " + joinPoint + "\t【耗时】 : " + (end - start) + " ms!");
			return result;

		} catch (Throwable e) {
			long end = System.currentTimeMillis();
			logger.debug("【调用方法】 " + joinPoint + "\t【耗时】 : " + (end - start) + " ms with exception : " + e.getMessage());
			throw e;
		}
	}

	@Before("execution(* com.xyauto.interact.broker.server.controller..*.*(..))")
	public void permissionCheck(JoinPoint point) {
		logger.info("请求:【{}】", getMethodInfo(point));
	}

	@AfterReturning(pointcut = "execution(* com.xyauto.interact.broker.server.controller..*.*(..))", returning = "returnValue")
	public void log(JoinPoint point, Object returnValue) {
		logger.info("请求:【{}】 ,返回值:{}", getMethodInfo(point), FastJsonUtils.toJSONString(returnValue));
	}

	private String getMethodInfo(JoinPoint point) {
		String className = point.getSignature().getDeclaringType().getSimpleName();
		String methodName = point.getSignature().getName();
		String[] parameterNames = ((MethodSignature) point.getSignature()).getParameterNames();
		StringBuilder sb = null;
		if (Objects.nonNull(parameterNames)) {
			sb = new StringBuilder();
			for (int i = 0; i < parameterNames.length; i++) {
				String value = point.getArgs()[i] != null ? point.getArgs()[i].toString() : "null";
				sb.append(parameterNames[i] + "=" + value + "&");
			}
		}
		sb = sb == null ? new StringBuilder() : sb;
		String info = String.format("class:%s/method:%s?%s", className, methodName, sb.toString());
		return info;
	}
}