/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PopUps;

import DataTypes.User;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import project.MasterClass;

/**
 *
 * @authors Andrew Hinsz and Geoffrey Laleau
 */
public class Register {
    
    /**
     * Default Constructor
     */
    public Register(){
        
    }
    
    /**
     * Opens the registration popup
     * @param master Reference to the MasterClass
     */
    public void openPopup(MasterClass master){
        JFrame frame = new JFrame("Register");
	frame.setSize(400, 520);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	JPanel panel = new JPanel();
	frame.add(panel);
	placeComponents(panel, master, frame);

	frame.setVisible(true);
    }
    
    /**
     * Places the components of the popup and sets the functionality of its buttons and lists
     * @param panel the panel used in popup
     * @param master Reference to the MasterClass
     * @param frame the frame of the popup
     */
    private static void placeComponents(final JPanel panel, final MasterClass master, final JFrame frame) {

		panel.setLayout(null);

		final JLabel userLabel = new JLabel("UserName");
		userLabel.setBounds(10, 10, 80, 25);
		panel.add(userLabel);

		final JTextField userText = new JTextField(20);
		userText.setBounds(100, 10, 160, 25);
		panel.add(userText);

		final JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(10, 40, 80, 25);
		panel.add(passwordLabel);

		final JPasswordField passwordText = new JPasswordField(20);
		passwordText.setBounds(100, 40, 160, 25);
		panel.add(passwordText);
                
                final JLabel fnameLabel = new JLabel("First Name");
		fnameLabel.setBounds(10, 80, 80, 25);
		panel.add(fnameLabel);

		final JTextField fnameText = new JTextField(20);
		fnameText.setBounds(100, 80, 160, 25);
		panel.add(fnameText);
                
                final JLabel lnameLabel = new JLabel("Last Name");
		lnameLabel.setBounds(10, 120, 80, 25);
		panel.add(lnameLabel);

		final JTextField lnameText = new JTextField(20);
		lnameText.setBounds(100, 120, 160, 25);
		panel.add(lnameText);
                
                final JLabel emailLabel = new JLabel("Email");
		emailLabel.setBounds(10, 160, 80, 25);
		panel.add(emailLabel);

		final JTextField emailText = new JTextField(100);
		emailText.setBounds(100, 160, 250, 25);
		panel.add(emailText);
                
                final JLabel address1Label = new JLabel("Address 1");
		address1Label.setBounds(10, 200, 80, 25);
		panel.add(address1Label);

		final JTextField address1Text = new JTextField(100);
		address1Text.setBounds(100, 200, 250, 25);
		panel.add(address1Text);
                
                final JLabel address2Label = new JLabel("Address 2");
		address2Label.setBounds(10, 240, 80, 25);
		panel.add(address2Label);

		final JTextField address2Text = new JTextField(100);
		address2Text.setBounds(100, 240, 250, 25);
		panel.add(address2Text);
                
                final JLabel cityLabel = new JLabel("City");
		cityLabel.setBounds(10, 280, 80, 25);
		panel.add(cityLabel);

		final JTextField cityText = new JTextField(30);
		cityText.setBounds(100, 280, 160, 25);
		panel.add(cityText);
                
                final JLabel stateLabel = new JLabel("State");
		stateLabel.setBounds(10, 320, 80, 25);
		panel.add(stateLabel);

		final JTextField stateText = new JTextField(30);
		stateText.setBounds(100, 320, 160, 25);
		panel.add(stateText);
                
                final JLabel zipLabel = new JLabel("ZIP");
		zipLabel.setBounds(10, 360, 80, 25);
		panel.add(zipLabel);

		final JTextField zipText = new JTextField(30);
		zipText.setBounds(100, 360, 160, 25);
		panel.add(zipText);
                
                final JCheckBox seller = new JCheckBox("Seller");
                seller.setBounds(10, 400, 80, 25);
                panel.add(seller);
		
		JButton registerButton = new JButton("register");
		registerButton.setBounds(10, 440, 80, 25);
                
                registerButton.addActionListener(new ActionListener(){

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        boolean failed = false;
                        userLabel.setForeground(Color.black);
                        fnameLabel.setForeground(Color.black);
                        lnameLabel.setForeground(Color.black);
                        emailLabel.setForeground(Color.black);
                        passwordLabel.setForeground(Color.black);
                        address1Label.setForeground(Color.black);
                        cityLabel.setForeground(Color.black);
                        stateLabel.setForeground(Color.black);
                        zipLabel.setForeground(Color.black);
                        
                        if(master.UserExists(userText.getText()) || userText.getText().isEmpty()){
                            userLabel.setForeground(Color.red);
                            failed = true;
                        }
                        if(master.EmailExists(emailText.getText()) || emailText.getText().isEmpty()){
                            emailLabel.setForeground(Color.red);
                            failed = true;
                        }
                        if(fnameText.getText().isEmpty()){
                            fnameLabel.setForeground(Color.red);
                            failed = true;
                        }
                        if(lnameText.getText().isEmpty()){
                            lnameLabel.setForeground(Color.red);
                            failed = true;
                        }
                        if(new String(passwordText.getPassword()).isEmpty()){
                            passwordLabel.setForeground(Color.red);
                            failed = true;
                        }
                        if(address1Text.getText().isEmpty()){
                            address1Label.setForeground(Color.red);
                            failed = true;
                        }
                        if(cityText.getText().isEmpty()){
                            cityLabel.setForeground(Color.red);
                            failed = true;
                        }
                        if(stateText.getText().isEmpty()){
                            stateLabel.setForeground(Color.red);
                            failed = true;
                        }
                        if(zipText.getText().isEmpty()){
                            zipLabel.setForeground(Color.red);
                            failed = true;
                        }
                        
                        if(failed){
                            frame.repaint();
                            return;
                        }
                        
                        User user = new User();
                        user.username = userText.getText();
                        user.FirstName = fnameText.getText();
                        user.LastName = lnameText.getText();
                        user.password = new String(passwordText.getPassword());
                        user.email = emailText.getText();
                        user.Address1 = address1Text.getText();
                        user.Address2 = address2Text.getText();
                        user.City = cityText.getText();
                        user.State = stateText.getText();
                        user.Zip = zipText.getText();
                        user.isSeller = seller.isSelected();
                        
                        try {
                            master.createUser(user);
                        } catch (IOException ex) {
                            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        
                        frame.dispose();
                        master.openLoginPopup();
                    }
                });
                
		panel.add(registerButton);
	}
}
