package com.greengiant.website.controller.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 一个webSocket Endpoint只干一件事
 */
@Slf4j
@ServerEndpoint(value = "/websocket")
@Component
public class WsEndpoint {

    /**
     *  存放每个连接对应的session。有的场景需要使用Map，保存用户和连接的对应关系，单点推送数据
     */
    private static CopyOnWriteArraySet<Session> sessionSet = new CopyOnWriteArraySet<>();// 根据场景来用，有的场景不一定是读多写少

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        sessionSet.add(session);
        try {
            sendMessage("连接成功", session);
        } catch (IOException e) {
            log.error("建立websocket连接异常， sessionId:" + session.getId());
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        // 将session从Set里面删掉
        log.info(session.getId() + " closed.");
        for (Session ss : sessionSet) {
            if (ss.getId().equals(session.getId())) {
                sessionSet.remove(ss);
                break;
            }
        }
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) {
//        log.info("收到来自连接sessionId:" + session.getId() + "的信息:" + message);
    }

    /**
     * 很多时候触发onError方法后会触发onClose.比如网络异常导致连接异常。也有一些情况是仅触发onError方法，
     * 所以在onError回调中仅打印一条日志或者针对不同的异常写逻辑
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误，sessionId" + session.getId() + ", " + error.getMessage());
    }

    /**
     * 给web端推送消息
     */
    public static void sendMessage(String message, Session session) throws IOException {
        session.getBasicRemote().sendText(message);
    }

}
