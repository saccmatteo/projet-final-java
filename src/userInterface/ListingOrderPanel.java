package userInterface;

import controller.*;
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

    // CONSTRUCTOR
    public ListingOrderPanel() {
        this.setLayout(new BorderLayout());

        setOrderController(new OrderController());
        setOrderLineController(new OrderLineController());
        this.orders = orderController.getAllOrders();

        //cas où la ArrayList est vide
        if (!orders.isEmpty()) {
            // Methode qui SET le prix total de TOUTES les commandes
            for (Order order : orders) {
                order.setTotaPrice(orderLineController.getTotalPriceOrderLine(order.getId()));
            }

            // Initialisation
            ordersList = new JList<>(orders.toArray(new Order[0]));
            ordersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            ordersList.clearSelection();

            ordersScrollPane = new JScrollPane(ordersList); // Faire en sorte que la JList soit plus beau ????
            commandLabel = new JLabel("Liste des commandes en cours : ");

                // Apparence liste des orders (JList)
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
                // Apparence scrollPane
            ordersScrollPane.setMaximumSize(new Dimension(800, 400));
            ordersScrollPane.setBorder(new EmptyBorder(40, 0, 40, 0));
            ordersScrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
            ordersScrollPane.setVisible(true);
                // Apparence du label pour la liste des commandes
            commandLabel.setFont(new Font("Arial", Font.BOLD, 30));
            commandLabel.setHorizontalAlignment(SwingConstants.CENTER);
            commandLabel.setBorder(new EmptyBorder(20, 0, 10, 0));

            // Ajout au panel
            this.add(commandLabel, BorderLayout.NORTH);
            this.add(ordersScrollPane, BorderLayout.CENTER);
        }else{
            JOptionPane.showMessageDialog(null, "Aucune commande à afficher.");
        }
    }

    // GETTERS
    public JList<Order> getOrdersList() {
        return ordersList;
    }

    public OrderController getOrderController() {
        return orderController;
    }

    //SETTERS
    public void setOrderController(OrderController orderController) {
        this.orderController = orderController;
    }

    public void setOrderLineController(OrderLineController orderLineController) {
        this.orderLineController = orderLineController;
    }

    // METHODES
        // Methode pour refresh l'affichage
    public void refreshOrders() {
        this.orders = orderController.getAllOrders();
        for (Order order : orders) {
            order.setTotaPrice(orderLineController.getTotalPriceOrderLine(order.getId()));
        }

        ordersList.setListData(orders.toArray(new Order[0])); // SET les anciens avec des nouvelles Order (MAJ)
    }


}