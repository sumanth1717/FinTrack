package com.finance.tracker.service;

import com.finance.tracker.dao.TransactionDao;
import com.finance.tracker.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);
    private final TransactionDao transactionDao;

    public TransactionService(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    public List<Transaction> getAllTransactions(int userId) {
        logger.info("Fetching all transactions for user {}", userId);
        return transactionDao.findAll(userId);
    }

    public void addTransaction(Transaction t) {
        logger.info("Adding transaction: {}", t.getTitle());
        transactionDao.save(t);
    }

    public void deleteTransaction(int id, int userId) {
        logger.info("Deleting transaction id: {}", id);
        transactionDao.delete(id, userId);
    }

    public Transaction getById(int id, int userId) {
        return transactionDao.findById(id, userId);
    }

    public void updateTransaction(Transaction t) {
        logger.info("Updating transaction id: {}", t.getId());
        transactionDao.update(t);
    }
}