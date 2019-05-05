package com.boot.example;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.zookeeper.CreateMode;
import org.junit.Test;

import java.util.Objects;

/**
 * com.boot.example.RegisterServerTest
 * 往Zookeeper结点中注册服务
 * @author lipeng
 * @date 2019-05-05 14:55
 */
public class RegisterServerTest {

    @Test
    public void registerServer() throws Exception {
        CuratorFramework client = CuratorUtils.getClient();
        PathChildrenCache cache = new PathChildrenCache(client, "/servers", true);
        cache.getListenable().addListener((curatorFramework, event) -> {
            if (Objects.nonNull(event.getData())) {
                System.out.println(new String(event.getData().getData()) + "服务上线......");
            }
        });
        cache.start();

        client.create()
                .creatingParentContainersIfNeeded()
                .withMode(CreateMode.EPHEMERAL)
                .forPath("/servers/server1", "192.168.0.1".getBytes());
        Thread.sleep(500);

        client.create()
                .creatingParentContainersIfNeeded()
                .withMode(CreateMode.EPHEMERAL)
                .forPath("/servers/server2", "192.168.0.2".getBytes());
        Thread.sleep(500);

        client.create()
                .creatingParentContainersIfNeeded()
                .withMode(CreateMode.EPHEMERAL)
                .forPath("/servers/server3", "192.168.0.3".getBytes());
        Thread.sleep(10000);

        client.delete().forPath("/servers/server1");
        client.delete().forPath("/servers/server2");
        System.in.read();
    }
}
