package com.xyauto.interact.broker.server.interceptor;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(-99)
public class DealerExistInterceptor {

    @Pointcut("@annotation(com.xyauto.interact.broker.server.annotation.DealerExist)")
    public void point() {
    }

    @Before("point()")
    public void process(JoinPoint point) {

    }
}
