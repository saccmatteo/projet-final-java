package userInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {
    private JLabel welcomeMsg;
    private JMenuBar menuBar;
    private JMenuItem welcomeMenu, command, product, dataBase;
    private Container container;

    // CONSTRUCTOR
    public MainWindow() {
        super("Terminal");
        this.setBounds(0, 0, 1920, 1080);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        // Container
        container = this.getContentPane();
        container.setLayout(new BorderLayout());

        // Message d'accueil
        welcomeMsg = new JLabel("Welcome to the terminal", SwingConstants.CENTER);
        container.add(welcomeMsg, BorderLayout.CENTER);

        // Declaration variable du menu
        menuBar = new JMenuBar();
        welcomeMenu = new JMenuItem("Welcome");
        command = new JMenuItem("Command");
        product = new JMenuItem("Product");
        dataBase = new JMenuItem("Database");

        // ItemMenu Listeners
        welcomeMenu.addActionListener(new WelcomeMenuListener());

        // Ajout des variables a la barre
        menuBar.add(welcomeMenu);
        menuBar.add(command);
        menuBar.add(product);
        menuBar.add(dataBase);
        this.setJMenuBar(menuBar);

        this.setVisible(true);
    }

    private class WelcomeMenuListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            container.removeAll();
            container.add(welcomeMsg);

            container.revalidate();
            container.repaint();
        }
    }

    private class CommandMenuListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            container.removeAll();
            container.add(new CommandPanel());

            container.revalidate();
            container.repaint();
        }
    }
}