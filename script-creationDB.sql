DROP DATABASE IF EXISTS db;

CREATE DATABASE db;
USE db;

-- 3) Tables de référence (pas de FK vers elles)
CREATE TABLE category (
                          label VARCHAR(20) PRIMARY KEY
);

CREATE TABLE supplier (
                          label VARCHAR(20) PRIMARY KEY,
                          phone_number VARCHAR(20) NOT NULL
);

CREATE TABLE `function` (
                            label VARCHAR(20) PRIMARY KEY
);

CREATE TABLE status (
                        label VARCHAR(20) PRIMARY KEY
);

CREATE TABLE paymentMethod (
                               label VARCHAR(20) PRIMARY KEY
);

-- 4) Table utilisateur
CREATE TABLE `user` (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        last_name      VARCHAR(20)  NOT NULL,
                        first_name     VARCHAR(20)  NOT NULL,
                        password_hash  VARCHAR(255) NOT NULL,
                        function_label VARCHAR(20)  NOT NULL,
                        FOREIGN KEY (function_label) REFERENCES `function`(label)
);

-- 5) Table commandes
CREATE TABLE `order` (
                         id                    INT AUTO_INCREMENT PRIMARY KEY,
                         order_date            DATE               NOT NULL,
                         payment_date          DATE,
                         discount_percentage   INT                DEFAULT NULL,
                         `comment`             VARCHAR(200)       DEFAULT NULL,
                         is_happy_hour         BOOLEAN            NOT NULL,
                         status_label          VARCHAR(20)        NOT NULL,
                         user_id               INT                NOT NULL,
                         payment_method_label  VARCHAR(20)        NOT NULL,

                         FOREIGN KEY (status_label)         REFERENCES status(label),
                         FOREIGN KEY (user_id)              REFERENCES `user`(id),
                         FOREIGN KEY (payment_method_label) REFERENCES paymentMethod(label),

                         CHECK (discount_percentage IS NULL OR (discount_percentage BETWEEN 1 AND 100)),
                         CHECK (payment_date IS NULL OR payment_date >= order_date)
);

-- 6) Table produits
CREATE TABLE product (
                         id                  INT AUTO_INCREMENT PRIMARY KEY,
                         label               VARCHAR(20)   NOT NULL,
                         price               DECIMAL(10,2) NOT NULL,
                         nb_in_stock         INT           NOT NULL,
                         min_treshold        INT           NOT NULL,
                         is_gluten_free      BOOLEAN       NOT NULL,
                         alcohol_percentage  DECIMAL(3,1)  DEFAULT NULL,
                         distribution_date   DATE          NOT NULL,
                         last_restock_date   DATE          NOT NULL,
                         description         VARCHAR(300)  DEFAULT NULL,
                         category_label      VARCHAR(20)   NOT NULL,
                         supplier_label      VARCHAR(20)   NOT NULL,

                         FOREIGN KEY (category_label) REFERENCES category(label),
                         FOREIGN KEY (supplier_label) REFERENCES supplier(label),

                         CHECK (price >= 0),
                         CHECK (nb_in_stock >= 0),
                         CHECK (min_treshold >= 0),
                         CHECK (alcohol_percentage IS NULL OR (alcohol_percentage BETWEEN 1 AND 100)),
                         CHECK (last_restock_date >= distribution_date)
);

-- 7) Table lignes de commande
CREATE TABLE orderLine (
                           order_id   INT   NOT NULL,
                           product_id INT   NOT NULL,
                           quantity   INT   NOT NULL,
                           unit_price DECIMAL(10,2) NOT NULL,

                           PRIMARY KEY (order_id, product_id),

                           FOREIGN KEY (order_id)   REFERENCES `order`(id)
                               ON DELETE CASCADE
                               ON UPDATE CASCADE,
                           FOREIGN KEY (product_id) REFERENCES product(id)
                               ON DELETE RESTRICT
                               ON UPDATE CASCADE,

                           CHECK (quantity > 0),
                           CHECK (unit_price >= 0)
);