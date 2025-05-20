package userInterface;
import javax.swing.*;
import controller.*;
import model.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.LocalDate;

public class CreateOrderPanel extends JPanel {
    private Double prixTotal;
    private JPanel usersPanel, middlePanel, formPanel, productPanel, commandPanel, buttonPanel;
    private JLabel usersLabel, commentsLabel, discountLabel;
    private JTextField commentsText, discountField;
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
        prixTotal = 0.0;

        setLayout(new BorderLayout());

        // Panels
        usersPanel = new JPanel();
        formPanel = new JPanel(new GridLayout(3, 2));
        productPanel = new JPanel(new GridLayout(1, 2));
        listingProductPanel = new ListingProductPanel();
        commandPanel = new JPanel(new BorderLayout());
        middlePanel = new JPanel(new BorderLayout());
        buttonPanel = new JPanel();

        // User Panel
        usersLabel = new JLabel("Utilisateur gérant la commande : ");
        usersLabel.setFont(new Font("Arial", Font.BOLD, 24));

        users = new JComboBox<>(userController.getAllUsers().toArray(new User[0]));
        users.setSelectedIndex(-1);
        users.setPreferredSize(new Dimension(500, 50));

        usersPanel.add(usersLabel);
        usersPanel.add(users);

        // Form Panel
        happyHourRadio = new JCheckBox("Happy hour");
        commentsLabel = new JLabel("Commentaires : ");
        commentsText = new JTextField();
        discountLabel = new JLabel("Réduction : ");
        discountField = new JTextField();

        happyHourRadio.addItemListener(new HappyHourListener());

        formPanel.add(happyHourRadio);
        formPanel.add(new JLabel());
        formPanel.add(discountLabel);
        formPanel.add(discountField);
        formPanel.add(commentsLabel);
        formPanel.add(commentsText);

        // Command List
        commandListModel = new DefaultListModel<>();
        commandList = new JList<>(commandListModel);
        JScrollPane commandScroll = new JScrollPane(commandList);

        commandPanel.add(new JLabel("Panier :"), BorderLayout.NORTH);
        commandPanel.add(commandScroll, BorderLayout.CENTER);

        // Product Panel
        productPanel.add(listingProductPanel);
        productPanel.add(commandPanel);

        // Middle Panel = Form + Products
        middlePanel.add(formPanel, BorderLayout.NORTH);
        middlePanel.add(productPanel, BorderLayout.CENTER);

        // Button Panel
        addProdButton = new JButton("Ajouter produit");
        deleteProdButton = new JButton("Supprimer produit");
        submitButton = new JButton("Valider commande");
        resetButton = new JButton("Réinitialiser");

        addProdButton.addActionListener(new AddButtonListener());
        deleteProdButton.addActionListener(new DeleteButtonListener());
        submitButton.addActionListener(new SubmitButtonListener());
        resetButton.addActionListener(new ResetButtonListener());

        buttonPanel.add(addProdButton);
        buttonPanel.add(deleteProdButton);
        buttonPanel.add(submitButton);
        buttonPanel.add(resetButton);

        // Ajout des panels
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
            if (selectedProd == null) return;

            int nbProd;
            try {
                nbProd = Integer.parseInt(JOptionPane.showInputDialog(null, "Combien de " + selectedProd.getLabel() + " ?"));
            } catch (Exception ex) {
                return;
            }

            int i = 0;
            while (i < commandListModel.getSize() && selectedProd.getId() != commandListModel.getElementAt(i).getProduct().getId()) {
                i++;
            }

            if (i == commandListModel.getSize()) {
                commandListModel.addElement(new OrderLine(nbProd, selectedProd.getPrice(), selectedProd));
            } else {
                commandListModel.getElementAt(i).addQuantity(nbProd);
            }

            prixTotal += selectedProd.getPrice() * nbProd;
            revalidate();
            repaint();
        }
    }

    private class DeleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            OrderLine selected = commandList.getSelectedValue();
            if (selected != null) {
                prixTotal -= selected.getProduct().getPrice() * selected.getQuantity();
                commandListModel.removeElement(selected);
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
            prixTotal = 0.0;
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
                    "En cours",
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
}
