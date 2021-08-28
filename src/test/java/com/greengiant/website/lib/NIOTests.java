package com.greengiant.website.lib;

import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOTests {

    // https://www.tutorialspoint.com/java_nio/java_nio_channels.htm
    // https://javapapers.com/java/java-nio-channel/
    // http://tutorials.jenkov.com/java-nio/index.html
    // https://blog.csdn.net/weixin_29323273/article/details/115167651
    // https://blog.csdn.net/u011191463/article/details/58597432
    // todo 写通，再看一遍原理

    // todo Java NIO？看这一篇就够了：https://blog.csdn.net/forezp/article/details/88414741
    // todo zero-copy为什么叫zero-copy？NIO如果从系统调用的层面，是不是反而更好理解？（先简单理解微它借助了系统调用的功能）
    // todo 看看这个ByteBuffer满了是怎么处理的？
    // todo 【NIO与操作系统IO模式的关系？ NIO与zero-copy的关系？哪些API用到epoll，哪些API用到zero-copy？】
    //  https://blog.csdn.net/wuyangyang555/article/details/81240411
    //  https://www.jianshu.com/p/dc1acbc7e130
    // todo Java中提供的nio的方式是通过transferTo来实现：https://blog.csdn.net/u011262847/article/details/78089432
    /**
     * NIO底层原理：
     *  https://www.jianshu.com/p/4543c92b2fbd
     *  JAVA NIO: 同步非阻塞，服务器实现模式为一个请求一个线程，即客户端发送的连接请求都会注册到多路复用器上，多路复用器轮询到连接有IO请求才启动一个线程进行处理。
     *  ByteBuffer支持直接内存。使用直接缓冲区可以使得内核缓冲区和用户缓冲区映射到同一块物理内存地址上，实现零拷贝：
     *      https://blog.csdn.net/lixinkuan328/article/details/114156517
     *      https://blog.csdn.net/qq_40837310/article/details/106241945
     *
     *  NIO易用性？所有IO的地方都看看NIO是不是可以完成/更好完成?
     */

    @Test
    public void testFileCombine() throws IOException {
        String[] args = {"D:\\test\\from1.txt", "D:\\test\\from2.txt", "D:\\test\\to.txt"};

        if (args.length < 2) {
            System.err.println("Usage: java NIOCat inFile1 inFile2... outFile");
            return;
        }

        ByteBuffer[] buffers = new ByteBuffer[args.length-1];
        for (int i = 0; i < args.length-1; i++) {
            RandomAccessFile raf = new RandomAccessFile(args[i], "r");
            FileChannel channel = raf.getChannel();
            buffers[i] = channel.map(FileChannel.MapMode.READ_ONLY, 0, raf.length());
        }

        FileOutputStream outFile = new FileOutputStream(args[args.length-1]);
        FileChannel out = outFile.getChannel();
        out.write(buffers);
        out.close();
    }

}