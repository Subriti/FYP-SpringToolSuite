package com.example.project.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.project.Model.BlockList;
import com.example.project.Model.User;

@Repository
public interface BlockListRepository extends JpaRepository<BlockList,Integer> {
    
    //show his blocks and an option to unblock
    @Query("SELECT b FROM BlockList b WHERE b.blockedBy=?1")
    List<BlockList> findUserBlockList(User userId);
   
    
    //hide profiles of the people who have blocked him
    @Query("SELECT b FROM BlockList b WHERE b.blockedUser=?1")
    List<BlockList> findBlockedList(User userId);
    
    //knowing the block id
    @Query("SELECT b FROM BlockList b where b.blockedUser=?1 and b.blockedBy=?2")
    BlockList findBlockList(User blockedUser, User blockedBy);
}
