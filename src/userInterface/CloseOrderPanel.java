package userInterface;

import model.Order;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CloseOrderPanel extends JPanel {
    private ListOrderPanel listOrderPanel;
    private JButton closeButton;

    public CloseOrderPanel() {
        this.setLayout(new BorderLayout());
        listOrderPanel = new ListOrderPanel();
        this.add(listOrderPanel, BorderLayout.CENTER);

        closeButton = new JButton("Clôturer la commande");
        closeButton.addActionListener(new closeButtonListener());
        this.add(closeButton, BorderLayout.SOUTH);
    }

    private class closeButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Order selected = listOrderPanel.getOrdersList().getSelectedValue();
            if (selected == null) {
                JOptionPane.showMessageDialog(null, "Aucune commande sélectionnée.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            listOrderPanel.getOrderController().updateStatus(selected.getId());

            //refraichir list via methode
            listOrderPanel.refreshOrders();

            JOptionPane.showMessageDialog(null, "Commande clôturée !", "Confirmation", JOptionPane.INFORMATION_MESSAGE );
        }
    }
}
