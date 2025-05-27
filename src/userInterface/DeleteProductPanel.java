package userInterface;

import exceptions.DAOException;
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

        // Déclaration + style
        deleteButton = new JButton("Supprimer le produit");
        deleteButton.setFont(new Font("Arial", Font.BOLD, 20));
        deleteButton.setBorder(new EmptyBorder(10, 15, 10, 15));
            // Listeners
        deleteButton.addActionListener(new DeleteButtonListener());

        // Ajout
        this.add(listingProductPanel, BorderLayout.CENTER);
        this.add(deleteButton, BorderLayout.SOUTH);
    }

    // LISTENERS
    private class DeleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Product selectedProd = listingProductPanel.getProductJList().getSelectedValue();
            if (selectedProd != null) {
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
                    try {
                        listingProductPanel.getProductController().deleteProduct(selectedProd.getId());
                        listingProductPanel.refreshAndFilter();
                        JOptionPane.showMessageDialog(null, "Produit supprimée avec succès", "Suppression confirmée", JOptionPane.INFORMATION_MESSAGE);
                    } catch (DAOException daoException) {
                        JOptionPane.showMessageDialog(null, "Erreur lors de la suppression du produit");
                    }
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "Aucun produit sélectionnée.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
