package com.bluestar.teach.entity;
/**
 * 用户所属城市类，对应city表
 * @author happyChicken
 *
 */
public class City {
	
	// 城市id
	private Integer cityId;
	
	// 省份id
	private Integer provinceId;
	
	// 城市名
	private String city;

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public Integer getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return "City [cityId=" + cityId + ", provinceId=" + provinceId + ", city=" + city + "]";
	}
}
