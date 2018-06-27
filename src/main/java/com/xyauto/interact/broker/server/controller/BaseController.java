package com.xyauto.interact.broker.server.controller;

import com.mcp.fastcloud.util.exception.base.BaseException;
import com.mcp.validate.BindResult;
import com.mcp.validate.exception.ValidateException;
import com.xyauto.interact.broker.server.enums.ResultCode;
import com.xyauto.interact.broker.server.util.ILogger;
import com.xyauto.interact.broker.server.util.Result;

import org.apache.commons.lang.exception.ExceptionUtils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.http.MediaType.TEXT_PLAIN;

public class BaseController implements ILogger {

    @Autowired
    protected Result result;

    @ExceptionHandler({Exception.class})
    public Object handleException(HttpServletRequest req, Exception ex) {
        Throwable e = ExceptionUtils.getRootCause(ex);
        if (null != e) {
            ex = (Exception) e;
        }
        if (BaseException.class.isAssignableFrom(ex.getClass())) {
            BaseException baseException = (BaseException) ex;
            switch (baseException.getLevel()) {
                case INFO:
                    this.info(baseException.getCode().toString());
                    break;
                case WARN:
                    this.warn(baseException.getCode().toString());
                    break;
                case DEBUG:
                    this.debug(baseException.getCode().toString());
                    break;
                case ERROR:
                    this.error(baseException.getCode().toString());
                    break;
                default:
                    this.info(baseException.getCode().toString());
            }
            result = result.format(baseException.getCode().getCode(), baseException.getMessage());
        } else if (ex.getClass().isAssignableFrom(MissingServletRequestParameterException.class)) {
            MissingServletRequestParameterException msrpException = (MissingServletRequestParameterException) ex;
            this.warn("请求:" + req.getRequestURI() + " 缺少参数：" + msrpException.getParameterName());
            result = result.format(ResultCode.ERROR_PARAMS);
        } else if (ex.getClass().isAssignableFrom(ValidateException.class)) {
            ValidateException validateException = (ValidateException) ex;
            BindResult bindResult = validateException.getBindResult();
            result = result.format(ResultCode.UnKnownError, bindResult.getMessage());
        } else if (ex.getClass().isAssignableFrom(NumberFormatException.class)) {
            this.warn("请求:" + req.getRequestURI() + " 参数类型错误");
            this.error(ExceptionUtils.getMessage(ex));
            result = result.format(ResultCode.UnKnownError, "传入的数据类型有误");
        } else {
            this.error(ExceptionUtils.getMessage(ex));
            result = result.format(ResultCode.UnKnownError, ex.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(TEXT_PLAIN);
        headers.setContentType(APPLICATION_JSON_UTF8);
        return new ResponseEntity(result, headers, HttpStatus.OK);
    }
}