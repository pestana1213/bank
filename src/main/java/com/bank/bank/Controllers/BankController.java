package com.bank.bank.Controllers;

import com.bank.bank.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class BankController {

    @Autowired
    TransactionService transactionService;
}
