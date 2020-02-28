package com.greengiant.website.controller.websocket;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//@Component
//@ServerEndpoint("/websocket/{sid}")
public class WebSocketServer {

//    /**
//     *  concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
//     */
//    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<>();
//
//    private static CopyOnWriteArraySet<WsBusiness> wsBusinessSet = new CopyOnWriteArraySet<>();
//
//    /**
//     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
//     */
//    private Session session;
//
//    // 接收sid
//    private String sid = "";
//
//    /**
//     * 连接建立成功调用的方法
//     */
//    @OnOpen
//    public void onOpen(Session session, @PathParam("sid") String sid) {// todo 如何传对象过来，需要抽象一下
//
//        // 1.根据类型创建对象放入保存所有连接的List/Set里面
//        // 2.调用它实现的接口里面的open方法
//
//        this.session = session;
//        webSocketSet.add(this);     //加入set中
//        addOnlineCount();           //在线数加1
//        log.info("有新webSocket长连接开始监听:" + sid + ",当前在线人数为" + getOnlineCount());
//        this.sid = sid;
//        try {
//            sendMessage("连接成功");// todo 通知连接成功也用标准数据格式
//        } catch (IOException e) {
//            log.error("websocket IO异常");
//        }
//    }
//
//    /**
//     * 连接关闭调用的方法
//     */
//    @OnClose
//    public void onClose() {
//
//        // 1.调用它实现的接口里面的close方法
//        // 2.根据类型创建对象放入保存所有连接的List/Set里面
//
//        webSocketSet.remove(this);  //从set中删除
//        subOnlineCount();           //在线数减1
//        log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
//    }
//
//    /**
//     * 收到客户端消息后调用的方法
//     *
//     * @param message 客户端发送过来的消息*/
//    @OnMessage
//    public void onMessage(String message, Session session) {
//        log.info("收到来自连接" + sid + "的信息:" + message);
//        //群发消息
//        for (WebSocketServer item : webSocketSet) {
//            //
//            try {
//                item.sendMessage(message);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    /**
//     *
//     * @param session
//     * @param error
//     */
//    @OnError
//    public void onError(Session session, Throwable error) {
//        log.error("发生错误");
//        error.printStackTrace();
//    }
//    /**
//     * 实现服务器主动推送
//     */
//    public void sendMessage(String message) throws IOException {
//        this.session.getBasicRemote().sendText(message);
//    }
//
//
//    /**
//     * 群发自定义消息
//     * */
//    public static void sendInfo(String message,@PathParam("sid") String sid) throws IOException {
//        log.info("推送消息到窗口"+sid+"，推送内容:"+message);
//        for (WebSocketServer item : webSocketSet) {
//            try {
//                //这里可以设定只推送给这个sid的，为null则全部推送
//                if(sid == null) {
//                    item.sendMessage(message);
//                }else if(item.sid.equals(sid)){
//                    item.sendMessage(message);
//                }
//            } catch (IOException e) {
//                continue;
//            }
//        }
//    }


}
