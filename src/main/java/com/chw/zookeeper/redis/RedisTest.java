package com.chw.zookeeper.redis;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author chw
 * 2018/5/10
 */
public class RedisTest {
    static String listKey = "redpack";
    static int len = 100;
    static Vector<String> vector = new Vector<>(100);

    public static void main(String[] args) throws InterruptedException {
//        JedisUtils jedisUtils = new JedisUtils("127.0.0.1", 6379, "123123");
//        int threadLen = 150;
//        Thread[] threads = new Thread[threadLen];
//        for (int i = 0; i < threadLen; i++) {
//            threads[i] = new Thread(new RedPackThread(jedisUtils));
//        }
//        for (int i = 0; i < threadLen; i++) {
//            threads[i].start();
//        }
//        String lpop = jedisUtils.lpop(listKey);
//        if (lpop != null){
//            System.out.println("get key ：" + lpop);
//            vector.add(lpop);
//        }

        LinkedBlockingQueue<String> strings = new LinkedBlockingQueue<>();


    }

    @Test
    public void create() {
        JedisUtils jedisUtils = new JedisUtils("127.0.0.1", 6379, "123123");
        String redpack = null;
        for (int i = 0; i < len; i++) {
            redpack =  "red-" + i;
            System.out.println("add key :" + redpack);
            jedisUtils.lpush(listKey, redpack);
        }

    }

    static class RedPackThread implements Runnable {

        JedisUtils jedisUtils;

        public RedPackThread(JedisUtils jedisUtils) {
            this.jedisUtils = jedisUtils;
        }

        @Override
        public void run() {
            String lpop = jedisUtils.lpop(listKey);
            if (lpop != null){
                System.out.println("get key ：" + lpop);
                vector.add(lpop);
            }
        }
    }

}
