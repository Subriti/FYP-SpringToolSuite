package com.example.project.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.Model.Rating;
import com.example.project.Model.Transaction;
import com.example.project.Service.TransactionService;

import net.minidev.json.JSONObject;

@RestController
@RequestMapping(path = "api/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService= transactionService;
    }

    @GetMapping("/showRecievedTransactions/{userId}")
    public List<Transaction> findRecievedTransaction(@PathVariable int userId) {
        return transactionService.findRecievedTransaction(userId);
    }

    @GetMapping("/showGivenTransactions/{userId}")
    public List<Transaction> findGivenTransaction(@PathVariable int userId) {
        return transactionService.findGivenTransaction(userId);
    }

    @GetMapping("/showOngoingTransactions/{userId}")
    public List<Transaction> findOngoingTransaction(@PathVariable int userId) {
        return transactionService.findOnGoingTransaction(userId);
    }

    @GetMapping(path= "/findTransaction/{transactionId}")
    public Transaction findTransaction(@PathVariable int transactionId) {
        return transactionService.findTransaction(transactionId);
    }

    @GetMapping(path= "/getRating/{userId}")
    public Rating findRating(@PathVariable int userId) {
        return transactionService.findRatingAndCount(userId);
    }

    @PostMapping("/addTransaction")
    public JSONObject addNewTransaction(@RequestBody Transaction transaction) {
        return transactionService.addNewTransaction(transaction);
    }

    @DeleteMapping(path= "/deleteTransaction/{transactionId}")
    public void deleteTransaction(@PathVariable("transactionId") int transactionId) {
        transactionService.deleteTransaction(transactionId);
    }

    @PutMapping(path = "/updateTransaction")
    public JSONObject updateTransaction(@RequestBody Transaction transaction) {
        return transactionService.updateTransaction(transaction);
    }
}
