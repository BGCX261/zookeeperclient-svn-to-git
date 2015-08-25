package com.netease.backend;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;

public class JoinGroup extends MyWatcher {
    public void join(String groupName, String memberName) throws KeeperException, InterruptedException {
        String path = "/" + groupName + "/" + memberName;
        String createPath = zk.create(path, null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("Created" + createPath);
    }

    public void join(String memberName) throws KeeperException, InterruptedException {
        String path = "/zoo" + "/" + memberName;
        String createPath = zk.create(path, null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("Created" + createPath);
    }

    public void createNode(String node) throws KeeperException, InterruptedException {
        String createPath = zk.create("/" + node, null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("created" + createPath);
    }

    public static void main(String[] args) throws Exception {
        JoinGroup joinGroup = new JoinGroup();
        joinGroup.connect("app-59.photo.163.org");
        joinGroup.close();
    }
}
