package com.eidlink.config.zk.main;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.eidlink.config.common.GlobalConstant;
import com.eidlink.config.model.JsonKey;
import com.eidlink.config.zk.ZKClient;
import com.eidlink.config.zk.ZKWatcher;
import com.eidlink.config.zk.acl.ZKIds;
import com.eidlink.config.zk.constant.ZKCreateModel;
import com.eidlink.config.zk.constant.ZKStat;

public class Main {

  public static void main(String[] args) {
    ZKClient zkClient = new ZKClient(40000);
//   List<String> child = zkClient.getChildren("/eidlink", true);
//   ZKStat stat = zkClient.exists("/eidlink", new ZKWatcher());
//   System.out.println(stat);
//    zkClient.create("/"+GlobalConstant.DEFAULT_ROOT, null, ZKIds.OPEN_ACL_UNSAFE, ZKCreateModel.PERSISTENT);
//   zkClient.create(GlobalConstant.APP_DIR+"/username", "sa".getBytes(), ZKIds.OPEN_ACL_UNSAFE, ZKCreateModel.PERSISTENT);
//   zkClient.create(GlobalConstant.COMMON_DIR+"/12", "sa".getBytes(), ZKIds.OPEN_ACL_UNSAFE, ZKCreateModel.PERSISTENT);
   ZKStat zkStat = zkClient.exists(GlobalConstant.COMMON_DIR+"/12",true);
//   if(zkStat != null){
//     System.out.println(zkStat.getAversion()+","+zkStat.getCversion());
     zkClient.setData(GlobalConstant.COMMON_DIR+"/12", "333333".getBytes(), -1);
//   }
//     zkClient.delete("/eidlink/app/password", zkStat.getCversion()+1);
//   zkClient.delete("/", 0, true);
//   System.out.println(child);
//   for(String ch : child){
//     List<String> chs = zkClient.getChildren("/"+ch, true);
//     System.out.println(chs);
//   }
  }

}
