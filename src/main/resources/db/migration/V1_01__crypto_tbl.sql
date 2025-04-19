CREATE TABLE crypto
(
    id         UUID PRIMARY KEY NOT NULL,
    symbol     VARCHAR(255)     NOT NULL,
    open       DECIMAL(15, 3) DEFAULT 0,
    high       DECIMAL(15, 3) DEFAULT 0,
    low        DECIMAL(15, 3) DEFAULT 0,
    close      DECIMAL(15, 3) DEFAULT 0,
    bid        DECIMAL(15, 3) DEFAULT 0,
    ask        DECIMAL(15, 3) DEFAULT 0,
    status     VARCHAR(30)      NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);