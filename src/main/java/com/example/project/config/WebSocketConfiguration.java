package com.example.project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.example.project.Service.MessageService;

import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

@Configuration
@EnableWebSocket
// Add this annotation to an @Configuration class to configure processing WebSocket requests
public class WebSocketConfiguration /* extends ServerEndpointConfig.Configurator */ implements WebSocketConfigurer {
   
    private final MessageService messageService;

    @Autowired
    public WebSocketConfiguration(MessageService messageService) {
        this.messageService = messageService;
    }
    
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new WebSocketHandler(messageService), "/api/messageSocket/{chatRoomId}");
    }
    
    /*
     * @Override
     * public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest
     * request, HandshakeResponse response) {
     * String[] clientIdArray =
     * request.getParameterMap().get("clientId").toArray(new String[0]);
     * String clientId = clientIdArray[0];config.getUserProperties().put("clientId",
     * clientId);
     * config.getUserProperties().put("clientId", clientId);
     * }
     * 
     * @Bean
     * public MessageSocket messageSocket() {
     * return new MessageSocket();
     * }
     */
}