package userInterface;

import controller.ApplicationController;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AllUsersPanel extends JPanel {
    private JLabel connectionLabel;
    private JComboBox connectionComboBox;
    private ApplicationController controller;
    private ArrayList<User> users;
    JList<User> usersList;

    // CONSTRUCTORS
    public AllUsersPanel() {
        this.setLayout(new BorderLayout());
        connectionLabel = new JLabel("Utilisateur : ", SwingConstants.CENTER);
        this.add(connectionLabel, BorderLayout.NORTH);
        setController(new ApplicationController());

        // Pas besoin du try ?????
        try {
            users = controller.getAllUsers();
            usersList = new JList(users.toArray()); // toArray => transforme l'objet en array

            JScrollPane scrollPane = new JScrollPane(usersList);
            usersList.setVisibleRowCount(2);
            usersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            this.add(scrollPane, BorderLayout.CENTER);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
        // CAS OU ON DOIT FAIRE UN COMBOBOX
//        users = controller.getAllUsers();
//        connectionComboBox = new JComboBox(users.toArray());
//        connectionComboBox.setMaximumRowCount(1);
//        this.add(connectionComboBox);
    }

    // SETTERS
    public void setController(ApplicationController controller) {
        this.controller = controller;
    }
}