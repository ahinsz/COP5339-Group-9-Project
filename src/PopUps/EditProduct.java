/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PopUps;

import DataTypes.*;
import static PopUps.NewProduct.isDouble;
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
 *
 * @author Andrew
 */
public class EditProduct {
    
    public EditProduct(){
        
    }
    
    public void openPopup(MasterClass master, Inventory item){
        JFrame frame = new JFrame("Edit Product");
	frame.setSize(400, 450);
	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	JPanel panel = new JPanel();
	frame.add(panel);
	placeComponents(panel, master, frame, item);

	frame.setVisible(true);
    }
    
    private void placeComponents(final JPanel panel,final MasterClass master,final JFrame frame, final Inventory item){
        panel.setLayout(null);

        final JLabel productLabel = new JLabel("Product Name");
        productLabel.setBounds(10, 10, 80, 25);
        panel.add(productLabel);

        final JTextField nameText = new JTextField(100);
        nameText.setText(item.Name);
        nameText.setBounds(100, 10, 160, 25);
        panel.add(nameText);

        final JLabel descriptionLabel = new JLabel("Description");
        descriptionLabel.setBounds(10, 40, 80, 25);
        panel.add(descriptionLabel);

        final JTextArea descriptionText = new JTextArea();
        descriptionText.setText(item.Description);
        descriptionText.setColumns(200);
        descriptionText.setLineWrap(true);

        JScrollPane scrollDescText = new JScrollPane(descriptionText);
        scrollDescText.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollDescText.setBounds(100, 40, 200, 200);

        panel.add(scrollDescText);

        final JLabel sellPriceLabel = new JLabel("Sell Price");
        sellPriceLabel.setBounds(10, 250, 80, 25);
        panel.add(sellPriceLabel);

        final JTextField sellText = new JTextField(100);
        sellText.setText(Double.toString(item.Sell_Price));
        sellText.setBounds(100, 250, 250, 25);
        panel.add(sellText);

        final JLabel invoiceLabel = new JLabel("Invoice Price");
        invoiceLabel.setBounds(10, 290, 80, 25);
        panel.add(invoiceLabel);

        final JTextField invoiceText = new JTextField(100);
        invoiceText.setText(Double.toString(item.Invoice_Price));
        invoiceText.setBounds(100, 290, 250, 25);
        panel.add(invoiceText);

        final JLabel quantityLabel = new JLabel("Quantity");
        quantityLabel.setBounds(10, 330, 80, 25);
        panel.add(quantityLabel);

        final JTextField quantityText = new JTextField(30);
        quantityText.setText(Integer.toString(item.Quantity));
        quantityText.setBounds(100, 330, 160, 25);
        panel.add(quantityText);

        JButton registerButton = new JButton("Edit Product");
        registerButton.setBounds(10, 370, 120, 25);

        registerButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                boolean failed = false;
                productLabel.setForeground(Color.black);
                descriptionLabel.setForeground(Color.black);
                sellPriceLabel.setForeground(Color.black);
                invoiceLabel.setForeground(Color.black);
                quantityLabel.setForeground(Color.black);

                if(nameText.getText().isEmpty()){
                    productLabel.setForeground(Color.red);
                    failed = true;
                }
                if(descriptionText.getText().isEmpty()){
                    descriptionLabel.setForeground(Color.red);
                    failed = true;
                }
                if(sellText.getText().isEmpty() || !isDouble(sellText.getText())){
                    sellPriceLabel.setForeground(Color.red);
                    failed = true;
                }
                if(invoiceText.getText().isEmpty() || !isDouble(invoiceText.getText())){
                    invoiceLabel.setForeground(Color.red);
                    failed = true;
                }
                if(quantityText.getText().isEmpty() || !isDouble(quantityText.getText())){
                    quantityLabel.setForeground(Color.red);
                    failed = true;
                }

                if(failed){
                    frame.repaint();
                    return;
                }

                item.Name = nameText.getText();
                item.Description = descriptionText.getText();
                item.Sell_Price = Double.parseDouble(sellText.getText());
                item.Invoice_Price = Double.parseDouble(invoiceText.getText());
                item.Quantity = Integer.parseInt(quantityText.getText());

                try {
                    master.updateProduct(item);
                } catch (IOException ex) {
                    Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
                }

                frame.dispose();
            }
        });

        panel.add(registerButton);
    }
    
}
