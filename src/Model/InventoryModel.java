/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;
import java.util.ArrayList;
import java.util.LinkedList;
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
    
    private LinkedList<String[]> categoryLinkedList = new LinkedList<>();
    
    private final int MAX_RECENT_ITEMS = 5;
    private String[][] recentItemsQueue = new String[MAX_RECENT_ITEMS][7];
    private int front = -1;
    private int rear = -1;
    
    
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
    

    public void addToRecentQueue(String[] item) {
        // If queue is full, dequeue (remove oldest)
        if ((rear == MAX_RECENT_ITEMS - 1 && front == 0) || (rear + 1 == front)) {
            // Queue is full, remove front element
            if (front == rear) {
                // Only one element
                front = -1;
                rear = -1;
            } else if (front == MAX_RECENT_ITEMS - 1) {
                front = 0; // Wrap around
            } else {
                front++;
            }
        }
        
        // Enqueue the new item
        if (front == -1) {
            // Queue is empty
            front = 0;
            rear = 0;
        } else if (rear == MAX_RECENT_ITEMS - 1) {
            // Wrap around
            rear = 0;
        } else {
            rear++;
        }
        
        // Copy item data to queue
        recentItemsQueue[rear] = new String[7];
        for (int i = 0; i < 7; i++) {
            recentItemsQueue[rear][i] = item[i];
        }
    }
    
    // ✅ GET QUEUE SIZE (MANUAL)
    public int getRecentQueueSize() {
        if (front == -1) {
            return 0;
        }
        
        if (rear >= front) {
            return rear - front + 1;
        } else {
            return (MAX_RECENT_ITEMS - front) + rear + 1;
        }
    }
    
    // ✅ CHECK IF QUEUE IS EMPTY
    public boolean isRecentQueueEmpty() {
        return front == -1;
    }
    
    // ✅ GET QUEUE ARRAY (for traversal)
    public String[][] getRecentItemsQueue() {
        return recentItemsQueue;
    }
    
    public int getQueueFront() {
        return front;
    }
    
    public int getQueueRear() {
        return rear;
    }
    
    public int getMaxRecentItems() {
        return MAX_RECENT_ITEMS;
    }
    
    // ✅ REMOVE ITEM FROM QUEUE (MANUAL)
    public void removeFromRecentQueue(String itemID) {
        if (front == -1) {
            return; // Queue is empty
        }
        
        // Create temporary queue to rebuild without the item
        String[][] tempQueue = new String[MAX_RECENT_ITEMS][7];
        int tempFront = -1;
        int tempRear = -1;
        
        // Traverse original queue
        int i = front;
        while (true) {
            if (recentItemsQueue[i] != null && !recentItemsQueue[i][0].equals(itemID)) {
                // Add to temp queue
                if (tempFront == -1) {
                    tempFront = 0;
                    tempRear = 0;
                } else {
                    tempRear++;
                }
                
                tempQueue[tempRear] = new String[7];
                for (int j = 0; j < 7; j++) {
                    tempQueue[tempRear][j] = recentItemsQueue[i][j];
                }
            }
            
            if (i == rear) {
                break;
            }
            
            i = (i + 1) % MAX_RECENT_ITEMS;
        }
        
        // Copy temp back to original
        recentItemsQueue = tempQueue;
        front = tempFront;
        rear = tempRear;
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
    
    public LinkedList<String[]> getCategoryLinkedList() {
        return categoryLinkedList;
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