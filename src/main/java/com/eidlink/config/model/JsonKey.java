package com.eidlink.config.model;

import java.io.Serializable;

public class JsonKey implements Serializable {
  
  private static final long serialVersionUID = 3321638662495120827L;

  /**
   * zk主机key
   */
  public static final String ZK_HOST = "host";
  /**
   * zk端口key
   */
  public static final String ZK_PORT = "port";

  /**
   * zk配置根目录key
   */
  public static final String ROOT_PATH = "root";
  /**
   * 
   */
  public static final String ZK_HOSTS = "zk.host";
  
  public static final String ZK_PORTS = "zk.port";
}
