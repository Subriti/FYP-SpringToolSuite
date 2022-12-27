package com.example.project.Service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project.Model.Transaction;
import com.example.project.Model.TransactionPK;
import com.example.project.Repository.TransactionRepository;

@Service
public class TransactionService {

    private final TransactionRepository TransactionRepository;

    @Autowired
    public TransactionService(TransactionRepository TransactionRepository) {
        this.TransactionRepository = TransactionRepository;
    }

    public List<Transaction> getTransaction() {
        return TransactionRepository.findAll();
    }

    public List<Transaction> findUserTransaction(int userId) {
        return TransactionRepository.findUserTransaction(userId);
    }

    public Transaction findTransaction(TransactionPK transactionId) {
        return TransactionRepository.findById(transactionId)
                .orElseThrow(
                        () -> new IllegalStateException("Transaction with ID " + transactionId + " does not exist"));
    }

    public void addNewTransaction(Transaction Transaction) {
        TransactionRepository.save(Transaction);
    }

    public void deleteTransaction(TransactionPK TransactionId) {
        boolean exists = TransactionRepository.existsById(TransactionId);
        if (!exists) {
            throw new IllegalStateException("status with id " + TransactionId + " does not exist");
        }
        TransactionRepository.deleteById(TransactionId);
    }

    @Transactional
    public String updateTransaction(TransactionPK transactionId, Transaction Newtransaction) {
        Transaction transaction = TransactionRepository.findById(transactionId)
                .orElseThrow(
                        () -> new IllegalStateException("Transaction with ID " + transactionId + " does not exist"));

        if (Newtransaction.getRating() != 0 && Newtransaction.getRating() > 0
                && !Objects.equals(transaction.getRating(), Newtransaction.getRating())) {

            transaction.setRating(Newtransaction.getRating());
        }

        if (Newtransaction.getTransactionDate() != null
                && !Objects.equals(transaction.getTransactionDate(), Newtransaction.getTransactionDate())) {

            transaction.setTransactionDate(Newtransaction.getTransactionDate());
        }
        return "Successfully updated records";

    }
}
