package com.example.project.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    
    public List<String> getUserChatRooms(User userId) {
        return messageRepository.findUserChatRooms(userId);
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
