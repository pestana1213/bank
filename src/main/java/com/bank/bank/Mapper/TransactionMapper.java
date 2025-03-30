package com.bank.bank.Mapper;

import com.bank.bank.DTO.TransactionDTOResponse;
import com.bank.bank.Models.Transaction;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    TransactionDTOResponse transactionToResponse(Transaction transaction);

    List<TransactionDTOResponse> transactionListResponse(List<Transaction> transactions);
}
