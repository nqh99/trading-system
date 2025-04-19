CREATE TABLE "order"
(
    id         VARCHAR(255) PRIMARY KEY NOT NULL,
    user_id    VARCHAR(255)             NOT NULL,
    crypto_id  VARCHAR(255)             NOT NULL,
    price      DECIMAL(15, 3) DEFAULT 0,
    amount     DECIMAL(15, 3) DEFAULT 0,
    bs         CHAR(1)                  NOT NULL,
    status     VARCHAR(30)              NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,

    FOREIGN KEY (user_id) REFERENCES "user" (id) ON DELETE CASCADE,
    FOREIGN KEY (crypto_id) REFERENCES crypto (id) ON DELETE CASCADE
);