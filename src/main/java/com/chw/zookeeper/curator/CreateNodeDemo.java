package com.chw.zookeeper.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import javax.sound.midi.Soundbank;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author chw
 * 2018/5/9
 */
public class CreateNodeDemo {

    public static void main(String[] args) throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181",retryPolicy);
        //启动客户端
        client.start();

        //创建数据节点
//        String cmp = client.create().forPath("/cmp", "data123".getBytes());
//        System.out.println(cmp);
        //创建临时节点 递归创建
//        client.create().creatingParentContainersIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/configServer/chww", "data123".getBytes());
        //delete
       // client.delete().forPath("/configServer/chww");//只能删除叶子节点
//        client.delete().deletingChildrenIfNeeded().forPath("/cmp");//递归删除所有节点
        //强制删除
//        client.delete().guaranteed().forPath("");
        //指定版本号
//        client.delete().withVersion(1231).forPath("");



        //读取数据 并获取该节点的stat
        Stat stat = new Stat();
        byte[] bytes = client.getData().storingStatIn(stat).forPath("/configServer");
        System.out.println(new String(bytes));
        System.out.println(stat);
        //检查节点是否存在
        Stat stat1 = client.checkExists().forPath("/cmp");
        System.out.println(stat1);
        //获取所有子节点
        List<String> strings = client.getChildren().forPath("/configServer");
        for (String string : strings) {
            System.out.println(string);
        }
        //异步调用

        Executor executor = Executors.newFixedThreadPool(2);
        client.create().creatingParentContainersIfNeeded().withMode(CreateMode.PERSISTENT)
                .inBackground(new BackgroundCallback() {
                    @Override
                    public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
                        System.out.println("event:" + event.getResultCode());
                    }
                },executor).forPath("/cmp/13212");

        Thread.sleep(Integer.MAX_VALUE);

    }
}
