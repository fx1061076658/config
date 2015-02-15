package com.eidlink.config.zk.constant;

public interface ZKState {
	
	/**
	 * The client is in the disconnected state - it is not connected to any
	 * server in the ensemble.
	 */
	int DISCONNECTED = 0;

	/**
	 * The client is in the connected state - it is connected to a server in the
	 * ensemble (one of the servers specified in the host connection parameter
	 * during ZooKeeper client creation).
	 */
	int SYNC_CONNECTED = 3;

	/**
	 * Auth failed state
	 */
	int AUTH_FAILED = 4;

	/**
	 * The client is connected to a read-only server, that is the server which
	 * is not currently connected to the majority. The only operations allowed
	 * after receiving this state is read operations. This state is generated
	 * for read-only clients only since read/write clients aren't allowed to
	 * connect to r/o servers.
	 */
	int CONNECTED_READ_ONLY = 5;

	/**
	 * SaslAuthenticated: used to notify clients that they are
	 * SASL-authenticated, so that they can perform Zookeeper actions with their
	 * SASL-authorized permissions.
	 */
	int SASL_AUTHENTICATED = 6;

	/**
	 * The serving cluster has expired this session. The ZooKeeper client
	 * connection (the session) is no longer valid. You must create a new client
	 * connection (instantiate a new ZooKeeper instance) if you with to access
	 * the ensemble.
	 */
	int EXPIRED = -112;
}
