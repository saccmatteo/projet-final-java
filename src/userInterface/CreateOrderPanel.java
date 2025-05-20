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
    private static String priceText = "Prix total : ";

    // Composants
    private JPanel usersPanel, formPanel, productPanel, commandPanel, middlePanel, buttonPanel;
    private JLabel usersLabel, commentsLabel, discountLabel;
    private JTextField commentsText, discountField, priceField;
    private JButton addProdButton, deleteProdButton, submitButton, resetButton;
    private JCheckBox happyHourRadio;
    private JScrollPane commandScroll;
    private JComboBox<User> users;
    private JList<OrderLine> commandList;
    private DefaultListModel<OrderLine> commandListModel;

    // Controllers
    private UserController userController;
    private OrderController orderController;
    private OrderLineController orderLineController;
    private ListingProductPanel listingProductPanel;

    public CreateOrderPanel() {
        setUserController(new UserController());
        setOrderController(new OrderController());
        setOrderLineController(new OrderLineController());

        this.setLayout(new BorderLayout());

        initFormComponents();
        createFormPanel();

        this.add(usersPanel, BorderLayout.NORTH);
        this.add(middlePanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);

        happyHourRadio.addItemListener(new HappyHourListener());
        addProdButton.addActionListener(new AddButtonListener());
        resetButton.addActionListener(new ResetButtonListener());
        deleteProdButton.addActionListener(new DeleteButtonListener());
        submitButton.addActionListener(new SubmitButtonListener());
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

    // METHODES
        // Déclaration les composants du form
    private void initFormComponents() {
        // Panels
        usersPanel = new JPanel();
        productPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        commandPanel = new JPanel(new BorderLayout());
        middlePanel = new JPanel(new BorderLayout());
        buttonPanel = new JPanel(new FlowLayout());

        // Labels
        usersLabel = new JLabel("Utilisateur gérant la commande : ");
        usersLabel.setFont(new Font("Arial", Font.BOLD, 24));
        commentsLabel = new JLabel("Commentaires : ");
        discountLabel = new JLabel("Réduction (1-100) : ");

        // Text fields
        commentsText = new JTextField();
        discountField = new JTextField();
        discountField.getDocument().addDocumentListener(new refreshPriceListener()); // Refresh automatiquement le totalPrice l'affichage

        totalPrice = 0.0;
        priceField = new JTextField(priceText + totalPrice);
        priceField.setEnabled(false);

        // Buttons
        addProdButton = new JButton("Ajouter produit");
        deleteProdButton = new JButton("Supprimer produit");
        submitButton = new JButton("Valider commande");
        resetButton = new JButton("Réinitialiser");

        // Check Box
        happyHourRadio = new JCheckBox("Happy hour");
        users = new JComboBox<>(userController.getAllUsers().toArray(new User[0]));
        users.setSelectedIndex(-1);
        users.setPreferredSize(new Dimension(500, 50));

        // Liste des produits commandés
        commandListModel = new DefaultListModel<>();
        commandList = new JList<>(commandListModel); // La JList suit le model -> Si on modifie le model, la Jlist est modifiée
        commandScroll = new JScrollPane(commandList);

        // Liste des produits
        listingProductPanel = new ListingProductPanel();
    }
        // Création du form
    private void createFormPanel() {
        // Panel user
        usersPanel.add(usersLabel);
        usersPanel.add(users);

        // Panel form
        formPanel.add(happyHourRadio);
        formPanel.add(new JLabel());
        formPanel.add(discountLabel);
        formPanel.add(discountField);
        formPanel.add(commentsLabel);
        formPanel.add(commentsText);
        // Panel commandes (liste du panier)
        commandPanel.add(new JLabel("Panier :"), BorderLayout.NORTH);
        commandPanel.add(commandScroll, BorderLayout.CENTER);
        // Panel produits + panier des commandes
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

        // NORTH UserPanel
        // CENTER MiddlePanel : NORTH FormPanel (formulaire)
        //                      CENTER ProductPanel : Liste des produits
        //                                       Liste du panier
        // SOUTH ButtonPanel
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
            if (selectedProd != null) {
                int nbProd = Integer.parseInt(JOptionPane.showInputDialog(null, "Combien de " + selectedProd.getLabel() + " ?"));
                OrderLine existingOl = findOrderLine(selectedProd);

                // Si null, le produit sélectionné ne se trouve pas dedans
                if (existingOl == null) {
                    commandListModel.addElement(new OrderLine(nbProd, selectedProd.getPrice(), selectedProd));
                } else {
                    existingOl.addQuantity(nbProd);
                }
                calcTotalPrice();
            }
        }
    }

    private class DeleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            OrderLine selected = commandList.getSelectedValue();
            if (selected != null) { // Si un produit est sélectionné
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
            if (selectedUser != null && !(commandListModel.isEmpty())) {
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

                // Message de confirmation + reset du formulaire
                JOptionPane.showMessageDialog(null, "Commande enregistrée avec succès !");
                new ResetButtonListener().actionPerformed(null); // Reset après création
                //
                removeAll();
                new CreateOrderPanel();
            }else{
                JOptionPane.showMessageDialog(null, "Veuillez sélectionner un utilisateur et ajouter des produits.");
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

        }
    }

    private OrderLine findOrderLine(Product selectedProd) {
        int i = 0;
        // Recherche si le produit selectionné est déjà présent dans le panier
        while (i < commandListModel.getSize() && commandListModel.getElementAt(i).getProduct() != selectedProd) {
            i++;
        }
        if (i != commandListModel.getSize()) { // Si l'élément s'y retrouve déjà
            return commandListModel.getElementAt(i);
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

        // 2) Calcul du pourcentage de remise
        String txt = discountField.getText().trim();
        int discountPct = txt.isEmpty() ? 0 : Integer.parseInt(txt);
        if (discountPct < 0 || discountPct > 100) {
            discountPct = 0;
        }

        // 4) Application de la remise
        double net = rawTotal * (100 - discountPct) / 100;

        // 5) Affichage
        priceField.setText(priceText + String.format("%.2f€", net));
        revalidate();
        repaint();
    }
}
