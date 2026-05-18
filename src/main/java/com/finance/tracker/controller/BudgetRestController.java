package com.finance.tracker.controller;

import com.finance.tracker.dao.BudgetDao;
import com.finance.tracker.dao.UserDao;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.*;

@RestController
@RequestMapping("/api/budget")
public class BudgetRestController {

    private final BudgetDao budgetDao;
    private final UserDao userDao;

    public BudgetRestController(BudgetDao budgetDao, UserDao userDao) {
        this.budgetDao = budgetDao;
        this.userDao = userDao;
    }

    @GetMapping("/{month}/{year}")
    public ResponseEntity<List<Map<String, Object>>> getBudget(
            @PathVariable int month, @PathVariable int year, Principal principal) {
        int userId = userDao.findByUsername(principal.getName()).getId();
        return ResponseEntity.ok(budgetDao.getBudgetVsActual(month, year, userId));
    }

    @PostMapping
    public ResponseEntity<String> saveBudget(@RequestBody Map<String, Object> body, Principal principal) {
        int userId = userDao.findByUsername(principal.getName()).getId();
        int categoryId = (int) body.get("categoryId");
        double limit = Double.parseDouble(body.get("limit").toString());
        int month = (int) body.get("month");
        int year = (int) body.get("year");
        budgetDao.saveBudget(categoryId, limit, month, year, userId);
        return ResponseEntity.ok("Budget saved");
    }
}