package com.xyauto.interact.broker.server.interceptor;

import com.google.common.collect.Lists;
import com.xyauto.interact.broker.server.annotation.ExcludeZero;
import com.xyauto.interact.broker.server.util.ILogger;
import com.xyauto.interact.broker.server.util.ListUtil;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collection;
import java.util.List;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(100)
public class ExincludeZeroInterceptor implements ILogger {

    @Pointcut("execution(* com.xyauto.interact.broker.server.controller..*(..)))")
    public void point() {}

    @Around("point()")
    public Object process(ProceedingJoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        Parameter[] parameters = method.getParameters();
        Object[] args = pjp.getArgs();
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            if (parameter.isAnnotationPresent(ExcludeZero.class)) {
                if(args[i]!=null){
                    if (args[i] instanceof Collection) {
                        args[i] = ListUtil.clearZero((List) args[i]);
                    }
                }else{
                    args[i] = Lists.newArrayList();
                }
            }
        }
        Object retVal = null;
        try {
            retVal = pjp.proceed(args);
        } catch (Throwable ex) {
            this.error(ex.getMessage());
        }
        return retVal;
    }
}
