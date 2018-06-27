package com.xyauto.interact.broker.server.exceptions;

import com.xyauto.interact.broker.server.enums.ResultCode;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class ResultException extends Exception {

    private ResultCode result;

    public ResultException() {
        super();
    }

    public ResultException(ResultCode ret) {
        super(ret.getMessage());
        this.result = ret;
    }
    
    public ResultException(ResultCode ret, String message) {
        super(message);
        this.result = ret;
    }

    public ResultCode getResult() {
        return result;
    }

    public void setResult(ResultCode result) {
        this.result = result;
    }
}
