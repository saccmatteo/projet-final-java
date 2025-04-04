package userInterface;

import controller.ApplicationController;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AllUsersPanel extends JPanel {
    private JLabel connectionLabel;
    private ApplicationController controller;
    private ArrayList<User> users;
    JList<User> usersList;

    // CONSTRUCTORS
    public AllUsersPanel() {
        this.setLayout(new BorderLayout());
        connectionLabel = new JLabel("Utilisateur : ", SwingConstants.CENTER);
        this.add(connectionLabel, BorderLayout.NORTH);
        setController(new ApplicationController());

        try {
            users = controller.getAllUsers();
            for (User user : users) {
                JLabel userInfos = new JLabel(user.getId() + " (" + user.getFirstName() + " " + user.getLastName() + ")");
                usersList.add(userInfos);
            }
            usersList.setSelectedIndex(0);
            this.add(usersList, BorderLayout.CENTER);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        } // Créer une classe AllUsersException
    }

    // METHODES
    public void setController(ApplicationController controller) {
        this.controller = controller;
    }
}