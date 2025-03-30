package com.bank.bank.DTO;

import java.util.Date;

public record TransactionDTOResponse(Double value, Date scheduledDate, Double fee) {}
