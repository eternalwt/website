package com.greengiant.infrastructure.utils;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class PasswordUtil {

    private PasswordUtil() {}

    /**
     * 随机数生成器
     */
    private static RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    /**
     * 指定散列算法为md5
     */
    public final static String ALGORITHM_NAME = "MD5";

    /**
     * 散列迭代次数
     */
    public final static int HASH_ITERATION_COUNT = 2;

    /**
     * 存储散列后的密码为16进制
     */
    public final static boolean STORED_CREDENTIALS_HEX_ENCODED = true;

    /**
     * 生成随机盐值
     */
    public static String getSalt() {
        return randomNumberGenerator.nextBytes().toHex();
    }

    /**
     * 对密码进行加密
     * @param rawPassword 原始密码
     * @param salt 盐值
     * @return 加密后的密码
     */
    public static String encrypt(String rawPassword, String salt) {
        String newPassword = null;

        if (STORED_CREDENTIALS_HEX_ENCODED) {
            newPassword = new SimpleHash(ALGORITHM_NAME, rawPassword, ByteSource.Util.bytes(salt),
                    HASH_ITERATION_COUNT).toHex();
        }
        else {
            //对应HashedCredentialsMatcher里面的2种判断
            newPassword = new SimpleHash(ALGORITHM_NAME, rawPassword, ByteSource.Util.bytes(salt),
                    HASH_ITERATION_COUNT).toBase64();
        }

        return newPassword;
    }
}
