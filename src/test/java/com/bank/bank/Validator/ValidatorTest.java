package com.bank.bank.Validator;

import com.bank.bank.DTO.CreateTransactionDTO;
import com.bank.bank.DTO.TransactionDTO;
import com.bank.bank.Exceptions.TransactionInvalid;
import com.bank.bank.Service.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ValidatorTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        validator = new Validator();
    }

    @Test
    public void testValidateCreateTransaction_validInput() {
        CreateTransactionDTO validTransaction = mock(CreateTransactionDTO.class);
        when(validTransaction.value()).thenReturn(1000.0);
        when(validTransaction.scheduledDate()).thenReturn(new Date(System.currentTimeMillis() + 1000));
        when(validTransaction.accountFrom()).thenReturn("12345");
        when(validTransaction.accountTo()).thenReturn("54321");

        assertDoesNotThrow(() -> validator.validateCreateTransaction(validTransaction));
    }

    @Test
    public void testValidateCreateTransaction_invalidValue() {
        CreateTransactionDTO invalidTransaction = mock(CreateTransactionDTO.class);
        when(invalidTransaction.value()).thenReturn(-1000.0);
        when(invalidTransaction.scheduledDate()).thenReturn(new Date(System.currentTimeMillis() + 1000));
        when(invalidTransaction.accountFrom()).thenReturn("12345");
        when(invalidTransaction.accountTo()).thenReturn("54321");

        TransactionInvalid exception = assertThrows(TransactionInvalid.class, () -> validator.validateCreateTransaction(invalidTransaction));
        assertEquals("Invalid transaction", exception.getMessage());
    }

    @Test
    public void testValidateCreateTransaction_emptyAccountFrom() {
        CreateTransactionDTO invalidTransaction = mock(CreateTransactionDTO.class);
        when(invalidTransaction.value()).thenReturn(1000.0);
        when(invalidTransaction.scheduledDate()).thenReturn(new Date(System.currentTimeMillis() + 1000));
        when(invalidTransaction.accountFrom()).thenReturn("");
        when(invalidTransaction.accountTo()).thenReturn("54321");

        TransactionInvalid exception = assertThrows(TransactionInvalid.class, () -> validator.validateCreateTransaction(invalidTransaction));
        assertEquals("Invalid transaction", exception.getMessage());
    }

    @Test
    public void testValidateCreateTransaction_invalidScheduledDate() {
        CreateTransactionDTO invalidTransaction = mock(CreateTransactionDTO.class);
        when(invalidTransaction.value()).thenReturn(1000.0);
        when(invalidTransaction.scheduledDate()).thenReturn(new Date(System.currentTimeMillis() - 1000));
        when(invalidTransaction.accountFrom()).thenReturn("12345");
        when(invalidTransaction.accountTo()).thenReturn("54321");

        TransactionInvalid exception = assertThrows(TransactionInvalid.class, () -> validator.validateCreateTransaction(invalidTransaction));
        assertEquals("Invalid transaction", exception.getMessage());
    }

    @Test
    public void testValidateUpdateTransaction_validInput() {
        TransactionDTO validTransaction = mock(TransactionDTO.class);
        when(validTransaction.id()).thenReturn(1L);
        when(validTransaction.value()).thenReturn(1000.0);
        when(validTransaction.scheduledDate()).thenReturn(new Date(System.currentTimeMillis() + 1000));

        assertDoesNotThrow(() -> validator.validateUpdateTransaction(validTransaction));
    }

    @Test
    public void testValidateUpdateTransaction_nullId() {
        TransactionDTO invalidTransaction = mock(TransactionDTO.class);
        when(invalidTransaction.id()).thenReturn(null);

        TransactionInvalid exception = assertThrows(TransactionInvalid.class, () -> validator.validateUpdateTransaction(invalidTransaction));
        assertEquals("Transaction ID must not be null", exception.getMessage());
    }

    @Test
    public void testValidateUpdateTransaction_invalidValueAndDate() {
        TransactionDTO invalidTransaction = mock(TransactionDTO.class);
        when(invalidTransaction.id()).thenReturn(1L);
        when(invalidTransaction.value()).thenReturn(-1000.0);
        when(invalidTransaction.scheduledDate()).thenReturn(new Date(System.currentTimeMillis() - 1000));

        TransactionInvalid exception = assertThrows(TransactionInvalid.class, () -> validator.validateUpdateTransaction(invalidTransaction));
        assertEquals("Both value and scheduled date are invalid", exception.getMessage());
    }

    @Test
    public void testValidateUpdateTransaction_validValue_invalidDate() {
        TransactionDTO invalidTransaction = mock(TransactionDTO.class);
        when(invalidTransaction.id()).thenReturn(1L);
        when(invalidTransaction.value()).thenReturn(0.0);
        when(invalidTransaction.scheduledDate()).thenReturn(new Date(System.currentTimeMillis() - 1000));
        TransactionInvalid exception = assertThrows(TransactionInvalid.class, () -> validator.validateUpdateTransaction(invalidTransaction));
        assertEquals("Both value and scheduled date are invalid", exception.getMessage());
    }
}
