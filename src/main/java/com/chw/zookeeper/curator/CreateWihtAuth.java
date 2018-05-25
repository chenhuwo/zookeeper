package com.chw.zookeeper.curator;

import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author chw
 * 2018/5/10
 */
public class CreateWihtAuth {

    public static void main(String[] args) {
        CuratorFrameworkFactory.builder().connectString("127.0.0.1:2181").sessionTimeoutMs(50000).retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .connectionTimeoutMs(5000).authorization("auth", "foo:true".getBytes()).build();



    }

}
