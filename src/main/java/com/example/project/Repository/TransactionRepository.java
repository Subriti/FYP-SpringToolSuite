package com.example.project.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.project.Model.Transaction;
import com.example.project.Model.TransactionPK;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,TransactionPK> {
    @Query("SELECT t FROM Transaction t join Post p on p.postId=t.postId WHERE p.postBy=?1")
    List<Transaction> findUserTransaction(int userId);
}
