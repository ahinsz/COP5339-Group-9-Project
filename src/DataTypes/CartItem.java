/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataTypes;

import java.io.Serializable;

/**
 *CartItem is a apart of the Cart class. This holds all the data on which product the customer has added to cart.
 * @authors Andrew Hinsz and Geoffrey Laleau
 */
public class CartItem implements Serializable{
    public int productId;
    public int SellerID;
    public int amount;
    public String Name;
    public double Sell_Price;
}
