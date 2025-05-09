package userInterface;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import beerThread.*;

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
        this.setBounds(0, 0, 1280, 720);
        addWindowListener (new WindowAdapter() {
            public void windowClosing (WindowEvent e) {
                System.exit(0);
            }
        } );
        this.setLayout(new BorderLayout());

        // Container
        container = this.getContentPane();
        container.setLayout(new BorderLayout());

        // Message d'accueil
        welcomeLabel = new JLabel("Bienvenue sur le terminal", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        welcomeLabel.setBorder(new EmptyBorder(200, 0, 100, 0));
        container.add(welcomeLabel, BorderLayout.NORTH);
        //Verre de choppe
        container.add(new BeerGlass());

        // Declaration variable du menu
        menuBar = new JMenuBar();
        welcomeMenu = new JMenuItem("Accueil");
        commandMenu = new JMenuItem("Commande");
        productMenu = new JMenuItem("Produit");
        dataBaseMenu = new JMenuItem("Base de donnees");

        // ItemMenu Listeners
        welcomeMenu.addActionListener(new WelcomeMenuListener());
        commandMenu.addActionListener(new CommandMenuListener());
        productMenu.addActionListener(new ProductMenuListener());

        // Ajout des variables a la barre
        menuBar.add(welcomeMenu);
        menuBar.add(new JSeparator(SwingConstants.VERTICAL)); // Ajoute des barres verticales ;)
        menuBar.add(commandMenu);
        menuBar.add(new JSeparator(SwingConstants.VERTICAL));
        menuBar.add(productMenu);
        menuBar.add(new JSeparator(SwingConstants.VERTICAL));
        menuBar.add(dataBaseMenu);
        this.setJMenuBar(menuBar);
        this.setVisible(true);
    }
    // Tout faire dans un seul listener ou faire un pour chaque comme ici ?
    // Implémentation listeners
    private class WelcomeMenuListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            container.removeAll();
            container.add(welcomeLabel, BorderLayout.NORTH);
            container.add(new BeerGlass());
            container.revalidate();
            container.repaint();
        }
    }

    private class CommandMenuListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            container.removeAll();
            CommandPanel commandPanel = new CommandPanel();
            container.add(commandPanel, BorderLayout.CENTER);
            container.revalidate();
            container.repaint();
        }
    }

    private class ProductMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            container.removeAll();
            ProductPanel productPanel = new ProductPanel();
            container.add(productPanel, BorderLayout.CENTER);
            container.revalidate();
            container.repaint();
        }
    }
}