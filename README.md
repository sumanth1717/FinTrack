# FinTrack - Personal Finance Tracker

A full-stack SaaS personal finance management application built with **Spring Boot**, **Spring Security**, **MySQL/H2**, and a modern **dark-themed UI**. Designed with multi-tenant architecture, automated background tasks, and containerized deployment support.

---

## 🎬 Features

| Feature | Description |
|---|---|
| 🔐 **Secure Authentication** | Spring Security with BCrypt password hashing & session management |
| 👤 **Multi-Tenant** | Every user has completely isolated data (transactions, goals, budgets) |
| 💸 **Transactions** | Full CRUD with category tagging, search, sorting, and filtering |
| 📊 **Budget Planner** | Set monthly limits per category with real-time progress bars |
| 🎯 **Savings Goals** | Create financial goals, track progress, and contribute funds |
| 🔄 **Recurring Subscriptions** | Automated recurring expense tracking via Spring `@Scheduled` cron jobs |
| 📥 **CSV Import / Export** | Bulk-import transactions via CSV upload; export your data anytime |
| 🤖 **AI Insights** | Automated smart insights: top spending categories, MoM changes, savings rate |
| 📈 **Interactive Charts** | Cash Flow Trend chart and Spending Breakdown pie chart via Chart.js |
| 📄 **PDF Export** | Export dashboard overview to PDF with one click |
| 🐳 **Docker Ready** | Multi-stage `Dockerfile` + `docker-compose.yml` for production deployment |
| 🧪 **Unit Tested** | JUnit 5 + Mockito test suite covering all service layer business logic |

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| **Backend** | Java 21, Spring Boot 3.5, Spring Security, Spring JDBC |
| **Database** | H2 (default, zero-config) / MySQL 8 (production) |
| **Frontend** | Thymeleaf, Bootstrap 5, Chart.js, jsPDF |
| **Testing** | JUnit 5, Mockito |
| **DevOps** | Docker, Docker Compose |

---

## 🚀 Quick Start (Recruiters — No Setup Required)

This is the fastest way to run the application. No MySQL, no Docker, no configuration needed!

### Prerequisites
- Java 21+
- Maven 3.9+

### Run
```bash
git clone https://github.com/YOUR_USERNAME/fintrack.git
cd fintrack
mvn spring-boot:run
```

Open your browser and go to `http://localhost:8080`

**Login with the pre-seeded demo account:**
- **Username:** `testuser`
- **Password:** `password`

> The app uses an H2 in-memory database by default. All tables and seed data are auto-generated on startup via `schema.sql` and `data.sql`.

---

## 🐳 Production Deployment (Docker)

To run a production-grade stack with a persistent MySQL database:

### Prerequisites
- Docker Desktop

### Run
```bash
docker-compose up --build -d
```

Access at `http://localhost:8080`

> Docker will pull a MySQL 8 image, run your Spring Boot app, and securely wire them together automatically.

---

## 🏢 Local Development with MySQL

To use your own persistent MySQL database:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=mysql
```

Update your credentials in `src/main/resources/application-mysql.properties`.

---

## 🗂️ Project Structure

```
src/
├── main/
│   ├── java/com/finance/tracker/
│   │   ├── config/          # Spring Security config
│   │   ├── controller/      # REST controllers & MVC controllers
│   │   ├── dao/             # JDBC Data Access Objects
│   │   ├── model/           # POJO domain models
│   │   └── service/         # Business logic & scheduled tasks
│   └── resources/
│       ├── templates/        # Thymeleaf HTML templates
│       ├── schema.sql        # Auto-run DDL script
│       ├── data.sql          # Auto-run seed data script
│       ├── application.properties         # Default (H2) config
│       └── application-mysql.properties   # MySQL config (local dev)
└── test/
    └── java/com/finance/tracker/
        └── service/          # JUnit + Mockito unit tests
```

---

## 🧪 Running Tests

```bash
mvn test
```

All 7 tests should pass. Tests cover `TransactionService`, `UserDao`, and application context loading.

---

## 📷 Screenshots

_Dashboard with AI Insights, Charts, and Transaction History_

---

## 📄 License

This project is open source and available under the [MIT License](LICENSE).
