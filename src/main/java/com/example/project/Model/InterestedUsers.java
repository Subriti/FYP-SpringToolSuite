package com.example.project.Model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonPropertyOrder({"interested_id, user_id, post_id" })

//@IdClass(InterestedUsersPK.class)

@Entity
@Table(name = "Interested_Users")

public class InterestedUsers implements Serializable{

    @Id
    @SequenceGenerator(
            name = "interested_sequence",
            sequenceName = "interested_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "interested_sequence"
    )
    
    @Column(name = "interested_id")
    private int interestedId;
    
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User userId;
  
    @OneToOne
    @JoinColumn(name = "post_id", referencedColumnName = "post_id")
    private Post postId;

    public InterestedUsers() {
        super();
    }

    public InterestedUsers(int interestedId, User userId, Post postId) {
        super();
        this.interestedId = interestedId;
        this.userId = userId;
        this.postId = postId;
    }

    public InterestedUsers(User userId, Post postId) {
        super();
        this.userId = userId;
        this.postId = postId;
    }
    
    @JsonGetter("interested_id")
    public int getInterestedId() {
        return interestedId;
    }

    @JsonSetter("interested_id")
    public void setInterestedId(int interestedId) {
        this.interestedId = interestedId;
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
