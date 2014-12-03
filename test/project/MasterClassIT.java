/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import DataTypes.*;
import DataTypes.CartItem;
import DataTypes.Inventory;
import DataTypes.User;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @authors Andrew Hinsz and Geoffrey Laleau
 */
public class MasterClassIT {
    
    public MasterClass instance;
    public Cart testCart;
    public ArrayList<Inventory> invTest;
    public ArrayList<Inventory> invBackup;
    public ArrayList<User> userBackup;
    public ArrayList<Invoices> invoiceBackup;
    
    public MasterClassIT() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws IOException, ClassNotFoundException {
        instance = new MasterClass(); 
        
        File f = new File("products.dat");
        if(f.exists() && !f.isDirectory()){
            ObjectInputStream in = new ObjectInputStream(
                new FileInputStream("products.dat"));
            invBackup = (ArrayList<Inventory>)in.readObject();
            in.close();
        }
        
        f = new File("users.dat");
        if(f.exists() && !f.isDirectory()){
            ObjectInputStream in = new ObjectInputStream(
                new FileInputStream("users.dat"));
            userBackup = (ArrayList<User>)in.readObject();
            in.close();
        }
        
        f = new File("invoices.dat");
        if(f.exists() && !f.isDirectory()){
            ObjectInputStream in = new ObjectInputStream(
                new FileInputStream("invoices.dat"));
            invoiceBackup = (ArrayList<Invoices>)in.readObject();
            in.close();
        }
    }
    
    @After
    public void tearDown() throws IOException {
        //Restore user database
        try{   
            ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream("users.dat"));
            out.writeObject(userBackup);
            out.close();
        } catch(IOException e){
            throw e;
        }
        
