package com.chw.zookeeper.redis;

import java.util.HashMap;

/**
 * @author chw
 * 2018/5/23
 */
public class CacheServer {

    String ip;

    String port;

    public CacheServer(String ip, String port) {
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

    public HashMap<String, String> map = new HashMap<>();

    public HashMap<String, String> getMap() {
        return map;
    }

    public void set(String key, String value) {
        map.put(key,value);
    }


    public Object get(String key) {
       return map.get(key);
    }

    @Override
    public String toString() {
        return "CacheServer{" +
                "ip='" + ip + '\'' +
                ", port='" + port + '\'' +
                '}';
    }
}
