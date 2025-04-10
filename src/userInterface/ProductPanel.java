package userInterface;

import controller.ProductController;
import model.Product;

import javax.sql.StatementEventListener;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class ProductPanel extends JPanel {
    private ProductController productController;
    private ArrayList<Product> products;
    private JPanel listsPanel, buttonPanel;
    private JButton addButton, editButton, deleteButton, resetButton;
    private JList<Product> alcoolList, softList, snackList, glutenFreeList, hotDrinkList, iceCreamList;

    public ProductPanel() {
        this.productController = new ProductController();
        this.products = productController.getAllProducts();
        this.setLayout(new BorderLayout());

        listsPanel = new JPanel(new GridLayout(2, 3));

        //ArrayListe de chaque catégorie
        ArrayList<Product> alcoolProducts = new ArrayList<>();
        ArrayList<Product> softProducts = new ArrayList<>();
        ArrayList<Product> snackProducts = new ArrayList<>();
        ArrayList<Product> glutenFreeProducts = new ArrayList<>();
        ArrayList<Product> hotDrinkProducts = new ArrayList<>();
        ArrayList<Product> iceCreamProducts = new ArrayList<>();

        //switch categorie pour ajouter aux bonnes ArrayListe
        for (Product product : products) {
            String category = product.getCategoryLabel();
            switch (category) {
                case "Boisson alcoolisée":
                    alcoolProducts.add(product);
                    break;
                case "Soft":
                    softProducts.add(product);
                    break;
                case "Snacks":
                    snackProducts.add(product);
                    break;
                case "Sans gluten":
                    glutenFreeProducts.add(product);
                    break;
                case "Boisson chaude":
                    hotDrinkProducts.add(product);
                    break;
                case "Glace":
                    iceCreamProducts.add(product);
                    break;
                default:
                    System.out.println("Catégorie inconnue: " + product.getCategoryLabel());
                    break;
            }
        }

        //creation des JList
        alcoolList = new JList<>(alcoolProducts.toArray(new Product[0]));
        softList = new JList<>(softProducts.toArray(new Product[0]));
        snackList = new JList<>(snackProducts.toArray(new Product[0]));
        glutenFreeList = new JList<>(glutenFreeProducts.toArray(new Product[0]));
        hotDrinkList = new JList<>(hotDrinkProducts.toArray(new Product[0]));
        iceCreamList = new JList<>(iceCreamProducts.toArray(new Product[0]));

        //augmenter la taille des cellules
        alcoolList.setFixedCellHeight(30);
        softList.setFixedCellHeight(30);
        snackList.setFixedCellHeight(30);
        glutenFreeList.setFixedCellHeight(30);
        hotDrinkList.setFixedCellHeight(30);
        iceCreamList.setFixedCellHeight(30);

        //ajout evenements aux 6 lists
        alcoolList.addListSelectionListener(new ListListener());
        softList.addListSelectionListener(new ListListener());
        snackList.addListSelectionListener(new ListListener());
        glutenFreeList.addListSelectionListener(new ListListener());
        hotDrinkList.addListSelectionListener(new ListListener());
        iceCreamList.addListSelectionListener(new ListListener());

        //ajout scrollpane avec titre
        listsPanel.add(createScrollPane(alcoolList, "Boissons alcoolisées"));
        listsPanel.add(createScrollPane(softList, "Boissons non alcoolisées"));
        listsPanel.add(createScrollPane(snackList, "Snacks"));
        listsPanel.add(createScrollPane(glutenFreeList, "Sans gluten"));
        listsPanel.add(createScrollPane(hotDrinkList, "Boissons chaudes"));
        listsPanel.add(createScrollPane(iceCreamList, "Glaces"));

        //boutons
        buttonPanel = new JPanel();
        addButton = new JButton("Ajouter");
        addButton.setPreferredSize(new Dimension(300,50));

        editButton = new JButton("Modifier");
        editButton.setPreferredSize(new Dimension(300,50));
        editButton.setEnabled(false);

        deleteButton = new JButton("Supprimer");
        deleteButton.setPreferredSize(new Dimension(300,50));
        deleteButton.setEnabled(false);

        resetButton = new JButton("Réinitialiser");
        resetButton.setPreferredSize(new Dimension(300,50));
        resetButton.addActionListener(new ButtonListener());

        //ajout des boutons
        buttonPanel.add(editButton);
        buttonPanel.add(addButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(deleteButton);

        this.add(listsPanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    //méthode creation de scrollpane avec un titre
    private JScrollPane createScrollPane(JList<Product> list, String title) {
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBorder(BorderFactory.createTitledBorder(title));
        return scrollPane;
    }
    private class ListListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            deleteButton.setEnabled(true);
            editButton.setEnabled(true);
        }
    }
    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            alcoolList.clearSelection();
            softList.clearSelection();
            snackList.clearSelection();
            glutenFreeList.clearSelection();
            hotDrinkList.clearSelection();
            iceCreamList.clearSelection();
            deleteButton.setEnabled(false);
            editButton.setEnabled(false);
        }
    }
}