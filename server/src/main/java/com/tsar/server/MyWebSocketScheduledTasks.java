package com.tsar.server;

import java.util.ArrayList;
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
			this.send();
		} catch (Exception e) {
			logger.error(e.getMessage());
			// e.printStackTrace();
		}
	}

	private void send() throws Exception {
		if (MyWebSocketHandler.sessionList.size() > 0) {
			List<ServerMetric> list = transData(s.loadAndSave());
			logger.debug("send session size :" + String.valueOf(MyWebSocketHandler.sessionList.size()));
			for (WebSocketSession session : MyWebSocketHandler.sessionList) {
				if (list != null && list.size() > 0 && session.isOpen()) {
					logger.debug("send message :" + session.getId() + "   new list size:" + String.valueOf(list.size()));
					session.sendMessage(new TextMessage(Json.toJson(list)));
				}
			}
		}
	}
	
	private 	List<ServerMetric>  transData(List<ServerMetric> list) {
//		List<ServerMetric> newlist = new ArrayList<ServerMetric>();
//		for(ServerMetric m:list) {
//			m.setDest_url(m.getDest_url().replaceAll(".", "_"));
//			newlist.add(m);
//		}
//		return newlist;
		return list;
	}

}
