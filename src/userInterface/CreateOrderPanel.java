package userInterface;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import controller.*;
import model.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;

public class CreateOrderPanel extends JPanel {
    private Double totalPrice;
    private String priceText;

    // Composants
    private JPanel usersPanel, formPanel, productPanel, commandPanel, middlePanel, buttonPanel;
    private JLabel usersLabel, commentsLabel, discountLabel, cartLabel;
    private JTextField commentsText, discountField, priceField;
    private JButton addProdButton, deleteProdButton, submitButton, resetButton;
    private JCheckBox happyHourRadio;
    private JComboBox<User> users;
    private JList<OrderLine> commandList;
    private DefaultListModel<OrderLine> commandListModel;

    private UserController userController;
    private OrderController orderController;
    private OrderLineController orderLineController;
    private ProductController productController;
    private ListingProductPanel listingProductPanel;

    public CreateOrderPanel() {
        setUserController(new UserController());
        setOrderController(new OrderController());
        setOrderLineController(new OrderLineController());
        setProductController(new ProductController());

        setLayout(new BorderLayout());

        initFormComponents();

        createFormPanel();

        add(usersPanel, BorderLayout.NORTH);
        add(middlePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        addProdButton.addActionListener(new AddButtonListener());
        deleteProdButton.addActionListener(new DeleteButtonListener());
        submitButton.addActionListener(new SubmitButtonListener());
        resetButton.addActionListener(new ResetButtonListener());
        happyHourRadio.addItemListener(new HappyHourListener());
    }

    //itialise les composants du form
    // initFormComponents
    private void initFormComponents() {
        usersPanel = new JPanel();
        formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        productPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        commandPanel = new JPanel(new BorderLayout());
        middlePanel = new JPanel(new BorderLayout());

        buttonPanel = new JPanel(new BorderLayout());

        usersLabel = new JLabel("Utilisateur gérant la commande : ");
        usersLabel.setFont(new Font("Arial", Font.BOLD, 24));
        commentsLabel = new JLabel("    Commentaires : ");
        commentsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        discountLabel = new JLabel("    Réduction : ");
        discountLabel.setFont(new Font("Arial", Font.BOLD, 16));
        cartLabel = new JLabel("Panier : ");
        cartLabel.setFont(new Font("Arial", Font.BOLD, 16));

        commentsText = new JTextField();
        discountField = new JTextField();
        discountField.getDocument().addDocumentListener(new refreshPriceListener());

        addProdButton = new JButton("Ajouter produit");
        deleteProdButton = new JButton("Supprimer produit");
        submitButton = new JButton("Valider commande");
        resetButton = new JButton("Réinitialiser");

        priceText = "Prix total : ";
        totalPrice = 0.0;
        priceField = new JTextField(priceText + totalPrice);
        priceField.setEnabled(false);

        happyHourRadio = new JCheckBox("   Happy hour  ");
        happyHourRadio.setHorizontalTextPosition(SwingConstants.LEFT);
        happyHourRadio.setFont(new Font("Arial", Font.BOLD, 16));

        users = new JComboBox<>(userController.getAllUsers().toArray(new User[0]));
        users.setFont(new Font("Arial", Font.BOLD, 16));
        users.setSelectedIndex(-1);
        users.setPreferredSize(new Dimension(500, 50));

        commandListModel = new DefaultListModel<>();
        commandList = new JList<>(commandListModel);
        commandList.setFixedCellHeight(50);
        commandList.setFont(new Font("Arial", Font.BOLD, 24));

        listingProductPanel = new ListingProductPanel();
    }

    // createFormPanel
    private void createFormPanel() {
        usersPanel.add(usersLabel);
        usersPanel.add(users);

        formPanel.add(happyHourRadio);
        formPanel.add(new JLabel());
        formPanel.add(discountLabel);
        formPanel.add(discountField);
        formPanel.add(commentsLabel);
        formPanel.add(commentsText);

        JScrollPane commandScroll = new JScrollPane(commandList);
        commandPanel.add(cartLabel, BorderLayout.NORTH);
        commandPanel.add(commandScroll, BorderLayout.CENTER);

        productPanel.add(listingProductPanel);
        productPanel.add(commandPanel);

        middlePanel.add(formPanel, BorderLayout.NORTH);
        middlePanel.add(productPanel, BorderLayout.CENTER);

        JPanel leftButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftButtons.add(addProdButton);
        leftButtons.add(deleteProdButton);
        leftButtons.add(submitButton);
        leftButtons.add(resetButton);

        buttonPanel.add(leftButtons, BorderLayout.WEST);
        buttonPanel.add(priceField, BorderLayout.EAST);
    }

    // SETTERS
    public void setOrderController(OrderController orderController) {
        this.orderController = orderController;
    }

    public void setUserController(UserController userController) {
        this.userController = userController;
    }

    public void setProductController(ProductController productController) {
        this.productController = productController;
    }

    public void setOrderLineController(OrderLineController orderLineController) {
        this.orderLineController = orderLineController;
    }

    // LISTENERS
    private class HappyHourListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                discountField.setText("50");
                discountField.setEnabled(false);
            } else {
                discountField.setText("");
                discountField.setEnabled(true);
            }
        }
    }

    private class AddButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Product selectedProd = listingProductPanel.getProductJList().getSelectedValue();
            if (selectedProd == null) {
                return;
            }

            String input = JOptionPane.showInputDialog(null, "Combien de " + selectedProd.getLabel() + " ?");
            if (input == null) {
                // L'utilisateur a appuyé sur "Annuler"
                return;
            }

            input = input.trim();

            if (input.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Veuillez entrer un nombre.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int nbProd;
            try {
                nbProd = Integer.parseInt(input);
                if (nbProd <= 0) {
                    JOptionPane.showMessageDialog(null, "La quantité doit être supérieure à 0.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                //pour gérer le nombre dans la commande depasse pas le stock
                int alreadyInOrder = 0;
                OrderLine existingOl = findOrderLine(selectedProd);
                if (existingOl != null) {
                    alreadyInOrder = existingOl.getQuantity();
                }
                int totalRequested = alreadyInOrder + nbProd;
                if (totalRequested > selectedProd.getNbInStock()) {
                    JOptionPane.showMessageDialog(null, "Quantité totale (" + totalRequested + ") dépasse le stock disponible (" + selectedProd.getNbInStock() + ").", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Veuillez entrer un nombre valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            OrderLine existingOl = findOrderLine(selectedProd);
            try {
                if (existingOl == null) {
                    commandListModel.addElement(new OrderLine(nbProd, selectedProd.getPrice(), selectedProd));
                } else {
                    existingOl.addQuantity(nbProd);
                }
                calcTotalPrice();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Quantité invalide", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class DeleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            OrderLine selected = commandList.getSelectedValue();
            if (selected != null) {
                commandListModel.removeElement(selected);
                calcTotalPrice();
            }
        }
    }

    private class ResetButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            users.setSelectedIndex(-1);
            commentsText.setText("");
            discountField.setText("");
            discountField.setEnabled(true);
            happyHourRadio.setSelected(false);
            listingProductPanel.getCategoryComboBox().setSelectedIndex(-1);
            commandListModel.clear();
            calcTotalPrice();
        }
    }

    private class SubmitButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            User selectedUser = (User) users.getSelectedItem();
            if (selectedUser == null || commandListModel.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Veuillez sélectionner un utilisateur et ajouter des produits.");
                return;
            }

            String txt = discountField.getText().trim();
            Integer discount = null;

            if (txt.isEmpty()) {
                discount = null;
            } else {
                try {
                    int val = Integer.parseInt(txt);
                    if (val < 0 || val > 100) {
                        JOptionPane.showMessageDialog(null, "Le pourcentage de remise doit être entre 0 et 100.", "Erreur...", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    discount = val;
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Le pourcentage de remise doit être un nombre valide.", "Erreur...", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            try {
                Order order = new Order(LocalDate.now(),
                        null,
                        discount,
                        commentsText.getText(),
                        happyHourRadio.isSelected(),
                        OrderStatus.IN_PROGRESS.getLabel(),
                        PaymentMethod.NOTPAID.getLabel(),
                        selectedUser
                );
                int orderId = orderController.createOrder(order);
                order.setId(orderId);

                for (int i = 0; i < commandListModel.getSize(); i++) {
                    OrderLine orderLine = commandListModel.getElementAt(i);
                    orderLineController.createOrderLine(orderLine, orderId);

                    //Récupérer le produit
                    Product product = orderLine.getProduct();

                    //Calculer newStock
                    int newStock = product.getNbInStock() - orderLine.getQuantity();

                    // Mettre à jour en base (adapter selon ta méthode dans ProductDBAccess)
                    productController.updateStock(product, newStock);
                    listingProductPanel.refreshAndFilter();
                }

                JOptionPane.showMessageDialog(null, "Commande enregistrée avec succès !");
                new ResetButtonListener().actionPerformed(null);
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage(), "Erreur...", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class refreshPriceListener implements DocumentListener {
        @Override
        public void insertUpdate(DocumentEvent e) {
            calcTotalPrice();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            calcTotalPrice();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            // Pas utile dans notre cas
        }
    }

    private OrderLine findOrderLine(Product prod) {
        for (int i = 0; i < commandListModel.getSize(); i++) {
            OrderLine ol = commandListModel.getElementAt(i);
            if (ol.getProduct().getId().equals(prod.getId())) {
                return ol;
            }
        }
        return null;
    }

    private void calcTotalPrice() {
        // 1) Calcul du total brut
        double rawTotal = 0;
        for (int i = 0; i < commandListModel.getSize(); i++) {
            OrderLine ol = commandListModel.getElementAt(i);
            rawTotal += ol.getProduct().getPrice() * ol.getQuantity();
        }

        // Calcul du pourcentage de remise
        int discountPct = 0;
        String txt = discountField.getText().trim();

        if (!txt.isEmpty()) {
            try {
                int val = Integer.parseInt(txt);
                if (val >= 0 && val <= 100) {
                    discountPct = val;
                } else {
                    discountPct = 0;
                }
            } catch (NumberFormatException e) {
                discountPct = 0;
            }
        }

        // Application de la remise
        double net = (100 - discountPct) / 100.0 * rawTotal;
        // Affichage
        priceField.setText(priceText + String.format("%.2f€", net));
        revalidate();
        repaint();
    }
}
