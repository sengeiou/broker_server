package com.xyauto.interact.broker.server.interceptor;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

@ControllerAdvice(basePackages = "com.xyauto.interact.broker.server.controller")  
public class JsonpAdvice extends AbstractJsonpResponseBodyAdvice {  
    public JsonpAdvice() {  
        super("callback", "jsonp");  
    }
}