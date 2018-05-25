package com.chw.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chw
 * 2018/5/9
 */
public class ZookeeperConfigCenter {


    private String nodePath;

    private String zookeeperHost;

    private CuratorFramework client;

    private int connectionTimeoutMS;

    private int sessionTimeoutMS;

    public ZookeeperConfigCenter(String nodePath, String zookeeperHost, int connectionTimeoutMS, int sessionTimeoutMS) throws Exception {
        this.nodePath = nodePath;
        this.zookeeperHost = zookeeperHost;
        this.connectionTimeoutMS = connectionTimeoutMS;
        this.sessionTimeoutMS = sessionTimeoutMS;
        initClient();
        //
        System.out.println("jdbc 参数");
        getUpdateData();

        addCenterListener();
    }

    private Map<String, String> configInfo = new ConcurrentHashMap<>(16);


    public void initClient() {
        client = CuratorFrameworkFactory.builder().connectString(zookeeperHost)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .connectionTimeoutMs(connectionTimeoutMS)
                .sessionTimeoutMs(sessionTimeoutMS).build();
        client.start();
    }

    public void addCenterListener() throws Exception {
        TreeCache treeCache = new TreeCache(client, nodePath);
        treeCache.getListenable().addListener(new TreeCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
                if (event.getType() == TreeCacheEvent.Type.NODE_UPDATED) {
                    //获取该节点中的配置信息
                    getUpdateData();
                    //重新设置
                }

            }
        });
        treeCache.start();
    }

    public void getUpdateData() throws Exception {
        List<String> strings = client.getChildren().forPath(nodePath);
        for (String path : strings) {
            System.out.println(path + ":" + new String(client.getData().forPath(nodePath +"/" + path)));
        }
    }


    public static void main(String[] args) throws Exception {
        ZookeeperConfigCenter configCenter = new ZookeeperConfigCenter("/conf/jdbc", "127.0.0.1:2181",
                5000, 5000);
        Thread.sleep(1000000);
    }

}
