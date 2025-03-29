package com.bank.bank.Service;

import com.bank.bank.DTO.CreateTransactionDTO;
import com.bank.bank.DTO.TransactionDTO;
import com.bank.bank.Exceptions.TransactionNotFoundException;
import com.bank.bank.Models.Transaction;
import com.bank.bank.Repos.TransactionRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepo transactionRepo;

    @Autowired
    private Validator validator;

    @Autowired
    private Calculator calculateFee;

    public List<Transaction> getAllTransactions() {
        return transactionRepo.findAll();
    }

    public Transaction getTransactionById(Long id) {
        return transactionRepo.findById(id).orElseThrow(() -> new TransactionNotFoundException("Transaction not found"));
    }

    public Date getScheduling(Long id) {
        Transaction transaction = getTransactionById(id);
        return transaction.getScheduledDate();
    }

    @Transactional
    public void deleteTransaction(Long id) {
        transactionRepo.deleteById(id);
    }

    public Transaction createTransaction(CreateTransactionDTO createTransactionDTO) {
        validator.validateCreateTransaction(createTransactionDTO);
        Transaction transaction = new Transaction();
        transaction.setValue(createTransactionDTO.value());
        transaction.setScheduledDate(createTransactionDTO.scheduledDate());
        transaction.setFee(calculateFee.calculateFee(createTransactionDTO.value(), createTransactionDTO.scheduledDate()));
        return transactionRepo.save(transaction);
    }

    @Transactional
    public Transaction updateTransaction(TransactionDTO transactionDTO) {
        validator.validateUpdateTransaction(transactionDTO);
        Transaction transaction = getTransactionById(transactionDTO.id());
        if (null != transactionDTO.scheduledDate()) {
            transaction.setScheduledDate(transactionDTO.scheduledDate());
        }
        if (null != transactionDTO.value()) {
            transaction.setValue(transactionDTO.value());
        }
        transaction.setFee(calculateFee.calculateFee(transaction.getValue(), transaction.getScheduledDate()));
        return transactionRepo.save(transaction);
    }
}
