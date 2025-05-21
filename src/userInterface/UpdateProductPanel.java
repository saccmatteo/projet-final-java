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

        updateButton = new JButton("Modifier le produit");
        updateButton.setFont(new Font("Arial", Font.BOLD, 20));
        updateButton.setBorder(new EmptyBorder(10, 15, 10, 15));

        updateButton.addActionListener(new UpdateButtonListener());

        this.add(listingProductPanel, BorderLayout.CENTER);
        this.add(updateButton, BorderLayout.SOUTH);
    }

    // LISTENERS
    private class UpdateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Product selected = listingProductPanel.getProductJList().getSelectedValue();
            if (selected != null) {
                removeAll();
                add(new EditProductPanel(selected), BorderLayout.CENTER);

                revalidate();
                repaint();
            }else{
                JOptionPane.showMessageDialog(null, "Veuillez sélectionner un produit");
            }
        }
    }
}
