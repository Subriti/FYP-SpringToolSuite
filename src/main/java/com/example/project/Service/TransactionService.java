package com.example.project.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project.Model.DonationStatus;
import com.example.project.Model.Post;
import com.example.project.Model.Rating;
import com.example.project.Model.Status;
import com.example.project.Model.Transaction;
import com.example.project.Repository.PostRepository;
import com.example.project.Repository.TransactionRepository;
import net.minidev.json.JSONObject;

@Service
public class TransactionService {

    private final TransactionRepository TransactionRepository;
    private final PostRepository postRepository;

    @Autowired
    public TransactionService(TransactionRepository TransactionRepository, PostRepository postRepository) {
        this.TransactionRepository = TransactionRepository;
        this.postRepository = postRepository;
    }

    public List<Transaction> getTransaction() {
        return TransactionRepository.findAll();
    }

    public List<Transaction> findUserTransaction(int userId) {
        return TransactionRepository.findUserTransaction(userId);
    }
    
    public List<Transaction> findRecievedTransaction(int userId) {
        return TransactionRepository.findRecievedDonations(userId);
    }
    
    public List<Transaction> findGivenTransaction(int userId) {
        return TransactionRepository.findGivenDonations(userId);
    }
    
    public List<Transaction> findOnGoingTransaction(int userId) {
        return TransactionRepository.findOngoingTransactions(userId);
    }
    
    @Transactional
    public Transaction updateTransactionStatus(int transactionId) {
        Transaction transaction = TransactionRepository.findById(transactionId)
                .orElseThrow(
                        () -> new IllegalStateException("Transaction with ID " + transactionId + " does not exist"));

        transaction.setStatus(Status.CANCELED);
        
        //change status of cloth donation to active again
        Post post= postRepository.findById(transaction.getPostId().getPostId())  
                .orElseThrow(
                () -> new IllegalStateException("Post with ID " + transaction.getPostId().getPostId() + " does not exist"));
       
        DonationStatus donationStatus= new DonationStatus();
        donationStatus.setDonationStatusId(1);
       
        post.setDonationStatus(donationStatus);
        
        return transaction;
    }

    public Transaction findTransaction(int transactionId) {
        return TransactionRepository.findById(transactionId)
                .orElseThrow(
                        () -> new IllegalStateException("Transaction with ID " + transactionId + " does not exist"));
    }

    private JdbcTemplate template;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    public JdbcTemplate getTemplate() {
        return template;
    }

    @Autowired
    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }
    
    public Rating findRatingAndCount(int userId) {
        
        String queryString= "select count(rating), avg(rating) from transaction t join post p on p.post_id= t.post_id where p.donation_status=3 and p.post_by="+userId;
        return template.query(queryString, new ResultSetExtractor<Rating>() {

            @Override
            public Rating extractData(ResultSet rs) throws SQLException, DataAccessException {
                
                Rating s = new Rating();
                
                while (rs.next()) {
                    s = new Rating();
           
                    s.setClothDonated(rs.getInt(1));
                    System.out.println("\nNumber of Cloth Donated is " + s.getClothDonated());

                    s.setRating(rs.getFloat(2));
                    System.out.println("Rating is " + s.getRating());               
                }
                return s;
            }
        });
    }
    

    public JSONObject addNewTransaction(Transaction Transaction) {
        TransactionRepository.save(Transaction);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Message", "Successfully Added the Transaction");
        return jsonObject;
    }

    public void deleteTransaction(int TransactionId) {
        boolean exists = TransactionRepository.existsById(TransactionId);
        if (!exists) {
            throw new IllegalStateException("status with id " + TransactionId + " does not exist");
        }
        TransactionRepository.deleteById(TransactionId);
    }

    @Transactional
    public JSONObject updateTransaction(Transaction Newtransaction) {
        Transaction transaction = TransactionRepository.findTransaction(Newtransaction.getPostId().getPostId());

        if (Newtransaction.getRating() != 0 && Newtransaction.getRating() > 0
                && !Objects.equals(transaction.getRating(), Newtransaction.getRating())) {

            transaction.setRating(Newtransaction.getRating());
        }

        if (Newtransaction.getTransactionDate() != null
                && !Objects.equals(transaction.getTransactionDate(), Newtransaction.getTransactionDate())) {

            transaction.setTransactionDate(Newtransaction.getTransactionDate());
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Message", "Successfully updated records");
        return jsonObject;
    }
}
