package com.netease.backend;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

/**
 * 
 * @author zhaopingfei
 * 
 */
public class MyWatcher implements Watcher {
	private static final int SESSION_TIMEOUT = 5000;

	protected ZooKeeper zk;

	private CountDownLatch connectedSignal = new CountDownLatch(1);

	//host∏Ò Ω(127.0.0.1:3000,127.0.0.1:3001,127.0.0.1:3002)
	public void connect(String hosts) throws IOException, InterruptedException {
		zk = new ZooKeeper(hosts, SESSION_TIMEOUT, this);
		connectedSignal.await();
	}

	@Override
	public void process(WatchedEvent event) {
		if (event.getState() == KeeperState.SyncConnected) {
			connectedSignal.countDown();
		}
	}

	public void create(String groupName) throws KeeperException, InterruptedException {
		String path = "/" + groupName;
		String createdPath = zk.create(path, null/* data */, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		System.out.print("Created" + createdPath);
	}

	public void close() throws InterruptedException {
		zk.close();
	}

	public static void main(String[] args) throws Exception {
		MyWatcher createGroup = new MyWatcher();
		createGroup.connect(args[0]);
		createGroup.create(args[1]);
		createGroup.close();

	}

}
