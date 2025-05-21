package userInterface;

import model.Order;
import model.Product;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteProductPanel extends JPanel {
    private ListingProductPanel listingProductPanel;
    private JButton deleteButton;

    public DeleteProductPanel() {
        this.setLayout(new BorderLayout());
        this.listingProductPanel = new ListingProductPanel();
        this.add(listingProductPanel, BorderLayout.CENTER);

        deleteButton = new JButton("Supprimer le produit");
        deleteButton.setFont(new Font("Arial", Font.BOLD, 20));
        deleteButton.setBorder(new EmptyBorder(10, 15, 10, 15));

        deleteButton.addActionListener(new DeleteButtonListener());
        this.add(deleteButton, BorderLayout.SOUTH);
    }
    private class DeleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Product selected = listingProductPanel.getProductJList().getSelectedValue();

            if (selected != null) {
                int reponse = JOptionPane.showOptionDialog(
                        null,
                        "Voulez-vous supprimer ce produit ?",
                        "Confirmation", JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.WARNING_MESSAGE,
                        null,
                        new Object[]{"Oui", "Non"},
                        null
                );
                if (reponse == JOptionPane.YES_OPTION) {
                    listingProductPanel.getProductController().deleteProduct(selected.getId());
                    listingProductPanel.refreshAndFilter();

                    JOptionPane.showMessageDialog(null, "Produit supprimée avec succès", "Suppression confirmée", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "Aucun produit sélectionnée.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
