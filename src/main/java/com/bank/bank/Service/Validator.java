package com.bank.bank.Service;

import com.bank.bank.DTO.CreateTransactionDTO;
import com.bank.bank.DTO.TransactionDTO;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class Validator implements DtoValidator {

    @Override
    public void validateCreateTransaction(CreateTransactionDTO createTransactionDTO) {
        if (!(validateValue(createTransactionDTO.value()) &&
                validateScheduledDate(createTransactionDTO.scheduledDate()))) {
            throw new IllegalArgumentException("Invalid transaction");
        }
    }

    @Override
    public void validateUpdateTransaction(TransactionDTO transactionDTO) {
        if (!(transactionDTO.id() != null && (
                validateValue(transactionDTO.value()) ||
                validateScheduledDate(transactionDTO.scheduledDate())))) {
            throw new IllegalArgumentException("Invalid transaction");
        }
    }

    private boolean validateScheduledDate(Date scheduledDate) {
        return scheduledDate != null && scheduledDate.after(new Date());
    }

    private boolean validateValue(Double value) {
        return value != null && value > 0;
    }
}
