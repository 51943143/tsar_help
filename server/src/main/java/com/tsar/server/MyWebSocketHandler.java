package com.tsar.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class MyWebSocketHandler extends TextWebSocketHandler {
	private static Logger logger = LoggerFactory.getLogger(MyWebSocketHandler.class);

	public static List<WebSocketSession> sessionList = new ArrayList<WebSocketSession>();

	@Override
	public void afterConnectionEstablished(WebSocketSession session) {
		logger.debug("Opened new session in instance " + session.getId());
		sessionList.add(session);
	}


	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		logger.debug("websocket connection closed......afterConnectionClosed.............:" + session.getId() + "--"
				+ closeStatus.getReason());
		closeSession(session);
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		logger.debug("websocket connection closed......handleTransportError...........:" + session.getId() + "--"
				+ exception.getMessage());
		closeSession(session);
	}	
	
	private void closeSession(WebSocketSession session) {
		try {
			if (session.isOpen()) {
				logger.info("...........sessionid:" + session.getId() + "..already conn , do  close................");
				session.close(CloseStatus.SERVER_ERROR);
			}
			sessionList.remove(session);
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}

	}
	
}

