CREATE DATABASE ECOM;
USE ECOM;
CREATE TABLE customers (
    customer_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE products (
    product_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    description TEXT,
    quantity INT NOT NULL
);
CREATE TABLE cart (
    cart_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL CHECK (quantity > 0),
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE
);
CREATE TABLE orders (
    order_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_price DECIMAL(10,2) NOT NULL CHECK (total_price >= 0),
    shipping_address TEXT NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON DELETE CASCADE
);
CREATE TABLE order_items (
    order_item_id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL CHECK (quantity > 0),
    FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE
);

INSERT INTO customers (name, email, password) VALUES
('Alice', 'alice@example.com', 'pass123'),
('Bob', 'bob@example.com', 'bobpass'),
('Charlie', 'charlie@example.com', 'charliepass'),
('Diana', 'diana@example.com', 'dianapass'),
('Ethan', 'ethan@example.com', 'ethan123'),
('Fiona', 'fiona@example.com', 'fiona123'),
('George', 'george@example.com', 'george123'),
('Hannah', 'hannah@example.com', 'hannah123'),
('Irene', 'irene@example.com', 'irene123'),
('Jack', 'jack@example.com', 'jack123');
INSERT INTO products (name, price, description, quantity) VALUES
('Wireless Mouse', 499.99, 'Ergonomic wireless mouse', 100),
('Mechanical Keyboard', 1499.50, 'RGB backlit mechanical keyboard', 80),
('USB-C Cable', 199.00, 'Fast charging 1m cable', 200),
('Laptop Stand', 899.00, 'Aluminum adjustable stand', 50),
('Bluetooth Speaker', 2499.99, 'Portable outdoor speaker', 60),
('Webcam', 1299.00, '1080p HD webcam', 40),
('Monitor 24"', 8499.00, '24-inch IPS display', 25),
('External HDD 1TB', 3499.99, 'USB 3.0 compatible', 35),
('Wireless Charger', 1099.00, '10W fast charger', 90),
('Gaming Headset', 2999.00, 'Noise-canceling mic and surround sound', 45);
INSERT INTO cart (customer_id, product_id, quantity) VALUES
(1, 1, 2),
(2, 3, 1),
(3, 2, 1),
(4, 4, 1),
(5, 5, 2),
(6, 6, 1),
(7, 7, 1),
(8, 8, 1),
(9, 9, 1),
(10, 10, 2);
INSERT INTO orders (customer_id, total_price, shipping_address) VALUES
(1, 999.98, '101 Main Street'),
(2, 199.00, '202 Oak Avenue'),
(3, 1499.50, '303 Pine Road'),
(4, 899.00, '404 Maple Lane'),
(5, 4999.98, '505 Birch Street'),
(6, 1299.00, '606 Elm Blvd'),
(7, 8499.00, '707 Cedar Drive'),
(8, 3499.99, '808 Walnut Way'),
(9, 1099.00, '909 Poplar Place'),
(10, 5998.00, '1001 Chestnut Court');
INSERT INTO order_items (order_id, product_id, quantity) VALUES
(1, 1, 2),
(2, 3, 1),
(3, 2, 1),
(4, 4, 1),
(5, 5, 2),
(6, 6, 1),
(7, 7, 1),
(8, 8, 1),
(9, 9, 1),
(10, 10, 2);
