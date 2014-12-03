/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataTypes;

import java.io.Serializable;

/**
 *Inventory is the class that holds the product information.
 * @authors Andrew Hinsz and Geoffrey Laleau
 */
public class Inventory implements Serializable {
    public int product_ID;
    public int SellerID;
    public String Name;
    public String Description;
    public double Sell_Price;
    public double Invoice_Price;
    public int Quantity;
    public double ItemsSold;
}
