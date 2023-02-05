package com.example.project.config;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import com.example.project.Model.Message;
import com.example.project.Model.User;
import com.example.project.Service.MessageService;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import kotlin.reflect.jvm.internal.ReflectProperties.Val;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WebSocketHandler extends AbstractWebSocketHandler {

    MessageService messageService;
    Message newMessage;
    private List<WebSocketSession> sessions = new ArrayList<>();


    public WebSocketHandler(MessageService messageService) {
        this.messageService = messageService;
    }
    
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        System.out.println("Client connected");
    }
    
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        System.out.println("Client disconnected");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        String msg = String.valueOf(message.getPayload());
        //System.out.println("Connected to Client");
        System.out.println("session is "+session);

        System.out.println(msg);
        /*
         * System.out.println(JSONParser(msg));
         * 
         * Gson g = new Gson();
         * JSONObject mObject= g.toJson(msg);
         * JSONObject messageVal= new Gson(msg).fromJson(json, classOfT);
         * //Message newMessage = g.fromJson(msg, Message.class) ;
         * 
         * try {
         * newMessage = g.fromJson(msg, Message.class) ;
         * } catch (JsonSyntaxException e) {
         * System.out.println("Error parsing JSON: " + e.getMessage());
         * }
         */
        if(!msg.equals("Hello World!")) {
            System.out.println("Not hello world");
            JSONParser parser = new JSONParser();  
            try {
                JSONObject json = (JSONObject) parser.parse(msg);
                System.out.println(json);
                
                String messageBody= json.getAsString("message_body");
                String timestamp= json.getAsString("timestamp");
                String sender= json.getAsString("sender_user_id");
                
                
                JSONObject jsonSender= (JSONObject) parser.parse(sender);
                String userIdString= jsonSender.getAsString("user_id");
                

                User userNew = new User();
                userNew.setUserId(Integer.parseInt(userIdString));
               
                String reciever= json.getAsString("reciever_user_id");
                
                JSONObject jsonReciever= (JSONObject) parser.parse(reciever);
                String userId= jsonReciever.getAsString("user_id");
                

                User user = new User();
                user.setUserId(Integer.parseInt(userId));

                
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
                Date date = null;
                try {
                    date = format.parse(timestamp);
                } catch (java.text.ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                
                String chatRoom= json.getAsString("chat_room_id");
                
                Message messagesMessage=  new Message(messageBody,date,userNew,user,chatRoom);
                
                /*
                 * Gson g = new Gson();
                 * newMessage = g.fromJson(json.toString(), Message.class) ;
                 * 
                 * System.out.println(newMessage);
                 */
                
                Message creationResponse= messageService.addNewMessage(messagesMessage);
              //send full detail for adding the msg
                JSONObject jsonBody= new JSONObject();
                jsonBody.put("message_id", creationResponse.getMessageId());
                jsonBody.put("message_body", creationResponse.getMessageBody());
                
                /*
                 * Date dateTime = null;
                 * try {
                 * dateTime = format.parse(creationResponse.getTimestamp().toGMTString());
                 * System.out.println(creationResponse.getTimestamp());
                 * System.out.println(dateTime);
                 * } catch (java.text.ParseException e) {
                 * // TODO Auto-generated catch block
                 * e.printStackTrace();
                 * }
                 */
                
                //jsonBody.put("timestamp", creationResponse.getTimestamp());
                jsonBody.put("timestamp", creationResponse.getTimestamp());
                
                
                JSONObject recievers= new JSONObject();
                recievers.put("user_id",creationResponse.getRecieverUserId().getUserId());
                jsonBody.put("reciever_user_id", recievers);

                JSONObject senders= new JSONObject();
                senders.put("user_id",creationResponse.getSenderUserId().getUserId());
                jsonBody.put("sender_user_id",senders);

                jsonBody.put("chat_room_id", creationResponse.getChatRoomId());
                System.out.println(jsonBody);
                
                
               // Message creationResponse= messageService.addNewMessage(newMessage);
                System.out.println("Message Created");
                //System.out.println(creationResponse.toString());

                //session.sendMessage(new TextMessage(jsonBody.toString()));
                
                //broadcast only to that chatroom
                for (WebSocketSession connectedSession : sessions) {
                    if (connectedSession.getUri().equals(session.getUri())) {
                        connectedSession.sendMessage(new TextMessage(jsonBody.toString()));
                    }
                }
                
                
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }  
        }else {
            session.sendMessage(new TextMessage("Message recieved from client: "+msg));
        }
        //save to database then get fcm_token then send again for notifications
        //session.sendMessage(new TextMessage("Message recieved from client: "+msg));
    }
}