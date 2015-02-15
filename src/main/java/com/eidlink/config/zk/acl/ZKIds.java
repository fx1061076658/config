package com.eidlink.config.zk.acl;

import java.util.ArrayList;
import java.util.Collections;


public interface ZKIds {
	/**
	 * This Id represents anyone.
	 */
	public final ZKId ANYONE_ID_UNSAFE = new ZKId("world", "anyone");

	/**
	 * This Id is only usable to set ACLs. It will get substituted with the Id's
	 * the client authenticated with.
	 */
	public final ZKId AUTH_IDS = new ZKId("auth", "");

	/**
	 * This is a completely open ACL .
	 */
	public final ArrayList<ZKAcl> OPEN_ACL_UNSAFE = new ArrayList<ZKAcl>(
			Collections.singletonList(new ZKAcl(ZKPermissions.ALL, ANYONE_ID_UNSAFE)));

	/**
	 * This ACL gives the creators authentication id's all permissions.
	 */
	public final ArrayList<ZKAcl> CREATOR_ALL_ACL = new ArrayList<ZKAcl>(
			Collections.singletonList(new ZKAcl(ZKPermissions.ALL, AUTH_IDS)));

	/**
	 * This ACL gives the world the ability to read.
	 */
	public final ArrayList<ZKAcl> READ_ACL_UNSAFE = new ArrayList<ZKAcl>(
			Collections.singletonList(new ZKAcl(ZKPermissions.READ, ANYONE_ID_UNSAFE)));
}
