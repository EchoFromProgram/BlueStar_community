package com.bluestar.teach.entity;
/**
 * 用户所属省份类，对应province表
 * @author happyChicken
 *
 */
public class Province {
	
	// 省份id
	private Integer id;
	
	// 省份
	private String name;

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

	@Override
	public String toString() {
		return "Province [id=" + id + ", name=" + name + "]";
	}


}
