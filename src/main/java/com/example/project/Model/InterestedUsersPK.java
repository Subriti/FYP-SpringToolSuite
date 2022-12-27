package com.example.project.Model;

import java.io.Serializable;

public class InterestedUsersPK implements Serializable {
    private User userId;
    private Post postId;

    public InterestedUsersPK() {
        super();
    }

    public InterestedUsersPK(User userId, Post postId) {
        super();
        this.userId = userId;
        this.postId = postId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Post getPostId() {
        return postId;
    }

    public void setPostId(Post postId) {
        this.postId = postId;
    }  
}
