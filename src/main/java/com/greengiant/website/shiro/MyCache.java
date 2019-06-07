package com.greengiant.website.shiro;

import org.apache.commons.collections.map.HashedMap;

import java.util.Date;
import java.util.Map;

public class MyCache {

    Map<String, ValuePair> cache = new HashedMap();

    public class ValuePair {
        private int count;
        private long updateTime;
    }
}
