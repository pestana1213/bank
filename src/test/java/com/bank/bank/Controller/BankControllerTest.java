package com.bank.bank.Controller;

import com.bank.bank.Controllers.BankController;
import com.bank.bank.DTO.CreateTransactionDTO;
import com.bank.bank.DTO.TransactionDTO;
import com.bank.bank.DTO.TransactionDTOResponse;
import com.bank.bank.Service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class BankControllerTest {

    @Mock
    private TransactionService transactionService;

    private MockMvc mockMvc;

    @InjectMocks
    private BankController bankController;

    private TransactionDTOResponse transactionDTOResponse;
    private CreateTransactionDTO createTransactionDTO;
    private TransactionDTO transactionDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bankController).build();

        transactionDTOResponse = new TransactionDTOResponse(1000.0, new Date(), 10.0, "1", "2");
        createTransactionDTO = new CreateTransactionDTO(1000.0, new Date(), "1", "2");
        transactionDTO = new TransactionDTO(1L, 1200.0, new Date());
    }

    @Test
    void testGetAllTransactions() throws Exception {
        when(transactionService.getAllTransactions()).thenReturn(List.of(transactionDTOResponse));

        mockMvc.perform(get("/transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].value").value(1000.0))
                .andExpect(jsonPath("$[0].fee").value(10.0))
                .andExpect(jsonPath("$[0].accountFrom").value("1"))
                .andExpect(jsonPath("$[0].accountTo").value("2"));
        ;

        verify(transactionService, times(1)).getAllTransactions();
    }

    @Test
    void testGetTransactionById() throws Exception {
        when(transactionService.getTransactionByIdDTO(1L)).thenReturn(transactionDTOResponse);

        mockMvc.perform(get("/transactions/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").value(1000.0))
                .andExpect(jsonPath("$.fee").value(10.0))
                .andExpect(jsonPath("$.accountFrom").value("1"))
                .andExpect(jsonPath("$.accountTo").value("2"));

        verify(transactionService, times(1)).getTransactionByIdDTO(1L);
    }

    @Test
    void testGetScheduling() throws Exception {
        Date scheduledDate = new Date();
        when(transactionService.getScheduling(1L)).thenReturn(scheduledDate);

        mockMvc.perform(get("/transactions/scheduling/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(scheduledDate.getTime())));

        verify(transactionService, times(1)).getScheduling(1L);
    }

    @Test
    void testCreateTransaction() throws Exception {
        when(transactionService.createTransaction(any(CreateTransactionDTO.class))).thenReturn(transactionDTOResponse);

        mockMvc.perform(post("/transactions")
                        .contentType("application/json")
                        .content("{\"value\": 1000.0, \"scheduledDate\": \"2025-03-31T00:00:00\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.value").value(1000.0))
                .andExpect(jsonPath("$.fee").value(10.0))
                .andExpect(jsonPath("$.accountFrom").value("1"))
                .andExpect(jsonPath("$.accountTo").value("2"));

        verify(transactionService, times(1)).createTransaction(any(CreateTransactionDTO.class));
    }

    @Test
    void testDeleteTransaction() throws Exception {
        doNothing().when(transactionService).deleteTransaction(1L);

        mockMvc.perform(delete("/transactions/{id}", 1L))
                .andExpect(status().isOk());

        verify(transactionService, times(1)).deleteTransaction(1L);
    }

    @Test
    void testPatchTransaction() throws Exception {
        when(transactionService.updateTransaction(any(TransactionDTO.class))).thenReturn(transactionDTOResponse);

        mockMvc.perform(patch("/transactions")
                        .contentType("application/json")
                        .content("{\"id\": 1, \"value\": 1200.0, \"scheduledDate\": \"2025-03-31T00:00:00\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").value(1000.0))
                .andExpect(jsonPath("$.fee").value(10.0))
                .andExpect(jsonPath("$.accountFrom").value("1"))
                .andExpect(jsonPath("$.accountTo").value("2"));

        verify(transactionService, times(1)).updateTransaction(any(TransactionDTO.class));
    }
}
