/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PopUps;

import java.awt.event.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import project.MasterClass;

/**
 *
 * @author Andrew
 */
public class Login {
    public Login(){
        
    }
    
    public void openLogin(MasterClass master){
        JFrame frame = new JFrame("Login");
	frame.setSize(300, 150);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	JPanel panel = new JPanel();
	frame.add(panel);
	placeComponents(panel, master, frame);

	frame.setVisible(true);
    }
    
    private static void placeComponents(final JPanel panel, final MasterClass master, final JFrame frame) {

		panel.setLayout(null);

		JLabel userLabel = new JLabel("User");
		userLabel.setBounds(10, 10, 80, 25);
		panel.add(userLabel);

		final JTextField userText = new JTextField(20);
		userText.setBounds(100, 10, 160, 25);
		panel.add(userText);

		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(10, 40, 80, 25);
		panel.add(passwordLabel);

		final JPasswordField passwordText = new JPasswordField(20);
		passwordText.setBounds(100, 40, 160, 25);
		panel.add(passwordText);

		JButton loginButton = new JButton("login");
		loginButton.setBounds(10, 80, 80, 25);
                
                loginButton.addActionListener(new ActionListener()
                    {
                       @Override
                       public void actionPerformed(ActionEvent event)
                       {
                           try {
                               if(master.LoginUser(userText.getText(), new String(passwordText.getPassword()))){
                                   frame.dispose();
                                   if(master.isSeller())
                                       master.openSellerList();
                                   else
                                       master.openProductList();
                               }
                           } catch (IOException ex) {
                               Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                           }
                           
                       }
                    });
                
		panel.add(loginButton);
		
		JButton registerButton = new JButton("register");
		registerButton.setBounds(180, 80, 80, 25);
                
                registerButton.addActionListener(new ActionListener()
                    {
                       @Override
                       public void actionPerformed(ActionEvent event)
                       {
                           frame.dispose();
                           master.openRegistrationPopup();
                       }
                    });
                
		panel.add(registerButton);
	}
    
}
