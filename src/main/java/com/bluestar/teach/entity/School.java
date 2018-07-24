package com.bluestar.teach.entity;
/**
 * 学校类，对应school表
 * @author happyChicken
 *
 */
public class School {
	
	//学校id
	private Integer schoolId;
	
	//城市id
	private Integer cityId;
	
	//学校名
	private String school;

	public Integer getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Integer schoolId) {
		this.schoolId = schoolId;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	@Override
	public String toString() {
		return "School [schoolId=" + schoolId + ", cityId=" + cityId + ", school=" + school + "]";
	}
}
