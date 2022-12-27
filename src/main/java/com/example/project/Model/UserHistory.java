package com.example.project.Model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
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

@JsonPropertyOrder({ "history_id, user_id, changed_password, changed_date" })

@Entity
@Table(name = "User_History")
public class UserHistory implements Serializable {

	private static final long serialVersionUID = -6894766197968370915L;

	@Id
	@SequenceGenerator(
            name = "history_sequence",
            sequenceName = "history_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "history_sequence"
    )
	
	@Column(name = "history_id")
	private int historyId;

	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "user_id")
	private User userId;

	@Column(name = "changed_password")
	private String changedPassword;

	@Temporal(TemporalType.TIMESTAMP)
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "changed_date")
	private Date changedDate;


	public UserHistory() {
		super();
	}

	
	public UserHistory(int historyId, User userId, String changedPassword, Date changedDate) {
        super();
        this.historyId = historyId;
        this.userId = userId;
        this.changedPassword = changedPassword;
        this.changedDate = changedDate;
    }



    @JsonGetter("history_id")
	public int getHistoryId() {
		return historyId;
	}

	public void setHistoryId(int historyId) {
		this.historyId = historyId;
	}

	@JsonGetter("user_id")
	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	@JsonGetter("changed_password")
	public String getChangedPassword() {
		return changedPassword;
	}

	public void setChangedPassword(String changedPassword) {
		this.changedPassword = changedPassword;
	}

	@JsonGetter("changed_date")
	public Date getChangedDate() {
		return changedDate;
	}

	public void setChangedDate(Date changedDate) {
		this.changedDate = changedDate;
	}

}
