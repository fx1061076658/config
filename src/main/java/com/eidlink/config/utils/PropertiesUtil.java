package com.eidlink.config.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * zk.properties文件内容的读取
 * 
 * @author fuxing
 *
 */
public class PropertiesUtil {

  private static Logger logger = LogManager.getLogger(PropertiesUtil.class.getName());

  private PropertiesUtil() {}

  private static Properties properties;

  /**
   * 解析properties文件并返回
   * 
   * @param filePath
   * @return
   */
  public static Properties parseProperties(String filePath) {
    logger.entry();
    properties = new Properties();
    try {
      InputStream in = new BufferedInputStream(new FileInputStream(filePath));
      properties.load(in);
      logger.info("Initializing Properties File Success");
      setPropertyKeyToSystem(getSystemKey());
      logger.info("Set Properties To System Success");
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    logger.exit();
    return properties;
  }

  public static Set<String> getAllPropertyKeys() {
    if (properties != null) {
      return properties.stringPropertyNames();
    }
    return null;
  }

  /**
   * 以zk.poperties的将作为系统变量添加到系统变量中
   * 
   * @return
   */
  public static Set<String> getSystemKey() {
    Set<String> keySet = getAllPropertyKeys();
    for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
      String key = (String) iterator.next();
      if (StringUtils.isNotEmpty(key)) {
        iterator.remove();
      }

    }
    return keySet;
  }

  public static String getProperty(String key) {
    String value = null;
    if (StringUtils.isNotEmpty(key)) {
      value = properties.getProperty(key);
    }
    return value;
  }

  public static void main(String[] args) {

  }
  /**
   * 
   * 将zk.poperties中指定的变量添加到系统变量中
   * @param keys
   */
  private static void setPropertyKeyToSystem(Set<String> keys){
    for(String key : keys){
      System.setProperty(key, properties.getProperty(key));
    }
  }
}
