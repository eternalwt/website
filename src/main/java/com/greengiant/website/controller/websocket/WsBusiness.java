package com.greengiant.website.controller.websocket;

public interface WsBusiness {

    boolean open();

    Integer getBusinessType();// todo 是否用枚举？

    Integer getMsgType();

    void processMsg();

    boolean close();

}
