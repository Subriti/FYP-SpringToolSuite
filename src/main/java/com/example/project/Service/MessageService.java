package com.example.project.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project.Model.Message;
import com.example.project.Model.User;
import com.example.project.Repository.MessageRepository;

import net.minidev.json.JSONObject;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<Message> getMessages() {
        return messageRepository.findAll();
    }

    public List<Message> getUserMessages(User userId) {
        return messageRepository.findUserMessages(userId);
    }
    
    public List<Message> getUserChatRoomMessages(String chatRoomId) {
        return messageRepository.findUserChatRoomMessages(chatRoomId);
    }
    
    public List<Message> getUserChatRoomMessages(JSONObject chatRoomId) {
        JSONObject tokenString= new JSONObject(chatRoomId);
        String chatRoom= tokenString.getAsString("chat_room_id");
        
        return messageRepository.findUserChatRoomMessages(chatRoom);
    }
    
    /*
     * public List<String> getUserChatRooms(User userId) {
     * System.out.println(userId);
     * System.out.println(userId.getUserId());
     * return messageRepository.findUserChatRooms(userId);
     * }
     * 
     * public List<String> getUserChatRooms(JSONObject userName,User userId) {
     * JSONObject userObject= new JSONObject(userName);
     * 
     * String userNames= userObject.getAsString("user_name");
     * System.out.println(userNames);
     * System.out.println(userId);
     * System.out.println(userId.getUserId());
     * 
     * return messageRepository.findUserChatRooms(userNames,
     * userId.getUserId(),userNames, userId.getUserId());
     * }
     */
    
    /*
     * public List<String> getUserChatRooms(String username, int userId) {
     * System.out.println(userId);
     * System.out.println(username);
     * return messageRepository.findUserChatRooms(username, userId, username);
     * }
     */
    
    
    //this works
    public List<String> getUserChatRooms(String username, int userId) {
        System.out.println(userId);
        System.out.println(username);
        
        String queryString="WITH first_query AS (SELECT DISTINCT chat_room_id, reciever_user_id FROM message WHERE chat_room_id LIKE '%"+username+"%' AND sender_user_id in ("+userId+")), \r\n"
                + "second_query AS (SELECT DISTINCT chat_room_id, sender_user_id FROM message WHERE chat_room_id LIKE '%"+username+"%' AND sender_user_id not in ("+userId+"))\r\n"
                + "SELECT * FROM first_query UNION SELECT * FROM second_query WHERE NOT EXISTS (SELECT 1 FROM first_query);";
      
        return getAll(queryString);
    }

    private JdbcTemplate template;
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    public JdbcTemplate getTemplate() {
        return template;
    }

    @Autowired
    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }
    
    public List<String> getAll(String queryString) {
        System.out.println(queryString);

        return template.query(queryString, new ResultSetExtractor<List<String>>() {

            @Override
            public List<String> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<String> list = new ArrayList<String>();
                while (rs.next()) {
                    List<String> list1 = new ArrayList<String>();
                    list1.add(rs.getString(1));
                    list1.add(rs.getString(2));
                    list.addAll(list1);
                    System.out.println(list);
                }
                return list;
            }

        });
    }

    
    public Message findMessage(int messageId) {
        return messageRepository.findById(messageId)
                .orElseThrow(() -> new IllegalStateException("Message with ID " + messageId + " does not exist"));
    }

    public Message addNewMessage(Message message) {      
       return messageRepository.save(message);
    }

    public void deleteMessage(int messageId) {
        boolean exists = messageRepository.existsById(messageId);
        if (!exists) {
            throw new IllegalStateException("Message with ID " + messageId + "does not exist");
        }
        messageRepository.deleteById(messageId);
    }

    @Transactional
    public String updateMessage(int messageId, Message Newmessage) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new IllegalStateException("Message with ID " + messageId + " does not exist"));

        if (Newmessage.getMessageBody() != null && Newmessage.getMessageBody().length() > 0) {
            message.setMessagebody(Newmessage.getMessageBody());
        }
        
        if (Newmessage.getTimestamp()!=null) {
            message.setTimestamp(Newmessage.getTimestamp());
        }
        
        return "Successfully updated records";
    }

}
