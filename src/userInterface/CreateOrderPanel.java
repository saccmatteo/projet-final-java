package userInterface;

import javax.swing.*;
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
    private JLabel usersLabel, commentsLabel, discountLabel;
    private JTextField commentsText, discountField, priceField;
    private JButton addProdButton, deleteProdButton, submitButton, resetButton;
    private JCheckBox happyHourRadio;
    private JComboBox<User> users;
    private JList<OrderLine> commandList;
    private DefaultListModel<OrderLine> commandListModel;

    private UserController userController;
    private OrderController orderController;
    private OrderLineController orderLineController;
    private ListingProductPanel listingProductPanel;

    public CreateOrderPanel() {
        setUserController(new UserController());
        setOrderController(new OrderController());
        setOrderLineController(new OrderLineController());

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
    private void initFormComponents() {
        usersPanel = new JPanel();
        formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        productPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        commandPanel = new JPanel(new BorderLayout());
        middlePanel = new JPanel(new BorderLayout());
        buttonPanel = new JPanel(new FlowLayout());

        usersLabel = new JLabel("Utilisateur gérant la commande : ");
        usersLabel.setFont(new Font("Arial", Font.BOLD, 24));
        commentsLabel = new JLabel("Commentaires : ");
        discountLabel = new JLabel("Réduction : ");

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


        happyHourRadio = new JCheckBox("Happy hour");

        users = new JComboBox<>(userController.getAllUsers().toArray(new User[0]));
        users.setSelectedIndex(-1);
        users.setPreferredSize(new Dimension(500, 50));

        commandListModel = new DefaultListModel<>();
        commandList = new JList<>(commandListModel);

        listingProductPanel = new ListingProductPanel();
    }

    //créé le formulaire
    private void createFormPanel() {
        usersPanel.add(usersLabel);
        usersPanel.add(users);

        formPanel.add(happyHourRadio);
        formPanel.add(new JLabel());
        formPanel.add(discountLabel);
        formPanel.add(discountField);
        formPanel.add(commentsLabel);
        formPanel.add(commentsText);

        // Panel commande (liste panier)
        JScrollPane commandScroll = new JScrollPane(commandList);
        commandPanel.add(new JLabel("Panier :"), BorderLayout.NORTH);
        commandPanel.add(commandScroll, BorderLayout.CENTER);

        // Panel produits + panier
        productPanel.add(listingProductPanel);
        productPanel.add(commandPanel);

        // Panel milieu
        middlePanel.add(formPanel, BorderLayout.NORTH);
        middlePanel.add(productPanel, BorderLayout.CENTER);

        // Panel boutons
        buttonPanel.add(addProdButton);
        buttonPanel.add(deleteProdButton);
        buttonPanel.add(submitButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(priceField);
    }

    // SETTERS
    public void setOrderController(OrderController orderController) {
        this.orderController = orderController;
    }

    public void setUserController(UserController userController) {
        this.userController = userController;
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

            int nbProd;
            try {
                nbProd = Integer.parseInt(JOptionPane.showInputDialog(null, "Combien de " + selectedProd.getLabel() + " ?"));
            } catch (Exception ex) {
                return;
            } // à regarder si le le produit est null ou si c'est 0 non ?

            int productQuantity = nbProd;
            if (productQuantity <= 0) {
                // Faire exception non ?
            }

            OrderLine existingOl = findOrderLine(selectedProd);
            if (existingOl == null) {
                commandListModel.addElement(new OrderLine(productQuantity, selectedProd.getPrice(), selectedProd));
            } else {
                existingOl.addQuantity(productQuantity);
            }
            calcTotalPrice();
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
            commandListModel.clear();
            totalPrice = 0.0;
        }
    }

    private class SubmitButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            User selectedUser = (User) users.getSelectedItem();
            if (selectedUser == null || commandListModel.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Veuillez sélectionner un utilisateur et ajouter des produits.");
                return;
            }

            int discount = 0;
            try {
                discount = Integer.parseInt(discountField.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Réduction invalide.");
                return;
            }

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
            }
            JOptionPane.showMessageDialog(null, "Commande enregistrée avec succès !");
            new ResetButtonListener().actionPerformed(null); // Reset après création
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
        int discountPct;
        String txt = discountField.getText().trim();
        discountPct = txt.isEmpty() ? 0 : Integer.parseInt(txt);
        if (discountPct < 0 || discountPct > 100) {
            discountPct = 0;
        }
        // Application de la remise
        double net = (100 - discountPct) / 100.0 * rawTotal;
        // Affichage
        priceField.setText(priceText + String.format("%.2f€", net));
        revalidate();
        repaint();
    }
}
