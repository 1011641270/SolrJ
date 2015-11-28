package com.tian.solr.model;

import org.apache.solr.client.solrj.beans.Field;

public class Item {

	@Field
	String id;
	
	@Field
	String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


}
