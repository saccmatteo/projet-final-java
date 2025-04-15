package userInterface;

import controller.OrderController;
import model.Order;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class ListingOrderPanel extends JPanel {
    private OrderController orderController;
    private JLabel commandLabel;
    private ArrayList<Order> orders;
    private JList<Order> ordersList;
    private JScrollPane ordersScrollPane;

    // CONSTRUCTORS
    public ListingOrderPanel() {
        this.setLayout(new BorderLayout());

        try {
            setOrderController(new OrderController());

            this.orders = orderController.getAllOrders();

            //cas où la ArrayList est vide
            if (orders == null || orders.isEmpty()) {
                commandLabel.setText("Aucune commande à afficher.");
            }

            ordersList = new JList<>(orders.toArray(new Order[0]));
            ordersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            ordersList.clearSelection();

            //apparenceJList
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

            ordersScrollPane = new JScrollPane(ordersList);

            //apparence scrollPane
            ordersScrollPane.setMaximumSize(new Dimension(800, 400));
            ordersScrollPane.setBorder(new EmptyBorder(40, 0, 40, 0));
            ordersScrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
            ordersScrollPane.setVisible(true);

            //petit texte pour indiquer les listes en cours
            commandLabel = new JLabel("Liste des commandes en cours : ");
            commandLabel.setFont(new Font("Arial", Font.BOLD, 30));
            commandLabel.setHorizontalAlignment(SwingConstants.CENTER);
            commandLabel.setBorder(new EmptyBorder(20, 0, 10, 0));
            this.add(commandLabel, BorderLayout.NORTH);
            // Ajoute + params au Panel
            this.add(ordersScrollPane, BorderLayout.CENTER);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    //utiliser cette méthode pour refresh dans l'affichage
    public void refreshOrders() {
        this.orders = orderController.getAllOrders();
        ordersList.setListData(orders.toArray(new Order[0]));
    }
    public JList<Order> getOrdersList() {
        return ordersList;
    }

    public OrderController getOrderController() {
        return orderController;
    }

    public void setOrderController(OrderController orderController) {
        this.orderController = orderController;
    }
}