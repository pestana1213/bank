package com.bank.bank.Service;

import com.bank.bank.DTO.CreateTransactionDTO;
import com.bank.bank.DTO.TransactionDTO;

public interface DtoValidator {

    void validateCreateTransaction(CreateTransactionDTO createTransactionDTO);

    void validateUpdateTransaction(TransactionDTO transactionDTO);
}
