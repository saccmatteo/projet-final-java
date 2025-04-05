package userInterface;

import controller.ApplicationController;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public class CommandPanel extends JPanel {
    private ApplicationController controller;

    private ArrayList<User> users;
    private JPanel allUsersPanel;
    private JLabel userLabel;
    private JComboBox<User> usersComboList;

    // CONSTRUCTORS
    public CommandPanel() {
        this.setLayout(new BorderLayout());
        setController(new ApplicationController());

        try {
            userLabel = new JLabel("Utilisateur : ");
            allUsersPanel = new JPanel(new FlowLayout());
            users = controller.getAllUsers();

            usersComboList = new JComboBox<>(users.toArray(new User[0]));
            usersComboList.setPreferredSize(new Dimension(200, 25));
                //ComboBox Listener
            ComboBoxListener comboListener = new ComboBoxListener();
            usersComboList.addItemListener(comboListener);

            allUsersPanel.add(userLabel);
            allUsersPanel.add(usersComboList);
            this.add(allUsersPanel, BorderLayout.NORTH);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
    // SETTERS
    public void setController(ApplicationController controller) {
        this.controller = controller;
    }

    // SOUS-CLASSE LISTENERS
    private class ComboBoxListener implements ItemListener {
        public void itemStateChanged(ItemEvent e) {

        }
    }
}