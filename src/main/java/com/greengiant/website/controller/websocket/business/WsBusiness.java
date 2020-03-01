package com.greengiant.website.controller.websocket.business;

public interface WsBusiness {

    // todo WsBusiness的实现类和WsEndpoint之间需要建立一个注册机制

    boolean open();

    Integer getMsgType();

    void receiveMsg();

    void sendMsg();

    boolean close();

}
