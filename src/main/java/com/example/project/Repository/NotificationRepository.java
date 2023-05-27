package com.example.project.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.project.Model.Notification;
import com.example.project.Model.Post;
import com.example.project.Model.User;
import com.google.common.base.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Integer> {
    @Query("SELECT m FROM Notification m WHERE m.recieverId=?1 ORDER by m.notificationId DESC")
    List<Notification> findUserNotifications(User userId);
    
    @Query("SELECT n FROM Notification n WHERE n.data=?1")
    List<Notification> findPostNotifications(Post postId);
    
    @Query("SELECT n FROM Notification n WHERE n.data=?1 and n.senderId=?2 and n.title='Someone was interested on your post'")
    Notification findNotification(Post postId, User userId);
}
