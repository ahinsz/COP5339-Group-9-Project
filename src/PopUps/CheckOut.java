/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PopUps;

import DataTypes.Cart;
import DataTypes.CartItem;
import DataTypes.User;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import project.MasterClass;

/**
 * The checkout popup
 * @authors Andrew Hinsz and Geoffrey Laleau
 */
public class CheckOut {
    private DefaultTableModel tableModel;
    
    /**
     * Default Constructor
     */
    public CheckOut(){
        
    }
    
    /**
     * Opens the Checkout popup
     * @param master Reference to the back to the MasterClass 
     * @param cart Reference to the shopping cart in MasterClass
     */
    public void openPopup(final MasterClass master, Cart cart){
        JFrame frame = new JFrame("Cart");
	frame.setSize(410, 510);
	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	JPanel panel = new JPanel();
	frame.add(panel);
        placeComponents(panel, master, frame, cart);
        
	frame.setVisible(true);
    }
    
    /**
     * Places the components of the popup and sets the functionality of its buttons and lists
     * @param panel the panel used in popup
     * @param master Reference to the MasterClass
     * @param frame the frame of the popup
     * @param cart Reference to the cart
     */
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
        scrollList.setBounds(10, 20, 375, 200);

        panel.add(scrollList);
        
        final JLabel cardHolderLabel = new JLabel("Card Holder:");
        cardHolderLabel.setBounds(10, 240, 80, 25);
        panel.add(cardHolderLabel);

        final JTextField cardHolderText = new JTextField(100);
        cardHolderText.setBounds(100, 240, 160, 25);
        panel.add(cardHolderText);
        
        final JLabel cardNumberLabel = new JLabel("Card Number:");
        cardNumberLabel.setBounds(10, 280, 80, 25);
        panel.add(cardNumberLabel);

        final JTextField cardNumberText = new JTextField(20);
        cardNumberText.setBounds(100, 280, 160, 25);
        panel.add(cardNumberText);
        
        final JLabel cvcLabel = new JLabel("CVC:");
        cvcLabel.setBounds(10, 320, 80, 25);
        panel.add(cvcLabel);

        final JTextField cvcText = new JTextField(3);
        cvcText.setBounds(100, 320, 30, 25);
        panel.add(cvcText);
        
        final JLabel cardExpirationLabel = new JLabel("Card Expiration Date:");
        cardExpirationLabel.setBounds(10, 360, 200, 25);
        panel.add(cardExpirationLabel);
        
        final JLabel mmLabel = new JLabel("MM:");
        mmLabel.setBounds(10, 390, 50, 25);
        panel.add(mmLabel);
        
        final JTextField mmText = new JTextField(2);
        mmText.setBounds(70, 390, 30, 25);
        panel.add(mmText);
        
        final JLabel yearLabel = new JLabel("YYYY:");
        yearLabel.setBounds(120, 390, 50, 25);
        panel.add(yearLabel);
        
        final JTextField yearText = new JTextField(4);
        yearText.setBounds(190, 390, 50, 25);
        panel.add(yearText);
        
        JButton continueButton = new JButton("Continue Shopping");
        continueButton.setBounds(10, 430, 150, 30);
        panel.add(continueButton);
        
        continueButton.addActionListener(new ActionListener()
            {
               @Override
               public void actionPerformed(ActionEvent event)
               {
                    frame.dispose();
               }
            });
        
        JButton checkOutButton = new JButton("Checkout");
        checkOutButton.setBounds(160, 430, 150, 30);

        checkOutButton.addActionListener(new ActionListener()
            {
               @Override
               public void actionPerformed(ActionEvent event)
               {
                   master.CheckOut();
                   frame.dispose();
               }
            });
        
        panel.add(checkOutButton);
    }
    
}
