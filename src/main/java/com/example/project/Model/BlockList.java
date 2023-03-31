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


@JsonPropertyOrder({"block_id, blocked_user_id, blocked_by_id"})

@Entity
@Table(name = "Block_List")
public class BlockList implements Serializable{
    
    private static final long serialVersionUID = 8139372911386924019L;

    @Id
    @SequenceGenerator(name = "block_sequence", sequenceName = "block_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "block_sequence")

    @Column(name = "block_id")
    private int blockId;

    @OneToOne
    @JoinColumn(name = "blocked_user_id", referencedColumnName = "user_id")
    private User blockedUser;
    
    @OneToOne
    @JoinColumn(name = "blocked_by_id", referencedColumnName = "user_id")
    private User blockedBy;
    
    
    public BlockList() {
        super();
    }

    
    public BlockList(int blockId, User blockedUser, User blockedBy) {
        super();
        this.blockId = blockId;
        this.blockedUser = blockedUser;
        this.blockedBy = blockedBy;
    }


    public BlockList(User blockedUser, User blockedBy) {
        super();
        this.blockedUser = blockedUser;
        this.blockedBy = blockedBy;
    }
    
    
    @JsonGetter("block_id")
    public int getBlockedUserId() {
        return blockId;
    }

    @JsonSetter("block_id")
    public void setBlockedUserId(int blockId) {
        this.blockId = blockId;
    }


    @JsonGetter("blocked_user_id")
    public User getBlockedUser() {
        return blockedUser;
    }

    @JsonSetter("blocked_user_id")
    public void setBlockedUser(User blockedUser) {
        this.blockedUser = blockedUser;
    }

    @JsonGetter("blocked_by_id")
    public User getBlockedBy() {
        return blockedBy;
    }

    @JsonSetter("blocked_by_id")
    public void setBlockedBy(User blockedBy) {
        this.blockedBy = blockedBy;
    }  
}
