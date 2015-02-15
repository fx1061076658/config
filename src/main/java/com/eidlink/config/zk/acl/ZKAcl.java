package com.eidlink.config.zk.acl;


public class ZKAcl {

	private int perms;

	private ZKId id;

	public ZKAcl() {
	}

	public ZKAcl(int perms, ZKId id) {
		super();
		this.perms = perms;
		this.id = id;
	}

	public int getPerms() {
		return perms;
	}

	public void setPerms(int perms) {
		this.perms = perms;
	}

	public ZKId getId() {
		return id;
	}

	public void setId(ZKId id) {
		this.id = id;
	}

}
