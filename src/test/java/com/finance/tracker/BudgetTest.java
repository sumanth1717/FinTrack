package com.finance.tracker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
public class BudgetTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testSaveBudget() {
        System.out.println("Executing BudgetDao.saveBudget...");
        try {
            int categoryId = 4, month = 5, year = 2026, userId = 1;
            double limit = 1000.5;
            String check = "SELECT COUNT(*) FROM budgets WHERE category_id=? AND month=? AND year=? AND user_id=?";
            int count = jdbcTemplate.queryForObject(check, Integer.class, categoryId, month, year, userId);
            System.out.println("COUNT: " + count);
            if (count > 0) {
                jdbcTemplate.update("UPDATE budgets SET limit_amount=? WHERE category_id=? AND month=? AND year=? AND user_id=?", limit, categoryId, month, year, userId);
            } else {
                jdbcTemplate.update("INSERT INTO budgets (category_id, limit_amount, month, year, user_id) VALUES (?,?,?,?,?)", categoryId, limit, month, year, userId);
            }
            System.out.println("SUCCESS");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
