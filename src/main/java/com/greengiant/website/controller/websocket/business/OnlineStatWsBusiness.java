package com.greengiant.website.controller.websocket.business;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OnlineStatWsBusiness implements WsBusiness{

    /**
     * 当前在线连接数。应该把它设计成线程安全的
     */
    private static int onlineCount = 0;

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    @Override
    public boolean open() {
        onlineCount++;
        log.info("当前在线人数为" + getOnlineCount());

        return true;
    }

    @Override
    public Integer getMsgType() {
        // todo
        return null;
    }

    @Override
    public void receiveMsg() {
        // todo
    }

    @Override
    public void sendMsg() {
        // todo
    }

    @Override
    public boolean close() {
        onlineCount--;
        // todo 要能知道哪个连接关了
        log.info("有一连接关闭！当前在线人数为" + getOnlineCount());

        return true;
    }
}
