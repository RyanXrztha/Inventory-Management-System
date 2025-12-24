/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;
import java.util.ArrayList;
/**
 *
 * @author aryan
 */
public class InventoryModel {
    private static InventoryModel a = null;
    
    final int size = 10;
    int front = -1;
    int rear = -1;
    String[][] queue = new String[10][7];
    final int fullSize = 10;
    int top = -1;
    String[][] stack = new String[10][7];
    ArrayList<String[]> inventoryList = new ArrayList<>();
    
    
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

    

    public InventoryModel(String itemID, String itemName, int quantity,
                          double costPrice, double price, String category) {
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
    
    public int getQuantity() { return quantity; }
    public double getCostPrice() { return costPrice; }
    public double getPrice() { return price; }
    public String getCategory() { return category; }
    public double getProfitLoss() { return profitLoss; }

    
    public void setItemName(String itemName) { this.itemName = itemName; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
        this.profitLoss = this.price - costPrice;
    }
    public void setPrice(double price) {
        this.price = price;
        this.profitLoss = price - this.costPrice;
    }
    public void setCategory(String category) { this.category = category; }
    
    
    
    public int getFront() {
        return front;
    }
    
    public void setFront(int front) {
        this.front = front;
    }
    
    public int getRear() {
        return rear;
    }
    
    public void setRear(int rear) {
        this.rear = rear;
    }
    
    public int getTop() {
        return top;
    }
    
    public void setTop(int top) {
        this.top = top;
    }
    
    public String[][] getQueue() {
        return queue;
    }
    
    public String[][] getStack() {
        return stack;
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
}
