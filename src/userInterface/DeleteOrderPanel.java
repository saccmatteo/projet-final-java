package userInterface;

import model.Order;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DeleteOrderPanel extends JPanel {
    private ListOrderPanel listOrderPanel;
    private JButton deleteButton;

    public DeleteOrderPanel() {
        this.setLayout(new BorderLayout());
        this.listOrderPanel = new ListOrderPanel();
        this.add(listOrderPanel, BorderLayout.CENTER);

        deleteButton = new JButton("Supprimer la commande");
        deleteButton.addActionListener(new deleteButtonListener());
        this.add(deleteButton, BorderLayout.SOUTH);
    }

    private class deleteButtonListener implements ActionListener {
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
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    new Object[]{"Oui", "Non"},
                    null
            );

            if (reponse == JOptionPane.YES_OPTION) {
                listOrderPanel.getOrderController().removeCommand(selected.getId());

                //refraichir list via methode
                listOrderPanel.refreshOrders();

                JOptionPane.showMessageDialog(null, "Commande supprimée !", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
