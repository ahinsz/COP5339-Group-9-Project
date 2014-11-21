/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import PopUps.*;
import DataTypes.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

/**
 *
 * @author Andrew
 */
public class MasterClass {
    //Containers for the datatypes
    private ArrayList<User> userList;
    private ArrayList<Inventory> productList;
    private ArrayList<Invoices> invoiceList;
    private ArrayList<SalesData> salesList;
    private User currentUser;
    private ArrayList<Inventory> sellerInventory;
    private Login loginPopup;
    private NewProduct newProductPopup;
    private Register registerPopup;
    private SellerList sellerPopup;
    private ProductList productPopup;
    
    public MasterClass() throws IOException, ClassNotFoundException{
        
        //initialize the popups
        loginPopup = new Login();
        newProductPopup = new NewProduct();
        registerPopup = new Register();
        sellerPopup = new SellerList();
        productPopup = new ProductList();

        //initialize the lists
        userList = new ArrayList();
        productList = new ArrayList();
        invoiceList = new ArrayList();
        salesList = new ArrayList();
        
        
        /*
        Here we load the data from the their respective dat files
        */
        
        //loads all users
        ObjectInputStream in = new ObjectInputStream(
            new FileInputStream("users.dat"));
        userList = (ArrayList<User>)in.readObject();
        in.close();
        
        //load all products
        in = new ObjectInputStream(
            new FileInputStream("products.dat"));
        productList = (ArrayList<Inventory>)in.readObject();
        in.close();
        
    }
    
    public void updateInventory() throws IOException{
        try{   
            ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream("products.dat"));
            out.writeObject(productList);
            out.close();
        } catch(IOException e){
            throw e;
        }
        
    }
    
    public void updateUsers() throws IOException{
        try{   
            ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream("users.dat"));
            out.writeObject(userList);
            out.close();
        } catch(IOException e){
            throw e;
        }
        
    }
    
    public void createProduct(Inventory item) throws IOException{
        item.product_ID = productList.size() + 1;
        item.SellerID = currentUser.userId;
        productList.add(item);
        sellerInventory.add(item);
        sellerPopup.updateList(item);
        
        this.updateInventory();
    }
    
    public void updateProduct(Inventory item) throws IOException{
        
        
        this.updateInventory();
    }
    
    public void createUser(User user) throws IOException{
        user.userId = userList.size() + 1;
        user.CreateDate = new Date();
        userList.add(user);
        
        this.updateUsers();
    }
    
    public boolean LoginUser(String user, String pass) throws IOException{
        
        for(int i = 0; i < userList.size(); i++){
            if(userList.get(i).username.equals(user))
                if(userList.get(i).password.equals(pass)){
                    userList.get(i).LastLogin = new Date();
                    this.updateUsers();
                    currentUser = userList.get(i);
                    if(currentUser.isSeller)
                    {
                        this.getSellerList();
                    }
                    return true;
                }else
                    return false;
        }
        
        return false;
    }
    
    public void openLoginPopup(){
        loginPopup.openLogin(this);
    }
    
    public void openRegistrationPopup(){
        registerPopup.openPopup(this);
    }
    public void openProductList(){
        productPopup.openPopup(this);
    }
    
    public void openSellerList(){
        sellerPopup.openPopup(this, sellerInventory);
    }
    
    public void openNewProduct(){
        NewProduct product = new NewProduct(this);
    }
    
    public boolean UserExists(String user){
        for(int i = 0; i < userList.size(); i++){
            if(userList.get(i).username.equals(user))
                return true;
        }
        
        return false;
    }
    
    public boolean EmailExists(String email){
        for(int i = 0; i < userList.size(); i++){
            if(userList.get(i).email.equals(email))
                return true;
        }
        
        return false;
    }
    
    public boolean isSeller(){
        return currentUser.isSeller;
    }
    
    private void getSellerList(){
        sellerInventory = new ArrayList();
        for (Inventory productList1 : productList) {
            if (productList1.SellerID == currentUser.userId) {
                sellerInventory.add(productList1);
            }
        }
    }
    
}
