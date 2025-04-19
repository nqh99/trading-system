CREATE TABLE wallet_detail
(
    id         VARCHAR(255) PRIMARY KEY NOT NULL,
    wallet_id  VARCHAR(255)             NOT NULL,
    crypto_id  VARCHAR(255)             NOT NULL,
    amount     DECIMAL(15, 3) DEFAULT 0,
    status     VARCHAR(30)              NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,

    FOREIGN KEY (wallet_id) REFERENCES wallet (id) ON DELETE CASCADE,
    FOREIGN KEY (crypto_id) REFERENCES crypto (id) ON DELETE CASCADE
);