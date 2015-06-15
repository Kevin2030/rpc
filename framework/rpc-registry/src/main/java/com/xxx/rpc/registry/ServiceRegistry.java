package com.xxx.rpc.registry;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 服务注册
 *
 * @author huangyong
 * @since 1.0.0
 */
public class ServiceRegistry {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceRegistry.class);

	private CountDownLatch latch = new CountDownLatch(1);

	private String registryAddress;

	public ServiceRegistry(String registryAddress) {
		this.registryAddress = registryAddress;
	}

	public void register(String data) {
		if (data != null) {
			ZooKeeper zk = connectServer();
			if (zk != null) {
				createNode(zk, data);
			}
		}
	}

	private ZooKeeper connectServer() {
		ZooKeeper zk = null;
		try {
			zk = new ZooKeeper(registryAddress, Constant.ZK_SESSION_TIMEOUT, new Watcher() {
				@Override
				public void process(WatchedEvent event) {
					if (event.getState() == Event.KeeperState.SyncConnected) {
						latch.countDown();
					}
				}
			});
			latch.await();
		} catch (IOException | InterruptedException e) {
			LOGGER.error("", e);
		}
		return zk;
	}

	private void createNode(ZooKeeper zk, String data) {
		try {
			if (zk.exists(Constant.ZK_REGISTRY_PATH, true) == null) {
				zk.create(Constant.ZK_REGISTRY_PATH, "zookeeper".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			}
			String path = "";
			if (zk.exists(Constant.ZK_DATA_PATH, true) == null) {
				byte[] bytes = data.getBytes();
				path = zk.create(Constant.ZK_DATA_PATH, bytes, ZooDefs.Ids.OPEN_ACL_UNSAFE,
						CreateMode.EPHEMERAL_SEQUENTIAL);
			}
			LOGGER.debug("create zookeeper node ({} => {})", path, data);
		} catch (KeeperException | InterruptedException e) {
			LOGGER.error("", e);
		}
	}
}