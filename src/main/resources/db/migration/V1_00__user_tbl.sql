CREATE TABLE "user"
(
    id         UUID PRIMARY KEY    NOT NULL,
    user_name  VARCHAR(255)        NOT NULL,
    password   VARCHAR(255)        NOT NULL,
    email      VARCHAR(255) UNIQUE NOT NULL,
    phone      VARCHAR(255) UNIQUE NOT NULL,
    address    VARCHAR(255),
    balance    DECIMAL(20, 3) DEFAULT 0,
    status     VARCHAR(30)         NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE INDEX idx_user_1 ON "user" (status);
CREATE INDEX idx_user_2 ON "user" (email, status);
CREATE INDEX idx_user_3 ON "user" (phone, status);