package com.example.project.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.project.Model.Rating;
import com.example.project.Model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Integer> {
    @Query("SELECT t FROM Transaction t join Post p on p.postId=t.postId WHERE p.postBy=?1")
    List<Transaction> findUserTransaction(int userId);
    
    @Query("SELECT t FROM Transaction t join Post p on p.postId=t.postId WHERE p.postId=?1")
    Transaction findTransaction(int postId);
    
    @Query(value = "select count(rating), avg(rating) from transaction t join post p on p.post_id= t.post_id where p.post_by=?1", nativeQuery = true)
    Rating getRatingAndCount(int userId);
}
