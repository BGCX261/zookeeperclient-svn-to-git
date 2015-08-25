package com.netease.backend.configserver;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;

public class ConfigWatcher implements Watcher {
	
	private ActiveKeyValueStore store;
	
	public ConfigWatcher(String hosts)throws IOException,InterruptedException{
		store = new ActiveKeyValueStore();
		store.connect(hosts);
	}
	
	public void displayConfig()throws InterruptedException,KeeperException{
		String value = store.read(ConfigUpdater.PATH,this);
		System.out.println("read as"+ ConfigUpdater.PATH + value);
	}
	

	@Override
	public void process(WatchedEvent event) {
		if(event.getType()==EventType.NodeDataChanged){
			try{
				displayConfig();
			}catch(InterruptedException e){
				System.err.println("Interrupted");
			}catch(KeeperException e){
				System.err.println("keeperexception");
			}
		}
	}
	
	public static void main(String[] args) throws Exception{
		ConfigWatcher configWatcher = new ConfigWatcher("localhost");
		configWatcher.displayConfig();
		Thread.sleep(Long.MAX_VALUE);
	}

}
