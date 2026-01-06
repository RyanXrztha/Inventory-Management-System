/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;
import Model.InventoryModel;
import View.CustomerPanel;
import java.util.ArrayList;
import java.util.Queue;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author aryan
 */
public final class CustomerPanelController {
    private final InventoryModel model;
    private final CustomerPanel view;

    public CustomerPanelController(CustomerPanel view) {
        this.view = view;
        this.model = InventoryModel.geta(); 
        loadCustomerTable();
    }

    public void loadCustomerTable() {
        DefaultTableModel loadcust = (DefaultTableModel) view.getjTable2().getModel();
        loadcust.setRowCount(0);
        
        ArrayList<String[]> list = model.getInventoryList();
    
        if(list.isEmpty()){
            return;
        }

        for(int i = 0; i < list.size(); i++){
            if(list.get(i)[0] != null){
                loadcust.addRow(new Object[]{
                    list.get(i)[0],  // ItemID
                    list.get(i)[1],  // ItemName
                    list.get(i)[2],  // Quantity
                    list.get(i)[4],  // Price
                    list.get(i)[5],  // Category
                });
            }
        }
    }
    
    public void loadRecentItemsTable() {
        DefaultTableModel tableModel = (DefaultTableModel) view.getjTable1().getModel();
        tableModel.setRowCount(0);
        
        Queue<String[]> recentQueue = model.getRecentItemsQueue();
        
        if(recentQueue.isEmpty()){
            return;
        }
        
        // Convert queue to array to display (without removing from queue)
        for(String[] item : recentQueue) {
            tableModel.addRow(new Object[]{
                item[0],  // ItemID
                item[1],  // ItemName
                item[2],  // Quantity
                item[4],  // Price (not costPrice)
                item[5]   // Category
            });
        }
    }
    
    public void sortByID() {
    ArrayList<String[]> list = model.getInventoryList();
    int size = list.size();
    
    for(int step = 0; step < size-1; step++){
        int min_idx = step;
        for(int i = step+1; i < size; i++){
            if(list.get(i)[0].compareToIgnoreCase(list.get(min_idx)[0]) < 0){
                min_idx = i;
            }
        }
        String[] temp = list.get(step);
        list.set(step, list.get(min_idx));
        list.set(min_idx, temp);
    }
    loadCustomerTable();
}

// Sort by ItemName
public void sortByName() {
    ArrayList<String[]> list = model.getInventoryList();
    int size = list.size();
    
    for(int step = 0; step < size-1; step++){
        int min_idx = step;
        for(int i = step+1; i < size; i++){
            String name = list.get(i)[1];
            if(name.compareToIgnoreCase(list.get(min_idx)[1]) < 0){
                min_idx = i;
            }
        }
        String[] temp = list.get(step);
        list.set(step, list.get(min_idx));
        list.set(min_idx, temp);
    }
    loadCustomerTable();
}

// Sort by Quantity
public void sortByQuantity() {
    ArrayList<String[]> list = model.getInventoryList();
    int size = list.size();
    
    for(int step = 0; step < size-1; step++){
        int min_idx = step;
        for(int i = step+1; i < size; i++){
            if(Integer.parseInt(list.get(i)[2]) < Integer.parseInt(list.get(min_idx)[2])){
                min_idx = i;
            }
        }
        String[] temp = list.get(step);
        list.set(step, list.get(min_idx));
        list.set(min_idx, temp);
    }
    loadCustomerTable();
}



// Sort by Selling Price
public void sortBySellingPrice() {
    ArrayList<String[]> list = model.getInventoryList();
    int size = list.size();
    
    for(int step = 0; step < size-1; step++){
        int min_idx = step;
        for(int i = step+1; i < size; i++){
            if(Double.parseDouble(list.get(i)[4]) < Double.parseDouble(list.get(min_idx)[4])){
                min_idx = i;
            }
        }
        String[] temp = list.get(step);
        list.set(step, list.get(min_idx));
        list.set(min_idx, temp);
    }
    loadCustomerTable();
}

// Sort by Profit/Loss
public void sortByProfitLoss() {
    ArrayList<String[]> list = model.getInventoryList();
    int size = list.size();
    
    for(int step = 0; step < size-1; step++){
        int min_idx = step;
        for(int i = step+1; i < size; i++){
            if(Double.parseDouble(list.get(i)[6]) < Double.parseDouble(list.get(min_idx)[6])){
                min_idx = i;
            }
        }
        String[] temp = list.get(step);
        list.set(step, list.get(min_idx));
        list.set(min_idx, temp);
    }
    loadCustomerTable();
}
}
