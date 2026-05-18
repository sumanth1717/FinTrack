package com.finance.tracker.service;

import com.finance.tracker.dao.TransactionDao;
import com.finance.tracker.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionDao transactionDao;

    @InjectMocks
    private TransactionService transactionService;

    private Transaction t1;
    private Transaction t2;
    private final int USER_ID = 1;

    @BeforeEach
    void setUp() {
        t1 = new Transaction();
        t1.setId(1);
        t1.setUserId(USER_ID);
        t1.setTitle("Salary");
        t1.setAmount(new BigDecimal("5000.00"));
        t1.setType("INCOME");
        t1.setTransactionDate(LocalDate.now());

        t2 = new Transaction();
        t2.setId(2);
        t2.setUserId(USER_ID);
        t2.setTitle("Groceries");
        t2.setAmount(new BigDecimal("200.00"));
        t2.setType("EXPENSE");
        t2.setTransactionDate(LocalDate.now());
    }

    @Test
    void testGetAllTransactions() {
        // Arrange
        when(transactionDao.findAll(USER_ID)).thenReturn(Arrays.asList(t1, t2));

        // Act
        List<Transaction> result = transactionService.getAllTransactions(USER_ID);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Salary", result.get(0).getTitle());
        verify(transactionDao, times(1)).findAll(USER_ID);
    }

    @Test
    void testAddTransaction() {
        // Act
        transactionService.addTransaction(t1);

        // Assert
        verify(transactionDao, times(1)).save(t1);
    }

    @Test
    void testUpdateTransaction() {
        // Arrange
        t1.setAmount(new BigDecimal("5500.00"));

        // Act
        transactionService.updateTransaction(t1);

        // Assert
        verify(transactionDao, times(1)).update(t1);
    }

    @Test
    void testDeleteTransaction() {
        // Act
        transactionService.deleteTransaction(1, USER_ID);

        // Assert
        verify(transactionDao, times(1)).delete(1, USER_ID);
    }
}
