package com.xyauto.interact.broker.server.enums;

import java.util.HashMap;
import java.util.Map;


public class BlockEnum {
	public enum BlockName{
		home_banner("home.banner","首页"),
		app_version("app.version","app版本号信息");
		private String name;
		private String desc;
		private BlockName(String name,String desc){
			this.name=name;
		}
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}			
		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

		public  static Map<String, String> resultMap=BlockName.getMap();
		public  static Map<String, String> getMap(){
			Map<String, String> map=new HashMap<String, String>();
			for (BlockName item : BlockName.values()) {
				map.put(item.getName(), item.getDesc());
			}
			return map;		
		}
	}
}
