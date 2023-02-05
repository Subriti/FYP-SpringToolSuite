package com.example.project.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.project.Model.Message;
import com.example.project.Model.User;

@Repository
public interface MessageRepository extends JpaRepository<Message,Integer> {
    @Query("SELECT m FROM Message m WHERE m.senderUserId=?1")
    List<Message> findUserMessages(User userId);

    @Query(value = "SELECT DISTINCT(chat_room_id),reciever_user_id FROM message where sender_user_id=?1",nativeQuery = true)
    List<String> findUserChatRooms(User userId);
    
    @Query("SELECT m FROM Message m WHERE m.chatRoomId=?1 ORDER by m.timestamp")
    List<Message> findUserChatRoomMessages(String chatRoomId);

}
