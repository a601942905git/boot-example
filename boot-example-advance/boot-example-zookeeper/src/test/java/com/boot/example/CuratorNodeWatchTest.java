package com.boot.example;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.zookeeper.CreateMode;
import org.junit.Test;

import java.util.Objects;

/**
 * com.boot.example.CuratorNodeWatchTest
 * 监听某一个结点
 * @author lipeng
 * @date 2019-05-05 14:42
 */
public class CuratorNodeWatchTest {

    public static final String DEFAULT_NODE = "/node";

    @Test
    public void nodeWatchTest() throws Exception {
        CuratorFramework client = CuratorUtils.getClient();
        NodeCache nodeCache = new NodeCache(client, DEFAULT_NODE);
        nodeCache.getListenable().addListener(() -> {
            ChildData childData = nodeCache.getCurrentData();
            if (Objects.isNull(childData)) {
                System.out.println("结点删除......");
            } else {
                System.out.println("结点数据为：" + new String(childData.getData()));
            }
        });
        nodeCache.start();
        client.create().creatingParentContainersIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(DEFAULT_NODE, "node cache".getBytes());
        client.setData().forPath(DEFAULT_NODE, "node cache modify".getBytes());
        client.delete().forPath(DEFAULT_NODE);
        System.in.read();
    }
}
