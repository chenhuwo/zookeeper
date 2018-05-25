package com.chw.zookeeper.redis;


import org.junit.Test;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author chw
 * 2018/5/23
 */
public class TreeMapTest {

    @Test
    public void test() {
        TreeMap<Long, String> treeMap = new TreeMap<>();
        treeMap.put(1001l, "cccccc");
        treeMap.put(10012l, "cccccc2");
        treeMap.put(10013l, "cccccc3");
        treeMap.put(10014l, "cccccc4");
        treeMap.put(10015l, "cccccc5");
        System.out.println(treeMap);

        SortedMap<Long, String> longStringSortedMap = treeMap.tailMap(10012l);
        System.out.println(longStringSortedMap);
    }
}
