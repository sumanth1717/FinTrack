package com.finance.tracker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;

@SpringBootTest(classes = com.finance.tracker.TrackerApplication.class)
public class JsonTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void test() throws Exception {
        int month = 5, year = 2026, userId = 1;
        String sql = "SELECT c.name as category, COALESCE(b.limit_amount, 0) as budget_limit, COALESCE(SUM(t.amount), 0) as actual_spent FROM categories c LEFT JOIN budgets b ON c.id = b.category_id AND b.month = ? AND b.year = ? LEFT JOIN transactions t ON c.id = t.category_id AND MONTH(t.transaction_date) = ? AND YEAR(t.transaction_date) = ? AND t.type = 'EXPENSE' WHERE c.type = 'EXPENSE' AND c.user_id = ? GROUP BY c.id, c.name, b.limit_amount";
        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql, month, year, month, year, userId);
        ObjectMapper mapper = new ObjectMapper();
        System.out.println("JSON: " + mapper.writeValueAsString(result));
    }
}
