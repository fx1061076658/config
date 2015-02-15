package com.eidlink.config.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eidlink.config.model.JsonKey;

/**
 * ȫ�ֳ�����
 * @author fuxing
 *
 */
public class GlobalConstant {

  public static Map<String,String> baseConf = defaultConfig();
  
  public static String DEFAULT_ZK_HOST = "127.0.0.1";
  
  public static String DEFAULT_ZK_PORT = "2181";
  
  public static final String DEFAULT_ROOT = "eidlink";
  
  public static final String ROOT_NAME = "eidlinkƽ̨���ù�������";
  
  public static final int ZK_TIME_OUT = 10;
  
  /**
   * Ӧ������Ŀ¼
   */
  public static final String APP_DIR = "/eidlink/app";
  
  public static final String APP_NAME = "APP";
  /**
   * ͨ������Ŀ¼
   */
  public static final String COMMON_DIR = "/eidlink/common";
  
  public static final String COMMON_NAME = "COMMON";
  
  public static final Map<String,String> defaultTree = defaultTree();
  /**
   * �ļ�·���ָ��
   */
  public final static String SYS_SEPARATOR = "/";
  /**
   * Ĭ������ �������ṩ�޸ģ�
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
   * ���������
   * @return
   */
  private static Map<String,String> defaultTree(){
    Map<String,String> tree = new HashMap<String,String>();
    tree.put(APP_DIR, APP_NAME);
    tree.put(COMMON_DIR, COMMON_NAME);
    return tree;
  }
  
  
}
 