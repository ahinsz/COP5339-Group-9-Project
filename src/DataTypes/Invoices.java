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
public class Invoices implements Serializable{
    public int InvoiceID;
    public int CustomerId;
    public ArrayList<CartItem> Products;
    public int Amount;
    public double total;
    public Date Date_of_Purchase;
}
