package userInterface;

import model.Order;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateOrderPanel extends JPanel {
    private ListingOrderPanel listingOrderPanel;
    private JButton updateButton;

    public UpdateOrderPanel () {
        this.setLayout(new BorderLayout());
        this.listingOrderPanel = new ListingOrderPanel();
        this.add(listingOrderPanel, BorderLayout.CENTER);

        updateButton = new JButton("Modifier la commande");
        updateButton.setFont(new Font("Arial", Font.BOLD, 20));
        updateButton.setBorder(new EmptyBorder(10, 15, 10, 15));

        updateButton.addActionListener(new UpdateButtonListener());
        this.add(updateButton, BorderLayout.SOUTH);
    }
    private class UpdateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Order selected = listingOrderPanel.getOrdersList().getSelectedValue();
            if (selected == null) {
                JOptionPane.showMessageDialog(null, "Veuillez sélectionner une commande");
                return;
            }
            removeAll();
            EditOrderPanel editOrderPanel = new EditOrderPanel(selected);
            add(editOrderPanel, BorderLayout.CENTER);
            revalidate();
            repaint();
        }
    }
}
