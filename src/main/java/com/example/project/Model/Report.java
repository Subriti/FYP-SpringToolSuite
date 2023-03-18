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
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonPropertyOrder({ "report_id, reported_by, post_id, feedback, report_date, post_deleted" })


@Entity
@Table(name = "Report")
public class Report implements Serializable{

	@Id	
	@SequenceGenerator(
            name = "report_sequence",
            sequenceName = "report_sequence",  
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "report_sequence"
    )
    
    @Column(name = "report_id")
    private int reportId;
	
	@OneToOne
	@JoinColumn(name = "reported_by", referencedColumnName = "user_id")
	private User reportedBy;
	  
    @OneToOne
    @JoinColumn(name = "post_id", referencedColumnName = "post_id")
    private Post postId;

	@Column(name = "feedback")
	private String feedback;

	@Temporal(TemporalType.TIMESTAMP)
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "report_date")
	private Date reportDate;

	@Column(name = "post_deleted")
    private Boolean postDeleted;

	public Report() {
		super();
	}

	
	public Report(User reportedBy, Post postId, String feedback, Date reportDate) {
        super();
        this.reportedBy = reportedBy;
        this.postId = postId;
        this.feedback = feedback;
        this.reportDate = reportDate;
    }
	
	
	 
	 public Report(User reportedBy, Post postId, String feedback, Date reportDate, Boolean postDeleted) {
        super();
        this.reportedBy = reportedBy;
        this.postId = postId;
        this.feedback = feedback;
        this.reportDate = reportDate;
        this.postDeleted = postDeleted;
    }


    @JsonGetter("report_id")
    public int getReportId() {
        return reportId;
    }

	 @JsonSetter("report_id")
    public void setReportId(int reportId) {
        this.reportId = reportId;
    }


    @JsonGetter("reported_by")
	public User getRecieverUserId() {
		return reportedBy;
	}

    @JsonSetter("reported_by")
	public void setRecieverUserId(User reportedBy) {
		this.reportedBy = reportedBy;
	}

	@JsonGetter("post_id")
	public Post getPostId() {
		return postId;
	}

	@JsonSetter("post_id")
	public void setPostId(Post postId) {
		this.postId = postId;
	}

    @JsonGetter("feedback")
	public String getFeedback() {
        return feedback;
    }

	@JsonSetter("feedback")
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    @JsonGetter("report_date")
	public Date getReportsDate() {
		return reportDate;
	}

    @JsonSetter("report_date")
	public void setReportsDate(Date reportDate) {
		this.reportDate = reportDate;
	}
    
    @JsonGetter("post_deleted")
    public Boolean getIsPostDeleted() {
        return postDeleted;
    }

    @JsonSetter("post_deleted")
    public void setIsPostDeleted(Boolean postDeleted) {
        this.postDeleted = postDeleted;
    }

}
