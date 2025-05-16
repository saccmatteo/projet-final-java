USE db;

CREATE TABLE product (
id INT AUTO_INCREMENT PRIMARY KEY,
label VARCHAR(20) NOT NULL, 
price NUMERIC(10, 2) NOT NULL,
nb_in_stock NUMERIC(3) NOT NULL, 
min_treshold NUMERIC(4) NOT NULL, 
is_gluten_free bool NOT NULL, 
alcohol_percentage NUMERIC(3, 1), 
distribution_date DATE NOT NULL, 
last_restock_date DATE NOT NULL, 
description TEXT, 
category_label VARCHAR(20) NOT NULL REFERENCES category(label), 
supplier_label VARCHAR(20) NOT NULL REFERENCES supplier(label)
);

CREATE TABLE category (
label VARCHAR(20) PRIMARY KEY
);

CREATE TABLE supplier (
label VARCHAR(20) PRIMARY KEY,
phone_number VARCHAR(20) NOT NULL -- à voir pour le type du tel
);

CREATE TABLE `user` (
id NUMERIC(10)  PRIMARY KEY,
last_name VARCHAR(20) NOT NULL, 
first_name VARCHAR(20) NOT NULL, 
password_hash VARCHAR(255) NOT NULL, -- à changer dans le doc
function_label VARCHAR(20) NOT NULL REFERENCES `function`(label)
);

CREATE TABLE `function` (
label VARCHAR(20) PRIMARY KEY
);

CREATE TABLE `order` (
id INT AUTO_INCREMENT PRIMARY KEY,
order_date DATE NOT NULL,
payment_date DATE,
discount_percentage NUMERIC(3),
`comment` VARCHAR(200),
is_happy_hour bool NOT NULL, 
status_label VARCHAR(20) NOT NULL REFERENCES `status`(label),
user_id NUMERIC(5) NOT NULL REFERENCES `user`(id),
payment_method_label VARCHAR(20) NOT NULL REFERENCES paymentMethod(label)
);

CREATE TABLE `status` (
label VARCHAR(20) PRIMARY KEY
);

CREATE TABLE paymentMethod (
label VARCHAR(20) PRIMARY KEY
);

CREATE TABLE orderLine (
order_id INT,
product_id INT,
quantity NUMERIC(5) NOT NULL,
unit_price NUMERIC(5, 2) NOT NULL,
PRIMARY KEY(order_id, product_id),
FOREIGN KEY(order_id) REFERENCES `order`(id),
FOREIGN KEY(product_id) REFERENCES product(id)
);