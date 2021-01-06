package com.boot.example;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * com.boot.example.ServerDiscoverTest
 * 服务发现
 * @author lipeng
 * @date 2019-05-05 15:11
 */
public class ServerDiscoverTest {

    @Test
    public void serverDiscoverTest() throws Exception {
        CuratorFramework client = CuratorUtils.getClient();
        PathChildrenCache cache = new PathChildrenCache(client, "/servers", true);
        cache.getListenable().addListener((curatorFramework, pathChildrenCacheEvent) -> {
            List<String> serverList = new ArrayList<>();
            for (ChildData childData : cache.getCurrentData()) {
                serverList.add(new String(childData.getData()));
            };
            System.out.println("当前可用服务器列表：" + serverList);
        });
        cache.start();
        System.in.read();
    }
}
