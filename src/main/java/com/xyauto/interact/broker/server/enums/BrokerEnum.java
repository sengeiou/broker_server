package com.xyauto.interact.broker.server.enums;

public class BrokerEnum {
	public static final String website="http://h5.qichedaquan.com/weidian/home/index.html?agentId=%s&dealerId=%s";
	
	/**
	 * 行圆慧平台用户身份变更成经纪人身份值
	 * @param type
	 * @return
	 */
	public static short chageBrokerType(short type){
		if (type==1) {
			type=BrokerTypeEnum.MarketManager.getValue();
		}else if(type==2){
			type=BrokerTypeEnum.Employee.getValue();
		}else if (type==4) {
			type=BrokerTypeEnum.Manager.getValue();
		}
		return type;
	}
}
