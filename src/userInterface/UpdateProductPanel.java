package userInterface;

import model.Order;
import model.Product;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateProductPanel extends JPanel {
    private ListingProductPanel listingProductPanel;
    private JButton updateButton;

    public UpdateProductPanel () {
        this.setLayout(new BorderLayout());
        this.listingProductPanel = new ListingProductPanel();
        this.add(listingProductPanel, BorderLayout.CENTER);

        updateButton = new JButton("Modifier le produit");
        updateButton.setFont(new Font("Arial", Font.BOLD, 20));
        updateButton.setBorder(new EmptyBorder(10, 15, 10, 15));

        updateButton.addActionListener(new UpdateButtonListener());
        this.add(updateButton, BorderLayout.SOUTH);
    }
    private class UpdateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Product selected = listingProductPanel.getProductJList().getSelectedValue();
            if (selected == null) {
                JOptionPane.showMessageDialog(null, "Veuillez sélectionner un produit");
                return;
            }
            removeAll();
            EditProductPanel editProductPanel = new EditProductPanel(selected);
            add(editProductPanel, BorderLayout.CENTER);
            revalidate();
            repaint();
        }
    }
}
