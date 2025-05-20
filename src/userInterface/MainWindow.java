package userInterface;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import beerThread.*;
import controller.ConnectionController;

public class MainWindow extends JFrame {
    private JLabel welcomeLabel;
    private JMenuBar menuBar;
    private JMenu welcomeMenu, commandMenu, productMenu, dataBaseMenu;
    private JMenuItem welcomeMenuItem, deleteCommandMenuItem, addCommandMenuItem, viewCommandMenuItem, updateCommandMenuItem, closeCommandMenuItem, deleteProductMenuItem, addProductMenuItem, viewProductMenuItem, updateProductMenuItem, researchMenuItem;
    private Container container;
    private ConnectionController connectionController;

    // CONSTRUCTOR
    public MainWindow() {
        super("Terminal");
        // Ouvrir en plein écran
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setBounds(0, 0, 1920, 1080);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                connectionController.closeConnection();
                System.exit(0);
            }
        });
        this.setLayout(new BorderLayout());

        // Controller
        connectionController = new ConnectionController();

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
        commandMenu = new JMenu("Commandes");
        productMenu = new JMenu("Produits");
        dataBaseMenu = new JMenu("Base de donnees");

        // ItemMenu accueil
        welcomeMenuItem = new JMenuItem("Accueil");
        welcomeMenuItem.addActionListener(new WelcomeMenuItemListener());
        welcomeMenu.add(welcomeMenuItem);

        // itemMenu commande
        deleteCommandMenuItem = new JMenuItem("Supprimer");
        deleteCommandMenuItem.addActionListener(new DeleteOrderListener());
        addCommandMenuItem = new JMenuItem("Ajouter");
        addCommandMenuItem.addActionListener(new CreateOrderListener());
        viewCommandMenuItem = new JMenuItem("Voir les commandes");
        viewCommandMenuItem.addActionListener(new ListingOrderListener());
        updateCommandMenuItem = new JMenuItem("Modifier");
        updateCommandMenuItem.addActionListener(new UpdateOrderListener());
        closeCommandMenuItem = new JMenuItem("Finaliser une commande");
        closeCommandMenuItem.addActionListener(new CloseOrderListener());
        commandMenu.add(viewCommandMenuItem);
        commandMenu.add(addCommandMenuItem);
        commandMenu.add(updateCommandMenuItem);
        commandMenu.add(closeCommandMenuItem);
        commandMenu.add(deleteCommandMenuItem);

        // itemMenu produit
        deleteProductMenuItem = new JMenuItem("Supprimer");
        deleteProductMenuItem.addActionListener(new DeleteProductListener());
        addProductMenuItem = new JMenuItem("Ajouter");
        addProductMenuItem.addActionListener(new CreateProductListener());
        viewProductMenuItem = new JMenuItem("Nos produits");
        viewProductMenuItem.addActionListener(new ListingProductListener());
        updateProductMenuItem = new JMenuItem("Modifier");
        updateProductMenuItem.addActionListener(new UpdateProductListener());
        productMenu.add(viewProductMenuItem);
        productMenu.add(addProductMenuItem);
        productMenu.add(updateProductMenuItem);
        productMenu.add(deleteProductMenuItem);

        researchMenuItem = new JMenuItem("Recherches");
        researchMenuItem.addActionListener(new DataBaseMenuListener());
        dataBaseMenu.add(researchMenuItem);

        // Ajout des variables a la barre
        menuBar.setLayout(new GridLayout(1,4));
        menuBar.add(welcomeMenu);
        menuBar.add(commandMenu);
        menuBar.add(productMenu);
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
    private class ListingOrderListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            container.removeAll();
            container.add(new ListingOrderPanel(), BorderLayout.CENTER);
            container.revalidate();
            container.repaint();
        }
    }
    private class CreateOrderListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            container.removeAll();
            container.add(new CreateOrderPanel(), BorderLayout.CENTER);
            container.revalidate();
            container.repaint();
        }
    }
    private class DeleteOrderListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            container.removeAll();
            container.add(new DeleteOrderPanel(), BorderLayout.CENTER);
            container.revalidate();
            container.repaint();
        }
    }
    private class UpdateOrderListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            container.removeAll();
            container.add(new UpdateOrderPanel(), BorderLayout.CENTER);
            container.revalidate();
            container.repaint();
        }
    }
    private class CloseOrderListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            container.removeAll();
            container.add(new CloseOrderPanel(), BorderLayout.CENTER);
            container.revalidate();
            container.repaint();
        }
    }
    private class ListingProductListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            container.removeAll();
            container.add(new ListingProductPanel(), BorderLayout.CENTER);
            container.revalidate();
            container.repaint();
        }
    }
    private class CreateProductListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            container.removeAll();
            container.add(new CreateProductPanel(), BorderLayout.CENTER);
            container.revalidate();
            container.repaint();
        }
    }
    private class DeleteProductListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            container.removeAll();
            container.add(new DeleteProductPanel(), BorderLayout.CENTER);
            container.revalidate();
            container.repaint();
        }
    }
    private class UpdateProductListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            container.removeAll();
            container.add(new UpdateProductPanel(), BorderLayout.CENTER);
            container.revalidate();
            container.repaint();
        }
    }

    private class DataBaseMenuListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            container.removeAll();
            container.add(new ResearchPanel(), BorderLayout.CENTER);
            container.revalidate();
            container.repaint();
        }
    }
}