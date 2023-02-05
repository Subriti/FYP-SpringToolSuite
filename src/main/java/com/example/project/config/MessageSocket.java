package com.example.project.config;

import java.io.IOException;
import java.util.Map;   
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

//its not working
@ServerEndpoint("/api/messageSocket/{clientId}")
public class MessageSocket {
   
      private static final Map<String, Session> sessions = new ConcurrentHashMap<>();

      @OnOpen
      public void onOpen(Session session, @PathParam("clientId") String clientId) {
        System.out.println("Session opened with clientId: " + clientId);
        sessions.put(clientId, session);
      }

      @OnClose
      public void onClose(Session session, @PathParam("clientId") String clientId) {
        System.out.println("Session closed with clientId: " + clientId);
        sessions.remove(clientId);
      }

      public static void sendMessage(String clientId, String message) {
        Session session = sessions.get(clientId);
        if (session != null && session.isOpen()) {
          session.getAsyncRemote().sendText(message);
        }
      }
}

