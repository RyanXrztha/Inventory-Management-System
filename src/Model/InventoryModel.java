/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
/**
 *
 * @author aryan
 */
public class InventoryModel {
    private static InventoryModel a = null;
    
    final int size = 10;
    int top = -1;
    String[][] stack = new String[10][7];
    
    final int fullSize = 10;
    int deletedTop = -1;
    String[][] deletedStack = new String[10][7];
    
    ArrayList<String[]> inventoryList = new ArrayList<>();
    
    private Queue<String[]> recentItemsQueue = new LinkedList<>();
    private final int MAX_RECENT_ITEMS = 5;
    
    
    private String itemID;
    private String itemName;
    private int quantity;
    private double costPrice;
    private double price;
    private String category;
    private double profitLoss;
    
    
    private InventoryModel() {
    }
    
   
    public static InventoryModel geta() {
        if (a == null) {
            a = new InventoryModel();
        }
        return a;
    }
    

    public Queue<String[]> getRecentItemsQueue() {
        return recentItemsQueue;
    }
    
    public void addToRecentQueue(String[] item) {
        // If queue is full, remove oldest item (FIFO)
        if (recentItemsQueue.size() >= MAX_RECENT_ITEMS) {
            recentItemsQueue.poll();  // Remove first (oldest)
        }
        recentItemsQueue.offer(item);  // Add to end (newest)
    }
    

    public InventoryModel(String itemID, String itemName, int quantity, double costPrice, double price, String category) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.quantity = quantity;
        this.costPrice = costPrice;
        this.price = price;
        this.category = category;
        this.profitLoss = price - costPrice;
    }

    public String getItemID() {
        return itemID;
    }
    
    public String getItemName() {
        return itemName;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public double getCostPrice() {
        return costPrice;
    }
    
    public double getPrice() {
        return price;
    }   
    
    public String getCategory() {
        return category;
    }
    
    public double getProfitLoss() {
        return profitLoss;
    }
    
    public int getTop() {
        return top;
    }
    
    public int getDeletedTop() {
        return deletedTop;
    }
    
    public String[][] getStack() {
        return stack;
    }
    
    public String[][] getDeletedStack() {
        return deletedStack;
    }
    
    public ArrayList<String[]> getInventoryList() {
        return inventoryList;
    }
    
    public int getSize() {
        return size;
    }
    
    public int getfullSize() {
        return fullSize;
    }
    
    public void setTop(int top) {
        this.top = top;
    }
    
    public void setDeletedTop(int deletedTop) {
        this.deletedTop = deletedTop;
    }
    
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
        this.profitLoss = this.price - costPrice;
    }
    
    public void setPrice(double price) {
        this.price = price;
        this.profitLoss = price - this.costPrice;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
}