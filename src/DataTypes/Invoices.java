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
    public int SaleID;
    public int CustomerId;
    public int ProductId;
    public int Amount;
    public double Price;
    public Date Date_of_Purchase;
    public int CartID;
}
