INSERT INTO crypto (id, symbol, open, high, low, close, bid, ask, status, created_at, updated_at)
VALUES (RANDOM_UUID(), 'BTCUSDT', 45000.00, 46000.00, 44000.00, 45500.00, 45450.00, 45550.00,
        'N', CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP),
       (RANDOM_UUID(), 'ETHUSDT', 3000.00, 3100.00, 2900.00, 3050.00, 3045.00, 3055.00, 'N',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP);
