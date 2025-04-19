CREATE TABLE "user"
(
    id         VARCHAR(255) PRIMARY KEY NOT NULL,
    user_name  VARCHAR(255)             NOT NULL,
    password   VARCHAR(255)             NOT NULL,
    email      VARCHAR(255) UNIQUE      NOT NULL,
    phone      VARCHAR(255) UNIQUE      NOT NULL,
    address    VARCHAR(255),
    status     VARCHAR(30)              NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);