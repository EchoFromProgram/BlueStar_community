package com.bluestar.teach.enums.impl;

import com.bluestar.teach.enums.Statusable;

public enum ScoreStatus implements Statusable {
	
	UPDATE_ERROR(-1,"修改成绩失败！"),
	DELTE_ERROR(-2,"删除成绩失败！"),
	INSERT_ERROR(-3,"添加成绩成功！");
	
	private ScoreStatus(int code, String info) {
		this.code = code;
		this.info = info;
	}

	//状态码
	private int code;
	
	//处理结果信息
	private String info;

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
