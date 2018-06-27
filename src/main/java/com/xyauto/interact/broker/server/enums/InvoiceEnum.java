package com.xyauto.interact.broker.server.enums;

import java.util.HashMap;
import java.util.Map;


public class InvoiceEnum {
	public enum InvoiceStatus{
		success(1,"已经审核"),
		init(0,"等待审核"),
		faile(-1,"驳回"),
		NoInvoice(-2,"未上传发票");	
		private int code;
		private String name;
		private InvoiceStatus(int code,String name){
			this.code=code;
			this.name=name;
		}
		public int getCode() {
			return code;
		}
		public void setCode(int code) {
			this.code = code;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}		
		public  static Map<Integer, String> resultMap=InvoiceStatus.getMap();
		public  static Map<Integer, String> getMap(){
			Map<Integer, String> map=new HashMap<Integer, String>();
			for (InvoiceStatus item : InvoiceStatus.values()) {
				map.put(item.getCode(), item.getName());
			}
			return map;		
		}
	}
	
	
}
