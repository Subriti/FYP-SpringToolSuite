package com.example.project.Model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonPropertyOrder({"donation_status_id, donation_status"})

@Entity
@Table(name = "Donation_Status")
public class DonationStatus implements Serializable {
	
	private static final long serialVersionUID = -3456522709697343323L;
	@Id
	 @SequenceGenerator(
	            name = "donationStatus_sequence",
	            sequenceName = "donationStatus_sequence",
	            allocationSize = 1
	    )
	    @GeneratedValue(
	            strategy = GenerationType.SEQUENCE,
	            generator = "donationStatus_sequence"
	    )
	
	@Column(name = "donation_status_id")
	private int donationStatusId;
	
	@Column(name = "donation_status")
	private String donationStatus;
	

	@JsonGetter("donation_status_id")
	public int getDonationStatusId() {
		return donationStatusId;
	}

	@JsonSetter("donation_status_id")
	public void setDonationStatusId(int donationStatusId) {
		this.donationStatusId = donationStatusId;
	}

	@JsonGetter("donation_status")
	public String getDonationStatus() {
		return donationStatus;
	}

	@JsonSetter("donation_status")
	public void setDonationStatus(String donationStatus) {
		this.donationStatus = donationStatus;
	}

	public DonationStatus() {

	}

	public DonationStatus(int donationStatusId, String donationStatus) {
		super();
		this.donationStatusId = donationStatusId;
		this.donationStatus = donationStatus;
	}

	public DonationStatus(String donationStatus) {
		super();
		this.donationStatus = donationStatus;
	}
	
}
