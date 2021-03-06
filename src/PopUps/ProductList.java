/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PopUps;

import DataTypes.*;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import project.MasterClass;

/**
 *
 * @authors Andrew Hinsz and Geoffrey Laleau
 */
public class ProductList {

    /**
     * Default Constructor
     */
    public ProductList() {

    }

    /**
     * Opens and sets up the popup
     * @param master Reference to the current cart
     * @param list Reference to the existing products
     * @param currentCart reference to shopping cart
     */
    public void openPopup(MasterClass master, ArrayList<Inventory> list, Cart currentCart) {

        ProductList.master = master;
        ProductList.list = list;
        ProductList.currentCart = currentCart;

        initComponents();
        addCompsToPanel();
        actionListeners();
                
        frame.add(panel);
        frame.setVisible(true);
    }

    /**
     * Initializes the components of the popup
     */
    private static void initComponents() {
        frame = new JFrame("Product List");
        panel = new JPanel();
        cartAmount = new JLabel();
        cartTotal = new JLabel();

        //String[] columns = {"Product ID", "Name", "Seller", "Sell Price", "Quantity"};
        String[] columns = {"ID","Name","Price","In Stock"};        
        tableModel = new DefaultTableModel(columns, 0);        
        products = new JTable(tableModel);
        scrollList = new JScrollPane(products);
        logOutButton = new JButton("Log Out");
        detailsButton = new JButton("More Details");
        editCartButton = new JButton("Edit Cart");
        addCartButton = new JButton("Add to Cart");
        checkOutButton = new JButton("CheckOut");
        invoiceButton = new JButton("Purchases");
        image = new ImageIcon("images/samsung_lcd.jpg");      
        
        imageLabel = new JLabel();
        configComponents();
    }

    /**
     * Configures the popup elements
     */
    private static void configComponents() {

        frame.setSize(650, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        //menu bar
        logOutButton.setBounds(10, 90, 150, 30);
        detailsButton.setBounds(10, 170, 150, 30);
        addCartButton.setBounds(10, 200, 150, 30);       
        editCartButton.setBounds(10, 230, 150, 30);
        checkOutButton.setBounds(10, 260, 150, 30);
        invoiceButton.setBounds(10, 290, 150, 30);
        
        //cart info
        cartAmount.setBounds(10, 20, 140, 30);
        cartTotal.setBounds(10, 50, 140, 30);
        //scrollList.setBounds(260, 410, 375, 200);
        scrollList.setBounds(200, 90, 400, 230);
        imageLabel.setBounds(270, 90, 350, 300);
        
        
        cartAmount.setText("Items in Cart: " + master.getCurrentCartTotal());        
        cartTotal.setText("Total: " + Double.toString(currentCart.total));        
        scrollList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        for (Inventory l : list) {
            User user = master.getSeller(l.SellerID);
            //Object[] item = {l.product_ID, l.Name, user.username, l.Sell_Price, l.Quantity};
            Object[] item = {l.product_ID, l.Name, l.Sell_Price, l.Quantity};
            
            tableModel.addRow(item);
        }              
    }

    /**
     * Adds components to the popup
     */
    private static void addCompsToPanel() {

        panel.setLayout(null);
        panel.add(cartAmount);
        panel.add(cartTotal);        
        panel.add(scrollList);
        panel.add(logOutButton);
        panel.add(detailsButton);
        panel.add(editCartButton);
        panel.add(addCartButton);       
        panel.add(checkOutButton);
        panel.add(invoiceButton);
        panel.add(imageLabel);        
    }
    
    /**
     * Creates the action listeners for all the popups objects
     */
    private static void actionListeners(){         
        
        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                master.openLoginPopup();
                frame.dispose();
            }
        });        

        detailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                if (products.getSelectedRow() > -1) {
                    master.openMoreDetailsPopup(master.getProduct((int) products.getModel().getValueAt(products.getSelectedRow(), 0)));
                }
            }
        });       

        editCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                master.openCartPopup();
            }
        });        

        addCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                if (products.getSelectedRow() > -1) {
                    master.addToCart(1, (int) products.getModel().getValueAt(products.getSelectedRow(), 0));
                }
            }
        });
        
        checkOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                master.openCheckOutPopup();
            }
        });
        
        invoiceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                master.openInvoicePopup();
            }
        });
    }

    /**
     * Updates the Cart information
     * @param currentCart Reference to current cart
     * @param master Reference to MasterClass
     */
    public void updateCart(Cart currentCart, MasterClass master) {
        cartAmount.setText("Items in Cart: " + master.getCurrentCartTotal());
        cartTotal.setText("Total: " + currentCart.total);
    }
    
    /**
     * Refreshes the product list
     * @param list Reference to products list
     * @param master Reference to MasterClass
     */
    public void refreshList(ArrayList<Inventory> list,MasterClass master){
        if (tableModel.getRowCount() > 0) {
            for (int i = tableModel.getRowCount() - 1; i > -1; i--) {
                tableModel.removeRow(i);
            }
        }
        
        for (Inventory l : list) {
            User user = master.getSeller(l.SellerID);
            Object[] item = {l.product_ID, l.Name, l.Sell_Price, l.Quantity};
            tableModel.addRow(item);
        }
    }

    private static MasterClass master;
    private static JFrame frame;
    private static JPanel panel;
    private static DefaultTableModel tableModel;
    private static JLabel cartAmount;
    private static JLabel cartTotal;
    private static JLabel imageLabel;
    private static ImageIcon image;
    private static JTable products;
    private static JButton logOutButton;
    private static Cart currentCart;
    private static JScrollPane scrollList;    
    private static JButton detailsButton;
    private static JButton editCartButton;
    private static JButton addCartButton;
    private static JButton checkOutButton;
    private static JButton invoiceButton;
    private static ArrayList<Inventory> list;
}
