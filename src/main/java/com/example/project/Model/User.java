package com.example.project.Model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonPropertyOrder({
        "user_id, user_name, email, password, phone_number, birth_date, location, signup_date, profile_picture" })

@Entity
@Table(name = "Users")
public class User implements Serializable {

    private static final long serialVersionUID = 8139372911386924019L;

    @Id
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")

    @Column(name = "user_id")
    private int userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "location")
    private String location;

    @Column(name = "birth_date")
    private Date birth_date;

    @Temporal(TemporalType.TIMESTAMP)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "signup_date")
    private Date signupDate;

    @Column(name = "profile_picture")
    private String profilePicture;

    public User() {
        super();
    }

    public User(int userId, String userName, String email, String password, String phoneNumber, String location,
            Date birth_date, Date signupDate, String profilePicture) {
        super();
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.location = location;
        this.birth_date = birth_date;
        this.signupDate = signupDate;
        this.profilePicture = profilePicture;
    }

    public User(String userName, String email, String password, String phoneNumber, String location,
            Date birth_date, Date signupDate, String profilePicture) {
        super();
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.location = location;
        this.birth_date = birth_date;
        this.signupDate = signupDate;
        this.profilePicture = profilePicture;
    }

    @JsonGetter("user_id")
    public int getUserId() {
        return userId;
    }

    @JsonSetter("user_id")
    public void setUserId(int userId) {
        this.userId = userId;
    }

    @JsonGetter("user_name")
    public String getUserName() {
        return userName;
    }

    @JsonSetter("user_name")
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @JsonGetter("email")
    public String getEmail() {
        return email;
    }

    @JsonSetter("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonGetter("password")
    public String getPassword() {
        return password;
    }

    @JsonSetter("password")
    public void setPassword(String password) {
        this.password = password;
    }

    @JsonGetter("phone_number")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @JsonSetter("phone_number")
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @JsonGetter("location")
    public String getLocation() {
        return location;
    }

    @JsonSetter("location")
    public void setLocation(String location) {
        this.location = location;
    }

    @JsonGetter("birth_date")
    public Date getBirth_date() {
        return birth_date;
    }

    @JsonSetter("birth_date")
    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }

    @JsonGetter("signup_date")
    public Date getSignupDate() {
        return signupDate;
    }

    @JsonSetter("signup_date")
    public void setSignupDate(Date signupDate) {
        this.signupDate = signupDate;
    }

    @JsonGetter("profile_picture")
    public String getProfilePicture() {
        return profilePicture;
    }

    @JsonSetter("profile_picture")
    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
}
