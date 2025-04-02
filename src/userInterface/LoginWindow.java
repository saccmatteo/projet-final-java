package userInterface;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginWindow extends JFrame {
    private Container container;
    private JPanel infosPanel, buttonsPanel;
    private JTextField firstnameText, lastnameText, userIdText;
    private JPasswordField passwordText;
    private JLabel firstname, lastname, userId, password;
    private JButton exitButton, loginButton, resetButton;

    public LoginWindow () {
        super("Login");
        this.setBounds(660, 190, 600, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        //container
        container = getContentPane();
        container.setLayout(new BorderLayout());

        //infosPanel
        infosPanel = new JPanel(new GridLayout(4,2, 10,10));
        infosPanel.setBorder(new EmptyBorder(40,40,40,40));

        //firstname
        firstname = new JLabel("Prénom : ");
        firstnameText = new JTextField(15);
        infosPanel.add(firstname);
        infosPanel.add(firstnameText);

        //lastname
        lastname = new JLabel("Nom : ");
        lastnameText = new JTextField(15);
        infosPanel.add(lastname);
        infosPanel.add(lastnameText);

        //userId
        userId = new JLabel("ID utilisateur : ");
        userIdText = new JTextField(15);
        infosPanel.add(userId);
        infosPanel.add(userIdText);

        //password
        password = new JLabel("Mot de passe : ");
        passwordText = new JPasswordField(15);
        infosPanel.add(password);
        infosPanel.add(passwordText);

        //buttonsPanel
        buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        //buttons
        exitButton = new JButton("Quitter");
        exitButton.addActionListener(new ExitListener());
        loginButton = new JButton("Connexion");
        loginButton.addActionListener(new LoginListener());
        resetButton = new JButton("Réinitialiser");
        resetButton.addActionListener(new ResetListener());
        buttonsPanel.add(exitButton);
        buttonsPanel.add(loginButton);
        buttonsPanel.add(resetButton);

        //add labels to container
        container.add(infosPanel, BorderLayout.CENTER);
        container.add(buttonsPanel, BorderLayout.SOUTH);
        this.setVisible(true);
    }
    private class ExitListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
    //ICI IL FAUDRA RAJOUTER LE TRUC POUR VERIFIER LES INFOS PRCQ LA CA OUVRE LA MAINWINDOW SANS VERIF + PAS DE JOPTIONPANE.
    private class LoginListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            MainWindow mainWindow = new MainWindow();
        }
    }
    private class ResetListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            firstnameText.setText("");
            lastnameText.setText("");
            userIdText.setText("");
            passwordText.setText("");
        }
    }
}
