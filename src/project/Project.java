/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The main that starts the project and launches the login popup.
 * @authors Andrew Hinsz and Geoffrey Laleau
 */
public class Project {

    /**
     * @param args the command line arguments
     * @throws java.lang.ClassNotFoundException THrows exception if missing files
     */
    public static void main(String[] args) throws ClassNotFoundException {
        try {
            MasterClass master = new MasterClass();
            master.openLoginPopup();
            
        } catch (IOException ex) {
            Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
