package userInterface;

import model.Order;
import model.PaymentMethod;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CloseOrderPanel extends JPanel {
    private ListingOrderPanel listOrderPanel;
    private JButton closeButton;

    public CloseOrderPanel() {
        this.setLayout(new BorderLayout());
        listOrderPanel = new ListingOrderPanel();
        this.add(listOrderPanel, BorderLayout.CENTER);

        closeButton = new JButton("Clôturer la commande");
        closeButton.setFont(new Font("Arial", Font.BOLD, 20));
        closeButton.setBorder(new EmptyBorder(10, 15, 10, 15));

        closeButton.addActionListener(new closeButtonListener());
        this.add(closeButton, BorderLayout.SOUTH);
    }

    private class closeButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Order selected = listOrderPanel.getOrdersList().getSelectedValue();

            if (selected == null) {
                JOptionPane.showMessageDialog(
                        null,
                        "Aucune commande sélectionnée.",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            PaymentMethod[] options = { PaymentMethod.CARD, PaymentMethod.CASH };
            int reponse = JOptionPane.showOptionDialog(
                    null,
                    "Méthode de paiement ?",
                    "Confirmation",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    null
            );

            if (reponse == JOptionPane.CLOSED_OPTION) {
                return;
            }

            PaymentMethod chosenMethod = (reponse == JOptionPane.YES_OPTION) ? PaymentMethod.CARD : PaymentMethod.CASH;
            listOrderPanel.getOrderController().updateClosedOrder(selected.getId(), chosenMethod);
            listOrderPanel.refreshOrders(); //rafraichir list via methode

            JOptionPane.showMessageDialog(null, "Commande clôturée avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE );
        }
    }
}
