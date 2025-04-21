CREATE TABLE wallet_detail
(
    wallet_id  UUID        NOT NULL,
    crypto_id  UUID        NOT NULL,
    amount     DECIMAL(15, 3) DEFAULT 0,
    status     VARCHAR(30) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,

    PRIMARY KEY (wallet_id, crypto_id)
);

CREATE INDEX idx_wallet_detail_1 ON wallet_detail (wallet_id, status);
CREATE INDEX idx_wallet_detail_2 ON wallet_detail (crypto_id, status);