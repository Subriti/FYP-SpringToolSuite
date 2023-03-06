package com.example.project.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.project.Model.InterestedUsers;
import com.example.project.Model.Post;
import com.example.project.Model.User;

@Repository
public interface InterestedUsersRepository extends JpaRepository<InterestedUsers,Integer> {

    @Query("SELECT i FROM InterestedUsers i WHERE i.postId=?1")
    List<InterestedUsers> findUserByPost(Post postId);
    
    @Query("SELECT i FROM InterestedUsers i WHERE i.postId=?1 and i.userId=?2")
    InterestedUsers findUser(Post postId, User userId);
    
    @Modifying
    @Query(value = "DELETE FROM interested_users WHERE post_id=?1 and user_id=?2",nativeQuery=true)
    InterestedUsers DeleteLike(Post postId, User userId);
    
    @Modifying
    @Query(value = "DELETE FROM interested_users WHERE post_id=?1",nativeQuery=true)
    void DeletePostLikes(Post postId);
}
