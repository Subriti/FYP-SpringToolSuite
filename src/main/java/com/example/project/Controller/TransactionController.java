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

import com.example.project.Model.Transaction;
import com.example.project.Model.TransactionPK;
import com.example.project.Service.TransactionService;

@RestController
@RequestMapping(path = "api/transaction")
public class TransactionController {

	private final TransactionService transactionService;

	@Autowired
	public TransactionController(TransactionService transactionService) {
        this.transactionService= transactionService;
    }

	@GetMapping("/showTransactions")
	 public List<Transaction> getTransaction() {
        return transactionService.getTransaction();
	}
	
	@GetMapping("/showUserTransactions")
    public List<Transaction> findUserTransaction(@PathVariable int userId) {
       return transactionService.findUserTransaction(userId);
   }
	
	// Single item
    @GetMapping(path= "/findTransaction/{transactionId}")
    public Transaction findTransaction(@PathVariable TransactionPK transactionId) {
        return transactionService.findTransaction(transactionId);
    }

    @PostMapping("/addTransaction")
    public void addNewTransaction(@RequestBody Transaction transaction) {
    	transactionService.addNewTransaction(transaction);
	}

    @DeleteMapping(path= "/deleteTransaction/{transactionId}")
    public void deleteTransaction(@PathVariable("transactionId") TransactionPK transactionId) {
    	transactionService.deleteTransaction(transactionId);
    }

    @PutMapping(path = "/updateTransaction/{transactionId}")
    public void updateTransaction(@PathVariable TransactionPK transactionId,@RequestBody Transaction transaction) {
    	transactionService.updateTransaction(transactionId,transaction);
    }
}
