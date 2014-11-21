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
    
    public ProductList(){
        
    }
    
    public void openPopup(MasterClass master, ArrayList<Inventory> list){
        JFrame frame = new JFrame("Product List");
	frame.setSize(400, 450);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	JPanel panel = new JPanel();
	frame.add(panel);
        placeComponents(panel, master, frame, list);

	frame.setVisible(true);
    }
    
    private void placeComponents(JPanel panel,final MasterClass master,final JFrame frame,ArrayList<Inventory> list){
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
        scrollList.setBounds(10, 100, 360, 200);

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
    }
    
}
