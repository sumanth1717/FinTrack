package com.finance.tracker.scratch;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import java.sql.Connection;
public class TestDb {
    public static void main(String[] args) throws Exception {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("org.h2.Driver");
        ds.setUrl("jdbc:h2:mem:test;MODE=MySQL;NON_KEYWORDS=MONTH,YEAR,TYPE;DB_CLOSE_DELAY=-1");
        ds.setUsername("sa");
        ds.setPassword("password");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
        
        jdbcTemplate.execute("CREATE TABLE users (id INT AUTO_INCREMENT PRIMARY KEY, username VARCHAR(50) UNIQUE NOT NULL, password VARCHAR(255) NOT NULL, role VARCHAR(20) DEFAULT 'ROLE_USER')");
        jdbcTemplate.execute("CREATE TABLE categories (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(50) NOT NULL, type VARCHAR(20) NOT NULL, user_id INT NOT NULL, FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE)");
        jdbcTemplate.execute("CREATE TABLE budgets (id INT AUTO_INCREMENT PRIMARY KEY, category_id INT NOT NULL, limit_amount DECIMAL(15, 2) NOT NULL, month INT NOT NULL, year INT NOT NULL, user_id INT NOT NULL, FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE, FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE)");
        
        jdbcTemplate.update("INSERT INTO users(username, password) VALUES('test', 'test')");
        jdbcTemplate.update("INSERT INTO categories(name, type, user_id) VALUES('Food', 'EXPENSE', 1)");
        
        System.out.println("Executing BudgetDao.saveBudget...");
        try {
            int categoryId = 1, month = 5, year = 2026, userId = 1;
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
        }
    }
}
