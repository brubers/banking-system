# Banking System

A simple banking system developed in Java using JDBC and MySQL. This project was created to practice Object-Oriented Programming, database integration, SQL operations, and transaction management.

## Technologies

- Java
- Maven
- JDBC (Java Database Connectivity)
- MySQL
- Git and GitHub

## Features

- Create bank accounts and register clients
- List all registered accounts
- Search for accounts by account number
- Check account balance
- Deposit money
- Withdraw money
- Transfer money between accounts

## Database

The project uses MySQL with two main tables:

**clients**
- `id` — Primary key, auto-increment
- `name` — Client's name
- `cpf` — Client's CPF

**bank_accounts**
- `id` — Primary key, auto-increment
- `account_number` — Account number
- `balance` — Account balance
- `client_id` — Foreign key referencing the client

## Key Concepts Practiced

- Object-Oriented Programming (OOP)
- Separation of responsibilities using DAO (Data Access Object)
- JDBC and PreparedStatement
- SQL queries and relational database relationships
- BigDecimal for monetary values
- Exception handling
- JDBC transactions using `commit()` and `rollback()`

## How to Run

1. Clone this repository:

```bash
git clone https://github.com/brubers/banking-system.git
```

2. Open the project in your IDE.

3. Open `database.sql` and copy to your MySQL database and execute the SQL commands to create the `clients` and `bank_accounts` tables.

4. Configure your database credentials in `DBConnection.java`.

5. Run the `Application.java` class.

---

**Status:** Completed — Initial Version (v1.0)
