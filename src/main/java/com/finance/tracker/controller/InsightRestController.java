package com.finance.tracker.controller;

import com.finance.tracker.dao.UserDao;
import com.finance.tracker.model.Transaction;
import com.finance.tracker.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/insights")
public class InsightRestController {

    private final TransactionService transactionService;
    private final UserDao userDao;

    public InsightRestController(TransactionService transactionService, UserDao userDao) {
        this.transactionService = transactionService;
        this.userDao = userDao;
    }

    @GetMapping
    public ResponseEntity<Map<String, String>> getInsights(Principal principal) {
        int userId = userDao.findByUsername(principal.getName()).getId();
        List<Transaction> all = transactionService.getAllTransactions(userId);

        LocalDate now = LocalDate.now();
        
        List<Transaction> thisMonth = all.stream()
            .filter(t -> t.getTransactionDate().getYear() == now.getYear() && t.getTransactionDate().getMonthValue() == now.getMonthValue())
            .toList();

        List<Transaction> lastMonth = all.stream()
            .filter(t -> {
                LocalDate date = t.getTransactionDate();
                return (now.getMonthValue() == 1 ? (date.getYear() == now.getYear() - 1 && date.getMonthValue() == 12) : 
                        (date.getYear() == now.getYear() && date.getMonthValue() == now.getMonthValue() - 1));
            }).toList();

        double spentThisMonth = thisMonth.stream().filter(t -> "EXPENSE".equals(t.getType())).mapToDouble(t -> t.getAmount().doubleValue()).sum();
        double spentLastMonth = lastMonth.stream().filter(t -> "EXPENSE".equals(t.getType())).mapToDouble(t -> t.getAmount().doubleValue()).sum();
        double incomeThisMonth = thisMonth.stream().filter(t -> "INCOME".equals(t.getType())).mapToDouble(t -> t.getAmount().doubleValue()).sum();

        Map<String, String> insights = new HashMap<>();

        // 1. Month-over-Month Comparison
        if (spentLastMonth > 0) {
            double diff = spentThisMonth - spentLastMonth;
            double pct = (Math.abs(diff) / spentLastMonth) * 100;
            if (diff > 0) {
                insights.put("mom", String.format("You have spent %.1f%% more this month compared to last month. Keep an eye on expenses!", pct));
            } else {
                insights.put("mom", String.format("Great job! You have spent %.1f%% less this month compared to last month.", pct));
            }
        } else {
            insights.put("mom", "Not enough data from last month to compare spending.");
        }

        // 2. Top Spending Category
        Map<String, Double> categorySpending = thisMonth.stream()
            .filter(t -> "EXPENSE".equals(t.getType()))
            .collect(Collectors.groupingBy(Transaction::getCategoryName, Collectors.summingDouble(t -> t.getAmount().doubleValue())));
        
        if (!categorySpending.isEmpty()) {
            Map.Entry<String, Double> topCategory = Collections.max(categorySpending.entrySet(), Map.Entry.comparingByValue());
            insights.put("topCategory", String.format("Your highest expense this month is %s (₹%.2f).", topCategory.getKey(), topCategory.getValue()));
        } else {
            insights.put("topCategory", "No expenses recorded this month.");
        }

        // 3. Savings Rate
        if (incomeThisMonth > 0) {
            double saved = incomeThisMonth - spentThisMonth;
            if (saved > 0) {
                double rate = (saved / incomeThisMonth) * 100;
                insights.put("savingsRate", String.format("You are saving %.1f%% of your income this month. Keep it up!", rate));
            } else {
                insights.put("savingsRate", "Your expenses exceed your income this month! Time to review your budget.");
            }
        } else {
            insights.put("savingsRate", "Record some income to see your savings rate.");
        }

        return ResponseEntity.ok(insights);
    }
}
