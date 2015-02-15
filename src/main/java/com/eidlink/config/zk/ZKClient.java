package com.eidlink.config.zk;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import javax.sound.midi.SysexMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooKeeper.States;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;

import com.eidlink.config.common.GlobalConstant;
import com.eidlink.config.common.ZkConstant;
import com.eidlink.config.zk.acl.ZKAcl;
import com.eidlink.config.zk.acl.ZKId;
import com.eidlink.config.zk.constant.ZKStat;
import com.eidlink.config.zk.exception.AppExceptionCode;
import com.eidlink.config.zk.exception.AppExceptionSupport;

/**
 * Created by IntelliJ IDEA.
 * User: fuxing
 * Date: 14-12-8
 * Time: 下午9:04
 */
public class ZKClient {
  /**
   * 故障机(存在一个active机器 则故障机列表清空,若全为故障机 则退出)
   */
  private List<String> askedHosts = new ArrayList<String>();

    private Log log = LogFactory.getLog(ZKClient.class);

    private ZooKeeper zk;
    public ZKClient(int timeout){
       initZk(timeout);
  }

  private void initZk(int timeout) {
    CountDownLatch connectedLatch = new CountDownLatch(1);
    ZKWatcher watcher = new ZKWatcher(connectedLatch);
    try {
      if(!askedHosts.contains(GlobalConstant.DEFAULT_ZK_HOST)){
        this.zk = new ZooKeeper(GlobalConstant.DEFAULT_ZK_HOST+":"+GlobalConstant.DEFAULT_ZK_PORT, timeout, watcher);
        zk.exists("/", true);
        System.out.println(GlobalConstant.DEFAULT_ZK_HOST);
        askedHosts = new ArrayList<String>();
      }else{
        System.err.println("***全为故障机！！！！！");
        return;
      }
    } catch (IOException e) {
      askedHosts.add(GlobalConstant.DEFAULT_ZK_HOST);
      System.err.println(GlobalConstant.DEFAULT_ZK_HOST+"已加入黑名单。。");
      ZkConstant.selectBeantoGlobal();
      log.error("zookeeper 服务器异常正在尝试切换服务器",e);
      initZk(timeout);
    } catch (KeeperException e) {
      askedHosts.add(GlobalConstant.DEFAULT_ZK_HOST);
      System.err.println(GlobalConstant.DEFAULT_ZK_HOST+"已加入黑名单。。");
      ZkConstant.selectBeantoGlobal();
      log.error("zookeeper 服务器异常正在尝试切换服务器",e);
      initZk(timeout);
    } catch (InterruptedException e) {
      askedHosts.add(GlobalConstant.DEFAULT_ZK_HOST);
      System.err.println(GlobalConstant.DEFAULT_ZK_HOST+"已加入黑名单。。");
      ZkConstant.selectBeantoGlobal();
      log.error("zookeeper 服务器异常正在尝试切换服务器",e);
      initZk(timeout);
    }
    waitUntilConnected(zk, connectedLatch);
      
    }

  public ZKClient(String hostPort, int timeout, boolean canBeReadOnly) {
      super();

      try {
        CountDownLatch connectedLatch = new CountDownLatch(1);
        ZKWatcher watcher = new ZKWatcher(connectedLatch);
          this.zk = new ZooKeeper(hostPort, timeout, watcher, canBeReadOnly);
          waitUntilConnected(zk, connectedLatch);
      } catch (IOException e) {
          throw AppExceptionSupport.createAppRuntimeException(AppExceptionCode.ZK_CONNECT_EXCEPTION, "can not connect any zk server");
      }
  }

  public boolean close() {
      try {
          zk.close();
      } catch (InterruptedException e) {
          throw AppExceptionSupport.createAppRuntimeException(AppExceptionCode.ZK_CLOSE_EXCEPTION, "close zk client failure.");
      }

      return true;
  }

  public String create(final String path, byte data[], List<ZKAcl> zkAcls,
          int createMode) {
      try {
          return zk.create(path, data, convertAcls(zkAcls), getCreateMode(createMode));
      } catch (Exception e) {
          throw AppExceptionSupport.createAppRuntimeException(AppExceptionCode.ZK_ACCESS_EXCEPTION, e);
      } 
  }

