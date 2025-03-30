package com.bank.bank.Service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class Calculator {

    private static final double SMALL_TRANSFER_FEE_PERCENTAGE = 0.03;
    private static final double SMALL_TRANSFER_FEE_FIXED = 3;
    private static final double MEDIUM_TRANSFER_FEE_PERCENTAGE = 0.09;
    private static final double LARGE_FIRST_TRANSFER_FEE_PERCENTAGE = 0.082;
    private static final double LARGE_SECOND_TRANSFER_FEE_PERCENTAGE = 0.069;
    private static final double LARGE_THIRD_TRANSFER_FEE_PERCENTAGE = 0.047;
    private static final double LARGE_FOURTH_TRANSFER_FEE_PERCENTAGE = 0.017;

    public double calculateFee(double value, Date scheduledDate) {
        if (value <= 0 || scheduledDate == null) {
            throw new IllegalArgumentException("Invalid input: value must be positive and scheduled date cannot be null");
        }

        long diffDays = calculateDateDifferenceInDays(scheduledDate);

        if (value >= 0 && value <= 1000) {
            return calculateSmallTransferFee(value, scheduledDate);
        } else if (value > 1000 && value <= 2000) {
            return calculateMediumTransferFee(value, diffDays);
        }

        return calculateLargeTransferFee(value, diffDays);
    }

    private long calculateDateDifferenceInDays(Date scheduledDate) {
        LocalDate today = LocalDate.now();
        LocalDate scheduledLocalDate = scheduledDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        return ChronoUnit.DAYS.between(today, scheduledLocalDate);
    }

    private double calculateSmallTransferFee(double value, Date scheduledDate) {
        LocalDate today = LocalDate.now();
        LocalDate scheduledLocalDate = scheduledDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        if (scheduledLocalDate.isEqual(today)) {
            return (value * SMALL_TRANSFER_FEE_PERCENTAGE) + SMALL_TRANSFER_FEE_FIXED;
        }
        return 0;
    }

    private double calculateMediumTransferFee(double value, long diffDays) {
        if (diffDays >= 1 && diffDays <= 10) {
            return value * MEDIUM_TRANSFER_FEE_PERCENTAGE;
        }
        return 0;
    }

    private double calculateLargeTransferFee(double value, long diffDays) {
        if (diffDays >= 11 && diffDays <= 20) {
            return value * LARGE_FIRST_TRANSFER_FEE_PERCENTAGE;
        } else if (diffDays >= 21 && diffDays <= 30) {
            return value * LARGE_SECOND_TRANSFER_FEE_PERCENTAGE;
        } else if (diffDays >= 31 && diffDays <= 40) {
            return value * LARGE_THIRD_TRANSFER_FEE_PERCENTAGE;
        } else if (diffDays > 40) {
            return value * LARGE_FOURTH_TRANSFER_FEE_PERCENTAGE;
        }
        return 0;
    }
}
