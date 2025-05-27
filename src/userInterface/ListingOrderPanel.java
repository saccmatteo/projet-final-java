package userInterface;

import controller.*;
import exceptions.DAOException;
import model.Order;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class ListingOrderPanel extends JPanel {
    private JLabel commandLabel;
    private JScrollPane ordersScrollPane;

    private ArrayList<Order> orders;
    private JList<Order> ordersList;

    private OrderController orderController;
    private OrderLineController orderLineController;

    public ListingOrderPanel() {
        try {
            this.setLayout(new BorderLayout());
            setOrderController(new OrderController());
            setOrderLineController(new OrderLineController());
            this.orders = orderController.getAllOrders();

            //cas où la ArrayList est vide
            if (orders == null || orders.isEmpty()) {
                commandLabel.setText("Aucune commande à afficher.");
            }else {
                // Methode qui SET le prix total de TOUTES les commandes
                for (Order order : orders) {
                    order.setTotaPrice(orderLineController.getTotalPriceOrderLine(order.getId()));
                }

                // Orders JList
                ordersList = new JList<>(orders.toArray(new Order[0]));
                ordersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                ordersList.clearSelection();
                // Style
                ordersList.setFont(new Font("Arial", Font.BOLD, 28)); // Agrandir la taille de la police
                ordersList.setFixedCellHeight(50); // agrandir les lignes de la JList
                ordersList.setBorder(new EmptyBorder(10, 20, 10, 20));
                ordersList.setCellRenderer(new DefaultListCellRenderer() {
                    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                        label.setHorizontalAlignment(SwingConstants.CENTER); // Centrage horizontal du texte
                        label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
                        return label;
                    }
                });

                // ScrollPane + Style
                ordersScrollPane = new JScrollPane(ordersList);
                ordersScrollPane.setMaximumSize(new Dimension(800, 400));
                ordersScrollPane.setBorder(new EmptyBorder(40, 0, 40, 0));
                ordersScrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
                ordersScrollPane.setVisible(true);
                // CommendLabel pour la liste des commandes + Style
                commandLabel = new JLabel("Liste des commandes en cours : ");
                commandLabel.setFont(new Font("Arial", Font.BOLD, 30));
                commandLabel.setHorizontalAlignment(SwingConstants.CENTER);
                commandLabel.setBorder(new EmptyBorder(20, 0, 10, 0));

                // Ajout au panel
                this.add(commandLabel, BorderLayout.NORTH);
                this.add(ordersScrollPane, BorderLayout.CENTER);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    // GETTERS
    public OrderController getOrderController() {
        return orderController;
    }
    public JList<Order> getOrdersList() {
        return ordersList;
    }

    // SETTERS
    public void setOrderController(OrderController orderController) {
        this.orderController = orderController;
    }
    public void setOrderLineController(OrderLineController orderLineController) {
        this.orderLineController = orderLineController;
    }

    // METHODES
    // Methode pour refresh l'affichage
    public void refreshOrders() {
        try {
            this.orders = orderController.getAllOrders();
            for (Order order : orders) {
                order.setTotaPrice(orderLineController.getTotalPriceOrderLine(order.getId()));
            }
            ordersList.setListData(orders.toArray(new Order[0]));
        } catch (DAOException daoException) {
            JOptionPane.showMessageDialog(null, "Erreur lors de la récupération de liste des commandes");
        }
    }



}