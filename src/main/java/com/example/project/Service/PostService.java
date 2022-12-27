package com.example.project.Service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project.Model.Post;
import com.example.project.Repository.PostRepository;

@Service
public class PostService {

    private final PostRepository postRepository;


    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getPosts() {
        return postRepository.findAll();
    }

    public List<Post> getUserPosts(int userId) {
        return postRepository.findUserPost(userId);
    }
    
    public Post findPost(int postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new IllegalStateException("Post with ID " + postId + " does not exist"));
    }

    public String addNewPost(Post post) {
       postRepository.save(post);
       return "Post Successfully Registered !!";
    }

    public void deletePost(int postId) {
        boolean exists = postRepository.existsById(postId);
        if (!exists) {
            throw new IllegalStateException("Post with ID " + postId + "does not exist");
        }
        postRepository.deleteById(postId);
    }

    @Transactional
    public String updatePost(int postId, Post Newpost) {
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
        
        return "Successfully updated records";
    }

}
