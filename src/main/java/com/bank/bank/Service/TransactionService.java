package com.bank.bank.Service;

import com.bank.bank.DTO.TransactionDTO;
import com.bank.bank.Models.Transaction;
import com.bank.bank.Repos.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepo transactionRepo;

    public List<Transaction> getAllTransactions() {
        return transactionRepo.findAll();
    }

    public Transaction getTransactionById(Long id) throws Exception {
        return transactionRepo.findById(id).orElseThrow(() -> new Exception("Transaction not found"));
    }
    
    public void deleteTransaction(Long id) {
        transactionRepo.deleteById(id);
    }

    public Transaction updateTransaction(TransactionDTO transactionDTO) throws Exception {
        try {
            Transaction transaction = getTransactionById(transactionDTO.id());
            if (null != transactionDTO.scheduledDate()) {
                transaction.setScheduledDate(transactionDTO.scheduledDate());
            }
            if (null != transactionDTO.value()) {
                transaction.setValue(transactionDTO.value());
            }
            transaction.setFee(calculateFee(transaction.getValue(), transaction.getScheduledDate()));
            return transactionRepo.save(transaction);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    private double calculateFee(double value, Date scheduledDate) {
        return 0; // TODO: Still needs to be implemented
    }
}
