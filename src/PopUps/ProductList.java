/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PopUps;

import DataTypes.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * @author Andrew
 */
public class ProductList {
    private DefaultTableModel tableModel;
    private JLabel cartAmount;
    private JLabel cartTotal;
    
    
    public ProductList(){
    }
    
    public void openPopup(MasterClass master, ArrayList<Inventory> list, Cart currentCart){
        JFrame frame = new JFrame("Product List");
	frame.setSize(410, 430);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	JPanel panel = new JPanel();
	frame.add(panel);
        placeComponents(panel, master, frame, list, currentCart);

	frame.setVisible(true);
    }
    
    private void placeComponents(JPanel panel,final MasterClass master,final JFrame frame,ArrayList<Inventory> list, final Cart currentCart){
        cartAmount = new JLabel();
        cartTotal = new JLabel();
        
        cartAmount.setText("Cart: " + master.getCurrentCartTotal());
        cartAmount.setBounds(315, 20, 150, 30);
        panel.add(cartAmount);
        
        cartTotal.setText("Total: " + Double.toString(currentCart.total));
        cartTotal.setBounds(315, 50, 150, 30);
        panel.add(cartTotal);
        
        panel.setLayout(null);
        
        String[] columns = {"Product ID","Name", "Seller", "Sell Price", "Quantity"};
        
        tableModel  = new DefaultTableModel(columns, 0);
        
        final JTable products = new JTable(tableModel);
        
        for (Inventory l : list) {
            User user = master.getSeller(l.SellerID);
            Object[] item = {l.product_ID, l.Name,user.username , l.Sell_Price, l.Quantity};
            tableModel.addRow(item);
        }
        
        JScrollPane scrollList = new JScrollPane(products);
        scrollList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollList.setBounds(10, 100, 375, 200);

        panel.add(scrollList);
        
        JButton logOutButton = new JButton("Log Out");
        logOutButton.setBounds(10, 20, 150, 30);
        panel.add(logOutButton);
        
        logOutButton.addActionListener(new ActionListener()
            {
               @Override
               public void actionPerformed(ActionEvent event)
               {
                    master.openLoginPopup();
                    frame.dispose();
               }
            });
        
        JButton detailsButton = new JButton("More Details");
        detailsButton.setBounds(10, 50, 150, 30);

        detailsButton.addActionListener(new ActionListener()
            {
               @Override
               public void actionPerformed(ActionEvent event)
               {
                   if(products.getSelectedRow() > -1){
                        
                   }
               }
            });
        
        panel.add(detailsButton);
        
        JButton editCartButton = new JButton("Edit Cart");
        editCartButton.setBounds(160, 50, 150, 30);

        editCartButton.addActionListener(new ActionListener()
            {
               @Override
               public void actionPerformed(ActionEvent event)
               {
                   if(products.getSelectedRow() > -1){
                        master.openCartPopup();
                   }
               }
            });
        
        panel.add(editCartButton);
        
        JButton addCartButton = new JButton("Add to Cart");
        addCartButton.setBounds(160, 20, 150, 30);

        addCartButton.addActionListener(new ActionListener()
            {
               @Override
               public void actionPerformed(ActionEvent event)
               {
                   if(products.getSelectedRow() > -1){
                        master.addToCart(1, (int) products.getModel().getValueAt(products.getSelectedRow(), 0));
                   }
               }
            });
        
        panel.add(addCartButton);
    }
    
    public void updateCart(Cart currentCart, MasterClass master){
        cartAmount.setText("Cart: " + master.getCurrentCartTotal());
        cartTotal.setText("Total: " + currentCart.total);
    }
    
}
