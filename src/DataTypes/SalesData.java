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
public class SalesData implements Serializable {
    public int Sale_ID;
    public int Seller_ID;
    public int Product_ID;
    public int Amount;
    public double Price;
    public double InvoicePrice;
}
