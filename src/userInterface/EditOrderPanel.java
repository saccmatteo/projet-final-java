package userInterface;

import model.Order;
import controller.OrderController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;

public class EditOrderPanel extends JPanel {
    private Order order;
    private OrderController orderController;

    private JTextField discountField, commentField;
    private JCheckBox happyHourCheckBox;
    private JButton cancelButton, saveButton;

    public EditOrderPanel(Order order) {
        this.order = order;
        this.orderController = new OrderController();

        setLayout(new BorderLayout());

        initFormComponents();
        loadFields();

        JPanel buttonsPanel = new JPanel(new FlowLayout());
        cancelButton = new JButton("Annuler");
        saveButton = new JButton("Enregistrer");

        cancelButton.addActionListener(new CancelButtonListener());
        saveButton.addActionListener(new SaveButtonListener());

        buttonsPanel.add(cancelButton);
        buttonsPanel.add(saveButton);

        add(createFormPanel(), BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    //initialiser les composants du formulaire
    private void initFormComponents() {
        discountField = new JTextField();
        commentField = new JTextField();
        happyHourCheckBox = new JCheckBox("Happy Hour");
        happyHourCheckBox.addItemListener(new HappyHourListener());
    }

    //créé le formulaire
    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));

        formPanel.add(new JLabel("Date de commande :"));
        formPanel.add(new JLabel(order.getDate().toString()));

        formPanel.add(new JLabel("Date de paiement :"));
        formPanel.add(new JLabel(order.getPaymentDate() != null ? order.getPaymentDate().toString() : "Non payée"));

        formPanel.add(new JLabel("Réduction (%) :"));
        formPanel.add(discountField);

        formPanel.add(new JLabel("Commentaire :"));
        formPanel.add(commentField);

        formPanel.add(new JLabel("Happy Hour :"));
        formPanel.add(happyHourCheckBox);

        return formPanel;
    }

    private class HappyHourListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                discountField.setText("50");
                discountField.setEnabled(false);
            } else {
                discountField.setText("");
                discountField.setEnabled(true);
            }
        }
    }

    //va remplir les textFields du formulaire
    private void loadFields() {
        discountField.setText(String.valueOf(order.getDiscountPercentage()));
        commentField.setText(order.getComment());
        happyHourCheckBox.setSelected(order.getHappyHour());
    }

    private class CancelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            removeAll();
            UpdateOrderPanel updateOrderPanel = new UpdateOrderPanel();
            add(updateOrderPanel, BorderLayout.CENTER);
            revalidate();
            repaint();
        }
    }

    private class SaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Integer newDiscount = Integer.parseInt(discountField.getText());
                String newComment = commentField.getText();
                Boolean newHappyHour = happyHourCheckBox.isSelected();

                Order updatedOrder = new Order(
                        order.getId(),
                        order.getDate(),
                        order.getPaymentDate(),
                        newDiscount,
                        newComment,
                        newHappyHour,
                        order.getStatusLabel(),
                        order.getPaymentMethodLabel(),
                        order.getUser()
                );

                orderController.updateOrder(updatedOrder);
                JOptionPane.showMessageDialog(EditOrderPanel.this, "Commande mise à jour !");
                removeAll();
                add(new UpdateOrderPanel());
                revalidate();
                repaint();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(EditOrderPanel.this, "Erreur : le pourcentage de réduction doit être un nombre.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(EditOrderPanel.this, "Erreur inattendue : " + ex.getMessage());
            }
        }
    }
}
