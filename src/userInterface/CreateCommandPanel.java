package userInterface;

import javax.swing.*;

import controller.UserController;
import model.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

public class CreateCommandPanel extends JPanel {
    private JPanel usersPanel, formPanel;
    private JTextField commentsText;
    private JLabel usersLabel, commentsLabel;
    private JButton resetForm;
    private JRadioButton discountRadio, happyHourRadio;
    private ButtonGroup group;
    private boolean isHappyHour;
    private String[] discountOptions = {"10%", "20%", "50%", "100%"};
    private JComboBox<String> discountComboBox;
    private JComboBox<User> users;
    private UserController userController;

    public CreateCommandPanel() {
        this.setLayout(new BorderLayout());

        userController = new UserController();

        //gestion du panel avec la combobox
        usersPanel = new JPanel(new FlowLayout());

        usersLabel = new JLabel("Utilisateur gérant la commande : ");
        usersLabel.setFont(new Font("Arial", Font.BOLD, 24));

        users = new JComboBox<>(userController.getAllUsers().toArray(new User[0]));
        users.setSelectedIndex(-1);
        users.setPreferredSize(new Dimension(500, 50));

        usersPanel.add(usersLabel);
        usersPanel.add(users);

        //gestion panel avec le formulaire
        formPanel = new JPanel(new GridLayout(4,2));
        resetForm = new JButton("Réinitialiser le formulaire");
        discountRadio = new JRadioButton("Réduction");
        happyHourRadio = new JRadioButton("Happy hour");

        //groupe des radios
        group = new ButtonGroup();
        group.add(discountRadio);
        group.add(happyHourRadio);

        //comboBox
        discountComboBox = new JComboBox<>();
        discountComboBox.setSelectedIndex(-1);
        discountComboBox.setEnabled(false);

        //ajout des listeners
        discountRadio.addActionListener(new DiscountRadioListener());
        happyHourRadio.addActionListener(new HappyHourRadioListener());
        resetForm.addActionListener(new ResetFormListener());

        commentsLabel = new JLabel("Commentaires : ");
        commentsText = new JTextField();

        formPanel.add(discountRadio);
        formPanel.add(happyHourRadio);
        formPanel.add(discountComboBox);
        formPanel.add(resetForm);
        formPanel.add(commentsLabel);
        formPanel.add(commentsText);

        //ajout des 2 panels
        this.add(usersPanel, BorderLayout.NORTH);
        this.add(formPanel, BorderLayout.CENTER);
    }

    private class DiscountRadioListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            discountComboBox.setEnabled(true);
            discountComboBox.removeAllItems();
            for (String option : discountOptions) {
                discountComboBox.addItem(option);
            }
        }
    }
    private class HappyHourRadioListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            discountComboBox.setEnabled(true);
            discountComboBox.removeAllItems();
            discountComboBox.addItem("50%");
        }
    }
    private class ResetFormListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            users.setSelectedIndex(-1);
            group.clearSelection();
            discountComboBox.removeAllItems();
            discountComboBox.setEnabled(false);
        }
    }
}
