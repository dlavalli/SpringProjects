INSERT INTO customer (name, email, tier) VALUES ('Alice Smith', 'alice@example.com', 'VIP');
INSERT INTO customer (name, email, tier) VALUES ('Bob Jones', 'bob@example.com', 'STANDARD');

INSERT INTO customer_order (customer_id, order_date, total_amount, status) VALUES (1, '2026-07-01 10:00:00', 150.00, 'COMPLETED');
INSERT INTO customer_order (customer_id, order_date, total_amount, status) VALUES (1, '2026-07-05 14:30:00', 450.50, 'COMPLETED');
INSERT INTO customer_order (customer_id, order_date, total_amount, status) VALUES (2, '2026-07-06 09:15:00', 25.00, 'PENDING');
