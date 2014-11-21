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
 *
 * @author Andrew
 */
public class SellerList {
    private DefaultTableModel tableModel;
    
    
    public SellerList(){
        
    }
    
    public void openPopup(final MasterClass master, ArrayList<Inventory> list){
        JFrame frame = new JFrame("Product List");
	frame.setSize(400, 450);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	JPanel panel = new JPanel();
	frame.add(panel);
        placeComponents(panel, master, frame, list);
        
        

	frame.setVisible(true);
    }
    
    private void placeComponents(final JPanel panel, final MasterClass master, final JFrame frame, final ArrayList<Inventory> list) {
        
        panel.setLayout(null);
        
        JButton addProductButton = new JButton("Add New Product");
        addProductButton.setBounds(10, 20, 150, 30);

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
        editProductButton.setBounds(200, 20, 150, 30);
        panel.add(editProductButton);
        
        String[] columns = {"Product ID","Name", "Sell Price", "Invoice Price", "Quantity"};
        
        tableModel  = new DefaultTableModel(columns, 0);
        
        JTable products = new JTable(tableModel);
        
        for (Inventory l : list) {
            Object[] item = {l.product_ID, l.Name, l.Sell_Price, l.Invoice_Price, l.Quantity};
            tableModel.addRow(item);
        }
        
        JScrollPane scrollList = new JScrollPane(products);
        scrollList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollList.setBounds(10, 60, 360, 200);

        panel.add(scrollList);
        
        
        
    }
    
    public void updateList(Inventory item){
        Object[] i = {item.product_ID, item.Name, item.Sell_Price, item.Invoice_Price, item.Quantity};
        tableModel.addRow(i);
    }
}
