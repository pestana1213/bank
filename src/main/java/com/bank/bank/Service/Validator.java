package com.bank.bank.Service;

import com.bank.bank.DTO.CreateTransactionDTO;
import com.bank.bank.DTO.TransactionDTO;
import com.bank.bank.Exceptions.TransactionInvalid;
import com.bank.bank.Exceptions.TransactionNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class Validator implements DtoValidator {

    @Override
    public void validateCreateTransaction(CreateTransactionDTO createTransactionDTO) {
        if (!(validateValue(createTransactionDTO.value()) &&
                validateScheduledDate(createTransactionDTO.scheduledDate()))) {
            throw new TransactionInvalid("Invalid transaction");
        }
    }

    @Override
    public void validateUpdateTransaction(TransactionDTO transactionDTO) {
        if (transactionDTO.id() == null) {
            throw new TransactionInvalid("Transaction ID must not be null");
        }

        boolean isValidValue = validateValue(transactionDTO.value());
        boolean isValidDate = validateScheduledDate(transactionDTO.scheduledDate());

        if (!isValidValue && !isValidDate) {
            throw new TransactionInvalid("Both value and scheduled date are invalid");
        }
    }

    private boolean validateScheduledDate(Date scheduledDate) {
        return scheduledDate != null && !scheduledDate.before(new Date());
    }

    private boolean validateValue(Double value) {
        return value != null && value > 0;
    }
}
