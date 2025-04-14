package userInterface;

import model.Order;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class OrderCloserPanel extends JPanel {
    private OrderViewerPanel commandViewerPanel;
    private JButton closeButton;

    public OrderCloserPanel() {
        this.setLayout(new BorderLayout());
        commandViewerPanel = new OrderViewerPanel();
        this.add(commandViewerPanel, BorderLayout.CENTER); // ou NORTH, voir avec tout le monde
        closeButton = new JButton("Clôturer la commande");
        closeButton.addActionListener(new closeButtonListener());
        this.add(closeButton, BorderLayout.SOUTH);
    }

    private class closeButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            commandViewerPanel.getOrderController().updateStatus(commandViewerPanel.getOrdersList().getSelectedValue().getId());
            ArrayList<Order> orders = commandViewerPanel.getOrderController().getAllOrders();
            commandViewerPanel.getOrdersList().setListData(orders.toArray(new Order[0]));
            JOptionPane.showMessageDialog(null, "Commande clôturée !", "Confirmation", JOptionPane.INFORMATION_MESSAGE );
        }
    }
}
