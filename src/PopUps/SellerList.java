/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PopUps;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import project.MasterClass;
import DataTypes.*;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *The seller popup
 * @authors Andrew Hinsz and Geoffrey Laleau
 */
public class SellerList {
    private DefaultTableModel tableModel;
    
    /**
     * default Constructor
     */
    public SellerList(){
        
    }
    
    /**
     * Opens the seller popup
     * @param master Reference to MasterClass
     * @param list Reference to the seller inventory
     */
    public void openPopup(final MasterClass master, ArrayList<Inventory> list){
        JFrame frame = new JFrame("Seller List");
	frame.setSize(600, 300);
	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	JPanel panel = new JPanel();
	frame.add(panel);
        placeComponents(panel, master, frame, list);
        
	frame.setVisible(true);
    }
    
    /**
     * Places the components of the popup and sets the functionality of its buttons and lists
     * @param panel the panel used in popup
     * @param master Reference to the MasterClass
     * @param frame the frame of the popup
     * @param list Reference to the seller inventory
     */
    private void placeComponents(final JPanel panel, final MasterClass master, final JFrame frame, final ArrayList<Inventory> list) {
        panel.setLayout(null);
        
        String[] columns = {"Product ID","Name", "Sell Price", "Invoice Price", "Quantity"};
        
        tableModel  = new DefaultTableModel(columns, 0);
        
        final JTable products = new JTable(tableModel);
        
        for (Inventory l : list) {
            Object[] item = {l.product_ID, l.Name, l.Sell_Price, l.Invoice_Price, l.Quantity};
            tableModel.addRow(item);
        }
        
        JScrollPane scrollList = new JScrollPane(products);
        scrollList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollList.setBounds(180 , 20, 395, 200);

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
        
        JButton addProductButton = new JButton("Add New Product");
        addProductButton.setBounds(10, 50, 150, 30);

        addProductButton.addActionListener(new ActionListener()
            {
               @Override
               public void actionPerformed(ActionEvent event)
               {
                   master.openNewProduct();
               }
            });
        
        panel.add(addProductButton);
        
        JButton editProductButton = new JButton("Edit Product");
        editProductButton.setBounds(10, 80, 150, 30);
        panel.add(editProductButton);
        
        editProductButton.addActionListener(new ActionListener()
            {
               @Override
               public void actionPerformed(ActionEvent event)
               {
                    if(products.getSelectedRow() > -1){
                        master.openEditProduct((int) products.getModel().getValueAt(products.getSelectedRow(), 0));
                    }
               }
            });
        
        JButton removeProductButton = new JButton("Delete Product");
        removeProductButton.setBounds(10, 110, 150, 30);
        panel.add(removeProductButton);
        
        removeProductButton.addActionListener(new ActionListener()
            {
               @Override
               public void actionPerformed(ActionEvent event)
               {
                    if(products.getSelectedRow() > -1){
                        try {
                            master.removeProduct((int) products.getModel().getValueAt(products.getSelectedRow(), 0));
                        } catch (IOException ex) {
                            Logger.getLogger(SellerList.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
               }
            });
        
        
        
        JButton viewSalesButton = new JButton("View Sales");
        viewSalesButton.setBounds(10, 140, 150, 30);
        panel.add(viewSalesButton);
        
        viewSalesButton.addActionListener(new ActionListener()
            {
               @Override
               public void actionPerformed(ActionEvent event)
               {
                    master.openSellerRecords();
               }
            });
        
        
    }
    
    /**
     * Updates the seller list
     * @param item New product being added
     */
    public void updateList(Inventory item){
        Object[] i = {item.product_ID, item.Name, item.Sell_Price, item.Invoice_Price, item.Quantity};
        tableModel.addRow(i);
    }
    
    /**
     * Refreshes the seller's inventory
     * @param seller Reference to the seller's inventory
     */
    public void refreshList(ArrayList<Inventory> seller){
        if (tableModel.getRowCount() > 0) {
            for (int i = tableModel.getRowCount() - 1; i > -1; i--) {
                tableModel.removeRow(i);
            }
        }
        
        for (Inventory l : seller) {
            Object[] item = {l.product_ID, l.Name, l.Sell_Price, l.Invoice_Price, l.Quantity};
            tableModel.addRow(item);
        }
    }
}
