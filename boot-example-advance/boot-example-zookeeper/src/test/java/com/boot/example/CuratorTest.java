package com.boot.example;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.data.Stat;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * com.boot.example.CuratorTest1
 *
 * @author lipeng
 * @date 2019-04-29 14:53
 */
public class CuratorTest {

    @Test
    public void createNode() throws Exception {
        CuratorFramework client = CuratorUtils.getClient();
        // 创建一个临时结点
        client.create()
                .creatingParentContainersIfNeeded()
                .forPath("/test", "test".getBytes());
    }

    @Test
    public void existsNode() throws Exception {
        CuratorFramework client = CuratorUtils.getClient();
        Stat stat = client.checkExists().forPath("/test");
        System.out.println("结点/test是否存在：" + stat);
    }

    @Test
    public void getNodeData() throws Exception {
        CuratorFramework client = CuratorUtils.getClient();
        // 读取结点值
        System.out.println("结点/test值为：" + new String(client.getData().forPath("/test"), "UTF-8"));
    }

    @Test
    public void modifyNodeData() throws Exception {
        CuratorFramework client = CuratorUtils.getClient();
        // 修改结点值
        client.setData().forPath("/test", "curator".getBytes());
        System.out.println("更新之后，结点/test值为：" + new String(client.getData().forPath("/test"), "UTF-8"));
    }

    @Test
    public void getChildrenList() throws Exception {
        CuratorFramework client = CuratorUtils.getClient();
        // 获取指定路径下面的所有子节点
        List<String> childrenList = client.getChildren().forPath("/");
        System.out.println("/base下面的子节点个数：" + childrenList.size());
        childrenList.stream().forEach(System.out::println);
    }

    @Test
    public void deleteNode() throws Exception {
        CuratorFramework client = CuratorUtils.getClient();
        // 删除结点
        client.delete().forPath("/test");
        System.out.println("删除之后，结点/test是否存在：" + client.checkExists().forPath("/test"));
    }

    @Test
    public void transaction() throws Exception {
        CuratorFramework client = CuratorUtils.getClient();
        client.inTransaction()
                .create().forPath("/transaction", "transaction".getBytes())
                .and()
                .setData().forPath("/transaction", "transaction-test".getBytes())
                .and()
                .commit();
    }
}
