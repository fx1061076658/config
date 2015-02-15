package com.eidlink.config.zk.acl;

public class ZKId {

	private String scheme;
	
	private String id;
	
	public ZKId(){}

	public ZKId(String scheme, String id) {
		super();
		this.scheme = scheme;
		this.id = id;
	}

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


}
