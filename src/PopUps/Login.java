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
 * @authors Andrew Hinsz and Geoffrey Laleau
 */
public class Login {

    public Login() {

    }

    public void openLogin(MasterClass master) {
        initComponents();

        this.master = master;
        frame.add(panel);
        frame.setVisible(true);
    }

    private void initComponents() {

        frame = new JFrame("OpenBox Login");
        frame.setSize(300, 175);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        userLabel = new JLabel("User");
        userText = new JTextField(20);
        passwordLabel = new JLabel("Password");
        passwordText = new JPasswordField(20);
        loginButton = new JButton("login");
        registerButton = new JButton("register");
        authorizeLabel = new JLabel("Check Credentials");
        authorizeLabel.setVisible(false);

        configComponents();
        actionlisteners();
        addCompsToPanel();
    }

    private static void configComponents() {

        userLabel.setBounds(10, 10, 80, 25);
        userText.setBounds(100, 10, 160, 25);
        passwordLabel.setBounds(10, 40, 80, 25);
        passwordText.setBounds(100, 40, 160, 25);
        loginButton.setBounds(10, 80, 80, 25);
        registerButton.setBounds(180, 80, 80, 25);
        authorizeLabel.setBounds(10, 110, 200, 20);           
    }

    private static void addCompsToPanel() {

        panel.setLayout(null);

        panel.add(userLabel);
        panel.add(userText);
        panel.add(passwordLabel);
        panel.add(passwordText);
        panel.add(authorizeLabel);
        panel.add(loginButton);
        panel.add(registerButton);
    }
    
    private static void actionlisteners(){
        
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {

                authorizeLabel.setText("Authorizing...");

                authorizeLabel.setVisible(true);
                try {
                    if (master.LoginUser(userText.getText(), new String(
                            passwordText.getPassword()))) {
                        frame.dispose();
                        if (master.isSeller()) {
                            master.openSellerList();
                        } else {
                            master.openProductList();
                        }
                    } 
                    else if(userText.getText().isEmpty() || passwordText.getText().isEmpty()){
                        authorizeLabel.setText("");
                        authorizeLabel.setText("Both fields must be filled out");
                    }
                    else {
                        authorizeLabel.setText("");
                        authorizeLabel.setText("Incorrect Username or Password");
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE,
                            null, ex);
                }
            }

        });

        passwordText.addKeyListener(new KeyListener() {

            @Override
            public void keyReleased(KeyEvent arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void keyPressed(KeyEvent arg0) {

                if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        if (master.LoginUser(userText.getText(), new String(
                                passwordText.getPassword()))) {
                            frame.dispose();
                            if (master.isSeller()) {
                                master.openSellerList();
                            } else {
                                master.openProductList();
                            }
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(Login.class.getName()).log(
                                Level.SEVERE, null, ex);
                    }
                }
            }

            @Override
            public void keyTyped(KeyEvent arg0) {
                // TODO Auto-generated method stub

            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                frame.dispose();
                master.openRegistrationPopup();
            }
        });
    }

    private static MasterClass master;
    private static JFrame frame;
    private static JPanel panel;
    private static JLabel userLabel;
    private static JTextField userText;
    private static JLabel passwordLabel;
    private static JPasswordField passwordText;
    private static JButton loginButton;
    private static JButton registerButton;
    private static JLabel authorizeLabel;
}
