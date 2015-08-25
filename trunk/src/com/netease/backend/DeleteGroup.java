package com.netease.backend;

import java.util.List;

import org.apache.zookeeper.KeeperException;

public class DeleteGroup extends MyWatcher {
	public void delete(String groupName) throws KeeperException, InterruptedException {
		String path = "/" + groupName;
		try {
			List<String> children = zk.getChildren(path, false);
			for (String child : children) {
				zk.delete(path + "/" + child, -1);
			}
			zk.delete(path, -1);
		} catch (Exception e) {
			System.out.println("group does not exist  " + groupName);
		}
	}

	public static void main(String[] args) throws Exception {
		DeleteGroup deleteGroup = new DeleteGroup();
		deleteGroup.connect("localhost");
		deleteGroup.delete("zoo");
		deleteGroup.close();
	}
}
