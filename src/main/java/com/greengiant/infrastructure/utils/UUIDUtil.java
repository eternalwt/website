package com.greengiant.infrastructure.utils;

import java.util.UUID;

public class UUIDUtil {

    private UUIDUtil() {}

    public static String getUuid() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return uuid;
    }
}
