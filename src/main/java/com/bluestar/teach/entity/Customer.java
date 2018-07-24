package com.bluestar.teach.entity;

import javax.validation.constraints.Pattern;

/**
 * 客户类，对应customer表
 * @author happyChicken
 *
 */
public class Customer  {
	
	// id
	private Integer infoId;
	
	// 身份证
	@Pattern(regexp = "(^[A-Z0-9]{18}$)",
	   		 message = "身份证长度必须是18位")
	private String identityNum;
	
	// 学校
	private String school;
	
	// 专业年级
	private String gradeMajor;
	
	// qq
	@Pattern(regexp = "(^[0-9]{6,12}$)",
	   		 message = "输入的QQ格式不合法")
	private String qq;
	
	// 电话号码
	@Pattern(regexp = "(^[0-9]{7,11}$)",
	   		 message = "输入的电话格式不合法")
	private String telephone;
	
	// 邮箱
	@Pattern(regexp = "^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$",
	   		 message = "邮箱不合法")
	private String email;

	//城市
	private String city;
	
	//省份
	private String province;

	public Integer getInfoId() {
		return infoId;
	}

	public void setInfoId(Integer infoId) {
		this.infoId = infoId;
	}

	public String getIdentityNum() {
		return identityNum;
	}

	public void setIdentityNum(String identityNum) {
		this.identityNum = identityNum;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getGradeMajor() {
		return gradeMajor;
	}

	public void setGradeMajor(String gradeMajor) {
		this.gradeMajor = gradeMajor;
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	@Override
	public String toString() {
		return "Customer [infoId=" + infoId + ", identityNum=" + identityNum + ", school=" + school + ", gradeMajor="
				+ gradeMajor + ", qq=" + qq + ", telephone=" + telephone + ", email=" + email + ", city=" + city
				+ ", province=" + province + "]";
	}

	

	


	
}
