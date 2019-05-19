package com.greengiant.website.utils;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class PasswordUtil {
    /**
     * 随机数生成器
     */
    private static RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    // 指定散列算法为md5
    public final static String algorithmName = "MD5";
    // 散列迭代次数
    public final static int hashIterationCount = 2;
    // 存储散列后的密码为16进制
    public final static boolean storedCredentialsHexEncoded = true;

    /**
     * 生成随机盐值
     */
    public static String getSalt() {
        return randomNumberGenerator.nextBytes().toHex();
    }

    /**
     * 对密码进行加密
     * @param originalPassword 原始密码
     * @param salt 盐值
     * @return 加密后的密码
     */
    public static String encrypt(String originalPassword, String salt) {
        String newPassword = null;

        if (storedCredentialsHexEncoded) {
            newPassword = new SimpleHash(algorithmName, originalPassword, ByteSource.Util.bytes(salt),
                    hashIterationCount).toHex();
        }
        else {
            //todo 如果storedCredentialsHexEncoded为false怎么写？
        }

        return newPassword;
    }
}
