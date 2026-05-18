-- Create a default test user if needed
-- password is 'password' (bcrypt hash)
INSERT INTO users (username, password, role) 
VALUES ('testuser', '$2a$10$T8CzVaYJTkfDpkTbVmaQ4./KTIrnlQuka7NSpVFUOtk0fwbeVJbIy', 'ROLE_USER');

-- Add some default categories for the test user
INSERT INTO categories (name, type, user_id) VALUES ('Salary', 'INCOME', 1);
INSERT INTO categories (name, type, user_id) VALUES ('Investments', 'INCOME', 1);
INSERT INTO categories (name, type, user_id) VALUES ('Other', 'INCOME', 1);

INSERT INTO categories (name, type, user_id) VALUES ('Housing', 'EXPENSE', 1);
INSERT INTO categories (name, type, user_id) VALUES ('Food & Dining', 'EXPENSE', 1);
INSERT INTO categories (name, type, user_id) VALUES ('Transportation', 'EXPENSE', 1);
INSERT INTO categories (name, type, user_id) VALUES ('Utilities', 'EXPENSE', 1);
INSERT INTO categories (name, type, user_id) VALUES ('Entertainment', 'EXPENSE', 1);
INSERT INTO categories (name, type, user_id) VALUES ('Shopping', 'EXPENSE', 1);
INSERT INTO categories (name, type, user_id) VALUES ('Health & Fitness', 'EXPENSE', 1);
INSERT INTO categories (name, type, user_id) VALUES ('Other', 'EXPENSE', 1);

-- Seed some transactions for the test user
INSERT INTO transactions (title, amount, type, category_id, transaction_date, note, user_id)
VALUES ('Monthly Salary', 5000.00, 'INCOME', 1, CURRENT_DATE(), 'Paycheck', 1);

INSERT INTO transactions (title, amount, type, category_id, transaction_date, note, user_id)
VALUES ('Groceries', 150.50, 'EXPENSE', 5, CURRENT_DATE(), 'Walmart', 1);

INSERT INTO transactions (title, amount, type, category_id, transaction_date, note, user_id)
VALUES ('Rent', 1200.00, 'EXPENSE', 4, CURRENT_DATE(), 'Apartment Rent', 1);
