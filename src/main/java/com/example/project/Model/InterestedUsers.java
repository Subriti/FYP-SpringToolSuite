package com.example.project.Model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonPropertyOrder({"user_id, post_id" })

@IdClass(InterestedUsersPK.class)

@Entity
@Table(name = "Interested_Users")

public class InterestedUsers implements Serializable{

    @Id
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User userId;
    
    @Id
    @OneToOne
    @JoinColumn(name = "post_id", referencedColumnName = "post_id")
    private Post postId;

    public InterestedUsers() {
        super();
    }

    public InterestedUsers(User userId, Post postId) {
        super();
        this.userId = userId;
        this.postId = postId;
    }

    @JsonGetter("user_id")
    public User getUserId() {
        return userId;
    }

    @JsonSetter("user_id")
    public void setUserId(User userId) {
        this.userId = userId;
    }

    @JsonGetter("post_id")
    public Post getPostId() {
        return postId;
    }

    @JsonSetter("post_id")
    public void setPostId(Post postId) {
        this.postId = postId;
    }
}
