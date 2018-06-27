package com.xyauto.interact.broker.server.enums;

public enum ResultCode {
    Success(10000, "成功"),
    AppIdRequire(10001, "缺少app_id参数"),
    AppIdError(10002, "app_id校验失败"),
    ERROR_PARAMS(10003, "参数错误"),
    BrokerNotFound(20001, "经纪人不存在"),
    TargetBrokerNotFound(20002, "当前登录经纪人不存在"),
    NoPermissionByBroker(20003, "不允许跨经销商操作"),
    TargetBrokerStoreNotFound(20004, "当前登录经微店不存在"),
    DealerNotFound(30001, "经销商不存在"),
    CustomerNotFound(40001, "客户不存在"),
    CustomerExists(40002, "已存在相同客户"),
    NoUpdateTemplate(50001, "系统短信模版不允许删除和更改"),
    NoMoreTemplate(50002, "微聊模版至少保留一条"),
    MustName(50003, "短信name参数为必须"),
    BlockNotFound(50004, "块数据不存在"),
    BrokerReceiptNotFound(60001, "未找到发票信息"),
    BrokerClueNotFound(70001, "线索不存在"),
    BokerCluePickUpError(70002, "线索认领失败"),
    BrokerClueDistributeError(70003, "线索分配失败"),
    BrokerClueHandleError(70004, "线索处理失败"),
    NoPermission(99998, "权限不足"),
    UnKnownError(99999, "操作失败"),
    ServiceDown(99997, "服务不可用");

    private final int code;

    public int getCode() {
        return this.code;
    }
    private final String message;

    public String getMessage() {
        return this.message;
    }

    private ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
