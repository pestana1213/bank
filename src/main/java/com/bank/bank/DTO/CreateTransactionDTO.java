package com.bank.bank.DTO;

import java.util.Date;

public record CreateTransactionDTO (Double value, Date scheduledDate) {
}
