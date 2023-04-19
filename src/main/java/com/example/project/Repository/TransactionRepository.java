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
    
    @Query("SELECT t FROM Transaction t join Post p on p.postId=t.postId WHERE p.postId=?1 and t.status='ACTIVE'")
    Transaction findTransaction(int postId);
    
    @Query(value = "select count(rating), avg(rating) from transaction t join post p on p.post_id= t.post_id where p.post_by=?1", nativeQuery = true)
    Rating getRatingAndCount(int userId);
    
    
    @Query("SELECT t FROM Transaction t join Post p on p.postId=t.postId WHERE p.postId=?1")
    List<Transaction> findTransactionList(int postId);
  
    @Query(value = "select * from transaction t join post p on p.post_id=t.post_id where t.reciever_user_id=?1 and t.status='ACTIVE' and t.post_id in "
            + "(select post_id from post where donation_status=3) order by t.transaction_date desc", nativeQuery = true)
    List<Transaction> findRecievedDonations(int userId);
    
    @Query(value = "select * from transaction t join post p on p.post_id= t.post_id where p.post_by=?1 and p.donation_status=3 and t.status='ACTIVE' "
            + "order by t.transaction_date desc", nativeQuery = true)
    List<Transaction> findGivenDonations(int userId);
    
    @Query(value="select * from transaction t join post p on p.post_id= t.post_id where p.post_by=?1 and p.donation_status=2 "
            + "and t.status='ACTIVE' order by t.transaction_date desc", nativeQuery = true)
    List<Transaction> findOngoingTransactions(int userId);
}
