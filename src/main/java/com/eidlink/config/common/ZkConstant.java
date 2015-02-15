package com.eidlink.config.common;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

import com.eidlink.config.model.PropertyBean;
/**
 * 管理zookeeper队列
 * @author fuxing
 *
 */
public class ZkConstant {

  public static Queue<PropertyBean> zkQueue;
  
  public static void initQueue(List<PropertyBean> properties){
      zkQueue = new LinkedBlockingDeque<PropertyBean>();
      for(PropertyBean property:properties){
        zkQueue.add(property);
      }
  }
  /**
   * 推出一个ZK服务器配置
   * @return
   */
  public static PropertyBean selectZk(){
    return zkQueue.poll();
  }
  /**
   * 选取一个Zk服务 并赋值到全局变量
   */
  public static void selectBeantoGlobal(){
    /**
     * 获取一个zk服务地址
     */
    PropertyBean zkBean = selectZk();
    /**
     * 取出后 放多队列末尾
     */
    ZkConstant.zkQueue.add(zkBean);
    GlobalConstant.DEFAULT_ZK_HOST = zkBean.getKey();
    GlobalConstant.DEFAULT_ZK_PORT = zkBean.getValue().toString();
  }
}
