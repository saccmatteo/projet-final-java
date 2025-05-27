package userInterface;

import model.Order;
import controller.OrderController;
import model.PaymentMethod;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;

public class EditOrderPanel extends JPanel {
    private JTextField discountField, commentField;
    private JCheckBox happyHourCheckBox;
    private JButton cancelButton, saveButton;

    private Order order;
    private OrderController orderController;

    public EditOrderPanel(Order order) {
        setLayout(new BorderLayout());
        setOrderController(new OrderController());
        this.order = order;

        // Création du panel global
        initFormComponents();
        loadFields();

        // Panel des boutons
        JPanel buttonsPanel = new JPanel(new FlowLayout());
        cancelButton = new JButton("Annuler");
        saveButton = new JButton("Enregistrer");
            // Listeners
        cancelButton.addActionListener(new CancelButtonListener());
        saveButton.addActionListener(new SaveButtonListener());
        // Ajout au ButtonsPanel
        buttonsPanel.add(cancelButton);
        buttonsPanel.add(saveButton);

        // Ajout au EditProductPanel
        add(createFormPanel(), BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    // SETTERS
    public void setOrderController(OrderController orderController) {
        this.orderController = orderController;
    }

    // METHODES
        // Déclaration des composants du formulaire
    private void initFormComponents() {
        discountField = new JTextField();
        commentField = new JTextField();

        happyHourCheckBox = new JCheckBox("Happy Hour");
        happyHourCheckBox.addItemListener(new HappyHourListener());
    }
        // Création du formulaire
    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));

        formPanel.add(new JLabel("Date de commande :"));
        formPanel.add(new JLabel(order.getDate().toString()));

        formPanel.add(new JLabel("Date de paiement :"));
        formPanel.add(new JLabel(order.getPaymentDate() != null ? order.getPaymentDate().toString() : PaymentMethod.NOTPAID.getLabel()));

        formPanel.add(new JLabel("Réduction (%) :"));
        formPanel.add(discountField);

        formPanel.add(new JLabel("Commentaire :"));
        formPanel.add(commentField);

        formPanel.add(new JLabel("Happy Hour :"));
        formPanel.add(happyHourCheckBox);

        return formPanel;
    }
        // Remplir les textFields du formulaire
    private void loadFields() {
        if (order.getDiscountPercentage() == null) {
            discountField.setText("");
        } else {
            discountField.setText(String.valueOf(order.getDiscountPercentage()));
        }
        commentField.setText(order.getComment());
        happyHourCheckBox.setSelected(order.getHappyHour());
    }

    // LISTENERS
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
    private class CancelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            removeAll();
            add(new UpdateOrderPanel(), BorderLayout.CENTER);

            revalidate();
            repaint();
        }
    }
    private class SaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String discountText = discountField.getText().trim();
                Integer newDiscount = null;

                if (!discountText.isEmpty()) {
                    int val = Integer.parseInt(discountText);
                    if (val < 0 || val > 100) {
                        JOptionPane.showMessageDialog(EditOrderPanel.this, "Erreur : la remise doit être entre 0 et 100.");
                        return;
                    }
                    newDiscount = val;
                }

                String newComment;
                Boolean newHappyHour = happyHourCheckBox.isSelected();

                if (commentField.getText().trim().isEmpty()) {
                    newComment = null;
                } else {
                    newComment = commentField.getText();
                }

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
                JOptionPane.showMessageDialog(EditOrderPanel.this, "Erreur : le pourcentage de réduction doit être un nombre valide.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(EditOrderPanel.this, "Erreur inattendue : " + ex.getMessage());
            }
        }
    }
}
