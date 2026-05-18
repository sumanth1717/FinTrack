package com.finance.tracker.dao;

import com.finance.tracker.model.Category;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryDao {

    private final JdbcTemplate jdbcTemplate;

    public CategoryDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Category> findAll(int userId) {
        return jdbcTemplate.query("SELECT * FROM categories WHERE user_id = ?", (rs, rn) -> {
            Category c = new Category();
            c.setId(rs.getInt("id"));
            c.setName(rs.getString("name"));
            c.setType(rs.getString("type"));
            c.setUserId(rs.getInt("user_id"));
            return c;
        }, userId);
    }

    public void createDefaultCategories(int userId) {
        String sql = "INSERT INTO categories (name, type, user_id) VALUES (?, ?, ?)";
        jdbcTemplate.batchUpdate(sql, List.of(
            new Object[]{"Salary", "INCOME", userId},
            new Object[]{"Business", "INCOME", userId},
            new Object[]{"Investments", "INCOME", userId},
            new Object[]{"Food & Dining", "EXPENSE", userId},
            new Object[]{"Rent & Bills", "EXPENSE", userId},
            new Object[]{"Shopping", "EXPENSE", userId},
            new Object[]{"Entertainment", "EXPENSE", userId},
            new Object[]{"Transport", "EXPENSE", userId},
            new Object[]{"Health", "EXPENSE", userId},
            new Object[]{"Other", "EXPENSE", userId}
        ));
    }
}