/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataTypes;

import java.io.Serializable;

/**
 *
 * @author Andrew
 */
public class CartItem implements Serializable{
    public int productId;
    public int SellerID;
    public int amount;
    public String Name;
    public double Sell_Price;
}
