package userInterface;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import beerThread.*;
import controller.*;

public class MainWindow extends JFrame {
    private JLabel welcomeLabel;
    private JMenuBar menuBar;
    private JMenu welcomeMenu, commandMenu, productMenu, dataBaseMenu;
    private JMenuItem welcomeMenuItem, deleteCommandMenuItem, addCommandMenuItem, viewCommandMenuItem, updateCommandMenuItem, closeCommandMenuItem, deleteProductMenuItem, addProductMenuItem, viewProductMenuItem, updateProductMenuItem, researchMenuItem;
    private Container container;

    private ConnectionController connectionController;

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
        setConnectionController(new ConnectionController());

        // Déclaration des variables
            // Container
        container = this.getContentPane();
        container.setLayout(new BorderLayout());
            // Accueil
        welcomeLabel = new JLabel("Bienvenue sur le terminal", SwingConstants.CENTER);
                // Style message d'accueil
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        welcomeLabel.setBorder(new EmptyBorder(200, 0, 100, 0));
            // MenuBar
        menuBar = new JMenuBar();
        menuBar.setLayout(new GridLayout(1,4));
        welcomeMenu = new JMenu("Buvette");
        commandMenu = new JMenu("Commandes");
        productMenu = new JMenu("Produits");
        dataBaseMenu = new JMenu("Base de donnees");
                // MenuBar Item
                    // WelcomeMenuItem
        welcomeMenuItem = new JMenuItem("Accueil");
                    // CommandMenuItem
        viewCommandMenuItem = new JMenuItem("Voir les commandes");
        addCommandMenuItem = new JMenuItem("Ajouter");
        updateCommandMenuItem = new JMenuItem("Modifier");
        deleteCommandMenuItem = new JMenuItem("Supprimer");
        closeCommandMenuItem = new JMenuItem("Finaliser une commande");
                    // ProductMenuItem
        viewProductMenuItem = new JMenuItem("Voir les produits");
        addProductMenuItem = new JMenuItem("Ajouter");
        updateProductMenuItem = new JMenuItem("Modifier");
        deleteProductMenuItem = new JMenuItem("Supprimer");
                    // ResearchMenuItem
        researchMenuItem = new JMenuItem("Recherches");

        // Listeners
            // WelcomeMenuItem
        welcomeMenuItem.addActionListener(new WelcomeMenuItemListener());
            // CommandMenuItem
        viewCommandMenuItem.addActionListener(new ListingOrderListener());
        addCommandMenuItem.addActionListener(new CreateOrderListener());
        updateCommandMenuItem.addActionListener(new UpdateOrderListener());
        deleteCommandMenuItem.addActionListener(new DeleteOrderListener());
        closeCommandMenuItem.addActionListener(new CloseOrderListener());
            // ProductMenuItem
        viewProductMenuItem.addActionListener(new ListingProductListener());
        addProductMenuItem.addActionListener(new CreateProductListener());
        updateProductMenuItem.addActionListener(new UpdateProductListener());
        deleteProductMenuItem.addActionListener(new DeleteProductListener());
            // ResearchMenuItem
        researchMenuItem.addActionListener(new DataBaseMenuListener());

        // ============================================= //
        // Ajout des variables au contenair
            // Message d'accueil
        container.add(welcomeLabel, BorderLayout.NORTH);

        // Ajout des variables a la barre de menu
            // WelcomeMenu
        welcomeMenu.add(welcomeMenuItem);
                //Verre de choppe
        container.add(new BeerGlass());
        menuBar.add(welcomeMenu);
            // CommandMenu
        commandMenu.add(viewCommandMenuItem);
        commandMenu.add(addCommandMenuItem);
        commandMenu.add(updateCommandMenuItem);
        commandMenu.add(deleteCommandMenuItem);
        commandMenu.add(closeCommandMenuItem);
        menuBar.add(commandMenu);
            // ProductMenu
        productMenu.add(viewProductMenuItem);
        productMenu.add(addProductMenuItem);
        productMenu.add(updateProductMenuItem);
        productMenu.add(deleteProductMenuItem);
        menuBar.add(productMenu);
            // ResearchMenu
        dataBaseMenu.add(researchMenuItem);
        menuBar.add(dataBaseMenu);

        this.setJMenuBar(menuBar);
        this.setVisible(true);
    }

    // SETTERS
    public void setConnectionController(ConnectionController connectionController) {
        this.connectionController = connectionController;
    }

    // LISTENERS
        // WelcomeMenuItem
    private class WelcomeMenuItemListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            container.removeAll();
            container.add(welcomeLabel, BorderLayout.NORTH);
            container.add(new BeerGlass());

            container.revalidate();
            container.repaint();
        }
    }

        // CommandMenuItem
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
    private class UpdateOrderListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            container.removeAll();
            container.add(new UpdateOrderPanel(), BorderLayout.CENTER);

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
    private class CloseOrderListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            container.removeAll();
            container.add(new CloseOrderPanel(), BorderLayout.CENTER);

            container.revalidate();
            container.repaint();
        }
    }

        // ProductMenuItem
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
    private class UpdateProductListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            container.removeAll();
            container.add(new UpdateProductPanel(), BorderLayout.CENTER);

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

        // ResearchMenuItem
    private class DataBaseMenuListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            container.removeAll();
            container.add(new ResearchPanel(), BorderLayout.CENTER);

            container.revalidate();
            container.repaint();
        }
    }
}