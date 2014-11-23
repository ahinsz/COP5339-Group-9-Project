/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import PopUps.*;
import DataTypes.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private EditProduct editProductPopup;
    private Register registerPopup;
    private SellerList sellerPopup;
    private ProductList productPopup;
    private CartPopUp cartPopup;
    private EditCartItem cartEditPopup;
    private MoreDetails detailPopup;
    private CheckOut CheckOutPopup; 
    private Cart currentCart;
    private SellerRecords salesRecordPopup;
    private InvoicesPopup invoicePopup;
    
    public MasterClass() throws IOException, ClassNotFoundException{
        
        //initialize the popups
        loginPopup = new Login();
        newProductPopup = new NewProduct();
        registerPopup = new Register();
        sellerPopup = new SellerList();
        productPopup = new ProductList();
        editProductPopup = new EditProduct();
        cartPopup = new CartPopUp();
        cartEditPopup = new EditCartItem();
        detailPopup = new MoreDetails();
        CheckOutPopup = new CheckOut();
        salesRecordPopup = new SellerRecords();
        invoicePopup = new InvoicesPopup();

        //initialize the lists
        userList = new ArrayList();
        productList = new ArrayList();
        invoiceList = new ArrayList();
        salesList = new ArrayList();
        
        //initialize the objects
        currentCart = new Cart();
        
        /*
        Here we load the data from the their respective dat files
        */
        
        //loads all users
        File f = new File("users.dat"); //to check if the file exists
        if(f.exists() && !f.isDirectory()){
            ObjectInputStream in = new ObjectInputStream(
                new FileInputStream("users.dat"));
            userList = (ArrayList<User>)in.readObject();
            in.close();
        }
        
        //load all products
        
        f = new File("products.dat");
        if(f.exists() && !f.isDirectory()){
            ObjectInputStream in = new ObjectInputStream(
                new FileInputStream("products.dat"));
            productList = (ArrayList<Inventory>)in.readObject();
            in.close();
        }
        
        f = new File("invoices.dat");
        if(f.exists() && !f.isDirectory()){
            ObjectInputStream in = new ObjectInputStream(
                new FileInputStream("invoices.dat"));
            invoiceList = (ArrayList<Invoices>)in.readObject();
            in.close();
        }
        
    }
    
    public void addToCart(int amount, int id){
        Inventory item = getProduct(id);
        
        if(amount > item.Quantity)
            return;
        
        int pos = existsInCart(id);
        
        if(pos >= 0){
            CartItem oldItem = currentCart.itemList.get(pos);
            if(oldItem.amount + amount > item.Quantity)
                return;
            
            oldItem.amount += amount;
            currentCart.itemList.set(pos, oldItem);
            recalculateCartTotal();
            productPopup.updateCart(currentCart, this);
        }else{
            CartItem newItem = new CartItem();
            newItem.Name = item.Name;
            newItem.Sell_Price = item.Sell_Price;
            newItem.SellerID = item.SellerID;
            newItem.amount = amount;
            newItem.productId = item.product_ID;
            
            currentCart.itemList.add(newItem);
            recalculateCartTotal();
            productPopup.updateCart(currentCart, this);
        }
    }
    
    public void createInvoice(){
        Invoices in = new Invoices();
        in.Amount = this.getCurrentCartTotal();
        in.CustomerId = currentUser.userId;
        in.Date_of_Purchase = new Date();
        in.total = currentCart.total;
        in.Products = currentCart.itemList;
        
        
        if(invoiceList.size() > 0)
            in.InvoiceID = invoiceList.get(invoiceList.size() - 1).InvoiceID + 1;
        else
            in.InvoiceID = 1;
        
        invoiceList.add(in);
        try {
            this.updateInvoices();
        } catch (IOException ex) {
            Logger.getLogger(MasterClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean editCart(int amount, int id){
        Inventory item = getProduct(id);
        
        if(amount <= 0){
            this.removeProductFromCart(id);
            return true;
        }
        
        if(amount > item.Quantity)
            return false;
        else{
            int pos = existsInCart(id);
            CartItem oldItem = currentCart.itemList.get(pos);
            
            oldItem.amount = amount;
            currentCart.itemList.set(pos, oldItem);
            recalculateCartTotal();
            productPopup.updateCart(currentCart, this);
            cartPopup.refreshList(currentCart, this);
            return true;
        }
    }
    
    private void recalculateCartTotal(){
        currentCart.total = 0;
        for(CartItem item: currentCart.itemList){
            currentCart.total += item.amount * item.Sell_Price;
        }
    }
    
    private int existsInCart(int id){
        for(int i = 0; i < currentCart.itemList.size(); i++){
            if(currentCart.itemList.get(i).productId == id)
                return i;
        }
        return -1;
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
    
    public void updateInvoices() throws IOException{
        try{   
            ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream("invoices.dat"));
            out.writeObject(invoiceList);
            out.close();
        } catch(IOException e){
            throw e;
        }
        
    }
    
    public void createProduct(Inventory item) throws IOException{
        if(productList.size() > 0)
            item.product_ID = productList.get(productList.size() - 1).product_ID + 1;
        else
            item.product_ID = 1;
        item.product_ID = productList.size() + 1;
        item.SellerID = currentUser.userId;
        productList.add(item);
        sellerInventory.add(item);
        sellerPopup.updateList(item);
        
        this.updateInventory();
    }
    
    public void updateProduct(Inventory item) throws IOException{
        int local = findProductinList(item.product_ID);
        if(local >= 0)
            productList.set(local, item);
        
        this.updateInventory();
        this.getSellerList();
        sellerPopup.refreshList(sellerInventory);
    }
    
    public void removeProduct(int id) throws IOException{
        int local = findProductinList(id);
        if(local >= 0)
            productList.remove(local);
        
        this.updateInventory();
        this.getSellerList();
        sellerPopup.refreshList(sellerInventory);
    }
    
    public void removeProductFromCart(int id){
        int pos = -1;
        for(int i = 0; i < currentCart.itemList.size(); i++){
            if(currentCart.itemList.get(i).productId == id)
                pos = i;
        }
        
        if(pos >= 0){
            currentCart.itemList.remove(pos);
            cartPopup.refreshList(currentCart, this);
            productPopup.updateCart(currentCart, this);
        }
    }
    
    public void createUser(User user) throws IOException{
        if(userList.size() > 0)
            user.userId = userList.get(userList.size() - 1).userId + 1;
        else
            user.userId = 1;
        
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
                    currentCart = new Cart();
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
    
    public void openCartPopup(){
        cartPopup.openPopup(this, currentCart);
    }
    
    public void openCheckOutPopup(){
        CheckOutPopup.openPopup(this, currentCart);
    }
    
    public void openEditCartPopup(CartItem item){
        cartEditPopup.openEditCartPopup(this, item);
    }
    
    public void openEditProduct(int id){
        Inventory item = productList.get(findProductinList(id));
        editProductPopup.openPopup(this, item);
    }
    
    public void openInvoicePopup(){
        ArrayList<Invoices> userInvoices = new ArrayList();
        for(Invoices in: invoiceList){
            if(in.CustomerId == currentUser.userId)
                userInvoices.add(in);
        }
        invoicePopup.openPopup(userInvoices, this);
    }
    
    public void openLoginPopup(){
        loginPopup.openLogin(this);
    }
    
    public void openMoreDetailsPopup(Inventory item){
        detailPopup.openPopup(this, item);
    }
    
    public void openNewProduct(){
        newProductPopup.openNewProduct(this);
    }
    
    public void openProductList(){
        productPopup.openPopup(this, productList, currentCart);
    }
    
    public void openRegistrationPopup(){
        registerPopup.openPopup(this);
    }
    
    public void openSellerList(){
        sellerPopup.openPopup(this, sellerInventory);
    }
    
    public void openSellerRecords(){
        salesRecordPopup.openPopup(sellerInventory);
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
    
    public Inventory getProduct(int id){
        for (Inventory inv : productList) {
            if (inv.product_ID == id) {
                return inv;
            }
        }
        
        return null;
    }
    
    public CartItem getCartProduct(int id){
        for (CartItem c : currentCart.itemList) {
            if (c.productId == id) {
                return c;
            }
        }
        
        return null;
    }
    
    public User getSeller(int id){
        for (User user : userList) {
            if (user.userId == id) {
                return user;
            }
        }
        
        return null;
    }
    
    private int findProductinList(int id){
        for(int i = 0; i < productList.size(); i++){
            if(productList.get(i).product_ID == id)
                return i;
        }
        
        return -1;
    }
    
    public int getCurrentCartTotal(){
        int total = 0;
        for(CartItem item: currentCart.itemList)
            total += item.amount;
        
        return total;
    }
    
    public void CheckOut(){
        for (CartItem item : currentCart.itemList) {
            for(int j = 0; j < productList.size(); j++){
                if(productList.get(j).product_ID == item.productId){
                    Inventory oldItem = productList.get(j);
                    oldItem.ItemsSold += item.amount;
                    oldItem.Quantity -= item.amount;
                    productList.set(j, oldItem);
                    try {
                        this.updateInventory();
                    } catch (IOException ex) {
                        Logger.getLogger(MasterClass.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                }
            }
        }
        
        //Here goes the implemuntating for User sales records
        
        this.createInvoice();
        currentCart = new Cart();
        productPopup.updateCart(currentCart, this);
        productPopup.refreshList(productList, this);
    }
    
}
