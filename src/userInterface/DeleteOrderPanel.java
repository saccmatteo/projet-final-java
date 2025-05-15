package userInterface;

import model.Order;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteOrderPanel extends JPanel {
    private ListingOrderPanel listOrderPanel;
    private JButton deleteButton;

    public DeleteOrderPanel() {
        this.setLayout(new BorderLayout());
        this.listOrderPanel = new ListingOrderPanel();
        this.add(listOrderPanel, BorderLayout.CENTER);

        deleteButton = new JButton("Supprimer la commande");
        deleteButton.setFont(new Font("Arial", Font.BOLD, 20));
        deleteButton.setBorder(new EmptyBorder(10, 15, 10, 15));

        deleteButton.addActionListener(new DeleteButtonListener());
        this.add(deleteButton, BorderLayout.SOUTH);
    }

    private class DeleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Order selected = listOrderPanel.getOrdersList().getSelectedValue();

            if (selected == null) {
                JOptionPane.showMessageDialog(null, "Aucune commande sélectionnée.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int reponse = JOptionPane.showOptionDialog(
                    null,
                    "Voulez-vous supprimer cette commande ?",
                    "Confirmation", JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null,
                    new Object[]{"Oui", "Non"},
                    null
            );

            if (reponse == JOptionPane.YES_OPTION) {
                listOrderPanel.getOrderController().deleteCommand(selected.getId());
                listOrderPanel.refreshOrders();

                JOptionPane.showMessageDialog(null, "Commande supprimée avec succès", "Suppression confirmée", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
