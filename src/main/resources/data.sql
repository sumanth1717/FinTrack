-- ─────────────────────────────────────────
-- Demo User  (password = 'password')
-- ─────────────────────────────────────────
INSERT INTO users (username, password, role)
VALUES ('testuser', '$2a$10$T8CzVaYJTkfDpkTbVmaQ4./KTIrnlQuka7NSpVFUOtk0fwbeVJbIy', 'ROLE_USER');

-- ─────────────────────────────────────────
-- Categories
-- ─────────────────────────────────────────
INSERT INTO categories (name, type, user_id) VALUES ('Salary',           'INCOME',  1);
INSERT INTO categories (name, type, user_id) VALUES ('Freelance',        'INCOME',  1);
INSERT INTO categories (name, type, user_id) VALUES ('Other',            'INCOME',  1);
INSERT INTO categories (name, type, user_id) VALUES ('Housing',          'EXPENSE', 1);
INSERT INTO categories (name, type, user_id) VALUES ('Food & Dining',    'EXPENSE', 1);
INSERT INTO categories (name, type, user_id) VALUES ('Transportation',   'EXPENSE', 1);
INSERT INTO categories (name, type, user_id) VALUES ('Utilities',        'EXPENSE', 1);
INSERT INTO categories (name, type, user_id) VALUES ('Entertainment',    'EXPENSE', 1);
INSERT INTO categories (name, type, user_id) VALUES ('Shopping',         'EXPENSE', 1);
INSERT INTO categories (name, type, user_id) VALUES ('Health & Fitness', 'EXPENSE', 1);
INSERT INTO categories (name, type, user_id) VALUES ('Other',            'EXPENSE', 1);

-- ─────────────────────────────────────────
-- Transactions  (3 months of history)
-- ─────────────────────────────────────────

-- ── 3 months ago ──────────────────────────
INSERT INTO transactions (title, amount, type, category_id, transaction_date, note, user_id)
VALUES ('Monthly Salary',     55000.00, 'INCOME',  1, DATEADD('MONTH', -3, CURRENT_DATE()), 'March paycheck', 1);
INSERT INTO transactions (title, amount, type, category_id, transaction_date, note, user_id)
VALUES ('Apartment Rent',     12000.00, 'EXPENSE', 4, DATEADD('MONTH', -3, CURRENT_DATE()), 'March rent', 1);
INSERT INTO transactions (title, amount, type, category_id, transaction_date, note, user_id)
VALUES ('Groceries',           3200.00, 'EXPENSE', 5, DATEADD('MONTH', -3, CURRENT_DATE()), 'Supermarket', 1);
INSERT INTO transactions (title, amount, type, category_id, transaction_date, note, user_id)
VALUES ('Electricity Bill',    1100.00, 'EXPENSE', 7, DATEADD('MONTH', -3, CURRENT_DATE()), 'March bill', 1);
INSERT INTO transactions (title, amount, type, category_id, transaction_date, note, user_id)
VALUES ('Gym Membership',       800.00, 'EXPENSE', 10, DATEADD('MONTH', -3, CURRENT_DATE()), 'Monthly gym', 1);
INSERT INTO transactions (title, amount, type, category_id, transaction_date, note, user_id)
VALUES ('Netflix',              649.00, 'EXPENSE', 8, DATEADD('MONTH', -3, CURRENT_DATE()), 'Streaming', 1);
INSERT INTO transactions (title, amount, type, category_id, transaction_date, note, user_id)
VALUES ('Petrol',              2500.00, 'EXPENSE', 6, DATEADD('MONTH', -3, CURRENT_DATE()), 'Bike fuel', 1);
INSERT INTO transactions (title, amount, type, category_id, transaction_date, note, user_id)
VALUES ('Online Shopping',     3800.00, 'EXPENSE', 9, DATEADD('MONTH', -3, CURRENT_DATE()), 'Amazon order', 1);

