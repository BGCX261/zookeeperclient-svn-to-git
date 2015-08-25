package com.netease.backend;

import java.util.List;

import org.apache.zookeeper.KeeperException;

public class ListGroup extends MyWatcher {
	public void list(String groupName) throws KeeperException,InterruptedException{
		String path = "/"+groupName;
		try{
			List<String> children = zk.getChildren(path, false);
			if(children.isEmpty()){
				System.out.printf("No members in group "+ groupName);
				return;
			}
			for(String child:children){
				System.out.println(child);
			}
		}catch(KeeperException.NoNodeException e){
			System.out.printf("Group %s does not exist\n",groupName);
		}
	}
	
	public static void main(String[] args) throws Exception{
		ListGroup listGroup = new ListGroup();
		listGroup.connect("app-59.photo.163.org");
		listGroup.list("zoo");
		listGroup.close();
	}
}
