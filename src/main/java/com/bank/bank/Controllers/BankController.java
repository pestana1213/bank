package com.bank.bank.Controllers;

import com.bank.bank.DTO.CreateTransactionDTO;
import com.bank.bank.DTO.TransactionDTO;
import com.bank.bank.Models.Transaction;
import com.bank.bank.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class BankController {

    @Autowired
    TransactionService transactionService;

    @GetMapping
    public List<Transaction> getAllTransactions(){
        return transactionService.getAllTransactions();
    }

    @GetMapping("/{id}")
    public Transaction getTransaction(@PathVariable Long id) {
        return transactionService.getTransactionById(id);
    }

    @GetMapping("/scheduling/{id}")
    public Date getScheduling(@PathVariable("id") Long id) {
        return transactionService.getScheduling(id);
    }

    @PostMapping
    public Transaction createTransaction(@RequestBody CreateTransactionDTO createTransactionDTO) {
        return transactionService.createTransaction(createTransactionDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
    }

    @PatchMapping
    public Transaction patchTransaction(@RequestBody TransactionDTO transactionDTO) {
        return transactionService.updateTransaction(transactionDTO);
    }
}
