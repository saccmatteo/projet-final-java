package userInterface;

import controller.OrderController;
import model.Order;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class CommandViewerPanel extends JPanel {
    private OrderController orderController;
    private JLabel commandLabel;
    private ArrayList<Order> orders;
    private JList<Order> ordersList;
    private JScrollPane ordersScrollPane;

    // CONSTRUCTORS
    public CommandViewerPanel() {
        this.setLayout(new BorderLayout());

        try {
            // Orders list
            setOrderController(new OrderController());
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
                    label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
                    return label;
                }
            });

            // ScrollPane
            ordersScrollPane = new JScrollPane(ordersList);
                // Affichage
            ordersScrollPane.setMaximumSize(new Dimension(800, 400));
            ordersScrollPane.setBorder(new EmptyBorder(40, 0, 40, 0));
            ordersScrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
            ordersScrollPane.setVisible(true);

            commandLabel = new JLabel("Liste des commandes en cours : ");
            commandLabel.setHorizontalAlignment(SwingConstants.CENTER);
            this.add(commandLabel, BorderLayout.NORTH);
            // Ajoute + params au Panel
            this.add(ordersScrollPane, BorderLayout.CENTER);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
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