package userInterface;

import controller.ApplicationController;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CommandPanel extends JPanel {
    private JLabel userLabel;
    private ApplicationController controller;

    //private ArrayList<User> users;
    //private JList usersList;
    private JComboBox<User> usersComboList;
    private JPanel allUsersPanel;

    // CONSTRUCTORS
    public CommandPanel() {
        this.setLayout(new BorderLayout());
        setController(new ApplicationController());

        try {
            // SI ON A BESOIN D'UNE JLIST
            //users = controller.getAllUsers();
            //DefaultListModel<User> listModel = new DefaultListModel<>();
            //for (User user : users) {
            //    listModel.addElement(user);
            //}
            //usersList = new JList<>(listModel); // toArray => transforme l'objet en array
            //usersList.setVisibleRowCount(1);
            //usersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            //JScrollPane scrollPane = new JScrollPane(usersList);
            //this.add(scrollPane, BorderLayout.CENTER);

            allUsersPanel = new JPanel(new FlowLayout());

            ArrayList<User> users = controller.getAllUsers();
            usersComboList = new JComboBox<>(users.toArray(new User[0]));
            usersComboList.setPreferredSize(new Dimension(200, 25));

            userLabel = new JLabel("Utilisateur : ");

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
}