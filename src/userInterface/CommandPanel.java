package userInterface;

import controller.OrderController;
import controller.UserController;
import model.Order;
import model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public class CommandPanel extends JPanel {
    private UserController userController;
    private OrderController orderController;

    private ArrayList<User> users;
    private ArrayList<Order> orders;

    private JPanel allUsersPanel, buttonsPanel, allOrdersPanel;
    private JButton removeCommand, createCommand, updateCommand, validateCommand, clearSelection;
    private JLabel userLabel;

    private JComboBox<User> usersComboList;
    private JList<Order> ordersList;
    private JScrollPane ordersScrollPane;

    // CONSTRUCTORS
    public CommandPanel() {
        this.setLayout(new BorderLayout());

        try {
            // User choice
            allUsersPanel = new JPanel(new FlowLayout());
            setUserController(new UserController());
            // Params Label combolist
            userLabel = new JLabel("Utilisateur : ");
            userLabel.setFont(new Font("Arial", Font.BOLD, 30));
            allUsersPanel.setBorder(new EmptyBorder(40, 0, 40, 0));
            users = userController.getAllUsers();
            // ComboBox params
            usersComboList = new JComboBox<>(users.toArray(new User[0]));
            usersComboList.setSelectedItem(null); // Ne prend en compte aucune valeur dans la comboBox
            usersComboList.setPreferredSize(new Dimension(300, 40));
            usersComboList.setFont(new Font("Arial", Font.BOLD, 20));
            // ComboBox Listener
            usersComboList.addItemListener(new ComboBoxListener());
            // Ajout + params au Panel
            allUsersPanel.add(userLabel);
            allUsersPanel.add(usersComboList);
            this.add(allUsersPanel, BorderLayout.NORTH);

            // Orders buttons
            buttonsPanel = new JPanel(new FlowLayout());
            buttonsPanel.setBorder(new EmptyBorder(0,0, 40, 0));
            removeCommand = new JButton("Supprimer commande");
            createCommand = new JButton("Prendre commande");
            updateCommand = new JButton("Modifier commande");
            validateCommand = new JButton("Cloturer commande");

            buttonsPanel.add(createCommand);
            // LISTENERS + Ajout au Panel
            updateCommand.setEnabled(false);
            buttonsPanel.add(updateCommand);

            removeCommand.setEnabled(false);
            buttonsPanel.add(removeCommand);

            validateCommand.setEnabled(false);
            buttonsPanel.add(validateCommand);
            this.add(buttonsPanel);

            // Orders list
            allOrdersPanel = new JPanel(new FlowLayout());
            setOrderController(new OrderController());
            this.orders = orderController.getAllOrders();
            ordersList = new JList<>(orders.toArray(new Order[0])); // Conversion en tableau
            // Params JList
            ordersList.setVisibleRowCount(5);
            ordersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            ordersList.clearSelection();
            // Params affichage JList
            ordersList.setFont(new Font("Arial", Font.PLAIN, 30)); // Agrandir la taille de la police
            ordersList.setFixedCellHeight(60); // agrandir les lignes de la JList
            ordersList.setBorder(new EmptyBorder(10, 10, 10, 10));
            ordersList.setCellRenderer(new DefaultListCellRenderer() {
                public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                    label.setHorizontalAlignment(SwingConstants.CENTER); // Centrage horizontal du texte
                    label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY)); // 👈 Ajoute une bordure grise en bas
                    return label;
                }
            }); // À voir si on garde ou pas, essayer de tout comprendre
            ordersList.addListSelectionListener(new JListListener());
            // ScrollPane
            ordersScrollPane = new JScrollPane(ordersList);
            ordersScrollPane.setPreferredSize(new Dimension(1000, 350));
            // Ajoute + params au Panel
            allOrdersPanel.add(ordersScrollPane);
            allOrdersPanel.setBorder(new EmptyBorder(0,0, 40, 0));
            allOrdersPanel.setPreferredSize(new Dimension(1000, 600));
            // Ajout clearButtonSelection + params affichage du bouton
            clearSelection = new JButton("Réinitialiser le choix");
            clearSelection.addActionListener(new ButtonsListener());
            allOrdersPanel.add(clearSelection);
            this.add(allOrdersPanel, BorderLayout.SOUTH);
            ordersScrollPane.setVisible(false);
            clearSelection.setVisible(false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
    // SETTERS
    public void setUserController(UserController userController) {
        this.userController = userController;
    }

    public void setOrderController(OrderController orderController) {
        this.orderController = orderController;
    }

    // SOUS-CLASSE LISTENERS
    private class ComboBoxListener implements ItemListener {
        public void itemStateChanged(ItemEvent e) {
            ordersScrollPane.setVisible(true);
            clearSelection.setVisible(true);
        }
    }

    private class JListListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            updateCommand.setEnabled(true);
            removeCommand.setEnabled(true);
            validateCommand.setEnabled(true);
        }
    }

    private class ButtonsListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == clearSelection) {
                ordersList.clearSelection();
                updateCommand.setEnabled(false);
                removeCommand.setEnabled(false);
                validateCommand.setEnabled(false);
            }
        }
    }
}