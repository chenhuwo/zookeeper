package com.chw.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author chw
 * 2018/5/9
 */
public class TreeCacheDemo {

    static String path = "/nodecache";

    public static void main(String[] args) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString("127.0.0.1:2181").sessionTimeoutMs(5000)
                .connectionTimeoutMs(5000).retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
        client.start();
        TreeCache tree = new TreeCache(client, path);
        tree.start();
        tree.getListenable().addListener(new TreeCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, TreeCacheEvent treeCacheEvent) throws Exception {
                TreeCacheEvent.Type type = treeCacheEvent.getType();
                ChildData data = treeCacheEvent.getData();

                System.out.println("event type : " + type);
                if (type == TreeCacheEvent.Type.INITIALIZED){
                    return ;
                }
                System.out.println("event data : " + data.getPath());
            }
        });

//        Thread.sleep(1000);
//        client.create().forPath(path + "/node1");
//        Thread.sleep(1000);
//        client.setData().forPath(path + "/node1", "data".getBytes());
//
        Thread.sleep(1000);
        client.create().forPath(path + "/node2");
//        Thread.sleep(1000);
//        client.setData().forPath(path + "/node2" ,"data".getBytes());
//
//        Thread.sleep(1000);
//        client.setData().forPath(path ,"data".getBytes());
        Thread.sleep(1000);
        client.delete().forPath(path + "/node2");

        Thread.sleep(999999999);
    }
}