-- ── 2 months ago ──────────────────────────
INSERT INTO transactions (title, amount, type, category_id, transaction_date, note, user_id)
VALUES ('Monthly Salary',     55000.00, 'INCOME',  1, DATEADD('MONTH', -2, CURRENT_DATE()), 'April paycheck', 1);
INSERT INTO transactions (title, amount, type, category_id, transaction_date, note, user_id)
VALUES ('Freelance Project',   8000.00, 'INCOME',  2, DATEADD('MONTH', -2, CURRENT_DATE()), 'Logo design work', 1);
INSERT INTO transactions (title, amount, type, category_id, transaction_date, note, user_id)
VALUES ('Apartment Rent',     12000.00, 'EXPENSE', 4, DATEADD('MONTH', -2, CURRENT_DATE()), 'April rent', 1);
INSERT INTO transactions (title, amount, type, category_id, transaction_date, note, user_id)
VALUES ('Groceries',           4100.00, 'EXPENSE', 5, DATEADD('MONTH', -2, CURRENT_DATE()), 'Supermarket + Bigbasket', 1);
INSERT INTO transactions (title, amount, type, category_id, transaction_date, note, user_id)
VALUES ('Restaurant Dinner',   1800.00, 'EXPENSE', 5, DATEADD('MONTH', -2, CURRENT_DATE()), 'Birthday dinner', 1);
INSERT INTO transactions (title, amount, type, category_id, transaction_date, note, user_id)
VALUES ('Electricity Bill',    1250.00, 'EXPENSE', 7, DATEADD('MONTH', -2, CURRENT_DATE()), 'April bill', 1);
INSERT INTO transactions (title, amount, type, category_id, transaction_date, note, user_id)
VALUES ('Gym Membership',       800.00, 'EXPENSE', 10, DATEADD('MONTH', -2, CURRENT_DATE()), 'Monthly gym', 1);
INSERT INTO transactions (title, amount, type, category_id, transaction_date, note, user_id)
VALUES ('Netflix',              649.00, 'EXPENSE', 8, DATEADD('MONTH', -2, CURRENT_DATE()), 'Streaming', 1);
INSERT INTO transactions (title, amount, type, category_id, transaction_date, note, user_id)
VALUES ('Movie Tickets',        700.00, 'EXPENSE', 8, DATEADD('MONTH', -2, CURRENT_DATE()), 'Weekend movie', 1);
INSERT INTO transactions (title, amount, type, category_id, transaction_date, note, user_id)
VALUES ('Petrol',              2200.00, 'EXPENSE', 6, DATEADD('MONTH', -2, CURRENT_DATE()), 'Fuel top-up', 1);

-- ── 1 month ago ───────────────────────────
INSERT INTO transactions (title, amount, type, category_id, transaction_date, note, user_id)
VALUES ('Monthly Salary',     58000.00, 'INCOME',  1, DATEADD('MONTH', -1, CURRENT_DATE()), 'May paycheck + bonus', 1);
INSERT INTO transactions (title, amount, type, category_id, transaction_date, note, user_id)
VALUES ('Freelance Project',  12000.00, 'INCOME',  2, DATEADD('MONTH', -1, CURRENT_DATE()), 'App UI contract', 1);
INSERT INTO transactions (title, amount, type, category_id, transaction_date, note, user_id)
VALUES ('Apartment Rent',     12000.00, 'EXPENSE', 4, DATEADD('MONTH', -1, CURRENT_DATE()), 'May rent', 1);
INSERT INTO transactions (title, amount, type, category_id, transaction_date, note, user_id)
VALUES ('Groceries',           3600.00, 'EXPENSE', 5, DATEADD('MONTH', -1, CURRENT_DATE()), 'Weekly groceries', 1);
INSERT INTO transactions (title, amount, type, category_id, transaction_date, note, user_id)
VALUES ('Electricity Bill',    1400.00, 'EXPENSE', 7, DATEADD('MONTH', -1, CURRENT_DATE()), 'May bill', 1);
INSERT INTO transactions (title, amount, type, category_id, transaction_date, note, user_id)
VALUES ('Gym Membership',       800.00, 'EXPENSE', 10, DATEADD('MONTH', -1, CURRENT_DATE()), 'Monthly gym', 1);
INSERT INTO transactions (title, amount, type, category_id, transaction_date, note, user_id)
VALUES ('Netflix',              649.00, 'EXPENSE', 8, DATEADD('MONTH', -1, CURRENT_DATE()), 'Streaming', 1);
INSERT INTO transactions (title, amount, type, category_id, transaction_date, note, user_id)
VALUES ('New Sneakers',        4500.00, 'EXPENSE', 9, DATEADD('MONTH', -1, CURRENT_DATE()), 'Nike shoes', 1);
INSERT INTO transactions (title, amount, type, category_id, transaction_date, note, user_id)
VALUES ('Petrol',              2600.00, 'EXPENSE', 6, DATEADD('MONTH', -1, CURRENT_DATE()), 'Road trip fuel', 1);
INSERT INTO transactions (title, amount, type, category_id, transaction_date, note, user_id)
VALUES ('Doctor Visit',        1200.00, 'EXPENSE', 10, DATEADD('MONTH', -1, CURRENT_DATE()), 'General checkup', 1);

