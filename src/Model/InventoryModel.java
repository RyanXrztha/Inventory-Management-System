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
    final int size = 10;
    int front = -1;
    int rear = -1;
    String[][] queue = new String[10][7];
    final int StackMax = 10;
    int top = -1;
    String[][] stack = new String[10][7];
    ArrayList<String[]> inventoryList = new ArrayList<>();
    
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
    
    public int getStackMax() {
        return StackMax;
    }
}
