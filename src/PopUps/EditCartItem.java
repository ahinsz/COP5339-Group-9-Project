/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PopUps;


import DataTypes.CartItem;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import project.MasterClass;

/**
 *The edit cart popup
 * @authors Andrew Hinsz and Geoffrey Laleau
 */
public class EditCartItem {
    /**
     * Default Constructor
     */
    public void EditCartItem(){
        
    }
    
    /**
     * Opens the edit cart popup
     * @param master Reference to the back to the MasterClass 
     * @param cart Reference to the cart item
     */
    public void openEditCartPopup(MasterClass master, CartItem cart){
        JFrame frame = new JFrame("Edit");
	frame.setSize(400, 450);
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
     * @param cart Reference to the cart item
     */
    private void placeComponents(JPanel panel, final MasterClass master, final JFrame frame, final CartItem cart){
        panel.setLayout(null);
        
        final JLabel itemName = new JLabel(cart.Name);
        itemName.setBounds(160, 20, 150, 30);
        panel.add(itemName);
        
        final JLabel itemPrice = new JLabel(String.valueOf(cart.Sell_Price));
        itemPrice.setBounds(160, 50, 150, 30);
        panel.add(itemPrice);
        
        final JLabel itemAmount = new JLabel(String.valueOf(cart.amount));
        itemAmount.setBounds(160, 80, 150, 30);
        panel.add(itemAmount);
        
        final JLabel name = new JLabel("Name:");
        name.setBounds(10, 20, 150, 30);
        panel.add(name);
        
        final JLabel price = new JLabel("Price:");
        price.setBounds(10, 50, 150, 30);
        panel.add(price);
        
        final JLabel amount = new JLabel("Amount:");
        amount.setBounds(10, 80, 150, 30);
        panel.add(amount);
        
        final JLabel newAmount = new JLabel("New Amount:");
        newAmount.setBounds(10, 120, 150, 30);
        panel.add(newAmount);
        
        final JTextField newAmountText = new JTextField(20);
        newAmountText.setBounds(160, 120, 100, 30);
        panel.add(newAmountText);
        
        JButton editButton = new JButton("Edit");
        editButton.setBounds(10, 150, 150, 30);
        
        editButton.addActionListener(new ActionListener()
            {
               @Override
               public void actionPerformed(ActionEvent event)
               {
                   newAmount.setForeground(Color.BLACK);
                   
                   if(isNumber(newAmountText.getText()))
                        if(master.editCart(Integer.parseInt(newAmountText.getText()), cart.productId))
                            frame.dispose();
                        else
                            newAmount.setForeground(Color.red);
                   else{
                            newAmount.setForeground(Color.red);
                   }
               }
               
               public boolean isNumber(String num){
                    try{
                        Integer.parseInt(num);
                        return true;
                    }catch (Exception e){
                        return false;
                    }
               }
            });
        
        panel.add(editButton);
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(160, 150, 150, 30);

        cancelButton.addActionListener(new ActionListener()
            {
               @Override
               public void actionPerformed(ActionEvent event)
               {
                   frame.dispose();
               }
            });
        
        panel.add(cancelButton);
        
    }
}
