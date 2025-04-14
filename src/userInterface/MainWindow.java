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
    private JMenu welcomeMenu, commandMenu, productMenu, dataBaseMenu;
    private JMenuItem welcomeMenuItem, deleteCommandMenuItem, addCommandMenuItem, viewCommandMenuItem, updateCommandMenuItem, closeCommandMenuItem, deleteProductMenuItem, addProductMenuItem, viewProductMenuItem, updateProductMenuItem;
    private Container container;

    // CONSTRUCTOR
    public MainWindow() {
        super("Terminal");
        // Ouvrir en plein écran
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setBounds(0, 0, 1920, 1080);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
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
        welcomeMenu = new JMenu("Buvette");
        commandMenu = new JMenu("Commande");
        productMenu = new JMenu("Produit");
        dataBaseMenu = new JMenu("Base de donnees");

        // ItemMenu accueil
        welcomeMenuItem = new JMenuItem("Accueil");
        welcomeMenuItem.addActionListener(new WelcomeMenuItemListener());
        welcomeMenu.add(welcomeMenuItem);

        // itemMenu commande
        deleteCommandMenuItem = new JMenuItem("Supprimer");
        deleteCommandMenuItem.addActionListener(new deleteCommandListener());
        addCommandMenuItem = new JMenuItem("Ajouter");
        viewCommandMenuItem = new JMenuItem("En cours");
        viewCommandMenuItem.addActionListener(new viewCommandListener());
        updateCommandMenuItem = new JMenuItem("Modifier");
        closeCommandMenuItem = new JMenuItem("Clôturer");
        closeCommandMenuItem.addActionListener(new closeCommandListener());
        commandMenu.add(viewCommandMenuItem);
        commandMenu.add(addCommandMenuItem);
        commandMenu.add(updateCommandMenuItem);
        commandMenu.add(closeCommandMenuItem);
        commandMenu.add(deleteCommandMenuItem);

        // itemMenu produit
        deleteProductMenuItem = new JMenuItem("Supprimer");
        addProductMenuItem = new JMenuItem("Ajouter");
        addProductMenuItem.addActionListener(new addProductListener());
        viewProductMenuItem = new JMenuItem("Nos produits");
        updateProductMenuItem = new JMenuItem("Modifier");
        productMenu.add(viewProductMenuItem);
        productMenu.add(addProductMenuItem);
        productMenu.add(updateProductMenuItem);
        productMenu.add(deleteProductMenuItem);

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
    private class WelcomeMenuItemListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            container.removeAll();
            container.add(welcomeLabel, BorderLayout.NORTH);
            container.add(new BeerGlass());

            container.revalidate();
            container.repaint();
        }
    }
    private class addProductListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new FormAddProduct();
        }
    }
    private class viewCommandListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            container.removeAll();
            container.add(new OrderViewerPanel(), BorderLayout.CENTER);
            container.revalidate();
            container.repaint();
        }
    }
    private class deleteCommandListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            container.removeAll();
            container.add(new OrderDeleterPanel(), BorderLayout.CENTER);
            container.revalidate();
            container.repaint();
        }
    }

    private class closeCommandListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            container.removeAll();
            container.add(new OrderCloserPanel(), BorderLayout.CENTER);
            container.revalidate();
            container.repaint();
        }
    }
}