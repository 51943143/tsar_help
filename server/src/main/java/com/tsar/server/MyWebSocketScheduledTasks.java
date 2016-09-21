package com.tsar.server;

import java.util.List;

import org.nutz.json.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Component
@Configurable
@EnableScheduling
public class MyWebSocketScheduledTasks {
	private static Logger logger = LoggerFactory.getLogger(MyWebSocketScheduledTasks.class);
	@Autowired
	private ServerMetricLoadService s;

	// 每隔3秒钟
	@Scheduled(fixedRate = 3 * 1000)
	public void senddata() {
		try {
			for (WebSocketSession session : MyWebSocketHandler.sessionList) {
				this.send(session);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			// e.printStackTrace();
		}
	}

	private void send(WebSocketSession session) throws Exception {
		List<ServerMetric> list = s.loadAndSave();
		if (list != null && list.size() > 0) {
			session.sendMessage(new TextMessage(Json.toJson(list)));
		}
	}

}
