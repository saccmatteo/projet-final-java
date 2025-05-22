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

    private JPanel usersPanel, formPanel, productPanel, commandPanel, middlePanel, buttonPanel, leftButtonPanel;
    private JLabel usersLabel, commentsLabel, discountLabel, cartLabel;
    private JTextField commentsText, discountField, priceField;
    private JButton addProdButton, deleteProdButton, submitButton, resetButton, cancelButton;
    private JCheckBox happyHourRadio;
    private JScrollPane commandScroll;

    private JComboBox<User> users;
    private JList<OrderLine> commandList;
    private DefaultListModel<OrderLine> commandListModel;

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
        this.listingProductPanel = new ListingProductPanel();

        initFormComponents();
        createFormPanel();

        add(usersPanel, BorderLayout.NORTH);
        add(middlePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
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

    // METHODES
    // Déclare les composants
    private void initFormComponents() {
        // Panels
        usersPanel = new JPanel();
        formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        productPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        commandPanel = new JPanel(new BorderLayout());
        middlePanel = new JPanel(new BorderLayout());
        buttonPanel = new JPanel(new BorderLayout());
        leftButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // JLabel + Style
        usersLabel = new JLabel("Utilisateur gérant la commande : ");
        usersLabel.setFont(new Font("Arial", Font.BOLD, 24));
        commentsLabel = new JLabel("    Commentaires : ");
        commentsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        discountLabel = new JLabel("    Réduction : ");
        discountLabel.setFont(new Font("Arial", Font.BOLD, 16));
        cartLabel = new JLabel("Panier : ");
        cartLabel.setFont(new Font("Arial", Font.BOLD, 16));

        // TextField
        commentsText = new JTextField();
        discountField = new JTextField();

        // Buttons
        addProdButton = new JButton("Ajouter produit");
        resetButton = new JButton("Réinitialiser");
        deleteProdButton = new JButton("Supprimer produit");
        submitButton = new JButton("Valider commande");
        cancelButton = new JButton("Annuler");

        // TextField du prix total
        priceText = "Prix total : ";
        totalPrice = 0.0;
        priceField = new JTextField(priceText + totalPrice);
        priceField.setEnabled(false);

        // CheckBox + Style
        happyHourRadio = new JCheckBox("   Happy hour  ");
        happyHourRadio.setHorizontalTextPosition(SwingConstants.LEFT);
        happyHourRadio.setFont(new Font("Arial", Font.BOLD, 16));

        // UsersComboBox + Style
        users = new JComboBox<>(userController.getAllUsers().toArray(new User[0]));
        users.setFont(new Font("Arial", Font.BOLD, 16));
        users.setSelectedIndex(-1);
        users.setPreferredSize(new Dimension(500, 50));

        // Panier List + Style
        commandListModel = new DefaultListModel<>();
        commandList = new JList<>(commandListModel);
        commandList.setFixedCellHeight(50);
        commandList.setFont(new Font("Arial", Font.BOLD, 24));
        // ScrollPane
        commandScroll = new JScrollPane(commandList);

        // Listeners
        happyHourRadio.addItemListener(new HappyHourListener());
        discountField.getDocument().addDocumentListener(new RefreshPriceListener());
        // Buttons
        addProdButton.addActionListener(new AddButtonListener());
        resetButton.addActionListener(new ResetButtonListener());
        deleteProdButton.addActionListener(new DeleteButtonListener());
        cancelButton.addActionListener(new CancelButtonListener());
        submitButton.addActionListener(new SubmitButtonListener());
    }

    // Créé le formulaire
    private void createFormPanel() {
        // UsersPanel
        usersPanel.add(usersLabel);
        usersPanel.add(users);

        // FormPanel
        formPanel.add(happyHourRadio);
        formPanel.add(new JLabel());
        formPanel.add(discountLabel);
        formPanel.add(discountField);
        formPanel.add(commentsLabel);
        formPanel.add(commentsText);

        // MiddlePanel
        // CommandPanel
        commandPanel.add(cartLabel, BorderLayout.NORTH);
        commandPanel.add(commandScroll, BorderLayout.CENTER);
        // ProductPanel
        productPanel.add(listingProductPanel);
        productPanel.add(commandPanel);
        // Ajout au MiddlePanel
        middlePanel.add(formPanel, BorderLayout.NORTH);
        middlePanel.add(productPanel, BorderLayout.CENTER);

        // ButtonPanel
        // LeftButtonsPanel
        leftButtonPanel.add(addProdButton);
        leftButtonPanel.add(resetButton);
        leftButtonPanel.add(deleteProdButton);
        leftButtonPanel.add(submitButton);
        leftButtonPanel.add(cancelButton);
        // Ajout au ButtonPanel
        buttonPanel.add(leftButtonPanel, BorderLayout.WEST);
        buttonPanel.add(priceField, BorderLayout.EAST);
    }

    // Pour trouver si le produit est déjà présent dans le panier
    private OrderLine findOrderLine(Product selectedProd) {
        int i = 0;
        // Recherche si le produit selectionné est déjà présent dans le panier
        while (i < commandListModel.getSize() && commandListModel.getElementAt(i).getProduct() != selectedProd) { // Si l'élément s'y retrouve déjà
            i++;
        }

        return i > commandListModel.getSize() ? commandListModel.getElementAt(i) : null;
    }

    private void calcTotalPrice() {
        // 1) Calcul du total brut
        double rawTotal = 0;
        for (int i = 0; i < commandListModel.getSize(); i++) {
            OrderLine ol = commandListModel.getElementAt(i);
            rawTotal += ol.getProduct().getPrice() * ol.getQuantity();
        }

        // 2) Calcul du pourcentage de remise
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

        // 3) Application de la remise
        double net = (100 - discountPct) / 100.0 * rawTotal;

        // 4) Affichage
        priceField.setText(priceText + String.format("%.2f€", net));
        revalidate();
        repaint();
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
            if (selectedProd != null) {
                String input = JOptionPane.showInputDialog(null, "Combien de " + selectedProd.getLabel() + " ?");
                if (input != null) { // L'utilisateur n'a pas appuyé sur "Annuler"
                    if (!input.trim().isEmpty()) { // La quantité rentrée n'est pas vide
                        int nbProd;
                        try {
                            nbProd = Integer.parseInt(input);
                            if (nbProd <= 0) { // Si nbProd est > 0 -> OK
                                JOptionPane.showMessageDialog(null, "La quantité doit être supérieure à 0.", "Erreur", JOptionPane.ERROR_MESSAGE);
                            } else {
                                OrderLine existingOl = findOrderLine(selectedProd);
                                    // Si le produit sélectionné est déjà présente dans le panier,
                                    //      alors on prend sa quantité
                                int alreadyInOrder = existingOl != null ? existingOl.getQuantity() : 0;
                                int totalRequested = alreadyInOrder + nbProd; // Quantité du produit sélectionné + nb d'ajout souhaité

                                // Si la quantité souhaité dépasse le nombre en stock -> Ajout annulé
                                if (totalRequested > selectedProd.getNbInStock()) {
                                    JOptionPane.showMessageDialog(null, "Quantité totale (" + totalRequested + ") dépasse le stock disponible (" + selectedProd.getNbInStock() + ").", "Erreur", JOptionPane.ERROR_MESSAGE);
                                } else {
                                    // TRESHOLD
                                    // Si la quantité demandé dépasse le seuil minimum -> Notification
                                    if (selectedProd.getNbInStock() - totalRequested <= selectedProd.getMinTreshold()) {
                                        JOptionPane.showMessageDialog(null, "La quantite demande depasse le seuil. Veuillez faire attention au stock.");
                                    }

                                    // Après envoie de notification, on ajoute soit le produit s'il n'est pas déjà dans le panier,
                                    //      soit on ajoute la quantité avec l'entrée (nbProd)
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
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Veuillez entrer un nombre valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Veuillez entrer un nombre.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
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
            } else {
                String txt = discountField.getText().trim();
                Integer discount = null;
                if (!txt.isEmpty()) { // Si discount n'est pas vide. Si vide -> discout = null
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

                // Création de l'objet et envoie de l'Order à la BD
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

                    // Ajout de toutes les OrderLines
                    for (int i = 0; i < commandListModel.getSize(); i++) {
                        OrderLine orderLine = commandListModel.getElementAt(i);
                        orderLineController.createOrderLine(orderLine, orderId);

                        // MAJ en stock
                        // Récupérer le produit
                        Product product = orderLine.getProduct();
                        // Calculer newStock
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
    }

    private class RefreshPriceListener implements DocumentListener {
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
}
