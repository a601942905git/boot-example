package com.boot.example;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * com.boot.example.CuratorUtils
 *
 * @author lipeng
 * @date 2019-05-02 16:43
 */
public class CuratorUtils {

    public static RetryPolicy getRetryPolicy() {
        // 指定重试策略
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        return retryPolicy;
    }

    public static CuratorFramework getClient() {
        // 创建客户端，指定命名空间为/base，接下来所有的操作都是针对/base路径下面的结点进行操作
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(CuratorConstants.CONNECTION_STRING)
                .sessionTimeoutMs(CuratorConstants.SESSION_TIMEOUT_MS)
                .connectionTimeoutMs(CuratorConstants.CONNECTION_TIMEOUT_MS)
                .retryPolicy(getRetryPolicy())
                .namespace(CuratorConstants.DEFAULT_NAMESPACE)
                .build();

        // 启动客户端
        client.start();

        return client;
    }
}
