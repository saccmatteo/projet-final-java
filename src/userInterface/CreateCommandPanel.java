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

public class CreateCommandPanel extends JFrame {
    private Double prixTotal;
    private Container container;
    private JPanel usersPanel, middlePanel, formPanel, productPanel, commandPanel, buttonPanel;
    private JLabel usersLabel, commentsLabel, discountLabel;
    private JTextField commentsText, discountField;
    private JButton addProdButton, deleteProdButton;
    private JCheckBox happyHourRadio;
    private JComboBox<User> users;
    private JList<OrderLine> commandList;
    private DefaultListModel<OrderLine> commandListModel;

    private JMenuBar menuBar;
    private JMenu commandeAction;
    private JMenuItem resetItem, submitItem, cancelItem;

    private UserController userController;
    private OrderController orderController;
    private OrderLineController orderLineController;
    private ListingProductPanel listingProductPanel;

    public CreateCommandPanel() {
        setTitle("Création de commande");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setBounds(0, 0, 1920, 1080);
        container = this.getContentPane();
        container.setLayout(new BorderLayout());

        setUserController(new UserController());
        setOrderController(new OrderController());
        setOrderLineController(new OrderLineController());
        prixTotal = 0.0;

        // Declaration des panels
        usersPanel = new JPanel();
        formPanel = new JPanel(new GridLayout(3,2));
        productPanel = new JPanel(new BorderLayout());
        listingProductPanel = new ListingProductPanel();
        commandPanel = new JPanel();
        middlePanel = new JPanel(new GridLayout(2,1));
        buttonPanel = new JPanel();

        // User Panel
        usersLabel = new JLabel("Utilisateur gérant la commande : ");
        usersLabel.setFont(new Font("Arial", Font.BOLD, 24));

        users = new JComboBox<>(userController.getAllUsers().toArray(new User[0]));
        users.setSelectedIndex(-1);
        users.setPreferredSize(new Dimension(500, 50));

        usersPanel.add(usersLabel);
        usersPanel.add(users);

        // Middle Panel
            // Form Panel
        happyHourRadio = new JCheckBox("Happy hour");
        commentsLabel = new JLabel("Commentaires : ");
        commentsText = new JTextField();
        discountLabel = new JLabel("Réduction : ");
        discountField = new JTextField();

                 // Ajout des listeners
        happyHourRadio.addItemListener(new HappyHourListener());

                // Ajout dans le form
        formPanel.add(happyHourRadio);
        formPanel.add(new JLabel());
        formPanel.add(discountLabel);
        formPanel.add(discountField);
        formPanel.add(commentsLabel);
        formPanel.add(commentsText);

            // Product Panel
                // List des produits

                // JList du panier
        commandListModel = new DefaultListModel<>();
        commandList = new JList<>(commandListModel);

                // Ajout au panel
        productPanel.add(listingProductPanel);
        productPanel.add(commandList);

                // Listeners

        // Button Panel
        addProdButton = new JButton("Ajouter produit");
        deleteProdButton = new JButton("Supprimer produit");

            // Listeners
        addProdButton.addActionListener(new AddProdListener());

            // Ajout au panel
        buttonPanel.add(addProdButton);
        buttonPanel.add(deleteProdButton);

        // Bar de menu
        menuBar = new JMenuBar();
        commandeAction = new JMenu("Commande");
        cancelItem = new JMenuItem("Annuler la commande");
        resetItem = new JMenuItem("Réinitialiser la commande");
        submitItem = new JMenuItem("Envoyer la commande");

            // Listeners
        resetItem.addActionListener(new ResetItemListener());
        cancelItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        submitItem.addActionListener(new SubmitItemListener());
            // Ajout a la bar
        commandeAction.add(cancelItem);
        commandeAction.add(resetItem);
        commandeAction.add(submitItem);
        menuBar.add(commandeAction);

        // Ajout au conteneur
        setJMenuBar(menuBar);
            // Nord
        container.add(usersPanel, BorderLayout.NORTH);
            // Milieu
        middlePanel.add(formPanel);
                // 2 JLists
        productPanel.add(listingProductPanel, BorderLayout.EAST);
        productPanel.add(commandPanel, BorderLayout.WEST);

        middlePanel.add(productPanel);
        container.add(middlePanel, BorderLayout.CENTER);
            // Sud
        container.add(buttonPanel, BorderLayout.SOUTH);
        setVisible(true);

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

    // Methodes

    // LISTENERS
    private class HappyHourListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                discountField.setText("50");
                discountField.setEnabled(false);
            }else{
                discountField.setText(" ");
                discountField.setEnabled(true);
            }
        }
    }

    private class AddProdListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Product selectedProd = listingProductPanel.getProductJList().getSelectedValue();
            int nbProd = Integer.parseInt(JOptionPane.showInputDialog(null, "Combien de " + selectedProd.getLabel() ));

            int i = 0;
            while (i < commandListModel.getSize() && selectedProd.getId() != commandListModel.getElementAt(i).getProduct().getId()) {
                i++;
            }

            if (i == commandListModel.getSize()){
                commandListModel.addElement(new OrderLine(nbProd, selectedProd.getPrice(), selectedProd));
            }else{
                commandListModel.getElementAt(i).addQuantity(nbProd);
                revalidate();
                repaint();
            }

            prixTotal += selectedProd.getPrice() * nbProd;
        }
    }

    private class ResetItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            new CreateCommandPanel();
        }
    }

    private class SubmitItemListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            User selectedUser = (User) users.getSelectedItem();
            Order order = new Order(LocalDate.now(),
                            LocalDate.now(),
                            Integer.valueOf((discountField.getText().equals("") ?"0":discountField.getText())),
                            commentsText.getText(),
                            happyHourRadio.isSelected(),
                            "En cours",
                            "Pas paye",
                            selectedUser);
            orderController.createCommand(order);

            for (int i = 0;i < commandListModel.getSize();i++) {
                orderLineController.createOrderLine(commandListModel.getElementAt(i));
            }
            JOptionPane.showMessageDialog(null, "Ajout de la commande");
        }
    }
}
