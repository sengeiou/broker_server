package com.xyauto.interact.broker.server.enums;

public enum  TemplateTypeEnum {
    Message((short) 1, "短信"),
    ChatTemplate((short) 2, "微聊"),
    Introduction((short) 3, "标题导语");


    private final int code;

    public int getCode() {
        return this.code;
    }
    private final String message;

    public String getMessage() {
        return this.message;
    }

    private TemplateTypeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
