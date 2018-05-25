package com.chw.zookeeper.redis;

import sun.security.provider.MD5;

import java.util.*;

/**
 * @author chw
 * 2018/5/23
 */
public class CacheClusterClient {

    static TreeMap<Integer, CacheServer> serverTreeMap = new TreeMap<>();

    public CacheClusterClient(List<ShardInfo> list) {
        for (ShardInfo shardInfo : list) {
            serverTreeMap.put(getHashCode(shardInfo.getIp() + shardInfo.getPort()),new CacheServer(shardInfo.getIp(), shardInfo.getPort()));
        }
        System.out.println(serverTreeMap);
    }


    private static int getHashCode(String key){

        int h;

        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);

    }

    public void set(String key, String val) {
        CacheServer server = getShardServer(key);
        server.set(key, val);
    }

    private CacheServer getShardServer(String key) {
        int hash = getHashCode(key);
        SortedMap<Integer, CacheServer> integerCacheServerSortedMap = serverTreeMap.tailMap(hash);
        int serverKey ;
        if (integerCacheServerSortedMap.isEmpty()) {
            serverKey = serverTreeMap.firstKey();
        } else {
            serverKey = integerCacheServerSortedMap.firstKey();
        }

        CacheServer server = serverTreeMap.get(serverKey);
        System.out.println("get server : " +server);
        return server;

    }

    public static void main(String[] args) {

        ShardInfo[] infos = new ShardInfo[]{

                new ShardInfo("127.168.0.1","1111"),
                new ShardInfo("127.157.0.1","1112"),
                new ShardInfo("127.223.0.1","1113"),
                new ShardInfo("127.111.0.1","1114"),
                new ShardInfo("127.123.0.1","1115")

        };
        CacheClusterClient cacheClusterClient = new CacheClusterClient(Arrays.asList(infos));
        for (int i = 0; i < 100; i++) {
            cacheClusterClient.set(UUID.randomUUID().toString(), UUID.randomUUID().toString());
        }

        Set<Map.Entry<Integer, CacheServer>> entries = serverTreeMap.entrySet();
        for (Map.Entry<Integer, CacheServer> entry : entries) {
            CacheServer value = entry.getValue();
            HashMap<String, String> map = value.getMap();
            System.out.println(value +"::  " + map.size());
        }
    }















    private static class ShardInfo {

        String ip;

        String port;

        public ShardInfo(String ip, String port) {
            this.ip = ip;
            this.port = port;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getPort() {
            return port;
        }

        public void setPort(String port) {
            this.port = port;
        }
    }
}
