package com.xyauto.interact.broker.server.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "pic.url")
public class Constants {

	// 上传图片大小限制
	public static final long PICTURE_LIMIT_SIZE = 8 * 1024 * 1024; // MB
	// 上传图片压缩长度
	public static final int PICTURE_PC_HEIGHT = 1000; // px
	// 上传图片压缩宽度
	public static final int PICTURE_PC_WIDTH = 300; // px
	// 上传图片压缩长度
	public static final int PICTURE_MOBILE_HEIGHT = 400; // px
	// 上传图片压缩宽度
	public static final int PICTURE_MOBILE_WIDTH = 200; // px
	// 上传图片类型
	public static final String[] PICTURE_TYPE = { "jpg", "png", "jpeg", "bmp",
			"gif" }; // px

	// 上传文件分组名
	public static String fileGroupName = "group1";
	// 默认值
	public static long zero = 0;

	private String avatarGroup1Root;
	private String avatarGroup2Root;

	public String getAvatarGroup1Root() {
		return avatarGroup1Root;
	}

	public void setAvatarGroup1Root(String avatarGroup1Root) {
		this.avatarGroup1Root = avatarGroup1Root;
	}

	public String getAvatarGroup2Root() {
		return avatarGroup2Root;
	}

	public void setAvatarGroup2Root(String avatarGroup2Root) {
		this.avatarGroup2Root = avatarGroup2Root;
	}

}
