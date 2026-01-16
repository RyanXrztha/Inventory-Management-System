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
    
    final int queueSize = 5;
    int front = -1;
    int rear = -1;
    int itemCount = 0;
    String[][] recentQueue = new String[5][7];

    

    
    
    private InventoryModel() {
    }
    
   
    public static InventoryModel geta() {
        if (a == null) {
            a = new InventoryModel();
        }
        return a;
    }
    

    public void addToRecentCirQueue(String[] item) {
        removeFromRecentCirQueue(item[0]);
        
        if(itemCount == queueSize) {

            front = (front + 1) % queueSize;
            itemCount--;
        }
        
        if(front == -1) {
            front = 0;
        }
        
        rear = (rear + 1) % queueSize;
        
        String[] newItem = new String[7];
        for (int j = 0; j < 7; j++) {
            newItem[j] = item[j];
        }
        recentQueue[rear] = newItem;
        
        itemCount++;
    }
    

    public void removeFromRecentCirQueue(String itemID) {
        if (itemCount == 0) {
            return;
        }

        int current = front;
        int foundIndex = -1;

        for (int i = 0; i < itemCount; i++) {
            if (recentQueue[current] != null && 
                recentQueue[current][0].equals(itemID)) {
                foundIndex = current;
                break;  
            }
            current = (current + 1) % queueSize;
        }

        if (foundIndex == -1) {
            return;
        }

        LinkedList<String[]> temp = new LinkedList<>();
        current = front;
        for (int i = 0; i < itemCount; i++) {
            if (current != foundIndex) {
                String[] item = new String[7];
                for (int j = 0; j < 7; j++) {
                    item[j] = recentQueue[current][j];
                }
                temp.add(item);
            }
            current = (current + 1) % queueSize;
        }

        for (int i = 0; i < queueSize; i++) {
            recentQueue[i] = null;
        }

        front = -1;
        rear = -1;
        itemCount = temp.size();

        if (itemCount > 0) {
            front = 0;
            rear = itemCount - 1;
            for (int i = 0; i < itemCount; i++) {
                recentQueue[i] = temp.get(i);
            }
        }
    }
    

    public void rebuildRecentCirQueue() {
        for (int i = 0; i < queueSize; i++) {
            recentQueue[i] = null;
        }
        front = -1;
        rear = -1;
        itemCount = 0;

        ArrayList<String[]> list = getInventoryList();
        if (list.isEmpty()) {
            return;
        }

        int count = Math.min(queueSize, list.size());
        int start = list.size() - count;

        for (int i = start; i < list.size(); i++) {
            String[] item = list.get(i);
            String[] copy = new String[7];
            for (int j = 0; j < 7; j++) {
                copy[j] = item[j];
            }
            addToRecentCirQueue(copy);
        }
    }

    public String[][] getRecentItems() {
        String[][] result = new String[itemCount][7];

        if (itemCount == 0) {
            return result;
        }

        int current = front;
        for (int i = 0; i < itemCount; i++) {
            String[] item = new String[7];
            for (int j = 0; j < 7; j++) {
                item[j] = recentQueue[current][j];
            }
            result[i] = item;
            current = (current + 1) % queueSize;
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