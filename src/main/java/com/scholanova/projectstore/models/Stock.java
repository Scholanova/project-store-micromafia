package com.scholanova.projectstore.models;

public class Stock {
	private Integer id;
	private String name;
	private String type;
	private Integer value;
	private Integer id_store;
	
	public Stock(Integer id, String name, String type, Integer value, Integer id_store) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.value = value;
		this.id_store = id_store;
	}
	public Integer getId_store() {
		return id_store;
	}
	public void setId_store(Integer id_store) {
		this.id_store = id_store;
	}
	
	public Stock() {
		
	}
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}
