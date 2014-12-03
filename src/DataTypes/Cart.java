/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataTypes;

import java.io.Serializable;
import java.util.ArrayList;
import DataTypes.*;

/**
 *The cart holds all the products that customer wants to buy.
 * @authors Andrew Hinsz and Geoffrey Laleau
 */
public class Cart implements Serializable {
    public Cart(){
        itemList = new ArrayList<CartItem>();
        total = 0;
    }
    
    public int CartID;
    public int userID;
    public ArrayList<CartItem> itemList;
    public double total;
}
