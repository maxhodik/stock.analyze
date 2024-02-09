-- Database: stocks_analyze
CREATE DATABASE IF NOT EXISTS stock_analyze;

--company
create TABLE IF NOT EXISTS company (
    id serial PRIMARY KEY,
    symbol VARCHAR(45),
    company_name VARCHAR(45)
    );

--stock
create TABLE IF NOT EXISTS stock (
    id serial PRIMARY KEY,
    symbol VARCHAR(45) not null,
    delta DECIMAL(10),
    latest_price DECIMAL(10)NOT NULL,
    company_name VARCHAR(45) NOT NULL
    );