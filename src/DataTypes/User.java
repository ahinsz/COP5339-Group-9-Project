/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataTypes;

import java.io.Serializable;
import java.util.*;

/**
 *
 * @author Andrew
 */
public class User implements Serializable{
    public int userId;
    public String FirstName;
    public String LastName;
    public String username;
    public String password;
    public boolean isSeller;
    public String email;
    public String Address1;
    public String Address2;
    public String City;
    public String State;
    public String Zip;
    public Date CreateDate;
    public Date LastLogin;
}
