CREATE DATABASE IF NOT EXISTS banking_system;

USE banking_system;

-- clients table
CREATE TABLE IF NOT EXISTS clients (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) NOT NULL UNIQUE
    );

-- bank accounts table
CREATE TABLE IF NOT EXISTS bank_accounts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    account_number VARCHAR(20) NOT NULL UNIQUE,
    balance DECIMAL(15, 2) NOT NULL DEFAULT 0.00,
    client_id INT NOT NULL,

    CONSTRAINT fk_bank_account_client
    FOREIGN KEY (client_id)
    REFERENCES clients(id)
    );