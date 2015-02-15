package com.eidlink.config.zk;

import org.apache.zookeeper.WatchedEvent;

public class ZKWatchedEvent {

	private WatchedEvent proxy;

	public ZKWatchedEvent(WatchedEvent proxy) {
		super();
		this.proxy = proxy;
	}

	public int getState() {
		return proxy.getState().getIntValue();
	}

	public int getType() {
		return proxy.getType().getIntValue();
	}

	public String getPath() {
		return proxy.getPath();
	}

}
