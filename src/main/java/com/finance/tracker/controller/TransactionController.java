package com.finance.tracker.controller;

import com.finance.tracker.dao.CategoryDao;
import com.finance.tracker.model.Transaction;
import com.finance.tracker.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.finance.tracker.dao.UserDao;
import java.security.Principal;

@Controller
public class TransactionController {

    private final TransactionService transactionService;
    private final CategoryDao categoryDao;
    private final UserDao userDao;

    public TransactionController(TransactionService transactionService, CategoryDao categoryDao, UserDao userDao) {
        this.transactionService = transactionService;
        this.categoryDao = categoryDao;
        this.userDao = userDao;
    }

    @GetMapping("/")
    public String dashboard(Model model, Principal principal) {
        int userId = userDao.findByUsername(principal.getName()).getId();
        var transactions = transactionService.getAllTransactions(userId);
        var totalIncome = transactions.stream()
            .filter(t -> t.getType().equals("INCOME"))
            .mapToDouble(t -> t.getAmount().doubleValue()).sum();
        var totalExpense = transactions.stream()
            .filter(t -> t.getType().equals("EXPENSE"))
            .mapToDouble(t -> t.getAmount().doubleValue()).sum();

        model.addAttribute("transactions", transactions);
        model.addAttribute("totalIncome", totalIncome);
        model.addAttribute("totalExpense", totalExpense);
        model.addAttribute("balance", totalIncome - totalExpense);
        model.addAttribute("categories", categoryDao.findAll(userId));
        model.addAttribute("newTransaction", new Transaction());
        return "dashboard";
    }

    @PostMapping("/transaction/add")
    public String addTransaction(@ModelAttribute Transaction transaction, Principal principal) {
        int userId = userDao.findByUsername(principal.getName()).getId();
        transaction.setUserId(userId);
        transactionService.addTransaction(transaction);
        return "redirect:/";
    }

    @GetMapping("/transaction/delete/{id}")
    public String deleteTransaction(@PathVariable int id, Principal principal) {
        int userId = userDao.findByUsername(principal.getName()).getId();
        transactionService.deleteTransaction(id, userId);
        return "redirect:/";
    }
}