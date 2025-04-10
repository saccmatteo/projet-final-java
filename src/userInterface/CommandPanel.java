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
    private JLabel userLabel, listLabel;
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
            allUsersPanel.setBorder(new EmptyBorder(100, 0, 60, 0));
            users = userController.getAllUsers();
            // ComboBox params
            usersComboList = new JComboBox<>(users.toArray(new User[0]));
            usersComboList.setSelectedItem(null); // Ne prend en compte aucune valeur dans la comboBox
            usersComboList.setPreferredSize(new Dimension(400, 45));
            usersComboList.setFont(new Font("Arial", Font.BOLD, 20));
            // ComboBox Listener
            usersComboList.addItemListener(new ComboBoxListener());
            // Ajout + params au Panel
            allUsersPanel.add(userLabel);
            allUsersPanel.add(usersComboList);
            this.add(allUsersPanel, BorderLayout.NORTH);

            // Orders buttons
            buttonsPanel = new JPanel(new FlowLayout());
            buttonsPanel.setBorder(new EmptyBorder(60,0, 10, 0));
            removeCommand = new JButton("Supprimer commande");
            removeCommand.setPreferredSize(new Dimension(250, 50));
            createCommand = new JButton("Prendre commande");
            createCommand.setPreferredSize(new Dimension(250, 50));
            updateCommand = new JButton("Modifier commande");
            updateCommand.setPreferredSize(new Dimension(250, 50));
            validateCommand = new JButton("Cloturer commande");
            validateCommand.setPreferredSize(new Dimension(250, 50));

            buttonsPanel.add(createCommand);
            // LISTENERS + Ajout au Panel
            updateCommand.setEnabled(false);
            buttonsPanel.add(updateCommand);

            removeCommand.setEnabled(false);
            removeCommand.addActionListener(new ButtonsListener());
            buttonsPanel.add(removeCommand);

            validateCommand.setEnabled(false);
            validateCommand.addActionListener(new ButtonsListener());
            buttonsPanel.add(validateCommand);
            this.add(buttonsPanel);

            // Orders list
            allOrdersPanel = new JPanel();
            allOrdersPanel.setLayout(new BoxLayout(allOrdersPanel, BoxLayout.Y_AXIS));
            setOrderController(new OrderController());
            listLabel = new JLabel("<html><u><b>Sélectionnez une commande :</u></b></html>");
                // Affichage
            listLabel.setFont(new Font("Arial", Font.PLAIN, 25));
            listLabel.setHorizontalAlignment(SwingConstants.CENTER);
            listLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            listLabel.setVisible(false);
            allOrdersPanel.add(listLabel, BorderLayout.NORTH);

            // Params JList
            this.orders = orderController.getAllOrders();
            ordersList = new JList<>(orders.toArray(new Order[0])); // Conversion en tableau
            ordersList.setVisibleRowCount(5);
            ordersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            ordersList.clearSelection();
                // Affichage
            ordersList.setFont(new Font("Arial", Font.PLAIN, 22)); // Agrandir la taille de la police
            ordersList.setFixedCellHeight(50); // agrandir les lignes de la JList
            ordersList.setBorder(new EmptyBorder(10, 10, 10, 10));
            ordersList.setCellRenderer(new DefaultListCellRenderer() {
                public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                    label.setHorizontalAlignment(SwingConstants.CENTER); // Centrage horizontal du texte
                    label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY)); // 👈 Ajoute une bordure grise en bas
                    return label;
                }
            }); // À voir si on garde ou pas, essayer de tout comprendre
                // Listeners
            ordersList.addListSelectionListener(new JListListener());

            // ScrollPane
            ordersScrollPane = new JScrollPane(ordersList);
                // Affichage
            ordersScrollPane.setMaximumSize(new Dimension(800, 400));
            ordersScrollPane.setBorder(new EmptyBorder(40, 0, 40, 0));
            ordersScrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
            ordersScrollPane.setVisible(false);

            // Ajoute + params au Panel
            allOrdersPanel.add(ordersScrollPane, BorderLayout.CENTER);
            allOrdersPanel.setBorder(new EmptyBorder(0,0, 40, 0));
            allOrdersPanel.setPreferredSize(new Dimension(900, 550));
            // Ajout clearButtonSelection + params affichage du bouton
            clearSelection = new JButton("Réinitialiser le choix");
            clearSelection.setAlignmentX(Component.CENTER_ALIGNMENT);
            clearSelection.setPreferredSize(new Dimension(250, 45));
            clearSelection.setMaximumSize(new Dimension(250, 45));
                // Listeners
            clearSelection.addActionListener(new ButtonsListener());
            clearSelection.setVisible(false);
            allOrdersPanel.add(clearSelection, BorderLayout.SOUTH);
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
            ordersScrollPane.setVisible(true);
            clearSelection.setVisible(true);
            listLabel.setVisible(true);
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
            } else if (e.getSource() == removeCommand) {
                int reponse = JOptionPane.showOptionDialog(null, "Voulez-vous supprimer cette commande ?", "Confirmation", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Oui", "Non"}, null);
                if (reponse == JOptionPane.YES_OPTION) {
                    int selectedOrderId = ordersList.getSelectedValue().getId();

                    orderController.removeCommand(selectedOrderId);
                    orders = orderController.getAllOrders();
                    ordersList.setListData(orders.toArray(new Order[0]));
                    JOptionPane.showMessageDialog(null, "Commande supprimée !", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                }
            } else if (e.getSource() == validateCommand) {
                orderController.updateStatus(ordersList.getSelectedValue().getId());
                ordersList.getSelectedValue().setStatusLabel("Terminé");
                orders = orderController.getAllOrders();
                ordersList.setListData(orders.toArray(new Order[0]));
                JOptionPane.showMessageDialog(null, "Commande clôturée !", "Confirmation", JOptionPane.INFORMATION_MESSAGE );
            }
        }
    }
}