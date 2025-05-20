package userInterface;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controller.OrderLineController;
import model.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModificationOrderPanel extends JPanel {
    private Order selectedOrder;

    private JPanel selectedOrderPanel;
    private ListingOrderPanel listOrderPanel;
    private JTable selectedOrderTable;
    private JButton modifyButton;

    private OrderLineController orderLineController;

    public ModificationOrderPanel() {
        // Initialisation des variables
        setLayout(new BorderLayout());
        listOrderPanel = new ListingOrderPanel();
        modifyButton = new JButton("Modifier la commande");

        // Affichage du bouton
        modifyButton.setFont(new Font("Arial", Font.BOLD, 20));
        modifyButton.setBorder(new EmptyBorder(10, 15, 10, 15));
            // Listeners
        modifyButton.addActionListener(new ModifyButtonListener());

        // Ajout au panel
        this.add(listOrderPanel, BorderLayout.CENTER);
        this.add(modifyButton, BorderLayout.SOUTH);
    }

    // METHODES
    public void createSelectedOrderPanel() {
        // Definir un model de tableau pour l'affichage
        DefaultTableModel tableModel = new DefaultTableModel(new String[]
                {"id", "order_date", "payement_date", "discount_percentage", "comment",
                        "is_happy_hour", "status_label", "user_id", "payement_method_label"}, 0);
        // Ajout de la ligne au tableau
        tableModel.addRow(new Object[]{selectedOrder.getId(), selectedOrder.getDate(),
                selectedOrder.getPaymentDate(), selectedOrder.getDiscountPercentage(),
                selectedOrder.getComment(), selectedOrder.getHappyHour(),
                selectedOrder.getStatusLabel(), selectedOrder.getUserId(), selectedOrder.getPaymentMethodLabel()});

        selectedOrderPanel = new JPanel();
        selectedOrderPanel.setLayout(new BorderLayout());
        selectedOrderTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(selectedOrderTable);


        this.add(scrollPane, BorderLayout.NORTH);
    }

    // LISTENERS
    private class ModifyButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            selectedOrder = listOrderPanel.getOrdersList().getSelectedValue();

            if (selectedOrder != null) {
                removeAll();
                createSelectedOrderPanel();
                revalidate();
                repaint();
            }else{
                JOptionPane.showMessageDialog(null, "Aucune commande sélectionnée.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
