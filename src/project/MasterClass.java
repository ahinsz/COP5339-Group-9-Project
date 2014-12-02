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
    private ArrayList<Inventory> sellerInventory;
    
    //Current user and cart
    private User currentUser;
    private Cart currentCart;
    
    //Creates the variables to hold the popup classes
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
    private SellerRecords salesRecordPopup;
    private InvoicesPopup invoicePopup;
    
    /**
     * Default Constructor that initializes the lists, popups, and the cart
     * @throws IOException There is a problem while reading from the dat files
     * @throws ClassNotFoundException The class type is not known at time of compile
     */
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
        
        //loads all invoices
        f = new File("invoices.dat");
        if(f.exists() && !f.isDirectory()){
            ObjectInputStream in = new ObjectInputStream(
                new FileInputStream("invoices.dat"));
            invoiceList = (ArrayList<Invoices>)in.readObject();
            in.close();
        }
        
    }
    
    /**
     * addToCart handles adding items to the shopping cart. It takes the id of the product and amount given then adds to the currentCart
     * @param amount The amount to be added to the shopping cart
     * @param id The id of the product
     */
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
    
    /**
     * Creates an invoice for the customer when the customer finishes checkout and stores it in the dat file
     */
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
    
    /**
     * Edit the amount of an item in the cart by using the product id
     * @param amount The new amount of the product
     * @param id 
     * @return Returns true if change is completed, false if the amount given is more than its quantity
     */
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
    
    /**
     * It recalculates the total price of the cart
     */
    private void recalculateCartTotal(){
        currentCart.total = 0;
        for(CartItem item: currentCart.itemList){
            currentCart.total += item.amount * item.Sell_Price;
        }
    }
    
    /**
     * Checks to see if product is Cart and returns the position in the productList
     * @param id The id of the product
     * @return Returns the position of the product
     */
    private int existsInCart(int id){
        for(int i = 0; i < currentCart.itemList.size(); i++){
            if(currentCart.itemList.get(i).productId == id)
                return i;
        }
        return -1;
    }
    
    /**
     * Updates the product dat file
     * @throws IOException If unable to write to file an exception will appear
     */
    private void updateInventory() throws IOException{
        try{   
            ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream("products.dat"));
            out.writeObject(productList);
            out.close();
        } catch(IOException e){
            throw e;
        }
        
    }
    
    /**
     * Updates the user dat file
     * @throws IOException If unable to write to file an exception will appear
     */
    private void updateUsers() throws IOException{
        try{   
            ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream("users.dat"));
            out.writeObject(userList);
            out.close();
        } catch(IOException e){
            throw e;
        }
        
    }
    
    /**
     * Updates the Invoice dat file
     * @throws IOException If unable to write to file an exception will appear
     */
    private void updateInvoices() throws IOException{
        try{   
            ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream("invoices.dat"));
            out.writeObject(invoiceList);
            out.close();
        } catch(IOException e){
            throw e;
        }
        
    }
    
    /**
     * Creates a new product and stores it in inventory
     * @param item The new item being added
     * @throws IOException If an error occurs when updating the Inventory
     */
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
    
    /**
     * Edits a  product and stores it in inventory
     * @param item The item being edited
     * @throws IOException If an error occurs when updating the Inventory
     */
    public void updateProduct(Inventory item) throws IOException{
        int local = findProductinList(item.product_ID);
        if(local >= 0)
            productList.set(local, item);
        
        this.updateInventory();
        this.getSellerList();
        sellerPopup.refreshList(sellerInventory);
    }
    
    /**
     * Removes product from inventory
     * @param id The id of the product
     * @throws IOException If an error occurs when updating the Inventory
     */
    public void removeProduct(int id) throws IOException{
        int local = findProductinList(id);
        if(local >= 0)
            productList.remove(local);
        
        this.updateInventory();
        this.getSellerList();
        sellerPopup.refreshList(sellerInventory);
    }
    
    /**
     * Remove a product from cart
     * @param id The id of the product
     */
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
    
    /**
     * Creates a new user
     * @param user The user information
     * @throws IOException If unable to update user dat file
     */
    public void createUser(User user) throws IOException{
        if(userList.size() > 0)
            user.userId = userList.get(userList.size() - 1).userId + 1;
        else
            user.userId = 1;
        
        user.CreateDate = new Date();
        userList.add(user);
        
        this.updateUsers();
    }
    
    /**
     * Logs the user in if credentials are valid and creates a new shopping cart
     * @param user Username
     * @param pass Password
     * @return Returns true if credentials are valid and false when not
     * @throws IOException 
     */
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
    
    /**
     * Open cart popup
     */
    public void openCartPopup(){
        cartPopup.openPopup(this, currentCart);
    }
    
    /**
     * Open checkout popup
     */
    public void openCheckOutPopup(){
        CheckOutPopup.openPopup(this, currentCart);
    }
    
    /**
     * Open edit edit cart popup
     */
    public void openEditCartPopup(CartItem item){
        cartEditPopup.openEditCartPopup(this, item);
    }
    
    /**
     * Open edit product popup
     */
    public void openEditProduct(int id){
        Inventory item = productList.get(findProductinList(id));
        editProductPopup.openPopup(this, item);
    }
    
    /**
     * Open invoice popup
     */
    public void openInvoicePopup(){
        ArrayList<Invoices> userInvoices = new ArrayList();
        for(Invoices in: invoiceList){
            if(in.CustomerId == currentUser.userId)
                userInvoices.add(in);
        }
        invoicePopup.openPopup(userInvoices, this);
    }
    
    /**
     * Open login popup
     */
    public void openLoginPopup(){
        loginPopup.openLogin(this);
    }
    
    /**
     * Open more details popup
     */
    public void openMoreDetailsPopup(Inventory item){
        detailPopup.openPopup(this, item);
    }
    
    /**
     * Open new product popup
     */
    public void openNewProduct(){
        newProductPopup.openNewProduct(this);
    }
    
    /**
     * Open productList popup
     */
    public void openProductList(){
        productPopup.openPopup(this, productList, currentCart);
    }
    
    /**
     * Open registration popup
     */
    public void openRegistrationPopup(){
        registerPopup.openPopup(this);
    }
    
    /**
     * Open sellerList popup
     */
    public void openSellerList(){
        sellerPopup.openPopup(this, sellerInventory);
    }
    
    /**
     * Open seller records popup
     */
    public void openSellerRecords(){
        salesRecordPopup.openPopup(sellerInventory);
    }
    
    /**
     * Check to see if existing username exists
     * @param user The username being checked
     * @return Return true if username is in use and false if it exists
     */
    public boolean UserExists(String user){
        for(int i = 0; i < userList.size(); i++){
            if(userList.get(i).username.equals(user))
                return true;
        }
        
        return false;
    }
    
    /**
     * Check to see if existing email is in use
     * @param email The email being checked
     * @return Return true if email is in use and false if it is in use
     */
    public boolean EmailExists(String email){
        for(int i = 0; i < userList.size(); i++){
            if(userList.get(i).email.equals(email))
                return true;
        }
        
        return false;
    }
    
    /**
     * Returns the isSeller flag in user data
     * @return returns true if user is seller and false when not
     */
    public boolean isSeller(){
        return currentUser.isSeller;
    }
    
    /**
     * Get the product list for sellers
     */
    private void getSellerList(){
        sellerInventory = new ArrayList();
        for (Inventory productList1 : productList) {
            if (productList1.SellerID == currentUser.userId) {
                sellerInventory.add(productList1);
            }
        }
    }
    
    /**
     * Get the product from inventory with an id
     * @param id The id of the product
     * @return The product information
     */
    public Inventory getProduct(int id){
        for (Inventory inv : productList) {
            if (inv.product_ID == id) {
                return inv;
            }
        }
        
        return null;
    }
    
    /**
     * Get the item in the cart
     * @param id The id of the product stored in the cart
     * @return Returns the cart information if it exists and null if nothing is there
     */
    public CartItem getCartProduct(int id){
        for (CartItem c : currentCart.itemList) {
            if (c.productId == id) {
                return c;
            }
        }
        
        return null;
    }
    
    /**
     * Get seller information
     * @param id THe id of the seller
     * @return Returns the user information if it exists and null if nothing is there
     */
    public User getSeller(int id){
        for (User user : userList) {
            if (user.userId == id) {
                return user;
            }
        }
        
        return null;
    }
    
    /**
     * Find the location of the product in the productList
     * @param id The id of the product
     * @return If the product exists in the list return position else it returns -1
     */
    private int findProductinList(int id){
        for(int i = 0; i < productList.size(); i++){
            if(productList.get(i).product_ID == id)
                return i;
        }
        
        return -1;
    }
    
    /**
     * Get amount of products currently in cart
     * @return The total count of products in cart
     */
    public int getCurrentCartTotal(){
        int total = 0;
        for(CartItem item: currentCart.itemList)
            total += item.amount;
        
        return total;
    }
    
    /**
     * Processes the information for checkout, edits the product dat file, creates a new invoice,
     * empties the cart, and refreshes the productList to show changes. 
     */
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
