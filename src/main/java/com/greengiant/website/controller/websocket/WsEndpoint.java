package com.greengiant.website.controller.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 1.建立连接时前端传入businessType，一个webSocket只干一件事
 * 2.如果如要msgType，放在各个业务类里面处理
 */
@Slf4j
@ServerEndpoint(value = "/websocket")
@Component
public class WsEndpoint {
    // todo 把 synchronized 和CopyOnWriteArraySet再看一下
    // todo WebSocket写好后确认要能实现下列3项功能：1.聊天；2.定时推送数据；3.消息中心
    // todo 需要一个注册机制，需要一段对消息类型进行统一管理的代码

    /**
     *  concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
     */
    private static CopyOnWriteArraySet<Session> sessionSet = new CopyOnWriteArraySet<>();

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
//        public void onOpen(Session session, @PathParam("businessType") Long businessType) {// todo 为啥能有多个签名？
        // todo 测试businessType是否需要类型转换
        // 1.根据类型创建对象放入保存所有连接的List/Set里面
        // todo 需要根据sessionId对比然后再添加吗？应该是没必要，再check一遍
        sessionSet.add(session);
        // 2.调用它实现的接口里面的open方法
        // todo 根据参数生成不同类的对象
        // WsBusiness business = new OnlineStatWsBusiness();
        // business.open();
        try {
            // todo 通知连接成功也用标准数据格式
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
        // 1.调用它实现的接口里面的close方法

        // 2.将session从Set里面删掉
        System.out.println(session.getId());
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
        // todo 响应消息的动作
    }

    /**
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {// todo 为啥有的文章没写第一个参数？是不是测一下就知道了
        // todo 尝试重连
        // todo 是否需要把session从set里面干掉？
//        log.error("发生错误，sessionId" + session.getId() + ", " + error.getMessage());
        System.out.println("error");
    }

    /**
     * 实现服务器主动推送
     */
    // todo 这个方法应该搞成static的把？
    public void sendMessage(String message, Session session) throws IOException {
        session.getBasicRemote().sendText(message);
    }

}
