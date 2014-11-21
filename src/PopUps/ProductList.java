/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PopUps;

import javax.swing.JFrame;
import javax.swing.JPanel;
import project.MasterClass;

/**
 *
 * @author Andrew
 */
public class ProductList {
    
    public ProductList(){
        
    }
    
    public void openPopup(MasterClass master){
        JFrame frame = new JFrame("Product List");
	frame.setSize(400, 450);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	JPanel panel = new JPanel();
	frame.add(panel);

	frame.setVisible(true);
    }
    
}
