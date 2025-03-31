package com.bank.bank.Service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@SpringBootTest
public class CalculatorTest {

    private final Calculator calculator = new Calculator();

    @Test
    void calculateFee_InvalidValue_ThrowsException() {
        Date validDate = createDate(LocalDate.now());
        assertThrows(IllegalArgumentException.class, () -> calculator.calculateFee(-1, validDate));
        assertThrows(IllegalArgumentException.class, () -> calculator.calculateFee(0, validDate));
    }

    @Test
    void calculateFee_NullDate_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> calculator.calculateFee(100, null));
    }

    @Test
    void calculateFee_SmallTransferToday_ReturnsCorrectFee() {
        Date today = createDate(LocalDate.now());
        double value = 500;
        double expected = (value * 0.03) + 3;
        assertEquals(expected, calculator.calculateFee(value, today), 0.001);
    }

    @Test
    void calculateFee_SmallTransferNotToday_ReturnsZero() {
        Date tomorrow = createDate(LocalDate.now().plusDays(1));
        assertEquals(0, calculator.calculateFee(500, tomorrow), 0.001);
    }

    @Test
    void calculateFee_MediumTransferWithinDays_Returns9Percent() {
        Date date = createDate(LocalDate.now().plusDays(5));
        double value = 1500;
        assertEquals(value * 0.09, calculator.calculateFee(value, date), 0.001);
    }

    @Test
    void calculateFee_MediumTransferSameDay_ReturnsZero() {
        Date today = createDate(LocalDate.now());
        assertEquals(0, calculator.calculateFee(1500, today), 0.001);
    }

    @Test
    void calculateFee_MediumTransferOutsideDays_ReturnsZero() {
        Date date = createDate(LocalDate.now().plusDays(11));
        assertEquals(0, calculator.calculateFee(1500, date), 0.001);
    }

    @Test
    void calculateFee_LargeTransfer11to20Days_Returns8_2Percent() {
        Date date = createDate(LocalDate.now().plusDays(15));
        double value = 2500;
        assertEquals(value * 0.082, calculator.calculateFee(value, date), 0.001);
    }

    @Test
    void calculateFee_LargeTransfer21Days_Returns6_9Percent() {
        Date date = createDate(LocalDate.now().plusDays(21));
        assertEquals(2500 * 0.069, calculator.calculateFee(2500, date), 0.001);
    }

    @Test
    void calculateFee_LargeTransfer31Days_Returns4_7Percent() {
        Date date = createDate(LocalDate.now().plusDays(31));
        assertEquals(2500 * 0.047, calculator.calculateFee(2500, date), 0.001);
    }

    @Test
    void calculateFee_LargeTransfer50Days_Returns1_7Percent() {
        Date date = createDate(LocalDate.now().plusDays(50));
        assertEquals(2500 * 0.017, calculator.calculateFee(2500, date), 0.001);
    }

    @Test
    void calculateFee_LargeTransfer5Days_ReturnsZero() {
        Date date = createDate(LocalDate.now().plusDays(5));
        assertEquals(0, calculator.calculateFee(2500, date), 0.001);
    }

    @Test
    void calculateFee_Value1000Today_ReturnsSmallFee() {
        Date today = createDate(LocalDate.now());
        double expected = (1000 * 0.03) + 3;
        assertEquals(expected, calculator.calculateFee(1000, today), 0.001);
    }

    @Test
    void calculateFee_Value2001_15Days_Returns8_2Percent() {
        Date date = createDate(LocalDate.now().plusDays(15));
        assertEquals(2001 * 0.082, calculator.calculateFee(2001, date), 0.001);
    }

    @Test
    void calculateFee_SmallTransferYesterday_ReturnsZero() {
        Date yesterday = createDate(LocalDate.now().minusDays(1));
        assertEquals(0, calculator.calculateFee(500, yesterday), 0.001);
    }

    @Test
    void calculateFee_MediumTransferPastDate_ReturnsZero() {
        Date pastDate = createDate(LocalDate.now().minusDays(5));
        assertEquals(0, calculator.calculateFee(1500, pastDate), 0.001);
    }

    @Test
    void calculateFee_LargeTransferPastDate_ReturnsZero() {
        Date pastDate = createDate(LocalDate.now().minusDays(15));
        assertEquals(0, calculator.calculateFee(2500, pastDate), 0.001);
    }

    private Date createDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}