        //Restore invoice database
        try{   
            ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream("invoices.dat"));
            out.writeObject(invoiceBackup);
            out.close();
        } catch(IOException e){
            throw e;
        }
        
        //Restore product database
        try{   
            ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream("products.dat"));
            out.writeObject(invBackup);
            out.close();
        } catch(IOException e){
            throw e;
        }
    }
    
    /**
     * Test of addToCart method, of class MasterClass.
     */
    @Test
    public void testAddToCart() {
        System.out.println("addToCart");
        int amount = 1;
        int id = 1;
        
        instance.openProductList();
        instance.addToCart(amount, id);
        
        if(instance.getCurrentCartTotal() != 1)
            fail("Add to cart is not working.");
    }

    /**
     * Test of createInvoice method, of class MasterClass.
     */
    @Test
    public void testCreateInvoice() throws IOException, ClassNotFoundException {
        System.out.println("createInvoice");
        
        instance.LoginUser("ahinsz", "pass");
        instance.openProductList();
        
        instance.addToCart(1, 1);
        
        instance.CheckOut();
        
        ObjectInputStream in = new ObjectInputStream(
            new FileInputStream("invoices.dat"));
        ArrayList<Invoices> newList = (ArrayList<Invoices>)in.readObject();
        in.close();
        
        if(newList.size() <= invoiceBackup.size())
            fail("Create invoice failed");
        
        
    }

    /**
     * Test of editCart method, of class MasterClass.
     */
    @Test
    public void testEditCart() {
        instance.openProductList();
        
        instance.addToCart(1, 1);
        
        int origAmount = instance.getCurrentCartTotal();
        
        instance.openCartPopup();
        
        if(instance.editCart(2, 1)){
            if(origAmount >= instance.getCurrentCartTotal())
                fail("Edit cart function failed");
        }else
            fail("Edit cart Function failed");
    }

    /**
     * Test of createProduct method, of class MasterClass.
     */
    @Test
    public void testCreateProduct() throws Exception {
        Inventory newItem = new Inventory();
        newItem.Description = "";
        newItem.Invoice_Price = 0;
        newItem.ItemsSold = 0;
        newItem.Name = "test create product";
        newItem.Quantity = 5;
        newItem.Sell_Price = 2;
        newItem.SellerID = 1;
        newItem.product_ID = invBackup.get(invBackup.size() - 1).product_ID + 1;
        
        instance.LoginUser("admin", "pass");
        instance.openSellerList();
        
        instance.createProduct(newItem);
        
        Inventory testItem = instance.getProduct(newItem.product_ID);
        
        if(!testItem.equals(newItem))
            fail("Create Product failed.");
    }

    /**
     * Test of updateProduct method, of class MasterClass.
     */
    @Test
    public void testUpdateProduct() throws Exception {
        Inventory item = instance.getProduct(1);
        int oldAmount = item.Quantity;
        
        item.Quantity = 100;
        
        instance.LoginUser("admin", "pass");
        instance.openSellerList();
        instance.updateProduct(item);
        
        Inventory newItem = instance.getProduct(item.product_ID);
        
        if(newItem.Quantity == oldAmount)
            fail("UpdateInventory failed.");
    }

    /**
     * Test of removeProduct method, of class MasterClass.
     */
    @Test
    public void testRemoveProduct() throws Exception {
        Inventory newItem = new Inventory();
        newItem.Description = "";
        newItem.Invoice_Price = 0;
        newItem.ItemsSold = 0;
        newItem.Name = "test create product";
        newItem.Quantity = 5;
        newItem.Sell_Price = 2;
        newItem.SellerID = 1;
        newItem.product_ID = invBackup.get(invBackup.size() - 1).product_ID + 1;
        
        instance.LoginUser("admin", "pass");
        instance.openSellerList();
        
        instance.createProduct(newItem);
        
        Inventory testItem = instance.getProduct(newItem.product_ID);
        
        if(!testItem.equals(newItem))
            fail("Test 1: Failed to create product to remove.");
        
        instance.removeProduct(newItem.product_ID);
        
        testItem = instance.getProduct(newItem.product_ID);
        
        if(testItem != null)
            fail("Test 2: Failed to remove product.");
    }

    /**
     * Test of removeProductFromCart method, of class MasterClass.
     */
    @Test
    public void testRemoveProductFromCart() throws IOException {
        System.out.println("createInvoice");
        
        instance.LoginUser("ahinsz", "pass");
        instance.openProductList();
        instance.openCartPopup();
        
        instance.addToCart(1, 1);
        
        int oldTotal = instance.getCurrentCartTotal();
        
        instance.removeProductFromCart(1);
        
        int newTotal = instance.getCurrentCartTotal();
        
        if(oldTotal == newTotal)
            fail("Failed to remove product from cart.");
    }

    /**
     * Test of createUser method, of class MasterClass.
     */
    @Test
    public void testCreateUser() throws Exception {
        User newUser = new User();
        newUser.Address1 = "";
        newUser.Address2 = "";
        newUser.City = "";
        newUser.CreateDate = new Date();
        newUser.FirstName = "test";
        newUser.LastLogin = new Date();
        newUser.LastName = "admin";
        newUser.State = "FL";
        newUser.Zip = "";
        newUser.email = "doesnotexist@test.com";
        newUser.isSeller = false;
        newUser.password = "newpass";
        newUser.userId = 0;
        newUser.username = "createUserAccountTest";
        
        boolean userNotExistResult = instance.UserExists(newUser.username);
        
        instance.createUser(newUser);
        
        boolean userExistsResult = instance.UserExists(newUser.username);
        
        if(userNotExistResult == userExistsResult)
            fail("Create User function fails.");
        
        
    }

    /**
     * Test of LoginUser method, of class MasterClass.
     * @throws java.lang.Exception
     */
    @Test
    public void testLoginUser() throws Exception {
        System.out.println("LoginUser");
        boolean expResult = true;
        boolean result = instance.LoginUser("admin", "pass");
        assertEquals("Test 2: Failed to log in. Login Function fails.", expResult, result);
        
        expResult = false;
        result = instance.LoginUser("admin", "");
        assertEquals("Test 2: Logged in with wrong password. Login Function fails.", expResult, result);
    }

    /**
     * Test of UserExists method, of class MasterClass.
     */
    @Test
    public void testUserExists() {
        System.out.println("UserExists");
        
        boolean expResult = true;
        boolean result = instance.UserExists("admin");
        assertEquals("Test 1: User should exist in database", expResult, result);
        
        expResult = false;
        result = instance.UserExists("adminTest");
        assertEquals("Test 2: User should not exist in database", expResult, result);
    }

    /**
     * Test of EmailExists method, of class MasterClass.
     */
    @Test
    public void testEmailExists() {
        System.out.println("EmailExists");
        String email = "admin@admin.com";
        
        boolean expResult = true;
        boolean result = instance.EmailExists(email);
        assertEquals("Test 1: Email should exist in database",expResult, result);
        
        expResult = false;
        result = instance.EmailExists("newAccount@test.com");
        assertEquals("Test 2: Email should not exist in database", expResult, result);
    }

    /**
     * Test of isSeller method, of class MasterClass.
     */
    @Test
    public void testIsSeller() throws IOException {
        System.out.println("isSeller");
        
        instance.LoginUser("admin", "pass");
        
        boolean expResult = true;
        boolean result = instance.isSeller();
        assertEquals("Admin account is supposed to be a seller, but it is not.", expResult, result);
    }

    /**
     * Test of getProduct method, of class MasterClass.
     */
    @Test
    public void testGetProduct() {
        System.out.println("getProduct");
        int id = 1;
        
        Inventory result = instance.getProduct(id);
        if(result == null)
            fail("Get product function failed.");
    }

    /**
     * Test of getCartProduct method, of class MasterClass.
     * @throws java.io.IOException
     */
    @Test
    public void testGetCartProduct() throws IOException {
        instance.LoginUser("ahinsz", "pass");
        instance.openProductList();
        
        int total = instance.getCurrentCartTotal();
        
        if(total != 0)
            fail("GetCurrentCart function failed.");
        
        instance.addToCart(1, 1);
        CartItem item = instance.getCartProduct(1);
        if(item == null)
            fail("GetCartProduct function failed to get product from cart.");
    }

    /**
     * Test of getSeller method, of class MasterClass.
     */
    @Test
    public void testGetSeller() {
        System.out.println("getSeller");
        int id = 1;
        
        User result = instance.getSeller(id);
        if(!result.username.equals("admin"))
            fail("Admin seller does not exist.");
    }

    /**
     * Test of getCurrentCartTotal method, of class MasterClass.
     * @throws java.io.IOException
     */
    @Test
    public void testGetCurrentCartTotal() throws IOException {
        
        instance.LoginUser("ahinsz", "pass");
        instance.openProductList();
        
        int total = instance.getCurrentCartTotal();
        
        if(total != 0)
            fail("GetCurrentCart function failed.");
        
        instance.addToCart(1, 1);
        
        total = instance.getCurrentCartTotal();
        
        if(total != 1)
            fail("GetCurrentCart function failed.");
    }

    /**
     * Test of CheckOut method, of class MasterClass.
     * @throws java.io.IOException
     * @throws java.lang.ClassNotFoundException
     */
    @Test
    public void testCheckOut() throws IOException, ClassNotFoundException {
        System.out.println("createInvoice");
        
        instance.openProductList();
        
        instance.addToCart(1, 1);
        
        instance.LoginUser("ahinsz", "pass");
        instance.openProductList();
        instance.CheckOut();
        
        ObjectInputStream in = new ObjectInputStream(
            new FileInputStream("invoices.dat"));
        ArrayList<Invoices> newList = (ArrayList<Invoices>)in.readObject();
        in.close();
        
        if(newList.size() <= invoiceBackup.size())
            fail("Create invoice failed");
    }

    /**
     * Test of openCartPopup method, of class MasterClass.
     */
    @Test
    public void testOpenCartPopup() {
        instance.openCartPopup();
    }

    /**
     * Test of openCheckOutPopup method, of class MasterClass.
     */
    @Test
    public void testOpenCheckOutPopup() {
        instance.openCheckOutPopup();
    }

    /**
     * Test of openEditCartPopup method, of class MasterClass.
     */
    @Test
    public void testOpenEditCartPopup() {
        instance.openEditCartPopup(new CartItem());
    }

    /**
     * Test of openEditProduct method, of class MasterClass.
     */
    @Test
    public void testOpenEditProduct() {
        instance.openEditProduct(1);
    }

    /**
     * Test of openInvoicePopup method, of class MasterClass.
     */
    @Test
    public void testOpenInvoicePopup() throws IOException {
        instance.LoginUser("admin", "pass");
        instance.openInvoicePopup();
    }

    /**
     * Test of openLoginPopup method, of class MasterClass.
     */
    @Test
    public void testOpenLoginPopup() {
        instance.openLoginPopup();
    }

    /**
     * Test of openMoreDetailsPopup method, of class MasterClass.
     */
    @Test
    public void testOpenMoreDetailsPopup() {
        Inventory test = new Inventory();
        test.Description = "";
        test.Invoice_Price = 0;
        test.ItemsSold = 0;
        test.Name = "test";
        test.Quantity = 0;
        test.Sell_Price = 0;
        test.SellerID = 1;
        test.product_ID = 1;
        
        instance.openMoreDetailsPopup(test);
    }

    /**
     * Test of openNewProduct method, of class MasterClass.
     */
    @Test
    public void testOpenNewProduct() {
        instance.openNewProduct();
    }

    /**
     * Test of openProductList method, of class MasterClass.
     */
    @Test
    public void testOpenProductList() {
        instance.openProductList();
    }

    /**
     * Test of openRegistrationPopup method, of class MasterClass.
     */
    @Test
    public void testOpenRegistrationPopup() {
        instance.openRegistrationPopup();
    }

    /**
     * Test of openSellerList method, of class MasterClass.
     */
    @Test
    public void testOpenSellerList() throws IOException {
        instance.LoginUser("admin", "pass");
        instance.openSellerList();
    }

    /**
     * Test of openSellerRecords method, of class MasterClass.
     */
    @Test
    public void testOpenSellerRecords() throws IOException {
        instance.LoginUser("admin", "pass");
        instance.openSellerRecords();
    }
    
}
