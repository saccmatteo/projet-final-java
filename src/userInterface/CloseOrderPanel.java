package userInterface;

import model.Order;
import model.PaymentMethod;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class CloseOrderPanel extends JPanel {
    private ListingOrderPanel listOrderPanel;
    private JButton closeButton;

    public CloseOrderPanel() {
        this.setLayout(new BorderLayout());
        listOrderPanel = new ListingOrderPanel();

        // Déclaration
        closeButton = new JButton("Clôturer la commande");
        closeButton.setFont(new Font("Arial", Font.BOLD, 20));
        closeButton.setBorder(new EmptyBorder(10, 15, 10, 15));
            // Listeners
        closeButton.addActionListener(new CloseButtonListener());

        // Ajout au panel
        this.add(listOrderPanel, BorderLayout.CENTER);
        this.add(closeButton, BorderLayout.SOUTH);
    }

    // LISTENERS
    private class CloseButtonListener implements ActionListener {
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
            }else {

                PaymentMethod[] options = {PaymentMethod.CARD, PaymentMethod.CASH};
                int response = JOptionPane.showOptionDialog(
                        null,
                        "Méthode de paiement ?",
                        "Confirmation",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        null
                );

                if (response != JOptionPane.CLOSED_OPTION) {
                    PaymentMethod chosenMethod = (response == JOptionPane.YES_OPTION) ? PaymentMethod.CARD : PaymentMethod.CASH;
                    listOrderPanel.getOrderController().updateClosedOrder(LocalDate.now(), selected.getId(), chosenMethod);
                    listOrderPanel.refreshOrders();

                    JOptionPane.showMessageDialog(null, "Commande clôturée avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
                }

            }
        }
    }
}
