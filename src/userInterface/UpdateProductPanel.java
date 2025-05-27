package userInterface;

import model.Product;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class UpdateProductPanel extends JPanel {
    private ListingProductPanel listingProductPanel;
    private JButton updateButton;

    public UpdateProductPanel () {
        this.setLayout(new BorderLayout());
        this.listingProductPanel = new ListingProductPanel();

        // Déclaration + style
        updateButton = new JButton("Modifier le produit");
        updateButton.setFont(new Font("Arial", Font.BOLD, 20));
        updateButton.setBorder(new EmptyBorder(10, 15, 10, 15));
            // Listeners
        updateButton.addActionListener(new UpdateButtonListener());

        // Ajout
        this.add(listingProductPanel, BorderLayout.CENTER);
        this.add(updateButton, BorderLayout.SOUTH);
    }

    // LISTENERS
    private class UpdateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Product selectedProd = listingProductPanel.getProductJList().getSelectedValue();
            if (selectedProd != null) {
                removeAll();
                add(new EditProductPanel(selectedProd), BorderLayout.CENTER);

                revalidate();
                repaint();
            } else {
                JOptionPane.showMessageDialog(null, "Veuillez sélectionner un produit");
            }
        }
    }
}
