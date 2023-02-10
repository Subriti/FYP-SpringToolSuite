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

    
    @Query(value = "SELECT DISTINCT chat_room_id FROM message WHERE chat_room_id LIKE ?1 and chat_room_id LIKE ?2",nativeQuery = true)
    String getUserChatRoomId(String senderUserName, String recieverUserName);

    /*
     * @Query(value =
     * "SELECT DISTINCT(chat_room_id),reciever_user_id FROM message where chat_room_id LIKE '%?1%' and reciever_user_id NOT IN (?2)"
     * ,nativeQuery = true)
     */
    @Query(value = "SELECT DISTINCT(chat_room_id),reciever_user_id FROM message where chat_room_id LIKE '%?1%'",nativeQuery = true)
    List<String> findUserChatRooms(String userName);
    
    @Query(value = "WITH first_query AS (SELECT DISTINCT chat_room_id, reciever_user_id FROM message WHERE chat_room_id LIKE '%?1%' AND sender_user_id in (?2)), \r\n"
            + "second_query AS (SELECT DISTINCT chat_room_id, reciever_user_id FROM message WHERE chat_room_id LIKE '%?3%' AND sender_user_id not in (?4))\r\n"
            + "SELECT * FROM first_query UNION SELECT * FROM second_query WHERE NOT EXISTS (SELECT 1 FROM first_query);",nativeQuery = true)
    List<String> findUserChatRooms(String userName, int userId, String userName2, int userId2);
    
    @Query(value = "WITH first_query AS (SELECT DISTINCT chat_room_id, reciever_user_id FROM message WHERE chat_room_id LIKE '%?1%' AND sender_user_id in (?2)), \r\n"
            + "second_query AS (SELECT DISTINCT chat_room_id, reciever_user_id FROM message WHERE chat_room_id LIKE '%?3%' AND sender_user_id not in (?2))\r\n"
            + "SELECT * FROM first_query UNION SELECT * FROM second_query WHERE NOT EXISTS (SELECT 1 FROM first_query);",nativeQuery = true)
    List<String> findUserChatRooms(String userName, int userId, String userName1);
}
