INSERT INTO crypto (id, symbol, open, high, low, close, bid, bid_qty, ask, ask_qty, status, created_at, updated_at)
VALUES (RANDOM_UUID(), 'BTCUSDT', 45000.00, 46000.00, 44000.00, 45500.00, 45450.00, 45450.00, 45550.00, 45450.00,
        'N', CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP),
       (RANDOM_UUID(), 'ETHUSDT', 3000.00, 3100.00, 2900.00, 3050.00, 3045.00, 3045.00, 3055.00, 3045.00, 'N',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP);

SET @userId = RANDOM_UUID();
SET @userID2 = RANDOM_UUID();

INSERT INTO "user" (id, user_name, password, email, phone, address, balance, status, created_at, updated_at)
VALUES (@userID, 'john_doe', '$2a$10$xP3/hE8a0A5xz6QKm1n7CeQvY7d5gL5KgV1yc5YB1S.GbpgZOIzJi', 'john@example.com',
        '1234567890', '123 Main St', 50000, 'N', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (@userID2, 'jane_smith', '$2a$10$hT9PZ0gjNhC1X7zE2IW7.O2vHqk0HMxJJ7SSYWz0yKrDUZwAFjPZS', 'jane@example.com',
        '0987654321', '456 Oak Ave', 50000, 'N', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO wallet (id, user_id, name, balance, priority, status, created_at, updated_at)
VALUES (RANDOM_UUID(), @userID, 'Spot Wallet', 50000.0, 1, 'N', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (RANDOM_UUID(), @userID, 'Savings Wallet', 0, 2, 'N', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (RANDOM_UUID(), @userID, 'Trading Wallet', 0, 3, 'N', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (RANDOM_UUID(), @userID2, 'Spot Wallet', 50000, 1, 'N', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);