/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PopUps;

import DataTypes.Cart;
import DataTypes.CartItem;
import DataTypes.Inventory;
import DataTypes.User;
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
 * @author Andrew
 */
public class CartPopUp {
    private DefaultTableModel tableModel;
    
    public CartPopUp(){
        
    }
    
    public void openPopup(final MasterClass master, Cart cart){
        JFrame frame = new JFrame("Cart");
	frame.setSize(410, 450);
	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	JPanel panel = new JPanel();
	frame.add(panel);
        placeComponents(panel, master, frame, cart);
        
	frame.setVisible(true);
    }
    
    private void placeComponents(JPanel panel, final MasterClass master, final JFrame frame, Cart cart){
        panel.setLayout(null);
        
        String[] columns = {"Product ID","Name", "Seller", "Sell Price", "Amount", "Cost"};
        
        tableModel  = new DefaultTableModel(columns, 0);
        
        final JTable products = new JTable(tableModel);
        
        for (CartItem l : cart.itemList) {
            User user = master.getSeller(l.SellerID);
            Object[] item = {l.productId, l.Name,user.username , l.Sell_Price, l.amount, (double)l.amount * l.Sell_Price};
            tableModel.addRow(item);
        }
        
        Object[] item = {"", "Total","" , "", "", cart.total};
        tableModel.addRow(item);
        
        JScrollPane scrollList = new JScrollPane(products);
        scrollList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollList.setBounds(10, 100, 375, 200);

        panel.add(scrollList);
        
        JButton continueButton = new JButton("Continue Shopping");
        continueButton.setBounds(10, 20, 150, 30);
        panel.add(continueButton);
        
        continueButton.addActionListener(new ActionListener()
            {
               @Override
               public void actionPerformed(ActionEvent event)
               {
                    frame.dispose();
               }
            });
        
        JButton removeButton = new JButton("Remove");
        removeButton.setBounds(10, 50, 150, 30);

        removeButton.addActionListener(new ActionListener()
            {
               @Override
               public void actionPerformed(ActionEvent event)
               {
                   if(products.getSelectedRow() > -1){
                        String result = products.getModel().getValueAt(products.getSelectedRow(), 0).toString();
                        if(!result.isEmpty())
                            master.removeProductFromCart((int) products.getModel().getValueAt(products.getSelectedRow(), 0));
                   }
               }
            });
        
        panel.add(removeButton);
        
        JButton editButton = new JButton("Edit");
        editButton.setBounds(160, 50, 150, 30);

        editButton.addActionListener(new ActionListener()
            {
               @Override
               public void actionPerformed(ActionEvent event)
               {
                    String result = products.getModel().getValueAt(products.getSelectedRow(), 0).toString();
                    if(!result.isEmpty())
                    if(products.getSelectedRow() > -1){
                         master.openEditCartPopup(master.getCartProduct((int) products.getModel().getValueAt(products.getSelectedRow(), 0)));
                    }
               }
            });
        
        panel.add(editButton);
        
        JButton checkOutButton = new JButton("Checkout");
        checkOutButton.setBounds(160, 20, 150, 30);

        checkOutButton.addActionListener(new ActionListener()
            {
               @Override
               public void actionPerformed(ActionEvent event)
               {
                   if(products.getSelectedRow() > -1){
                        
                   }
               }
            });
        
        panel.add(checkOutButton);
    }
    
    public void refreshList(Cart cart, MasterClass master){
        if (tableModel.getRowCount() > 0) {
            for (int i = tableModel.getRowCount() - 1; i > -1; i--) {
                tableModel.removeRow(i);
            }
        }
        
        cart.total = 0;
        
        for (CartItem l : cart.itemList) {
            User user = master.getSeller(l.SellerID);
            Object[] item = {l.productId, l.Name, user.username, l.Sell_Price, l.amount, (double)l.amount * l.Sell_Price};
            tableModel.addRow(item);
            cart.total += (double)l.amount * l.Sell_Price;
        }
        
        Object[] item = {"", "Total","" , "", "", cart.total};
        tableModel.addRow(item);
    }
}
