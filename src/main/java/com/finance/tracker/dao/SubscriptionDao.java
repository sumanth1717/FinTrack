package com.finance.tracker.dao;

import com.finance.tracker.model.Subscription;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class SubscriptionDao {

    private final JdbcTemplate jdbcTemplate;

    public SubscriptionDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Subscription> rowMapper = new RowMapper<>() {
        @Override
        public Subscription mapRow(ResultSet rs, int rowNum) throws SQLException {
            Subscription sub = new Subscription();
            sub.setId(rs.getInt("id"));
            sub.setUserId(rs.getInt("user_id"));
            sub.setTitle(rs.getString("title"));
            sub.setAmount(rs.getBigDecimal("amount"));
            
            int catId = rs.getInt("category_id");
            if (!rs.wasNull()) {
                sub.setCategoryId(catId);
            }
            
            sub.setFrequency(rs.getString("frequency"));
            if (rs.getDate("next_due_date") != null) {
                sub.setNextDueDate(rs.getDate("next_due_date").toLocalDate());
            }
            return sub;
        }
    };

    public List<Subscription> findAll(int userId) {
        return jdbcTemplate.query("SELECT * FROM subscriptions WHERE user_id = ? ORDER BY next_due_date ASC", rowMapper, userId);
    }

    public List<Subscription> findDueSubscriptions(String dateStr) {
        return jdbcTemplate.query("SELECT * FROM subscriptions WHERE next_due_date <= ?", rowMapper, dateStr);
    }

    public void save(Subscription sub) {
        String sql = "INSERT INTO subscriptions (user_id, title, amount, category_id, frequency, next_due_date) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, sub.getUserId(), sub.getTitle(), sub.getAmount(), sub.getCategoryId(), sub.getFrequency(), sub.getNextDueDate());
    }

    public void updateNextDueDate(int id, String newDateStr) {
        jdbcTemplate.update("UPDATE subscriptions SET next_due_date = ? WHERE id = ?", newDateStr, id);
    }

    public void delete(int id, int userId) {
        jdbcTemplate.update("DELETE FROM subscriptions WHERE id = ? AND user_id = ?", id, userId);
    }
}
