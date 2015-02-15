package com.eidlink.config.zk.exception;

public enum AppExceptionCode {
	DEFAULT(0, "未知错误编码"),
	SYSTEM_ERROR(1, "系统内部错误"),
	
    ZK_CONNECT_EXCEPTION(10, "连接ZooKeeper服务器异常"),
    ZK_ACCESS_EXCEPTION(11, "访问ZooKeeper异常"),
    ZK_CLOSE_EXCEPTION(12, "关闭ZooKeeper连接异常"),
    
    DB_CONNECT_EXCEPTION(20, "连接数据库异常"),
    DB_ACCESS_EXCEPTION(21, "访问数据库异常"),
    DB_CLOSE_EXCEPTION(22, "关闭数据库连接异常"),
    
    REDIS_CONNECT_EXCEPTION(30, "连接Redis缓存服务器异常"),
    REDIS_ACCESS_EXCEPTION(31, "访问Redis缓存服务器异常"),
    REDIS_CLOSE_EXCEPTION(32, "关闭Redis连接异常"),
    
    MQ_CONNECT_EXCEPTION(40, "连接消息服务器异常"),
    MQ_ACCESS_EXCEPTION(41, "访问消息服务器异常"),
    MQ_CLOSE_EXCEPTION(42, "关闭消息服务器连接异常"),
    
    MONGODB_CONNECT_EXCEPTION(50, "连接Mongodb异常"),
    MONGODB_ACCESS_EXCEPTION(51, "访问Mongodb异常"),
    MONGODB_CLOSE_EXCEPTION(52, "关闭Mongodb连接异常"),
    
    RMI_RESOURCE_ACCESS_EXCEPTION(60, "访问RMI资源异常"),
    WEBSERVICE_RESOURCE_ACCESS_EXCEPTION(61, "访问WebService资源异常"),
    REST_RESOURCE_ACCESS_EXCEPTION(62, "访问REST资源异常"),
    
	;
    
	private int value;
	
	private String reasonPhrase;
	
	private AppExceptionCode(int value, String desc) {
		this.value = value;
		this.reasonPhrase = desc;
	}

	public int value() {
		return value;
	}

	@Override
	public String toString() {
		return "ExceptionCode:EIDLINK-" + value + "[" + reasonPhrase + "]";
	}
	
	public static AppExceptionCode valueOf(int statusCode) {
		for (AppExceptionCode code : values()) {
			if (code.value == statusCode) {
				return code;
			}
		}
		throw new IllegalArgumentException("No matching constant for [" + statusCode + "]");
	}
}
