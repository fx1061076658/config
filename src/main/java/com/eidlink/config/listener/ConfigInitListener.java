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
   * 将zookeeper信息录入到队列中
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
        throw(new RuntimeException("zk 主机端口个数不对应"));
      }
    }
  }


  @Override
  public void contextInitialized(ServletContextEvent context) {
    String systemFile = "WEB-INF"+File.separator+"classes"+File.separator+"zk.properties";
    String path = context.getServletContext().getRealPath(File.separator);  
    Properties properties = PropertiesUtil.parseProperties(path+systemFile);
    /**
     * 将所有的zookeeper配置信息 均加载到队列中
     */
    loadPropertyToQueue(properties);
   /**
    * 选取一个Zk服务器 并添加到全局变量
    */
    ZkConstant.selectBeantoGlobal();
  }


  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    // TODO Auto-generated method stub
    
  }


}
