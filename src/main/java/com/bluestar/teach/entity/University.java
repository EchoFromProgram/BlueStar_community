package com.bluestar.teach.entity;

public class University {

	// 大学id
	private Integer id;
	
	//大学名称
	private String name;
	
	//省份id
	private Integer provinceId;
	
	private String level;
	
	private String website;
	
	private String abbreviation;
	
	private String city;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return "University [id=" + id + ", name=" + name + ", provinceId=" + provinceId + ", level=" + level
				+ ", website=" + website + ", abbreviation=" + abbreviation + ", city=" + city + "]";
	}


	
	
}
