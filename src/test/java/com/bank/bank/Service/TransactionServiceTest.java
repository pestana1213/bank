package com.bank.bank.Service;

import com.bank.bank.DTO.CreateTransactionDTO;
import com.bank.bank.DTO.TransactionDTO;
import com.bank.bank.DTO.TransactionDTOResponse;
import com.bank.bank.Exceptions.TransactionNotFoundException;
import com.bank.bank.Mapper.TransactionMapper;
import com.bank.bank.Models.Transaction;
import com.bank.bank.Repos.TransactionRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TransactionServiceTest {

    @Mock
    private TransactionRepo transactionRepo;

    @Mock
    private Validator validator;

    @Mock
    private Calculator calculateFee;

    @Mock
    private TransactionMapper transactionMapper;

    @InjectMocks
    private TransactionService transactionService;

    private Transaction transaction;
    private TransactionDTOResponse transactionDTOResponse;
    private CreateTransactionDTO createTransactionDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        transaction = new Transaction();
        transaction.setId(1L);
        transaction.setValue(1000.0);
        transaction.setScheduledDate(new Date());
        transaction.setFee(10.0);

        transactionDTOResponse = new TransactionDTOResponse(1000.0, new Date(), 10.0, "1", "2");

        createTransactionDTO = new CreateTransactionDTO(1000.0, new Date(), "1", "2");
    }

    @Test
    void testGetAllTransactions() {
        when(transactionRepo.findAll()).thenReturn(List.of(transaction));
        when(transactionMapper.transactionListResponse(anyList())).thenReturn(List.of(transactionDTOResponse));

        List<TransactionDTOResponse> result = transactionService.getAllTransactions();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1000.0, result.getFirst().value());
        verify(transactionRepo, times(1)).findAll();
    }

    @Test
    void testGetTransactionByIdDTO_TransactionExists() {
        when(transactionRepo.findById(1L)).thenReturn(Optional.of(transaction));
        when(transactionMapper.transactionToResponse(any())).thenReturn(transactionDTOResponse);

        TransactionDTOResponse result = transactionService.getTransactionByIdDTO(1L);

        assertNotNull(result);
        verify(transactionRepo, times(1)).findById(1L);
    }

    @Test
    void testGetTransactionByIdDTO_TransactionNotFound() {
        when(transactionRepo.findById(1L)).thenReturn(Optional.empty());

        TransactionNotFoundException exception = assertThrows(TransactionNotFoundException.class, () -> {
            transactionService.getTransactionByIdDTO(1L);
        });

        assertEquals("Transaction not found", exception.getMessage());
    }

    @Test
    void testCreateTransaction() {
        when(calculateFee.calculateFee(anyDouble(), any())).thenReturn(10.0);
        when(transactionRepo.save(any())).thenReturn(transaction);
        when(transactionMapper.transactionToResponse(any())).thenReturn(transactionDTOResponse);

        TransactionDTOResponse result = transactionService.createTransaction(createTransactionDTO);

        assertNotNull(result);
        assertEquals(1000.0, result.value());
        verify(transactionRepo, times(1)).save(any());
    }

    @Test
    void testUpdateTransaction() {
        TransactionDTO transactionDTO = new TransactionDTO(1L, 1200.0, new Date());
        Transaction saved = new Transaction(1L, 1200.0, new Date(), 1.0, "1", "2");
        transactionDTOResponse = new TransactionDTOResponse(1200.0, new Date(), 10.0, "1", "2");
        when(transactionRepo.findById(1L)).thenReturn(Optional.of(transaction));
        when(calculateFee.calculateFee(anyDouble(), any())).thenReturn(12.0);
        when(transactionRepo.save(any())).thenReturn(saved);
        when(transactionMapper.transactionToResponse(any())).thenReturn(transactionDTOResponse);

        TransactionDTOResponse result = transactionService.updateTransaction(transactionDTO);

        assertNotNull(result);
        assertEquals(1200.0, result.value());
        verify(transactionRepo, times(1)).save(any());
    }

    @Test
    void testDeleteTransaction() {
        doNothing().when(transactionRepo).deleteById(anyLong());
        transactionService.deleteTransaction(1L);
        verify(transactionRepo, times(1)).deleteById(1L);
    }

    @Test
    void testGetScheduling() {
        when(transactionRepo.findById(1L)).thenReturn(Optional.of(transaction));

        Date result = transactionService.getScheduling(1L);

        assertNotNull(result);
        verify(transactionRepo, times(1)).findById(1L);
    }

    @Test
    void testGetScheduling_TransactionNotFound() {
        when(transactionRepo.findById(1L)).thenReturn(Optional.empty());

        TransactionNotFoundException exception = assertThrows(TransactionNotFoundException.class, () -> {
            transactionService.getScheduling(1L);
        });
        assertEquals("Transaction not found", exception.getMessage());

    }
}
