/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PopUps;

import DataTypes.Inventory;
import DataTypes.User;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import project.MasterClass;

/**
 *The more details popup
 * @authors Andrew Hinsz and Geoffrey Laleau
 */
public class MoreDetails {
    /**
     * Default Constructor
     */
    public MoreDetails(){
        
    }
    
    /**
     * Opens the More details popup
     * @param master Reference to the MasterClass
     * @param item Reference to the item
     */
    public void openPopup(MasterClass master, Inventory item){
        JFrame frame = new JFrame("New Product");
	frame.setSize(400, 450);
	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	JPanel panel = new JPanel();
	frame.add(panel);
	placeComponents(panel, master, frame, item);

	frame.setVisible(true);
    }
    
    /**
     * Places the components of the popup and sets the functionality of its buttons and lists
     * @param panel the panel used in popup
     * @param master Reference to the MasterClass
     * @param frame the frame of the popup
     * @param item Reference to the product
     */
    private static void placeComponents(final JPanel panel, final MasterClass master, final JFrame frame, final Inventory item) {

		panel.setLayout(null);

		final JLabel productLabel = new JLabel("Product");
		productLabel.setBounds(10, 10, 80, 25);
		panel.add(productLabel);

		final JLabel nameText = new JLabel();
                nameText.setText(item.Name);
		nameText.setBounds(100, 10, 160, 25);
		panel.add(nameText);

		final JLabel descriptionLabel = new JLabel("Description");
		descriptionLabel.setBounds(10, 40, 80, 25);
		panel.add(descriptionLabel);

		final JTextArea descriptionText = new JTextArea();
		descriptionText.setColumns(200);
                descriptionText.setLineWrap(true);
                descriptionText.setText(item.Description);
                descriptionText.setEditable(false);
                
                JScrollPane scrollDescText = new JScrollPane(descriptionText);
                scrollDescText.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                scrollDescText.setBounds(100, 40, 200, 200);
                
		panel.add(scrollDescText);
                
                final JLabel sellPriceLabel = new JLabel("Sell Price");
		sellPriceLabel.setBounds(10, 250, 80, 25);
		panel.add(sellPriceLabel);

		final JLabel sellText = new JLabel();
                sellText.setText(Double.toString(item.Sell_Price));
		sellText.setBounds(100, 250, 250, 25);
		panel.add(sellText);
                
                final JLabel sellerNameLabel = new JLabel("Seller");
		sellerNameLabel.setBounds(10, 290, 80, 25);
		panel.add(sellerNameLabel);
                
                User seller = master.getSeller(item.SellerID);

		final JLabel sellerNameText = new JLabel();
		sellerNameText.setBounds(100, 290, 250, 25);
                sellerNameText.setText(seller.username);
		panel.add(sellerNameText);
                
                final JLabel sellercityLabel = new JLabel("Seller Address:");
		sellercityLabel.setBounds(170, 240, 150, 25);
		panel.add(sellercityLabel);

		final JLabel sellerAddressText = new JLabel();
		sellerAddressText.setBounds(170, 260, 250, 25);
                sellerAddressText.setText(seller.Address1);
		panel.add(sellerAddressText);
                
                final JLabel sellerCityStateZipText = new JLabel();
		sellerCityStateZipText.setBounds(170, 280, 250, 25);
                sellerCityStateZipText.setText(seller.City + ", " + seller.State + " " + seller.Zip);
		panel.add(sellerCityStateZipText);
                
                
                final JLabel amountLabel = new JLabel("Amount");
		amountLabel.setBounds(10, 330, 80, 25);
		panel.add(amountLabel);

		final JTextField amountText = new JTextField(30);
		amountText.setBounds(100, 330, 60, 25);
		panel.add(amountText);
		
		JButton addCartButton = new JButton("Add Product");
		addCartButton.setBounds(10, 370, 150, 25);
                
                addCartButton.addActionListener(new ActionListener(){

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        
                        if(isInt(amountText.getText())){
                            if(item.Quantity >= Integer.parseInt(amountText.getText())){
                                master.addToCart(Integer.parseInt(amountText.getText()), item.product_ID);
                                frame.dispose();
                            }
                        }
                    }
                    
                    public boolean isInt(String item){
                        try
                        {
                          Integer.parseInt(item);
                          return true;
                        }
                        catch(NumberFormatException e)
                        {
                          return false;
                        }        
                    }
                });
                
                panel.add(addCartButton);
                
                JButton exitButton = new JButton("Exit");
		exitButton.setBounds(160, 370, 150, 25);
                
                exitButton.addActionListener(new ActionListener(){

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frame.dispose();
                    }
                });
                
		panel.add(exitButton);
	}
}
