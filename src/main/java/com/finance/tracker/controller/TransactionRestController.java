package com.finance.tracker.controller;

import com.finance.tracker.model.Transaction;
import com.finance.tracker.service.TransactionService;
import com.finance.tracker.dao.CategoryDao;
import com.finance.tracker.dao.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class TransactionRestController {

    private static final Logger logger = LoggerFactory.getLogger(TransactionRestController.class);
    private final TransactionService transactionService;
    private final CategoryDao categoryDao;
    private final UserDao userDao;

    public TransactionRestController(TransactionService transactionService, CategoryDao categoryDao, UserDao userDao) {
        this.transactionService = transactionService;
        this.categoryDao = categoryDao;
        this.userDao = userDao;
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<Transaction>> getAllTransactions(Principal principal) {
        int userId = userDao.findByUsername(principal.getName()).getId();
        logger.info("REST: fetching all transactions");
        return ResponseEntity.ok(transactionService.getAllTransactions(userId));
    }

    @PostMapping("/transactions")
    public ResponseEntity<String> addTransaction(@RequestBody Transaction transaction, Principal principal) {
        int userId = userDao.findByUsername(principal.getName()).getId();
        transaction.setUserId(userId);
        logger.info("REST: adding transaction {}", transaction.getTitle());
        transactionService.addTransaction(transaction);
        return ResponseEntity.ok("Transaction added successfully");
    }

    @PutMapping("/transactions/{id}")
    public ResponseEntity<String> updateTransaction(@PathVariable int id, @RequestBody Transaction transaction, Principal principal) {
        int userId = userDao.findByUsername(principal.getName()).getId();
        logger.info("REST: updating transaction id {}", id);
        transaction.setId(id);
        transaction.setUserId(userId);
        transactionService.updateTransaction(transaction);
        return ResponseEntity.ok("Transaction updated successfully");
    }

    @DeleteMapping("/transactions/{id}")
    public ResponseEntity<String> deleteTransaction(@PathVariable int id, Principal principal) {
        int userId = userDao.findByUsername(principal.getName()).getId();
        logger.info("REST: deleting transaction id {}", id);
        transactionService.deleteTransaction(id, userId);
        return ResponseEntity.ok("Transaction deleted successfully");
    }

    @PostMapping("/transactions/import")
    public ResponseEntity<String> importTransactions(@RequestParam("file") MultipartFile file, Principal principal) {
        int userId = userDao.findByUsername(principal.getName()).getId();
        if (file.isEmpty()) return ResponseEntity.badRequest().body("File is empty");
        
        // Find default category ID ("Other" or first available)
        List<com.finance.tracker.model.Category> cats = categoryDao.findAll(userId);
        int defaultExpCat = cats.stream().filter(c -> "EXPENSE".equals(c.getType()) && c.getName().equalsIgnoreCase("Other"))
            .map(c -> c.getId()).findFirst().orElse(cats.stream().filter(c -> "EXPENSE".equals(c.getType())).map(c -> c.getId()).findFirst().orElse(0));
        int defaultIncCat = cats.stream().filter(c -> "INCOME".equals(c.getType()) && c.getName().equalsIgnoreCase("Other"))
            .map(c -> c.getId()).findFirst().orElse(cats.stream().filter(c -> "INCOME".equals(c.getType())).map(c -> c.getId()).findFirst().orElse(0));

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            boolean firstLine = true;
            while ((line = reader.readLine()) != null) {
                if (firstLine) { firstLine = false; continue; } // skip header
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    Transaction t = new Transaction();
                    t.setUserId(userId);
                    t.setTransactionDate(LocalDate.parse(parts[0].trim()));
                    t.setTitle(parts[1].trim());
                    t.setAmount(new BigDecimal(parts[2].trim()));
                    t.setType(parts[3].trim().toUpperCase());
                    
                    if (t.getType().equals("INCOME")) {
                        t.setCategoryId(defaultIncCat);
                    } else {
                        t.setCategoryId(defaultExpCat);
                    }
                    if (parts.length >= 5) t.setNote(parts[4].trim());
                    transactionService.addTransaction(t);
                }
            }
            return ResponseEntity.ok("Transactions imported successfully");
        } catch (Exception e) {
            logger.error("Error parsing CSV", e);
            return ResponseEntity.badRequest().body("Invalid CSV format. Please use Date,Title,Amount,Type");
        }
    }

    @GetMapping("/summary")
    public ResponseEntity<Map<String, Double>> getSummary(Principal principal) {
        int userId = userDao.findByUsername(principal.getName()).getId();
        List<Transaction> transactions = transactionService.getAllTransactions(userId);
        double totalIncome = transactions.stream()
            .filter(t -> t.getType().equals("INCOME"))
            .mapToDouble(t -> t.getAmount().doubleValue()).sum();
        double totalExpense = transactions.stream()
            .filter(t -> t.getType().equals("EXPENSE"))
            .mapToDouble(t -> t.getAmount().doubleValue()).sum();
        Map<String, Double> summary = new HashMap<>();
        summary.put("totalIncome", totalIncome);
        summary.put("totalExpense", totalExpense);
        summary.put("balance", totalIncome - totalExpense);
        return ResponseEntity.ok(summary);
    }

    @GetMapping("/categories")
    public ResponseEntity<Object> getCategories(Principal principal) {
        int userId = userDao.findByUsername(principal.getName()).getId();
        return ResponseEntity.ok(categoryDao.findAll(userId));
    }

    @GetMapping("/monthly-trend")
    public ResponseEntity<List<Map<String, Object>>> getMonthlyTrend(Principal principal) {
        int userId = userDao.findByUsername(principal.getName()).getId();
        logger.info("REST: fetching monthly trend");
        List<Transaction> all = transactionService.getAllTransactions(userId);
        Map<String, double[]> monthMap = new LinkedHashMap<>();

        all.forEach(t -> {
            String key = t.getTransactionDate().getYear() + "-"
                + String.format("%02d", t.getTransactionDate().getMonthValue());
            monthMap.putIfAbsent(key, new double[]{0, 0});
            if (t.getType().equals("INCOME"))
                monthMap.get(key)[0] += t.getAmount().doubleValue();
            else
                monthMap.get(key)[1] += t.getAmount().doubleValue();
        });

        List<Map<String, Object>> result = new ArrayList<>();
        monthMap.forEach((month, vals) -> {
            Map<String, Object> entry = new HashMap<>();
            entry.put("month", month);
            entry.put("income", vals[0]);
            entry.put("expense", vals[1]);
            result.add(entry);
        });

        result.sort(Comparator.comparing(m -> m.get("month").toString()));
        return ResponseEntity.ok(result);
    }

    @GetMapping("/transactions/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable int id, Principal principal) {
        int userId = userDao.findByUsername(principal.getName()).getId();
        return ResponseEntity.ok(transactionService.getById(id, userId));
    }
}