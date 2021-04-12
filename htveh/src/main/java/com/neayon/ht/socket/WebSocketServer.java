package com.neayon.ht.socket;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import net.sf.json.JSONObject;

@ServerEndpoint("/connect/{userId}")
@Component
public class WebSocketServer {
	private static Logger log = LoggerFactory.getLogger(WebSocketServer.class);

	private static CopyOnWriteArraySet<WebSocketServer> users = new CopyOnWriteArraySet<WebSocketServer>();
	private Session session;
	private int userId;

	@OnOpen
	public void onOpen(Session session, @PathParam("userId") Integer userId) {
		this.session = session;
		this.userId = userId;
		users.add(this);
		log.info("新建socket连接，" + userId);
	}

	@OnClose
	public void onClose() {
		users.remove(this);
		log.info("断开连接，" + userId);
	}

	@OnMessage
	public void onMessage(String message, Session session) {
		log.info("收到来自用户" + userId + "的消息：" + message);
		// 处理消息
		try {
			JSONObject mes = JSONObject.fromObject(message);
			if(mes.getString("to").equals("ALL")) {
				sendMessageToAll(message);
			} else {
				sendMessageToSomeone(message, mes.getInt("to"));
			}
		} catch (Exception e) {
			log.error("消息处理失败", e);
		}
	}

	@OnError
	public void onError(Session session, Throwable e) {
		log.error("发生错误", e);
	}

	public void sendMessage(String message) {
		try {
			this.session.getBasicRemote().sendText(message);
		} catch (IOException e) {
			log.error("发送错误", e);
		}
	}

	public static void sendMessageToSomeone(String message, Integer userId) {
		log.info("推送消息到用户" + userId + "，推送内容：" + message);
		for (WebSocketServer ws : users) {
			if (userId != null && userId == ws.userId) {
				ws.sendMessage(message);
			}
		}
	}
	
	public static void sendMessageToAll(String message) {
		log.info("推送群发消息：" + message);
		for (WebSocketServer ws : users) {
			ws.sendMessage(message);
		}
	}
}
