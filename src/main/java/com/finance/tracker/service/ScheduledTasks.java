package com.finance.tracker.service;

import com.finance.tracker.dao.SubscriptionDao;
import com.finance.tracker.dao.TransactionDao;
import com.finance.tracker.model.Subscription;
import com.finance.tracker.model.Transaction;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ScheduledTasks {

    private final SubscriptionDao subscriptionDao;
    private final TransactionDao transactionDao;

    public ScheduledTasks(SubscriptionDao subscriptionDao, TransactionDao transactionDao) {
        this.subscriptionDao = subscriptionDao;
        this.transactionDao = transactionDao;
    }

    // Run every day at midnight
    @Scheduled(cron = "0 0 0 * * ?")
    public void processDueSubscriptions() {
        LocalDate today = LocalDate.now();
        List<Subscription> dueSubscriptions = subscriptionDao.findDueSubscriptions(today.toString());

        for (Subscription sub : dueSubscriptions) {
            // Create the transaction
            Transaction t = new Transaction();
            t.setUserId(sub.getUserId());
            t.setTitle("Auto-Pay: " + sub.getTitle());
            t.setAmount(sub.getAmount());
            t.setType("EXPENSE");
            t.setTransactionDate(today);
            t.setNote("Automated recurring subscription");
            if (sub.getCategoryId() != null) {
                t.setCategoryId(sub.getCategoryId());
            }
            
            transactionDao.save(t);

            // Calculate next due date
            LocalDate nextDue = sub.getNextDueDate();
            if ("YEARLY".equalsIgnoreCase(sub.getFrequency())) {
                nextDue = nextDue.plusYears(1);
            } else {
                nextDue = nextDue.plusMonths(1); // Default to monthly
            }

            // Update subscription
            subscriptionDao.updateNextDueDate(sub.getId(), nextDue.toString());
        }
        
        if (!dueSubscriptions.isEmpty()) {
            System.out.println("Processed " + dueSubscriptions.size() + " automated subscriptions.");
        }
    }
}
