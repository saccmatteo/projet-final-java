package userInterface;

import model.Order;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class UpdateOrderPanel extends JPanel {
    private ListingOrderPanel listingOrderPanel;
    private JButton updateButton;

    public UpdateOrderPanel () {
        this.setLayout(new BorderLayout());
        this.listingOrderPanel = new ListingOrderPanel();

        // Déclaration + style
        updateButton = new JButton("Modifier la commande");
        updateButton.setFont(new Font("Arial", Font.BOLD, 20));
        updateButton.setBorder(new EmptyBorder(10, 15, 10, 15));
            // Listeners
        updateButton.addActionListener(new UpdateButtonListener());

        // Ajout
        this.add(listingOrderPanel, BorderLayout.CENTER);
        this.add(updateButton, BorderLayout.SOUTH);
    }

    // LISTENERS
    private class UpdateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Order selectedOrder = listingOrderPanel.getOrdersList().getSelectedValue();
            if (selectedOrder != null) {
                removeAll();
                add(new EditOrderPanel(selectedOrder), BorderLayout.CENTER);

                revalidate();
                repaint();
            }else{
                JOptionPane.showMessageDialog(null, "Veuillez sélectionner une commande");
            }
        }
    }
}
