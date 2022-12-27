package com.example.project.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project.Model.Message;
import com.example.project.Repository.MessageRepository;

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

    public List<Message> getUserMessages(int userId) {
        return messageRepository.findUserMessages(userId);
    }
    
    public Message findMessage(int messageId) {
        return messageRepository.findById(messageId)
                .orElseThrow(() -> new IllegalStateException("Message with ID " + messageId + " does not exist"));
    }

    public String addNewMessage(Message message) {
       messageRepository.save(message);
       return "Message Successfully Sent !!";
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
