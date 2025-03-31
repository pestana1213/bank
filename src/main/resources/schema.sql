DROP TABLE IF EXISTS transaction;

CREATE TABLE transaction (
    id BIGINT PRIMARY KEY,
    transaction_value DOUBLE,
    scheduled_date TIMESTAMP,
    fee DOUBLE,
    accountFrom VARCHAR(255),
    accountTo VARCHAR(255)
);