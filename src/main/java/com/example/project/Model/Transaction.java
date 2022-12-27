package com.example.project.Model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonPropertyOrder({ "reciever_user_id, post_id, rating, transaction_date" })

@IdClass(TransactionPK.class)

@Entity
@Table(name = "Transaction")
public class Transaction implements Serializable{

	@Id	
	@OneToOne
	@JoinColumn(name = "reciever_user_id", referencedColumnName = "user_id")
	private User recieverUserId;
	
	@Id    
    @OneToOne
    @JoinColumn(name = "post_id", referencedColumnName = "post_id")
    private Post postId;

	@Column(name = "rating")
	private int rating;

	@Temporal(TemporalType.TIMESTAMP)
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "transaction_date")
	private Date transactionDate;


	public Transaction() {
		super();
	}

	
	public Transaction(User recieverUserId, Post postId, int rating, Date transactionDate) {
        super();
        this.recieverUserId = recieverUserId;
        this.postId = postId;
        this.rating = rating;
        this.transactionDate = transactionDate;
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
	public int getRating() {
        return rating;
    }

	@JsonSetter("rating")
    public void setRating(int rating) {
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

}
