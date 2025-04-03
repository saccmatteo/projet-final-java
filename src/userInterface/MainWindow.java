package userInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {
    private JLabel welcomeLabel;
    private JMenuBar menuBar;
    private JMenuItem welcomeMenu, commandMenu, productMenu, dataBaseMenu;
    private Container container;

    // CONSTRUCTOR
    public MainWindow() {
        super("Terminal");
        // Ouvrir en plein écran
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setBounds(0, 0, 1920, 1080);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        // Container
        container = this.getContentPane();
        container.setLayout(new BorderLayout());

        // Message d'accueil
        welcomeLabel = new JLabel("Bienvenue sur le terminal", SwingConstants.CENTER);
        container.add(welcomeLabel, BorderLayout.CENTER);

        // Declaration variable du menu
        menuBar = new JMenuBar();
        welcomeMenu = new JMenuItem("Accueil");
        commandMenu = new JMenuItem("Commande");
        productMenu = new JMenuItem("Produit");
        dataBaseMenu = new JMenuItem("DB");

        // ItemMenu Listeners
        welcomeMenu.addActionListener(new WelcomeMenuListener());
        commandMenu.addActionListener(new CommandMenuListener());

        // Ajout des variables a la barre
        menuBar.add(welcomeMenu);
        menuBar.add(commandMenu);
        menuBar.add(productMenu);
        menuBar.add(dataBaseMenu);
        this.setJMenuBar(menuBar);
        this.setVisible(true);
    }
    // Tout faire dans un seul listener ou faire un pour chaque comme ici ?
    // Implémentation listeners
    private class WelcomeMenuListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            container.removeAll();
            container.add(welcomeLabel);
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