package com.boot.example;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.zookeeper.CreateMode;
import org.junit.Test;

import java.util.List;
import java.util.Objects;

/**
 * com.boot.example.CuratorWatchTest
 * 监听某一路径上的结点
 * @author lipeng
 * @date 2019-05-02 16:42
 */
public class CuratorPathWatchTest {

    public static final String DEFAULT_PATH = "/path/cache";

    @Test
    public void pathCacheTest() throws Exception {
        CuratorFramework client = CuratorUtils.getClient();
        PathChildrenCache cache = new PathChildrenCache(client, DEFAULT_PATH, true);
        cache.getListenable().addListener((curatorFramework, event) -> {
            System.out.println("事件类型为：" + event.getType() + "，数据为：" +
                    (Objects.nonNull(event.getData()) ? new String(event.getData().getData()) : null));
        });
        cache.start();

        client.create().creatingParentContainersIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(DEFAULT_PATH + "/data1", "data1".getBytes());
        Thread.sleep(500);
        client.create().creatingParentContainersIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(DEFAULT_PATH + "/data2", "data2".getBytes());
        Thread.sleep(500);

        // 获取所有子节点
        List<ChildData> childDataList = cache.getCurrentData();
        for (ChildData childData : childDataList) {
            System.out.println("节点路径："+childData.getPath() + ",节点值：" + new String(childData.getData()));
        }

        client.setData().forPath(DEFAULT_PATH + "/data1", "update data1".getBytes());
        Thread.sleep(500);
        client.setData().forPath(DEFAULT_PATH + "/data2", "update data2".getBytes());
        Thread.sleep(500);
        System.in.read();
    }

}