-- ── This month ────────────────────────────
INSERT INTO transactions (title, amount, type, category_id, transaction_date, note, user_id)
VALUES ('Monthly Salary',     58000.00, 'INCOME',  1, CURRENT_DATE(), 'This month paycheck', 1);
INSERT INTO transactions (title, amount, type, category_id, transaction_date, note, user_id)
VALUES ('Apartment Rent',     12000.00, 'EXPENSE', 4, CURRENT_DATE(), 'This month rent', 1);
INSERT INTO transactions (title, amount, type, category_id, transaction_date, note, user_id)
VALUES ('Groceries',           2800.00, 'EXPENSE', 5, CURRENT_DATE(), 'Supermarket run', 1);
INSERT INTO transactions (title, amount, type, category_id, transaction_date, note, user_id)
VALUES ('Electricity Bill',    1100.00, 'EXPENSE', 7, CURRENT_DATE(), 'This month bill', 1);
INSERT INTO transactions (title, amount, type, category_id, transaction_date, note, user_id)
VALUES ('Gym Membership',       800.00, 'EXPENSE', 10, CURRENT_DATE(), 'Monthly gym', 1);
INSERT INTO transactions (title, amount, type, category_id, transaction_date, note, user_id)
VALUES ('Netflix',              649.00, 'EXPENSE', 8, CURRENT_DATE(), 'Streaming', 1);
INSERT INTO transactions (title, amount, type, category_id, transaction_date, note, user_id)
VALUES ('Swiggy Orders',       1800.00, 'EXPENSE', 5, CURRENT_DATE(), 'Food delivery', 1);
INSERT INTO transactions (title, amount, type, category_id, transaction_date, note, user_id)
VALUES ('Petrol',              2000.00, 'EXPENSE', 6, CURRENT_DATE(), 'Bike fuel', 1);

-- ─────────────────────────────────────────
-- Savings Goals
-- ─────────────────────────────────────────
INSERT INTO savings_goals (user_id, name, target_amount, current_amount, target_date)
VALUES (1, 'Goa Vacation',       50000.00, 18000.00, DATEADD('MONTH', 4, CURRENT_DATE()));
INSERT INTO savings_goals (user_id, name, target_amount, current_amount, target_date)
VALUES (1, 'Emergency Fund',    100000.00, 35000.00, DATEADD('MONTH', 8, CURRENT_DATE()));
INSERT INTO savings_goals (user_id, name, target_amount, current_amount, target_date)
VALUES (1, 'New Laptop',         80000.00, 24000.00, DATEADD('MONTH', 3, CURRENT_DATE()));

-- ─────────────────────────────────────────
-- Budgets  (current month)
-- ─────────────────────────────────────────
INSERT INTO budgets (category_id, limit_amount, month, year, user_id)
VALUES (4,  13000.00, MONTH(CURRENT_DATE()), YEAR(CURRENT_DATE()), 1);  -- Housing
INSERT INTO budgets (category_id, limit_amount, month, year, user_id)
VALUES (5,   5000.00, MONTH(CURRENT_DATE()), YEAR(CURRENT_DATE()), 1);  -- Food & Dining
INSERT INTO budgets (category_id, limit_amount, month, year, user_id)
VALUES (6,   3000.00, MONTH(CURRENT_DATE()), YEAR(CURRENT_DATE()), 1);  -- Transportation
INSERT INTO budgets (category_id, limit_amount, month, year, user_id)
VALUES (7,   1500.00, MONTH(CURRENT_DATE()), YEAR(CURRENT_DATE()), 1);  -- Utilities
INSERT INTO budgets (category_id, limit_amount, month, year, user_id)
VALUES (8,   1500.00, MONTH(CURRENT_DATE()), YEAR(CURRENT_DATE()), 1);  -- Entertainment
INSERT INTO budgets (category_id, limit_amount, month, year, user_id)
VALUES (10,  1000.00, MONTH(CURRENT_DATE()), YEAR(CURRENT_DATE()), 1);  -- Health & Fitness
