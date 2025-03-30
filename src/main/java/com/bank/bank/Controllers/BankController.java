package com.bank.bank.Controllers;

import com.bank.bank.DTO.CreateTransactionDTO;
import com.bank.bank.DTO.TransactionDTO;
import com.bank.bank.DTO.TransactionDTOResponse;
import com.bank.bank.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class BankController {

    @Autowired
    TransactionService transactionService;

    @GetMapping
    public ResponseEntity<List<TransactionDTOResponse>> getAllTransactions(){
        List<TransactionDTOResponse> transactionDTOS = transactionService.getAllTransactions();
        return ResponseEntity.status(HttpStatus.OK).body(transactionDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDTOResponse> getTransaction(@PathVariable("id") Long id) {
        TransactionDTOResponse transactionDTO = transactionService.getTransactionByIdDTO(id);
        return ResponseEntity.status(HttpStatus.OK).body(transactionDTO);
    }

    @GetMapping("/scheduling/{id}")
    public ResponseEntity<Date> getScheduling(@PathVariable("id") Long id) {
        Date date = transactionService.getScheduling(id);
        return ResponseEntity.status(HttpStatus.OK).body(date);
    }

    @PostMapping
    public ResponseEntity<TransactionDTOResponse> createTransaction(@RequestBody CreateTransactionDTO createTransactionDTO) {
        TransactionDTOResponse transactionDTO = transactionService.createTransaction(createTransactionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionDTO);
    }


    @DeleteMapping("/{id}")
    public void deleteTransaction(@PathVariable("id") Long id) {
        transactionService.deleteTransaction(id);
    }

    @PatchMapping
    public ResponseEntity<TransactionDTOResponse> patchTransaction(@RequestBody TransactionDTO transactionDTO) {
        TransactionDTOResponse transaction = transactionService.updateTransaction(transactionDTO);
        return ResponseEntity.status(HttpStatus.OK).body(transaction);
    }
}
