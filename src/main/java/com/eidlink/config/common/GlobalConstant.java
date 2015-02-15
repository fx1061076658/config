package com.eidlink.config.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eidlink.config.model.JsonKey;

/**
 * 全局常量类
 * @author fuxing
 *
 */
public class GlobalConstant {

  public static Map<String,String> baseConf = defaultConfig();
  
  public static String DEFAULT_ZK_HOST = "127.0.0.1";
  
  public static String DEFAULT_ZK_PORT = "2181";
  
  public static final String DEFAULT_ROOT = "eidlink";
  
  public static final String ROOT_NAME = "eidlink平台配置管理中心";
  
  public static final int ZK_TIME_OUT = 10;
  
  /**
   * 应用配置目录
   */
  public static final String APP_DIR = "/eidlink/app";
  
  public static final String APP_NAME = "APP";
  /**
   * 通用配置目录
   */
  public static final String COMMON_DIR = "/eidlink/common";
  
  public static final String COMMON_NAME = "COMMON";
  
  public static final Map<String,String> defaultTree = defaultTree();
  /**
   * 文件路径分割符
   */
  public final static String SYS_SEPARATOR = "/";
  /**
   * 默认配置 （程序提供修改）
   * @return
   */
  private static Map<String, String> defaultConfig() {
    Map<String,String> map = new HashMap<String,String>();
    map.put(JsonKey.ZK_HOST, DEFAULT_ZK_HOST);
    map.put(JsonKey.ZK_PORT, DEFAULT_ZK_PORT);
    map.put(JsonKey.ROOT_PATH, DEFAULT_ROOT);
    return map;
  }
  
  /**
   * 配置左侧树
   * @return
   */
  private static Map<String,String> defaultTree(){
    Map<String,String> tree = new HashMap<String,String>();
    tree.put(APP_DIR, APP_NAME);
    tree.put(COMMON_DIR, COMMON_NAME);
    return tree;
  }
  
  
}
 