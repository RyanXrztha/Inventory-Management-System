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
    
    final int QUEUE_SIZE = 5;  // Max 5 recent items
    int queueFront = -1;
    int queueRear = -1;
    int itemCount = 0;  // Track number of items in queue
    String[][] recentQueue = new String[5][7];

    

    
    
    private InventoryModel() {
    }
    
   
    public static InventoryModel geta() {
        if (a == null) {
            a = new InventoryModel();
        }
        return a;
    }
    

    public void addToRecentQueue(String[] item) {
        // Remove duplicate if exists
        removeFromRecentQueue(item[0]);
        
        // Check if queue is full
        if(itemCount == QUEUE_SIZE) {
            // Queue is full, need to remove oldest (at front)
            // Move front forward circularly
            queueFront = (queueFront + 1) % QUEUE_SIZE;
            itemCount--;
        }
        
        // If queue is empty, set front to 0
        if(queueFront == -1) {
            queueFront = 0;
        }
        
        // Move rear forward CIRCULARLY (This is the key!)
        queueRear = (queueRear + 1) % QUEUE_SIZE;
        
        // Add item at rear position
        recentQueue[queueRear] = item.clone();
;
        
        itemCount++;
    }
    

    public void removeFromRecentQueue(String itemID) {
    if (itemCount == 0) {
        return;  // Queue is empty
    }
    
    // Find if the item exists (also gets foundIndex for efficiency)
    int current = queueFront;
    int foundIndex = -1;
    
    for (int i = 0; i < itemCount; i++) {
        if (recentQueue[current] != null && 
            recentQueue[current][0].equals(itemID)) {
            foundIndex = current;
            break;  
        }
        current = (current + 1) % QUEUE_SIZE;  // Move circularly
    }
    
    // If not found, return
    if (foundIndex == -1) {
        return;
    }
    
    // Collect remaining items in order using a temp list
    LinkedList<String[]> temp = new LinkedList<>();
    current = queueFront;
    for (int i = 0; i < itemCount; i++) {
        if (current != foundIndex) {
            temp.add(recentQueue[current].clone());  // Clone to avoid reference issues
        }
        current = (current + 1) % QUEUE_SIZE;
    }
    
    // Clear the queue array
    for (int i = 0; i < QUEUE_SIZE; i++) {
        recentQueue[i] = null;
    }
    
    // Reset pointers
    queueFront = -1;
    queueRear = -1;
    itemCount = temp.size();
    
    // Repopulate linearly if not empty
    if (itemCount > 0) {
        queueFront = 0;
        queueRear = itemCount - 1;
        for (int i = 0; i < itemCount; i++) {
            recentQueue[i] = temp.get(i);
        }
    }
}
    

    // ✅ ADD THESE GETTER METHODS:
    public String[][] getRecentItems() {
        String[][] result = new String[itemCount][7];

        if (itemCount == 0) {
            return result;
        }

        int current = queueFront;
        for (int i = 0; i < itemCount; i++) {
            result[i] = recentQueue[current].clone(); // ✅ FIX
            current = (current + 1) % QUEUE_SIZE;
        }

        return result;
    }


    public int getRecentCount() {
        return itemCount;
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

}