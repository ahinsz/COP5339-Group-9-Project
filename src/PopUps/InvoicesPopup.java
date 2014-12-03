/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PopUps;

import DataTypes.Cart;
import DataTypes.CartItem;
import DataTypes.Inventory;
import DataTypes.Invoices;
import DataTypes.User;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import project.MasterClass;

/**
 *
 * @authors Andrew Hinsz and Geoffrey Laleau
 */
public class InvoicesPopup {
    /**
     * Default constructor
     */
    public InvoicesPopup(){
        
    }
    
    /**
     * Opens the Invoice popup
     * @param list Reference of all customer invoices
     * @param master Reference for the MasterClass
     */
    public void openPopup(ArrayList<Invoices> list, MasterClass master){
        InvoicesPopup.list = list;
        InvoicesPopup.master = master;

        initComponents();
        addCompsToPanel();
        actionListeners();
                
        frame.add(panel);
        frame.setVisible(true);
    }
    
    /**
     * Initializes the components of the popup
     */
    private void initComponents(){
        frame = new JFrame("Invoices");
        panel = new JPanel();
        
        String[] invoiceColumns = {"ID","Totals","Date"};
        invoiceModel = new DefaultTableModel(invoiceColumns, 0);        
        invoicesTable = new JTable(invoiceModel);
        invoiceScrollList = new JScrollPane(invoicesTable);
        
        String[] itemColumns = {"Product ID","Name", "Seller", "Sell Price", "Amount", "Cost"};
        cartItemModel = new DefaultTableModel(itemColumns, 0);        
        itemTable = new JTable(cartItemModel);
        itemScrollList = new JScrollPane(itemTable);
        
        invoiceLabel = new JLabel("Invoices");
        itemLabel = new JLabel("Products");
        
        exitButton = new JButton("Exit");
        
        configComponents();
    }
    
    /**
     * Configures the popup elements
     */
    private void configComponents(){
        frame.setSize(450, 570);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        invoiceLabel.setBounds(10, 10, 150, 30);
        invoiceScrollList.setBounds(10, 30, 375, 200);
        invoiceScrollList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        itemLabel.setBounds(10, 240, 150, 30);
        itemScrollList.setBounds(10, 260, 375, 200);
        itemScrollList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        exitButton.setBounds(10, 480, 150, 30);
        
        for (Invoices l : list) {
            Object[] item = {l.InvoiceID, l.total, l.Date_of_Purchase};
            invoiceModel.addRow(item);
        }
    }
    
    /**
     * Adds components to the popup
     */
    private void addCompsToPanel(){
        panel.setLayout(null);
        panel.add(invoiceScrollList);
        panel.add(itemScrollList);
        panel.add(invoiceLabel);
        panel.add(itemLabel);
        panel.add(exitButton);
    }
    
    /**
     * Creates the action listeners for all the popups objects
     */
    private void actionListeners(){
        invoicesTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (invoicesTable.getSelectedRow() > -1) {
                    if (cartItemModel.getRowCount() > 0) {
                        for (int i = cartItemModel.getRowCount() - 1; i > -1; i--) {
                            cartItemModel.removeRow(i);
                        }
                    }
                    
                    for(int i = 0; i < list.size(); i++)
                    {
                        //Object temp = invoicesTable.getModel().getValueAt(invoicesTable.getSelectedRow(), 0);
                        int id = (int) invoicesTable.getModel().getValueAt(invoicesTable.getSelectedRow(), 0);
                        if(list.get(i).InvoiceID == id ){
                            for (CartItem l : list.get(i).Products) {
                                User user = master.getSeller(l.SellerID);
                                Object[] item = {l.productId, l.Name,user.username , l.Sell_Price, l.amount, (double)l.amount * l.Sell_Price};
                                cartItemModel.addRow(item);
                                
                            }
                            break;
                        }
                    }
                   
                   
                }
            }
          });
        
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
    }
    
    private static JFrame frame;
    private static JPanel panel;
    private static DefaultTableModel invoiceModel;
    private static DefaultTableModel cartItemModel;
    private static JLabel invoiceLabel;
    private static JLabel itemLabel;
    private static JTable invoicesTable;
    private static JTable itemTable;
    private static JScrollPane invoiceScrollList;
    private static JScrollPane itemScrollList;
    private static JButton exitButton;
    private static ArrayList<Invoices> list;
    private static MasterClass master;
}
