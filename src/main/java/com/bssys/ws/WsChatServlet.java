package com.bssys.ws;

import java.util.logging.Logger;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;


@ServerEndpoint("/echo")
public class WsChatServlet {
	private Logger logger = Logger.getLogger(this.getClass().getName());
	 
    @OnOpen
    public void onOpen(Session session) {
        logger.info("Connected ... " + session.getId());
        SessionManager.getInstance().addSession(session);
    }
 
    @OnMessage
    public void onMessage(Session session, String message ) {
    	logger.info(String.format("Client %s send message %s", session.getId(), message));
    	SessionManager.getInstance().writeMessageToClients(session, message);
    }
 
    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        logger.info(String.format("Session %s closed because of %s", session.getId(), closeReason));
        SessionManager.getInstance().removeSession(session);
    }
    
    @OnError
    public void onError(Session session, Throwable throwable){
    	logger.info(String.format("Session %s error ", session.getId()));
    	SessionManager.getInstance().removeSession(session);
    }
}
