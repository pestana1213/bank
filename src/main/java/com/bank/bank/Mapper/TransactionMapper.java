package com.bank.bank.Mapper;

import com.bank.bank.DTO.TransactionDTO;
import com.bank.bank.Models.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TransactionMapper {
    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    TransactionDTO transactionToDTO(Transaction transaction);
    List<TransactionDTO> transactionListToTransactionDTO(List<Transaction> transactions);
}
