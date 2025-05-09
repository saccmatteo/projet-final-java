-- 1. Tables de référence
-- CREATE SCHEMA db DEFAULT CHARSET UTF8;
-- DROP DATABASE db
INSERT INTO category (label) VALUES
                                 ('Boisson alcoolisée'),
                                 ('Soft'),
                                 ('Snacks'),
                                 ('Sans gluten'),
                                 ('Boisson chaude'),
                                 ('Glace');

INSERT INTO supplier (label, phone_number) VALUES
                                               ('CocaCola', '0123456789'),
                                               ('PepsiCo', '0987654321'),
                                               ('Nestlé', '0147258369'),
                                               ('Starbucks', '0154892674'),
                                               ('Ben & Jerry\'s', '0172456981');

INSERT INTO `function` (label) VALUES
                                   ('Barman'),
                                   ('Manager'),
                                   ('Serveur');

INSERT INTO `status` (label) VALUES
('En cours'),
('Terminé');

INSERT INTO paymentMethod (label) VALUES
('Carte Bancaire'),
('Espèces'),
('Ticket Restaurant');


INSERT INTO `user` (id, last_name, first_name, password_hash, function_label) VALUES
(1, 'Dupont', 'Jean', 'hash1', 'Manager'),
(2, 'Martin', 'Claire', 'hash2', 'Serveur'),
(3, 'Durand', 'Paul', 'hash3', 'Barman');


INSERT INTO product (id, label, price, nb_in_stock, min_treshold, is_gluten_free, alcohol_percentage, distribution_date, last_restock_date, description, category_label, supplier_label) VALUES
-- Boisson alcoolisée
(1, 'Bière Blonde', 3.20, 70, 7, FALSE, 5.0, '2024-03-20', '2024-03-20', 'Bière artisanale', 'Boisson alcoolisée', 'Nestlé'),
(2, 'Vin Rouge', 12.50, 50, 5, FALSE, 12.0, '2024-03-22', '2024-03-22', 'Vin rouge sec', 'Boisson alcoolisée', 'Nestlé'),
(3, 'Whisky Écossais', 25.00, 30, 3, FALSE, 40.0, '2024-03-25', '2024-03-25', 'Whisky de malt écossais', 'Boisson alcoolisée', 'PepsiCo'),

-- Boisson non alcoolisée
(4, 'Coca Zéro', 1.50, 100, 10, TRUE, NULL, '2024-04-01', '2024-04-01', 'Soda sans sucre', 'Soft', 'CocaCola'),
(5, 'Pepsi Max', 1.40, 90, 10, TRUE, NULL, '2024-04-01', '2024-04-01', 'Soda sans sucre', 'Soft', 'PepsiCo'),
(6, 'Jus d\'Orange', 2.20, 120, 15, TRUE, NULL, '2024-04-03', '2024-04-03', 'Jus d\'orange frais', 'Soft', 'Nestlé'),

-- Snacks
(7, 'Chips Paprika', 2.00, 50, 5, FALSE, NULL, '2024-03-25', '2024-03-25', 'Chips saveur paprika', 'Snacks', 'PepsiCo'),
(8, 'Barre Granola', 1.80, 40, 5, TRUE, NULL, '2024-03-26', '2024-03-26', 'Barre granola aux fruits', 'Snacks', 'Nestlé'),
(9, 'Popcorn Beurre', 2.50, 60, 5, FALSE, NULL, '2024-03-28', '2024-03-28', 'Popcorn au beurre', 'Snacks', 'PepsiCo'),

-- Sans gluten
(10, 'Crackers Sans Gluten', 2.50, 30, 5, TRUE, NULL, '2024-03-15', '2024-03-15', 'Crackers pour allergiques', 'Sans gluten', 'Nestlé'),
(11, 'Pain Sans Gluten', 3.00, 40, 5, TRUE, NULL, '2024-03-18', '2024-03-18', 'Pain sans gluten', 'Sans gluten', 'Nestlé'),
(12, 'Pâtes Sans Gluten', 4.00, 25, 3, TRUE, NULL, '2024-03-20', '2024-03-20', 'Pâtes pour personnes allergiques', 'Sans gluten', 'Nestlé'),

-- Boisson chaude
(13, 'Café Expresso', 2.50, 80, 10, TRUE, NULL, '2024-03-10', '2024-03-10', 'Café expresso torréfié', 'Boisson chaude', 'Starbucks'),
(14, 'Thé Vert', 1.80, 100, 15, TRUE, NULL, '2024-03-12', '2024-03-12', 'Thé vert bio', 'Boisson chaude', 'Nestlé'),
(15, 'Chocolat Chaud', 3.00, 70, 10, TRUE, NULL, '2024-03-15', '2024-03-15', 'Chocolat chaud crémeux', 'Boisson chaude', 'Nestlé'),

-- Glace
(16, 'Glace Vanille', 4.00, 50, 5, TRUE, NULL, '2024-03-22', '2024-03-22', 'Glace à la vanille', 'Glace', 'Ben & Jerry\'s'),
(17, 'Glace Chocolat', 4.50, 60, 6, TRUE, NULL, '2024-03-24', '2024-03-24', 'Glace au chocolat', 'Glace', 'Ben & Jerry\'s'),
(18, 'Glace Fraise', 4.20, 55, 5, TRUE, NULL, '2024-03-26', '2024-03-26', 'Glace à la fraise', 'Glace', 'Ben & Jerry\'s');

-- 4. Commandes (Order) et lignes de commande (orderLine)

INSERT INTO `order` (id, order_date, payment_date, discount_percentage, `comment`, is_happy_hour, status_label, user_id, payment_method_label) VALUES
(1, '2025-04-01', '2025-04-01', 0, 'RAS', FALSE, 'En cours', 1, 'Carte Bancaire'),
(2, '2025-04-01', '2025-04-01', 10, '', TRUE, 'En cours', 2, 'Espèces'),
(3, '2025-04-02', '2025-04-02', 5, 'Client fidèle', FALSE, 'En cours', 1, 'Ticket Restaurant'),
(4, '2025-04-02', '2025-04-02', 0, '', TRUE, 'En cours', 3, 'Carte Bancaire'),
(5, '2025-04-03', '2025-04-03', 0, '', FALSE, 'En cours', 2, 'Carte Bancaire'),
(6, '2025-04-03', '2025-04-03', 20, 'Promo spéciale', TRUE, 'En cours', 1, 'Espèces'),
(7, '2025-04-04', '2025-04-04', 0, '', FALSE, 'En cours', 3, 'Carte Bancaire'),
(8, '2025-04-04', '2025-04-04', 15, '', TRUE, 'En cours', 2, 'Carte Bancaire'),
(9, '2025-04-05', '2025-04-05', 0, 'Commande rapide', FALSE, 'En cours', 1, 'Espèces'),
(10, '2025-04-06', '2025-04-06', 0, '', FALSE, 'En cours', 3, 'Carte Bancaire');

-- Lignes de commande

INSERT INTO orderLine (order_id, product_id, quantity, unit_price) VALUES
(1, 1, 2, 1.50),
(1, 3, 1, 2.00),
(2, 2, 3, 1.40),
(3, 4, 2, 3.20),
(4, 1, 1, 1.50),
(4, 5, 1, 2.50),
(5, 2, 2, 1.40),
(6, 3, 4, 2.00),
(7, 1, 1, 1.50),
(8, 5, 2, 2.50),
(9, 4, 1, 3.20),
(10, 2, 2, 1.40),
(10, 3, 1, 2.00);
