package com.xyauto.interact.broker.server.util;

import com.xyauto.interact.broker.server.enums.ResultCode;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Result {

    /**
     * @return the code
     */
    public int getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * @return the msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @param msg the msg to set
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * @return the data
     */
    public Object getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(Object data) {
        this.data = data;
    }

    private int code;
    private int sub_code;
    private String msg;
    private Object data;

    public Result format(int code, String message) {
        this.code = code;
        this.msg = message;
        this.data = null;
        return this;
    }
    
    public Result format(ResultCode e, String message) {
        this.code = e.getCode();
        this.msg = message;
        this.data = null;
        return this;
    }

    public Result format(ResultCode e, Object data) {
        this.code = e.getCode();
        this.msg = e.getMessage();
        this.data = data;
        return this;
    }

    public Result format(ResultCode e) {
        this.code = e.getCode();
        this.msg = e.getMessage();
        this.data = null;
        return this;
    }

    /**
     * @return the sub_code
     */
    public int getSub_code() {
        return sub_code;
    }

    /**
     * @param sub_code the sub_code to set
     */
    public void setSub_code(int sub_code) {
        this.sub_code = sub_code;
    }

}
