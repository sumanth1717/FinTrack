package com.finance.tracker.dao;

import com.finance.tracker.model.Transaction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TransactionDao {

    private final JdbcTemplate jdbcTemplate;

    public TransactionDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Transaction> rowMapper = new RowMapper<>() {
        @Override
        public Transaction mapRow(ResultSet rs, int rowNum) throws SQLException {
            Transaction t = new Transaction();
            t.setId(rs.getInt("id"));
            t.setTitle(rs.getString("title"));
            t.setAmount(rs.getBigDecimal("amount"));
            t.setType(rs.getString("type"));
            t.setCategoryId(rs.getInt("category_id"));
            t.setCategoryName(rs.getString("category_name"));
            t.setTransactionDate(rs.getDate("transaction_date").toLocalDate());
            t.setNote(rs.getString("note"));
            t.setUserId(rs.getInt("user_id"));
            return t;
        }
    };

    public List<Transaction> findAll(int userId) {
        String sql = "SELECT t.*, c.name as category_name FROM transactions t " +
                     "LEFT JOIN categories c ON t.category_id = c.id " +
                     "WHERE t.user_id = ? " +
                     "ORDER BY t.transaction_date DESC, t.id DESC";
        return jdbcTemplate.query(sql, rowMapper, userId);
    }

    public void save(Transaction t) {
        String sql = "INSERT INTO transactions (title, amount, type, category_id, transaction_date, note, user_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, t.getTitle(), t.getAmount(), t.getType(),
                t.getCategoryId(), t.getTransactionDate(), t.getNote(), t.getUserId());
    }

    public void delete(int id, int userId) {
        jdbcTemplate.update("DELETE FROM transactions WHERE id = ? AND user_id = ?", id, userId);
    }

    public Transaction findById(int id, int userId) {
        String sql = "SELECT t.*, c.name as category_name FROM transactions t " +
                     "LEFT JOIN categories c ON t.category_id = c.id WHERE t.id = ? AND t.user_id = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, id, userId);
    }

    public void update(Transaction t) {
        String sql = "UPDATE transactions SET title=?, amount=?, type=?, category_id=?, transaction_date=?, note=? WHERE id=? AND user_id=?";
        jdbcTemplate.update(sql, t.getTitle(), t.getAmount(), t.getType(),
                t.getCategoryId(), t.getTransactionDate(), t.getNote(), t.getId(), t.getUserId());
    }
}