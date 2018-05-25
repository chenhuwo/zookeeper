package com.chw.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.data.Stat;

/**
 * @author chw
 * 2018/5/9
 */
public class NodeCacheDemo {
    static String  path ="/nodecache/dddd";



    public static void main(String[] args) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString("127.0.0.1:2181").sessionTimeoutMs(5000).connectionTimeoutMs(5000).
                retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();

        client.start();
        Stat stat = client.checkExists().forPath(path);
        if (stat == null) {
            client.create().creatingParentContainersIfNeeded().forPath(path);
        }
        NodeCache nodeCache = new NodeCache(client, path);
        nodeCache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                ChildData currentData = nodeCache.getCurrentData();
                System.out.println(currentData);
            }
        });
        nodeCache.start();
        for (int i = 0; i < 2; i++) {
            Thread.sleep(1000);
            client.setData().forPath(path, ("123" + i).getBytes());
        }
        Thread.sleep(1000);
        client.delete().forPath(path);
    }
}
