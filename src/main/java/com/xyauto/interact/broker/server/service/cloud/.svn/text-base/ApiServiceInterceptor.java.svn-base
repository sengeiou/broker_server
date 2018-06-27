package com.xyauto.interact.broker.server.service.cloud;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class ApiServiceInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate input) {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        if (ra != null) {
            ServletRequestAttributes sra = (ServletRequestAttributes) ra;
            HttpServletRequest request = sra.getRequest();
            if (request != null) {
                if (request.getSession().getAttribute("version") != null) {
                    input.query(true, "version", request.getSession().getAttribute("version").toString());
                }
                if (request.getSession().getAttribute("version") != null) {
                    input.query(true, "app_id", request.getSession().getAttribute("appid").toString());
                }
            }
        }
    }
}