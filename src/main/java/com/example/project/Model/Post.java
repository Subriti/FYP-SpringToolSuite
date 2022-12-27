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

@JsonPropertyOrder({"post_id, post_by, media_file, description, created_datetime, location, cloth_id, donation_status"})

@Entity
@Table(name = "Post")
public class Post implements Serializable{
	private static final long serialVersionUID = -4132868616593747054L;

	@Id
	@SequenceGenerator(
            name = "post_sequence",
            sequenceName = "post_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "post_sequence"
    )
	
    @Column(name = "post_id")
    private int postId;

	@OneToOne
    @JoinColumn(name = "post_by", referencedColumnName = "user_id")
    private User postBy;
	
	@Column(name = "media_file")
    private String mediaFile;

    @Column(name = "description")
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "created_datetime")
    private Date createdDatetime;
    
    @Column(name = "location")
    private String location;

    @OneToOne
    @JoinColumn(name = "cloth_id", referencedColumnName = "cloth_id")
    private Clothes clothId;    
        
    @OneToOne
    @JoinColumn(name = "donation_status", referencedColumnName = "donation_status_id")
    private DonationStatus donationStatus;

	public Post() {
		super();
	}

		public Post(int postId, User postBy, String mediaFile, String description, Date createdDatetime, String location,
            Clothes clothId, DonationStatus donationStatus) {
        super();
        this.postId = postId;
        this.postBy = postBy;
        this.mediaFile = mediaFile;
        this.description = description;
        this.createdDatetime = createdDatetime;
        this.location = location;
        this.clothId = clothId;
        this.donationStatus = donationStatus;
    }


    public Post(User postBy, String mediaFile, String description, Date createdDatetime, String location,
                Clothes clothId, DonationStatus donationStatus) {
            super();
            this.postBy = postBy;
            this.mediaFile = mediaFile;
            this.description = description;
            this.createdDatetime = createdDatetime;
            this.location = location;
            this.clothId = clothId;
            this.donationStatus = donationStatus;
        }

    @JsonGetter("post_id")
	public int getPostId() {
		return postId;
	}

    @JsonSetter("post_id")
	public void setPostId(int postId) {
		this.postId = postId;
	}

	@JsonGetter("post_by")
    public User getPostBy() {
        return postBy;
    }

    @JsonSetter("post_by")
    public void setPostBy(User postBy) {
        this.postBy = postBy;
    }

    @JsonGetter("media_file")
    public String getMediaFile() {
        return mediaFile;
    }

    @JsonSetter("media_file")
    public void setMediaFile(String mediaFile) {
        this.mediaFile = mediaFile;
    }

    @JsonGetter("description")
    public String getDescription() {
        return description;
    }

    @JsonSetter("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonGetter("created_datetime")
    public Date getCreatedDatetime() {
        return createdDatetime;
    }

    @JsonSetter("created_datetime")
    public void setCreatedDatetime(Date createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    @JsonGetter("location")
    public String getLocation() {
        return location;
    }

    @JsonSetter("location")
    public void setLocation(String location) {
        this.location = location;
    }

    @JsonGetter("cloth_id")
    public Clothes getClothId() {
        return clothId;
    }

    @JsonSetter("cloth_id")
    public void setClothId(Clothes clothId) {
        this.clothId = clothId;
    }

    @JsonGetter("donation_status")
    public DonationStatus getDonationStatus() {
        return donationStatus;
    }

    @JsonSetter("donation_status")
    public void setDonationStatus(DonationStatus donationStatus) {
        this.donationStatus = donationStatus;
    }
}
