package com.bluestar.teach.enums.impl;

import com.bluestar.teach.enums.Statusable;

public enum UpdateAccountStatus implements Statusable {
	 SUCCESS(0, "更新成功！"),
	 CORE_INFO_IS_NULL(-1, "用户名或密码为空！"),
	 INFO_IS_NOT_COMPLETED(-2, "用户信息不完整！"),
	 USER_IS_NULL(-3, "用户对象为空！"),
	 USERNAME_EXISTED(-4, "用户名已经存在！"),
	 UNKNOWN_ERROR(-5, "未知错误！"),
	 UPDATE_ERROR(-6,"更新失败,请重试");
	 // 创建状态码
	 private int code;

	 // 创建信息
	 private String info = null;

	 
	 private UpdateAccountStatus() {}

	private UpdateAccountStatus(int code, String info) {
			this.code = code;
			this.info = info;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	
}
