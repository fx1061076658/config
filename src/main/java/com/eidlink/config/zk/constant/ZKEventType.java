package com.eidlink.config.zk.constant;

public interface ZKEventType {
	
	int NONE = -1;
	
	int NODE_CREATED = 1;
	
	/**
	 * 删除任一节点时触�?
	 */
	int NODE_DELETED = 2;
	
	/**
	 * 修改任一节点数据时触�?
	 */
	int NODE_DATA_CHANGED = 3;
	
	/**
	 * 新增子节点时触发
	 */
	int NODE_CHILDREN_CHANGED = 4;
}
