package com.eidlink.config.listener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.eidlink.config.common.GlobalConstant;
import com.eidlink.config.common.ZkConstant;
import com.eidlink.config.model.JsonKey;
import com.eidlink.config.model.PropertyBean;
import com.eidlink.config.utils.PropertiesUtil;
import com.eidlink.config.utils.StringUtil;

public class ConfigInitListener implements ServletContextListener{


  /**
   * ��zookeeper��Ϣ¼�뵽������
   * @param properties
   */
  private void loadPropertyToQueue(Properties properties) {
    String hosts = properties.getProperty(JsonKey.ZK_HOSTS);
    String ports = properties.getProperty(JsonKey.ZK_PORTS);
    if(StringUtil.isAllNotEmpty(hosts,ports)){
      String[] hostArray = hosts.split(",");
      String[] portArray = ports.split(",");
      List<PropertyBean> propertyBeans = new ArrayList<PropertyBean>();
      if(hostArray.length == portArray.length){
        for(int i=0;i<hostArray.length;i++){
          PropertyBean property = new PropertyBean();
          property.set(hostArray[i], portArray[i]);
          propertyBeans.add(property);
        }
        ZkConstant.initQueue(propertyBeans);
      }else{
        throw(new RuntimeException("zk �����˿ڸ�������Ӧ"));
      }
    }
  }


  @Override
  public void contextInitialized(ServletContextEvent context) {
    String systemFile = "WEB-INF"+File.separator+"classes"+File.separator+"zk.properties";
    String path = context.getServletContext().getRealPath(File.separator);  
    Properties properties = PropertiesUtil.parseProperties(path+systemFile);
    /**
     * �����е�zookeeper������Ϣ �����ص�������
     */
    loadPropertyToQueue(properties);
   /**
    * ѡȡһ��Zk������ ����ӵ�ȫ�ֱ���
    */
    ZkConstant.selectBeantoGlobal();
  }


  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    // TODO Auto-generated method stub
    
  }


}
