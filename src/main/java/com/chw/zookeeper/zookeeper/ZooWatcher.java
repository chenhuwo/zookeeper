package com.chw.zookeeper.zookeeper;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author chw
 * 2018/5/9
 */
public class ZooWatcher {
    static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ZooKeeper zk = new ZooKeeper("127.0.0.1:2181", 100000, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                countDownLatch.countDown();
                System.out.println("回调watcher实例： 路径" + event.getPath() + " 类型："

                        + event.getType());

            }
        });
        countDownLatch.await();
        zk.exists("/root", true);

        zk.create("/root", "mydata".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,

                CreateMode.PERSISTENT);

        System.out.println("---------------------");

// 在root下面创建一个childone znode,数据为childone,不进行ACL权限控制，节点为永久性的

        zk.exists("/root/childone", true);

        zk.create("/root/childone", "childone".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,

                CreateMode.PERSISTENT);

        System.out.println("---------------------");

// 删除/root/childone这个节点，第二个参数为版本，－1的话直接删除，无视版本

        zk.exists("/root/childone", true);

        zk.delete("/root/childone", -1);

        System.out.println("---------------------");

        zk.exists("/root", true);

        zk.delete("/root", -1);

        System.out.println("---------------------");

// 关闭session

        zk.close();
    }
}
