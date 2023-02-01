package com.example.project.Service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project.Model.InterestedUsers;
import com.example.project.Model.Post;
import com.example.project.Model.User;
import com.example.project.Repository.InterestedUsersRepository;
import com.example.project.Repository.PostRepository;

import kotlin.reflect.jvm.internal.ReflectProperties.Val;
import net.minidev.json.JSONObject;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final InterestedUsersRepository interestedUsersRepository;

    @Autowired
    public PostService(PostRepository postRepository, InterestedUsersRepository interestedUsersRepository) {
        this.postRepository = postRepository;
        this.interestedUsersRepository = interestedUsersRepository;
    }

    public List<Post> getPosts() {
        return postRepository.findAllPosts();
    }

    public List<Post> getUserPosts(User userId) {
        return postRepository.findUserPost(userId);
    }
    
    public Post findPost(int postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new IllegalStateException("Post with ID " + postId + " does not exist"));
    }

    public JSONObject addNewPost(Post post) {
       postRepository.save(post);
       JSONObject jsonObject= new JSONObject();
       jsonObject.put("Success message", "Post Successfully Created !!");
       return jsonObject;
    }

    public void deletePost(int postId) {
        boolean exists = postRepository.existsById(postId);
        if (!exists) {
            throw new IllegalStateException("Post with ID " + postId + "does not exist");
        }
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalStateException("Post with ID " + postId + " does not exist"));
        
        List<InterestedUsers> user = interestedUsersRepository.findUserByPost(post); 
        for (int i = 0; i < user.size(); i++) {
            InterestedUsers users = user.get(i);
            // do something with the user object
            interestedUsersRepository.deleteById(users.getInterestedId());
        }
        /*
         * if (user!=null) {
         * interestedUsersRepository.DeletePostLikes(post);
         * }
         */       
        postRepository.deleteById(postId);
    }

    @Transactional
    public JSONObject updatePost(int postId, Post Newpost) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalStateException("Post with ID " + postId + " does not exist"));

        if (Newpost.getDescription() != null && Newpost.getDescription().length() > 0) {
            post.setDescription(Newpost.getDescription());
        }
        
        if (Newpost.getClothId()!=null) {
            post.setClothId(Newpost.getClothId());
        }
        
        if (Newpost.getDonationStatus()!=null) {
            post.setDonationStatus(Newpost.getDonationStatus());
        }
       
        if (Newpost.getLocation() != null && Newpost.getLocation().length() > 0
                && !Objects.equals(post.getLocation(), Newpost.getLocation())) {

            post.setLocation(Newpost.getLocation());
        }
        
        JSONObject jsonObject= new JSONObject();
        jsonObject.put("Success message", "Post Successfully Updated !!");
        return jsonObject;
    }

}
