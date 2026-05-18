package com.finance.tracker.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class BudgetDao {

    private final JdbcTemplate jdbcTemplate;

    public BudgetDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Map<String, Object>> getBudgetVsActual(int month, int year, int userId) {
        String sql = """
            SELECT c.name as category,
                   COALESCE(b.limit_amount, 0) as budget_limit,
                   COALESCE(SUM(t.amount), 0) as actual_spent
            FROM categories c
            LEFT JOIN budgets b ON c.id = b.category_id AND b.month = ? AND b.year = ?
            LEFT JOIN transactions t ON c.id = t.category_id
                AND MONTH(t.transaction_date) = ? AND YEAR(t.transaction_date) = ?
                AND t.type = 'EXPENSE'
            WHERE c.type = 'EXPENSE'
            AND c.user_id = ?
            GROUP BY c.id, c.name, b.limit_amount
            """;
        return jdbcTemplate.queryForList(sql, month, year, month, year, userId);
    }

    public void saveBudget(int categoryId, double limit, int month, int year, int userId) {
        String check = "SELECT COUNT(*) FROM budgets WHERE category_id=? AND month=? AND year=? AND user_id=?";
        int count = jdbcTemplate.queryForObject(check, Integer.class, categoryId, month, year, userId);
        if (count > 0) {
            jdbcTemplate.update("UPDATE budgets SET limit_amount=? WHERE category_id=? AND month=? AND year=? AND user_id=?",
                limit, categoryId, month, year, userId);
        } else {
            jdbcTemplate.update("INSERT INTO budgets (category_id, limit_amount, month, year, user_id) VALUES (?,?,?,?,?)",
                categoryId, limit, month, year, userId);
        }
    }
}