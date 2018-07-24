package com.bluestar.teach.entity;

import javax.validation.constraints.Pattern;

/**
 * 员工类，对应staff表
 * @author happyChicken
 *
 */
public class Staff {
	
	//id
	private Integer tId;
	
	//身份证
	@Pattern(regexp = "(^[A-Z0-9]{18}$)",
   		 message = "身份证长度必须是18位")
	private String identityNum;
	
	//工作履历
	@Pattern(regexp = "(^.*{2000}$)",
	   		 message = "履历的最大长度为2000")
	private String resume;
	
	//QQ
	@Pattern(regexp = "(^[0-9]{6,12}$)",
	   		 message = "输入的QQ格式不合法")
	private String qq;
	
	//电话
	@Pattern(regexp = "(^[0-9]{7,11}$)",
	   		 message = "输入的电话格式不合法")
	private String telephone;
	
	//邮箱
	@Pattern(regexp = "^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$",
   		 message = "邮箱不合法")
	private String email;

	public Integer gettId() {
		return tId;
	}

	public void settId(Integer tId) {
		this.tId = tId;
	}

	public String getIdentityNum() {
		return identityNum;
	}

	public void setIdentityNum(String identityNum) {
		this.identityNum = identityNum;
	}

	public String getResume() {
		return resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Staff [tId=" + tId + ", identityNum=" + identityNum + ", resume=" + resume + ", qq=" + qq
				+ ", telephone=" + telephone + ", email=" + email + "]";
	}

	
}
