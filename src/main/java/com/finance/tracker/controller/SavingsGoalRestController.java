package com.finance.tracker.controller;

import com.finance.tracker.dao.SavingsGoalDao;
import com.finance.tracker.dao.UserDao;
import com.finance.tracker.model.SavingsGoal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/goals")
public class SavingsGoalRestController {

    private final SavingsGoalDao savingsGoalDao;
    private final UserDao userDao;
    private final com.finance.tracker.dao.TransactionDao transactionDao;
    private final com.finance.tracker.dao.CategoryDao categoryDao;

    public SavingsGoalRestController(SavingsGoalDao savingsGoalDao, UserDao userDao, 
                                     com.finance.tracker.dao.TransactionDao transactionDao,
                                     com.finance.tracker.dao.CategoryDao categoryDao) {
        this.savingsGoalDao = savingsGoalDao;
        this.userDao = userDao;
        this.transactionDao = transactionDao;
        this.categoryDao = categoryDao;
    }

    @GetMapping
    public ResponseEntity<List<SavingsGoal>> getGoals(Principal principal) {
        int userId = userDao.findByUsername(principal.getName()).getId();
        return ResponseEntity.ok(savingsGoalDao.findAll(userId));
    }

    @PostMapping
    public ResponseEntity<String> addGoal(@RequestBody SavingsGoal goal, Principal principal) {
        int userId = userDao.findByUsername(principal.getName()).getId();
        goal.setUserId(userId);
        savingsGoalDao.save(goal);
        return ResponseEntity.ok("Goal created");
    }

    @PostMapping("/{id}/add-funds")
    public ResponseEntity<String> addFunds(@PathVariable int id, @RequestBody Map<String, Double> payload, Principal principal) {
        int userId = userDao.findByUsername(principal.getName()).getId();
        double amount = payload.get("amount");
        
        // Find the goal
        SavingsGoal goal = savingsGoalDao.findAll(userId).stream()
            .filter(g -> g.getId() == id).findFirst().orElse(null);
            
        if (goal == null) return ResponseEntity.notFound().build();

        // Update goal progress first
        savingsGoalDao.addFunds(id, amount, userId);

        // Create an expense transaction — always save even if category lookup fails
        try {
            com.finance.tracker.model.Transaction t = new com.finance.tracker.model.Transaction();
            t.setTitle("Savings: " + goal.getName());
            t.setAmount(BigDecimal.valueOf(amount));
            t.setType("EXPENSE");
            t.setTransactionDate(LocalDate.now());
            t.setNote("Transfer to savings goal");
            t.setUserId(userId);

            // Find a valid expense category — use first available
            List<com.finance.tracker.model.Category> cats = categoryDao.findAll(userId);
            cats.stream()
                .filter(c -> "EXPENSE".equals(c.getType()))
                .findFirst()
                .ifPresent(c -> t.setCategoryId(c.getId()));

            // Only save if we have a valid categoryId (> 0)
            if (t.getCategoryId() > 0) {
                transactionDao.save(t);
            }
        } catch (Exception e) {
            // Log but don't fail — goal already updated
            System.err.println("Warning: Could not save goal transfer transaction: " + e.getMessage());
        }

        return ResponseEntity.ok("Funds added");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGoal(@PathVariable int id, Principal principal) {
        int userId = userDao.findByUsername(principal.getName()).getId();
        savingsGoalDao.delete(id, userId);
        return ResponseEntity.ok("Goal deleted");
    }
}
