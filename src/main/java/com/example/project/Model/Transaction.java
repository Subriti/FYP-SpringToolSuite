package com.example.project.Model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonPropertyOrder({ "transaction_id, reciever_user_id, post_id, rating, transaction_date, status" })

@Entity
@Table(name = "Transaction")
public class Transaction implements Serializable{

	@Id	
	@SequenceGenerator(
            name = "transaction_sequence",
            sequenceName = "transaction_sequence",  
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "transaction_sequence"
    )
    
    @Column(name = "transaction_id")
    private int transactionId;
	
	@OneToOne
	@JoinColumn(name = "reciever_user_id", referencedColumnName = "user_id")
	private User recieverUserId;
	  
    @OneToOne
    @JoinColumn(name = "post_id", referencedColumnName = "post_id")
    private Post postId;

	@Column(name = "rating")
	private float rating;

	@Temporal(TemporalType.TIMESTAMP)
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "transaction_date")
	private Date transactionDate;
	
	 @Enumerated(EnumType.STRING)
	 @Column(name= "status")
	 private Status status;


	public Transaction() {
		super();
	}

	
	public Transaction(User recieverUserId, Post postId, float rating, Date transactionDate, Status status) {
        super();
        this.recieverUserId = recieverUserId;
        this.postId = postId;
        this.rating = rating;
        this.transactionDate = transactionDate;
        this.status= status;
    }
	
	 
	 @JsonGetter("transaction_id")
    public int getTransactionId() {
        return transactionId;
    }

	 @JsonSetter("transaction_id")
    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }


    @JsonGetter("reciever_user_id")
	public User getRecieverUserId() {
		return recieverUserId;
	}

    @JsonSetter("reciever_user_id")
	public void setRecieverUserId(User recieverUserId) {
		this.recieverUserId = recieverUserId;
	}

	@JsonGetter("post_id")
	public Post getPostId() {
		return postId;
	}

	@JsonSetter("post_id")
	public void setPostId(Post postId) {
		this.postId = postId;
	}

    @JsonGetter("rating")
	public float getRating() {
        return rating;
    }

	@JsonSetter("rating")
    public void setRating(float rating) {
        this.rating = rating;
    }

    @JsonGetter("transaction_date")
	public Date getTransactionDate() {
		return transactionDate;
	}

    @JsonSetter("transaction_date")
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

    @JsonGetter("status")
    public Status getStatus() {
        return status;
    }

    @JsonSetter("status")
    public void setStatus(Status status) {
        this.status = status;
    }
}
