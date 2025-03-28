package com.bank.bank.DTO;

import java.util.Date;

public record TransactionDTO (Long id, Double value, Date scheduledDate) {
}
