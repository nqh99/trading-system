CREATE TABLE wallet
(
    id         VARCHAR(255) PRIMARY KEY NOT NULL,
    user_id    VARCHAR(255)             NOT NULL,
    name       VARCHAR(255),
    balance    DECIMAL(15, 3) DEFAULT 0,
    status     VARCHAR(30)              NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,

    FOREIGN KEY (user_id) REFERENCES "user" (id) ON DELETE CASCADE
);