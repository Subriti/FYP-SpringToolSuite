package com.example.project.Model;

import java.io.Serializable;

public class TransactionPK implements Serializable {
    private User recieverUserId;
    private Post postId;

    public TransactionPK() {
        super();
    }

    public TransactionPK(User recieverUserId, Post postId) {
        super();
        this.recieverUserId = recieverUserId;
        this.postId = postId;
    }

    public User getReceiverUserId() {
        return recieverUserId;
    }

    public void setReceiverUserId(User recieverUserId) {
        this.recieverUserId = recieverUserId;
    }

    public Post getPostId() {
        return postId;
    }

    public void setPostId(Post postId) {
        this.postId = postId;
    }  
}
