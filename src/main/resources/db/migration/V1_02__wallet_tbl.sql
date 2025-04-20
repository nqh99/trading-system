CREATE TABLE wallet
(
    id         UUID PRIMARY KEY NOT NULL,
    user_id    VARCHAR(255)     NOT NULL,
    name       VARCHAR(255),
    balance    DECIMAL(20, 3) DEFAULT 0,
    priority   INT            DEFAULT 0,
    status     VARCHAR(30)      NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,

    FOREIGN KEY (user_id) REFERENCES "user" (id) ON DELETE CASCADE
);

CREATE INDEX idx_wallet_1 ON wallet (status);
CREATE INDEX idx_wallet_2 ON wallet (user_id, priority, status);