  private CreateMode getCreateMode(int createMode) {
      switch (createMode) {
      case 0:
          return CreateMode.PERSISTENT;
      case 1:
          return CreateMode.EPHEMERAL;
      case 2:
          return CreateMode.PERSISTENT_SEQUENTIAL;
      case 3:
          return CreateMode.EPHEMERAL_SEQUENTIAL;
      default:
          throw new IllegalArgumentException();
      }
  }

  /**
   * 
   * @param path
   * @param version
   * @param flag 是否级联删除
   */
  public void delete(final String path, int version,boolean flag) {
      try {
          if(flag){
            List<String> childrens = zk.getChildren(path, true);
            if(childrens == null){
              log.error("no exist child node");
              return;
            }else{
              for(String child : childrens){
                CountDownLatch connectedLatch = new CountDownLatch(1); 
                ZKStat stat = exists(path+GlobalConstant.SYS_SEPARATOR+child, new ZKWatcher(connectedLatch));
                if(stat != null){
                  int v = stat.getCversion();
                  delete(path+GlobalConstant.SYS_SEPARATOR+child,v,flag);
                }
              }
              zk.delete(path, version);
            }
          }else{
            zk.delete(path, version);
          }
      } catch (Exception e) {
          throw AppExceptionSupport.createAppRuntimeException(AppExceptionCode.ZK_ACCESS_EXCEPTION, e);
      } 
  }

  public ZKStat exists(final String path, ZKWatcher watcher) {
      try {
          return convertZKStat(zk.exists(path, watcher));
      } catch (Exception e) {
          throw AppExceptionSupport.createAppRuntimeException(AppExceptionCode.ZK_ACCESS_EXCEPTION, e);
      } 
  }

  public ZKStat exists(String path, boolean watch) {
      try {
          return convertZKStat(zk.exists(path, watch));
      } catch (Exception e) {
          throw AppExceptionSupport.createAppRuntimeException(AppExceptionCode.ZK_ACCESS_EXCEPTION, e);
      } 
  }

  public byte[] getData(final String path, ZKWatcher watcher, ZKStat zkStat) {
      Stat stat = new Stat();
      byte[] data;
      try {
          data = zk.getData(path, watcher, stat);
      } catch (Exception e) {
          throw AppExceptionSupport.createAppRuntimeException(AppExceptionCode.ZK_ACCESS_EXCEPTION, e);
      } 
      
      if (zkStat != null) {
          copyStat(zkStat, stat);
      }
      return data;
  }

  public byte[] getData(String path, boolean watch, ZKStat zkStat) {
      Stat stat = new Stat();
      byte[] data;
      try {
          data = zk.getData(path, watch, stat);
      } catch (Exception e) {
          throw AppExceptionSupport.createAppRuntimeException(AppExceptionCode.ZK_ACCESS_EXCEPTION, e);
      } 
      
      if (zkStat != null) {
          copyStat(zkStat, stat);
      }
      return data;
  }

  public ZKStat setData(final String path, byte data[], int version) {
      try {
          return convertZKStat(zk.setData(path, data, version));
      } catch (Exception e) {
          throw AppExceptionSupport.createAppRuntimeException(AppExceptionCode.ZK_ACCESS_EXCEPTION, e);
      } 
  }

  public List<ZKAcl> getACL(final String path, ZKStat zkStat) {
      Stat stat = new Stat();
      List<ACL> listAcls;
      try {
          listAcls = zk.getACL(path, stat);
      } catch (Exception e) {
          throw AppExceptionSupport.createAppRuntimeException(AppExceptionCode.ZK_ACCESS_EXCEPTION, e);
      } 
      
      if (zkStat != null) {
          copyStat(zkStat, stat);
      }
      return convertZKAcls(listAcls);
  }

  public ZKStat setACL(final String path, List<ZKAcl> zkAcls, int version) {
      try {
          return convertZKStat(zk.setACL(path, convertAcls(zkAcls), version));
      } catch (Exception e) {
          throw AppExceptionSupport.createAppRuntimeException(AppExceptionCode.ZK_ACCESS_EXCEPTION, e);
      } 
  }

  public List<String> getChildren(final String path, ZKWatcher watcher) {
      try {
          return zk.getChildren(path, watcher);
      } catch (Exception e) {
          throw AppExceptionSupport.createAppRuntimeException(AppExceptionCode.ZK_ACCESS_EXCEPTION, e);
      } 
  }

