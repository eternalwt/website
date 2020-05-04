package com.greengiant.website.utils;

import java.util.UUID;

public class UUIDUtil {
    public UUIDUtil() {}

    public static String getUuid() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return uuid;
    }
}
