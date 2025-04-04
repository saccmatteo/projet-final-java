package userInterface;

import controller.ApplicationController;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CommandPanel extends JPanel {
    private JLabel connectionLabel;
    private JComboBox connectionComboBox;
    private ApplicationController controller;
    private ArrayList<User> users;
    private JList usersList;
    private JPanel allUsersPanel;

    // CONSTRUCTORS
    public CommandPanel() {
        this.setLayout(new BorderLayout());
        connectionLabel = new JLabel("Utilisateur : ");
        //this.add(connectionLabel, BorderLayout.NORTH);
        setController(new ApplicationController());
        allUsersPanel = new JPanel(new GridLayout(1, 2));
        allUsersPanel.add(connectionLabel);

        // Pas besoin du try ?????
        try {
            users = controller.getAllUsers();
            DefaultListModel<User> listModel = new DefaultListModel<>();
            for (User user : users) {
                listModel.addElement(user);
            }
            usersList = new JList<>(listModel); // toArray => transforme l'objet en array
            usersList.setVisibleRowCount(1);
            usersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            JScrollPane scrollPane = new JScrollPane(usersList);
            //this.add(scrollPane, BorderLayout.CENTER);
            allUsersPanel.add(scrollPane);
            this.add(allUsersPanel, BorderLayout.NORTH);
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