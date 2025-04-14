package userInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CommandDeleterPanel extends JPanel{
    private CommandViewerPanel commandViewerPanel;
    private JButton deleteButton;

    public CommandDeleterPanel() {
        this.setLayout(new BorderLayout());
        this.commandViewerPanel = new CommandViewerPanel();
        this.add(commandViewerPanel, BorderLayout.CENTER);
        deleteButton = new JButton("Supprimer la commande");
        deleteButton.addActionListener();
        this.add(deleteButton, BorderLayout.SOUTH);
    }

    private class DeleterButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int reponse = JOptionPane.showOptionDialog(null, "Voulez-vous supprimer cette commande ?", "Confirmation", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Oui", "Non"}, null);
            if (reponse == JOptionPane.YES_OPTION) {
                int selectedOrderId = commandViewerPanel.getOrdersList().getSelectedValue().getId();
                commandViewerPanel.getOrderController().removeCommand(selectedOrderId);
                commandViewerPanel.getOrders() = commandViewerPanel.getOrderController().getAllOrders();
                ordersList.setListData(orders.toArray(new Order[0]));
                JOptionPane.showMessageDialog(null, "Commande supprimée !", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
