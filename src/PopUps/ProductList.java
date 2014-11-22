/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PopUps;

import DataTypes.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import project.MasterClass;

/**
 *
 * @authors Andrew Hinsz and Geoffrey Laleau
 */
public class ProductList {

    public ProductList() {

    }

    public void openPopup(MasterClass master, ArrayList<Inventory> list, Cart currentCart) {

        this.master = master;
        this.list = list;
        this.currentCart = currentCart;

        initComponents();
        addCompsToPanel();
        actionListeners();
        
        frame.add(panel);
        frame.setVisible(true);
    }

    private static void initComponents() {
        frame = new JFrame("Product List");
        panel = new JPanel();
        cartAmount = new JLabel();
        cartTotal = new JLabel();

        String[] columns = {"Product ID", "Name", "Seller", "Sell Price", "Quantity"};
        tableModel = new DefaultTableModel(columns, 0);
        products = new JTable(tableModel);
        scrollList = new JScrollPane(products);
        logOutButton = new JButton("Log Out");
        detailsButton = new JButton("More Details");
        editCartButton = new JButton("Edit Cart");
        addCartButton = new JButton("Add to Cart");

        configComponents();
    }

    //private static void placeComponents(JPanel panel,final MasterClass master,final JFrame frame,ArrayList<Inventory> list, final Cart currentCart){
    private static void configComponents() {

        frame.setSize(410, 430);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        cartAmount.setText("Cart: " + master.getCurrentCartTotal());
        cartAmount.setBounds(315, 20, 150, 30);
        cartTotal.setText("Total: " + Double.toString(currentCart.total));
        cartTotal.setBounds(315, 50, 150, 30);
        scrollList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollList.setBounds(10, 100, 375, 200);
        logOutButton.setBounds(10, 20, 150, 30);
        detailsButton.setBounds(10, 50, 150, 30);
        editCartButton.setBounds(160, 50, 150, 30);
        addCartButton.setBounds(160, 20, 150, 30);
        
        for (Inventory l : list) {
            User user = master.getSeller(l.SellerID);
            Object[] item = {l.product_ID, l.Name, user.username, l.Sell_Price, l.Quantity};
            tableModel.addRow(item);
        }              
    }

    private static void addCompsToPanel() {

        panel.add(cartAmount);
        panel.add(cartTotal);
        panel.setLayout(null);
        panel.add(scrollList);
        panel.add(logOutButton);
        panel.add(detailsButton);
        panel.add(editCartButton);
        panel.add(addCartButton);
        panel.add(cartAmount);
        panel.add(cartTotal);
        panel.setLayout(null);
        panel.add(scrollList);
    }
    
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
    }

    public void updateCart(Cart currentCart, MasterClass master) {
        cartAmount.setText("Cart: " + master.getCurrentCartTotal());
        cartTotal.setText("Total: " + currentCart.total);
    }

    private static MasterClass master;
    private static JFrame frame;
    private static JPanel panel;
    private static DefaultTableModel tableModel;
    private static JLabel cartAmount;
    private static JLabel cartTotal;
    private static JTable products;
    private static JButton logOutButton;
    private static Cart currentCart;
    private static JScrollPane scrollList;
    private static JButton detailsButton;
    private static JButton editCartButton;
    private static JButton addCartButton;
    private static ArrayList<Inventory> list;
}
