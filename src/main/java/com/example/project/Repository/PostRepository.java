package com.example.project.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.project.Model.Post;
import com.example.project.Model.User;

@Repository
public interface PostRepository extends JpaRepository<Post,Integer> {
    @Query("SELECT p FROM Post p WHERE p.postBy=?1 order by p.createdDatetime desc")
    List<Post> findUserPost(User userId);
    
    @Query("SELECT p FROM Post p ORDER BY p.createdDatetime desc")
    List<Post> findAllPosts();
}
