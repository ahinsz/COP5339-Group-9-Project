/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PopUps;

import DataTypes.Inventory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Open the sells records popup for the seller
 * @authors Andrew Hinsz and Geoffrey Laleau
 */
public class SellerRecords {
    
    /**
     * Default Constructor
     */
    public SellerRecords(){
        
    }
    
    /**
     * Open seller records popup
     * @param list Reference to the seller inventory
     */
    public void openPopup(ArrayList<Inventory> list){
        JFrame frame = new JFrame("Seller Records");
	frame.setSize(670, 330);
	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	JPanel panel = new JPanel();
	frame.add(panel);
        placeComponents(panel, frame, list);
        
	frame.setVisible(true);
    }
    
    /**
     * Places the components of the popup and sets the functionality of its buttons and lists
     * @param panel the panel used in popup
     * @param frame the frame of the popup
     * @param list Reference to the seller inventory
     */
    private void placeComponents(JPanel panel,final JFrame frame, ArrayList<Inventory> list){
        panel.setLayout(null);
        double totalCost = 0;
        double totalRevenue = 0;
        double totalProfit = 0;
        
        String[] columns = {"ID","Name", "Sell", "Invoice", "Available", "Sold", "Revenues", "Cost", "Profit"};
        
        DefaultTableModel tableModel  = new DefaultTableModel(columns, 0);
        
        final JTable products = new JTable(tableModel);
        
        for (Inventory l : list) {
            Object[] item = {l.product_ID, l.Name, l.Sell_Price, l.Invoice_Price, l.Quantity, 
                (int) l.ItemsSold, l.Sell_Price * l.ItemsSold, l.Invoice_Price * (l.Quantity + (int)l.ItemsSold), 
                (l.Sell_Price * l.ItemsSold) - (l.Invoice_Price * (l.Quantity + (int)l.ItemsSold))};
            tableModel.addRow(item);
            totalRevenue += l.Sell_Price * l.ItemsSold;
            totalCost += l.Invoice_Price * (l.Quantity + (int)l.ItemsSold);
            totalProfit += (l.Sell_Price * l.ItemsSold) - (l.Invoice_Price * (l.Quantity + (int)l.ItemsSold));
        }
        
        Object[] total = {"","Total","","","","",totalRevenue,totalCost,totalProfit};
        tableModel.addRow(total);
        
        JScrollPane scrollList = new JScrollPane(products);
        scrollList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollList.setBounds(10, 20, 635, 200);

        panel.add(scrollList);
        
        JButton addProductButton = new JButton("Exit");
        addProductButton.setBounds(10, 250, 150, 30);

        addProductButton.addActionListener(new ActionListener()
            {
               @Override
               public void actionPerformed(ActionEvent event)
               {
                   frame.dispose();
               }
            });
        
        panel.add(addProductButton);
    }
    
}
