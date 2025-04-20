CREATE TABLE crypto
(
    id         UUID PRIMARY KEY NOT NULL,
    symbol     VARCHAR(255)     NOT NULL,
    price      DECIMAL(15, 3) DEFAULT 0,
    amount     DECIMAL(15, 3) DEFAULT 0,
    open       DECIMAL(15, 3) DEFAULT 0,
    high       DECIMAL(15, 3) DEFAULT 0,
    low        DECIMAL(15, 3) DEFAULT 0,
    close      DECIMAL(15, 3) DEFAULT 0,
    bid        DECIMAL(15, 3) DEFAULT 0,
    bid_qty    DECIMAL(15, 7) DEFAULT 0,
    ask        DECIMAL(15, 3) DEFAULT 0,
    ask_qty    DECIMAL(15, 7) DEFAULT 0,
    status     VARCHAR(30)      NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE INDEX idx_crypto_1 ON crypto (status);
CREATE INDEX idx_crypto_2 ON crypto (symbol, status);