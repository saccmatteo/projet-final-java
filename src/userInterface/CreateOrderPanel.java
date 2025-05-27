package userInterface;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import controller.*;
import exceptions.DAOException;
import model.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class CreateOrderPanel extends JPanel {
    private Double totalPrice;
    private String priceText;

    // Composants
    private JPanel usersPanel, formPanel, productPanel, commandPanel, middlePanel, buttonPanel;
    private JLabel usersLabel, commentsLabel, discountLabel, cartLabel;
    private JTextField commentsText, discountField, priceField;
    private JButton addProdButton, deleteProdButton, submitButton, resetButton, cancelButton;
    private JCheckBox happyHourRadio;
    private JComboBox<User> users;
    private JList<OrderLine> commandList;
    private DefaultListModel<OrderLine> commandListModel;

    // Controllers
    private UserController userController;
    private OrderController orderController;
    private OrderLineController orderLineController;
    private ProductController productController;
    private ListingProductPanel listingProductPanel;

    public CreateOrderPanel() {
        this.setLayout(new BorderLayout());
        setUserController(new UserController());
        setOrderController(new OrderController());
        setOrderLineController(new OrderLineController());
        setProductController(new ProductController());

        initFormComponents();
        createFormPanel();

        add(usersPanel, BorderLayout.NORTH);
        add(middlePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    //itialise les composants du form
    // initFormComponents
    private void initFormComponents() {
        // Panels
        usersPanel = new JPanel();
        formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        productPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        commandPanel = new JPanel(new BorderLayout());
        middlePanel = new JPanel(new BorderLayout());
        buttonPanel = new JPanel(new BorderLayout());

        // JLabel
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
        discountField.getDocument().addDocumentListener(new RefreshPriceListener());

        addProdButton = new JButton("Ajouter produit");
        deleteProdButton = new JButton("Supprimer produit");
        submitButton = new JButton("Valider commande");
        resetButton = new JButton("Réinitialiser");
        cancelButton = new JButton("Annuler");

        priceText = "Prix total : ";
        totalPrice = 0.0;
        priceField = new JTextField(priceText + totalPrice + "€");
        priceField.setPreferredSize(new Dimension(200, 20));
        priceField.setEnabled(false);

        happyHourRadio = new JCheckBox("   Happy hour  ");
        happyHourRadio.setHorizontalTextPosition(SwingConstants.LEFT);
        happyHourRadio.setFont(new Font("Arial", Font.BOLD, 16));

        try {
            users = new JComboBox<>(userController.getAllUsers().toArray(new User[0]));
            users.setFont(new Font("Arial", Font.BOLD, 16));
            users.setSelectedIndex(-1);
            users.setPreferredSize(new Dimension(500, 50));
        } catch (DAOException daoException) {
            JOptionPane.showMessageDialog(null, "Erreur lors de la récupération des utilisateurs");
        }

        commandListModel = new DefaultListModel<>();
        commandList = new JList<>(commandListModel);
        commandList.setFixedCellHeight(50);
        commandList.setFont(new Font("Arial", Font.BOLD, 24));

        listingProductPanel = new ListingProductPanel();

        // Listeners
        happyHourRadio.addItemListener(new HappyHourListener());

        addProdButton.addActionListener(new AddButtonListener());
        deleteProdButton.addActionListener(new DeleteButtonListener());
        submitButton.addActionListener(new SubmitButtonListener());
        resetButton.addActionListener(new ResetButtonListener());
        cancelButton.addActionListener(new CancelButtonListener());
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
        leftButtons.add(cancelButton);

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

    private class CancelButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            removeAll();
            add(new ListingOrderPanel());

            revalidate();
            repaint();
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

                int alreadyInOrder = 0;
                OrderLine existingOl = findOrderLine(selectedProd);
                if (existingOl != null) {
                    alreadyInOrder = existingOl.getQuantity();
                }
                int totalRequested = alreadyInOrder + nbProd;

                // pour gérer le nombre dans la commande depasse pas le stock
                if (totalRequested > selectedProd.getNbInStock()) {
                    JOptionPane.showMessageDialog(null, "Quantité totale (" + totalRequested + ") dépasse le stock disponible (" + selectedProd.getNbInStock() + ").", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // TRESHOLD
                if (selectedProd.getNbInStock() - totalRequested <= selectedProd.getMinTreshold()) {
                    JOptionPane.showMessageDialog(null, "Veuillez faire attention au stock de ce produit");
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
                refreshPriceField();
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
                refreshPriceField();
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
            refreshPriceField();
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

                    // MAJ en stock
                        //recuperer le produit
                    Product product = orderLine.getProduct();
                        //calculer newStock
                    int newStock = product.getNbInStock() - orderLine.getQuantity();
                        //maj bd
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

    private class RefreshPriceListener implements DocumentListener {
        @Override
        public void insertUpdate(DocumentEvent e) {
            refreshPriceField();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            refreshPriceField();
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
    public void refreshPriceField() {
        ArrayList<OrderLine> orderLines = new ArrayList<>();
        for (int i = 0; i < commandListModel.size(); i++) {
            orderLines.add(commandListModel.get(i));
        }

        Integer discount = null;
        try {
            String discountText = discountField.getText().trim();
            if (!discountText.isEmpty()) {
                discount = Integer.parseInt(discountText);
            }
        } catch (NumberFormatException e) {
            discount = 0;
        }

        double newTotal = orderController.calculateTotalPrice(orderLines, discount);
        priceField.setText(priceText + newTotal + "€");
    }
}
