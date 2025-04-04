package userInterface;

import DataAccess.*;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class ConnectionPanel extends JPanel {
    private String sqlInstruction;
    private User user;
    private PreparedStatement preparedStatement;
    private ArrayList<String> users = new ArrayList<>();
    private JLabel connectionLabel;
    private JList userList;

    // CONSTRUCTORS
    public ConnectionPanel() {
        this.setLayout(new BorderLayout());

        connectionLabel = new JLabel("Utilisateur : ");

    }

    // METHODES

}