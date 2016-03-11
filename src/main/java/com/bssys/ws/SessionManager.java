package com.bssys.ws;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.websocket.Session;

public class SessionManager {

	private final List<Session> sessions = Collections.synchronizedList(new ArrayList<Session>());

	private static SessionManager instance;

	private SessionManager() {
	}

	public static SessionManager getInstance() {
		if (instance == null) {
			instance = new SessionManager();
		}
		return instance;
	}

	public void addSession(Session session) {
		sessions.add(session);
	}

	public void removeSession(Session session) {
		sessions.remove(session);
	}

	public void writeMessageToClients(Session session, String message) {
		synchronized (session) {
			for (Session clientSession : sessions) {
				if (session != clientSession) {
					try {
						clientSession.getBasicRemote().sendText(message);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
}
