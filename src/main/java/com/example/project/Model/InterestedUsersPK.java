package com.example.project.Model;

import java.io.Serializable;

public class InterestedUsersPK implements Serializable {
    private int userId;
    private int postId;

    public InterestedUsersPK() {
        super();
    }

    public InterestedUsersPK(int userId, int postId) {
        super();
        this.userId = userId;
        this.postId = postId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }  
}
