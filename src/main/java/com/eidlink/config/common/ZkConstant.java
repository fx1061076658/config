package com.eidlink.config.common;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

import com.eidlink.config.model.PropertyBean;
/**
 * ����zookeeper����
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
   * �Ƴ�һ��ZK����������
   * @return
   */
  public static PropertyBean selectZk(){
    return zkQueue.poll();
  }
  /**
   * ѡȡһ��Zk���� ����ֵ��ȫ�ֱ���
   */
  public static void selectBeantoGlobal(){
    /**
     * ��ȡһ��zk�����ַ
     */
    PropertyBean zkBean = selectZk();
    /**
     * ȡ���� �Ŷ����ĩβ
     */
    ZkConstant.zkQueue.add(zkBean);
    GlobalConstant.DEFAULT_ZK_HOST = zkBean.getKey();
    GlobalConstant.DEFAULT_ZK_PORT = zkBean.getValue().toString();
  }
}
