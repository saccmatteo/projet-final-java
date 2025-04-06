package userInterface;

import controller.OrderController;
import controller.UserController;
import model.Order;
import model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public class CommandPanel extends JPanel {
    private UserController userController;
    private OrderController orderController;
    private ArrayList<User> users;
    private ArrayList<Order> orders;
    private JPanel allUsersPanel, buttonsPanel, allOrdersPanel;
    private JButton removeCommand, createCommand, updateCommand;
    private JLabel userLabel;
    private JComboBox<User> usersComboList;
    private JList<Order> ordersList;
    private JScrollPane ordersScrollPane;

    // CONSTRUCTORS
    public CommandPanel() {
        this.setLayout(new BorderLayout());

        try {
            // User choice
            setUserController(new UserController());
            userLabel = new JLabel("Utilisateur : ");
            allUsersPanel = new JPanel(new FlowLayout());
            allUsersPanel.setBorder(new EmptyBorder(40, 0, 40, 0));
            users = userController.getAllUsers();

            usersComboList = new JComboBox<>(users.toArray(new User[0]));
            usersComboList.setPreferredSize(new Dimension(300, 30));
                //ComboBox Listener
            ComboBoxListener comboListener = new ComboBoxListener();
            usersComboList.addItemListener(comboListener);

            allUsersPanel.add(userLabel);
            allUsersPanel.add(usersComboList);
            this.add(allUsersPanel, BorderLayout.NORTH);

            // Orders buttons

            buttonsPanel = new JPanel(new FlowLayout());
            buttonsPanel.setBorder(new EmptyBorder(0,0, 40, 0));
            removeCommand = new JButton("Supprimer commande");
            createCommand = new JButton("Prendre commande");
            updateCommand = new JButton("Modifier commande");
            buttonsPanel.add(updateCommand);
            buttonsPanel.add(createCommand);
            buttonsPanel.add(removeCommand);
            this.add(buttonsPanel);

            // Orders list
            allOrdersPanel = new JPanel(new FlowLayout());
            setOrderController(new OrderController());
            this.orders = orderController.getAllOrders();
            ordersList = new JList<>(orders.toArray(new Order[0])); // Conversion en tableau
            ordersList.setVisibleRowCount(5);
            ordersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            ordersList.clearSelection();
            ordersScrollPane = new JScrollPane(ordersList);
            allOrdersPanel.add(ordersScrollPane);
            allOrdersPanel.setBorder(new EmptyBorder(0,0, 40, 0));
            this.add(allOrdersPanel, BorderLayout.SOUTH);

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

        }
    }
}