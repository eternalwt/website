package com.greengiant.website.controller.websocket.business;

public class WsInfo {
    String sessionId;

    Integer businessType;

    Integer msgType;

    String msgBody;

    public WsInfo() {
    }

    public WsInfo(String sessionId, Integer businessType, Integer msgType, String msgBody) {
        this.sessionId = sessionId;
        this.businessType = businessType;
        this.msgType = msgType;
        this.msgBody = msgBody;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    public String getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(String msgBody) {
        this.msgBody = msgBody;
    }
}
