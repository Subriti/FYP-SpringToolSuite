package com.example.project.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.Model.Message;
import com.example.project.Service.MessageService;

@RestController
@RequestMapping(path = "api/message")
public class MessageController {

	private final MessageService messageService;

	@Autowired
	public MessageController(MessageService messageService) {
        this.messageService= messageService;
    }

	@GetMapping("/showMessages")
	 public List<Message> getMessage() {
        return messageService.getMessages();
	}
	
	@GetMapping("/showUserMessages")
    public List<Message> getUserMessage(@PathVariable int userId) {
       return messageService.getUserMessages(userId);
   }
	
	// Single item
    @GetMapping(path= "/findMessage/{messageId}")
    public Message findMessage(@PathVariable int messageId) {
        return messageService.findMessage(messageId);
    }

    @PostMapping("/addMessage")
    public void addNewMessage(@RequestBody Message message) {
    	messageService.addNewMessage(message);
	}

    @DeleteMapping(path= "/deleteMessage/{messageId}")
    public void deleteMessage(@PathVariable("messageId") int messageId) {
    	messageService.deleteMessage(messageId);
    }

    @PutMapping(path = "/updateMessage/{messageId}")
    public void updateMessage(@PathVariable int messageId,@RequestBody Message message) {
    	messageService.updateMessage(messageId,message);
    }
}
