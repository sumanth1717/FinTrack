package com.finance.tracker.dao;

import com.finance.tracker.model.SavingsGoal;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SavingsGoalDao {

    private final JdbcTemplate jdbcTemplate;

    public SavingsGoalDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<SavingsGoal> rowMapper = (rs, rowNum) -> {
        SavingsGoal goal = new SavingsGoal();
        goal.setId(rs.getInt("id"));
        goal.setUserId(rs.getInt("user_id"));
        goal.setName(rs.getString("name"));
        goal.setTargetAmount(rs.getBigDecimal("target_amount"));
        goal.setCurrentAmount(rs.getBigDecimal("current_amount"));
        if (rs.getDate("target_date") != null) {
            goal.setTargetDate(rs.getDate("target_date").toLocalDate());
        }
        return goal;
    };

    public List<SavingsGoal> findAll(int userId) {
        String sql = "SELECT * FROM savings_goals WHERE user_id = ? ORDER BY target_date ASC";
        return jdbcTemplate.query(sql, rowMapper, userId);
    }

    public void save(SavingsGoal goal) {
        String sql = "INSERT INTO savings_goals (user_id, name, target_amount, current_amount, target_date) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, goal.getUserId(), goal.getName(), goal.getTargetAmount(), 
                goal.getCurrentAmount() != null ? goal.getCurrentAmount() : 0, goal.getTargetDate());
    }

    public void addFunds(int goalId, double amount, int userId) {
        String sql = "UPDATE savings_goals SET current_amount = current_amount + ? WHERE id = ? AND user_id = ?";
        jdbcTemplate.update(sql, amount, goalId, userId);
    }

    public void delete(int goalId, int userId) {
        String sql = "DELETE FROM savings_goals WHERE id = ? AND user_id = ?";
        jdbcTemplate.update(sql, goalId, userId);
    }
}
