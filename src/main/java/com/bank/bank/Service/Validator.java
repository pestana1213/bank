package com.bank.bank.Service;

import com.bank.bank.DTO.CreateTransactionDTO;
import org.springframework.stereotype.Service;

@Service
public class Validator {

    public void validateCreateTransaction(CreateTransactionDTO createTransactionDTO) {
        if (!(createTransactionDTO.value() != null &&
                createTransactionDTO.value() > 0 &&
                createTransactionDTO.scheduledDate() != null)) {
            throw new IllegalArgumentException("Invalid transaction");
        }
    }
}