  public List<String> getChildren(String path, boolean watch) {
      try {
          return zk.getChildren(path, watch);
      } catch (Exception e) {
          throw AppExceptionSupport.createAppRuntimeException(AppExceptionCode.ZK_ACCESS_EXCEPTION, e);
      } 
  }

  public List<String> getChildren(final String path, ZKWatcher watcher, ZKStat zkStat) {
      Stat stat = new Stat();
      List<String> childrens;
      try {
          childrens = zk.getChildren(path, watcher, stat);
      } catch (Exception e) {
          throw AppExceptionSupport.createAppRuntimeException(AppExceptionCode.ZK_ACCESS_EXCEPTION, e);
      } 
      if (zkStat != null) {
          copyStat(zkStat, stat);
      }
      return childrens;
  }

  public List<String> getChildren(String path, boolean watch, ZKStat zkStat) {
      Stat stat = new Stat();
      List<String> childrens;
      try {
          childrens = zk.getChildren(path, watch, stat);
      } catch (Exception e) {
          throw AppExceptionSupport.createAppRuntimeException(AppExceptionCode.ZK_ACCESS_EXCEPTION, e);
      } 
      if (zkStat != null) {
          copyStat(zkStat, stat);
      }
      return childrens;
  }

  private ZKStat convertZKStat(Stat stat) {
      if (stat == null) {
          return null;
      }
      ZKStat zkStat = new ZKStat();
      zkStat.setAversion(stat.getAversion());
      zkStat.setCtime(stat.getCtime());
      zkStat.setCversion(stat.getCversion());
      zkStat.setCzxid(stat.getCzxid());
      zkStat.setDataLength(stat.getDataLength());
      zkStat.setEphemeralOwner(stat.getEphemeralOwner());
      zkStat.setMtime(stat.getMtime());
      zkStat.setMzxid(stat.getMzxid());
      zkStat.setNumChildren(stat.getNumChildren());
      zkStat.setPzxid(stat.getPzxid());
      zkStat.setVersion(stat.getVersion());

      return zkStat;
  }
  
  private void copyStat(ZKStat zkStat, Stat stat){
      zkStat.setAversion(stat.getAversion());
      zkStat.setCtime(stat.getCtime());
      zkStat.setCversion(stat.getCversion());
      zkStat.setCzxid(stat.getCzxid());
      zkStat.setDataLength(stat.getDataLength());
      zkStat.setEphemeralOwner(stat.getEphemeralOwner());
      zkStat.setMtime(stat.getMtime());
      zkStat.setMzxid(stat.getMzxid());
      zkStat.setNumChildren(stat.getNumChildren());
      zkStat.setPzxid(stat.getPzxid());
      zkStat.setVersion(stat.getVersion());
  }

  private List<ACL> convertAcls(List<ZKAcl> zkAcls) {
      if (zkAcls == null || zkAcls.size() == 0) {
          return null;
      }

      List<ACL> result = new ArrayList<ACL>();
      for (ZKAcl zkAcl : zkAcls) {
          result.add(new ACL(zkAcl.getPerms(), convertId(zkAcl.getId())));
      }

      return result;
  }

  private List<ZKAcl> convertZKAcls(List<ACL> acls) {
      if (acls == null || acls.size() == 0) {
          return null;
      }

      List<ZKAcl> result = new ArrayList<ZKAcl>();
      for (ACL acl : acls) {
          result.add(new ZKAcl(acl.getPerms(), convertZKId(acl.getId())));
      }

      return result;
  }

  private Id convertId(ZKId zkId) {
      if (zkId == null) {
          return null;
      }
      return new Id(zkId.getScheme(), zkId.getId());
  }

  private ZKId convertZKId(Id id) {
      if (id == null) {
          return null;
      }
      return new ZKId(id.getScheme(), id.getId());
  }
  
  public static void waitUntilConnected(ZooKeeper zooKeeper, CountDownLatch connectedLatch) {  
    if (States.CONNECTING == zooKeeper.getState()) {  
        try {  
            connectedLatch.await();  
        } catch (InterruptedException e) {  
            throw new IllegalStateException(e);  
        }  
    }  
}  
  
}
