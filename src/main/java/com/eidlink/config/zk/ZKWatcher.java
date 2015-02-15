package com.eidlink.config.zk;

import java.util.concurrent.CountDownLatch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;

public class ZKWatcher implements Watcher{
  
private CountDownLatch connectedLatch;  
  
  public ZKWatcher(CountDownLatch connectedLatch) {  
      this.connectedLatch = connectedLatch;  
  }  

  @Override  
  public void process(WatchedEvent event) {  
     if (event.getState() == KeeperState.SyncConnected) {  
         connectedLatch.countDown();  
     }  
  }  


}
