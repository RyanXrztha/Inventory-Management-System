/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;
import Model.InventoryModel;
import View.AdminPanel;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
/**
 *
 * @author aryan
 */
public final class AdminPanelController {
    private final InventoryModel model;
    private final AdminPanel view;
    private int foundIndex = -1;
    
    public AdminPanelController(AdminPanel view) {
        this.view = view;
        this.model = InventoryModel.geta();
        loadPresentData();
        loadAdminPanelTable();
    }
    
    private void loadPresentData() {

    enqueue("1", "Bottle", "27", "105", "170", "Hydration Product");
    enqueue("2", "Pencil", "100", "5", "10", "Stationery");
    enqueue("3", "Pen", "50", "40", "100", "Stationery");
    enqueue("4", "Bulb", "60", "200", "500", "Electronics");
    enqueue("5", "Study Table", "80", "800", "1800", "Furniture");
}
    
    public void enqueue(String itemID, String itemName, String quantity, String costPrice, String price, String category) {
        if(model.getRear() == model.getSize()-1){
            JOptionPane.showMessageDialog(view, "Inventory queue is full");
            return;
        }
        
        if(itemID.isEmpty()){
        JOptionPane.showMessageDialog(view, "Please enter itemID", "itemID is not entered", JOptionPane.WARNING_MESSAGE);
        return;
        }
        
        if(itemName.isEmpty()){
        JOptionPane.showMessageDialog(view, "Please enter itemName", "itemName is not entered", JOptionPane.WARNING_MESSAGE);
        return;
        }
        
        if(quantity.isEmpty()){
        JOptionPane.showMessageDialog(view, "Please enter quantity", "Quantity is not entered", JOptionPane.WARNING_MESSAGE);
        return;
        }
        
        if(costPrice.isEmpty()){
        JOptionPane.showMessageDialog(view, "Please enter costPrice", "Cost Price is not entered", JOptionPane.WARNING_MESSAGE);
        return;
        }
        
        if(price.isEmpty()){
        JOptionPane.showMessageDialog(view, "Please enter selling price", "Selling Price is not entered", JOptionPane.WARNING_MESSAGE);
        return;
        }
        
        if(category.isEmpty()){
        JOptionPane.showMessageDialog(view, "Please enter category", "Category is not entered", JOptionPane.WARNING_MESSAGE);
        return;
        }
        
         if(itemID.isEmpty() || itemName.isEmpty() || quantity.isEmpty() || costPrice.isEmpty() || price.isEmpty() || category.isEmpty()){
            JOptionPane.showMessageDialog(view, "All fields are required! Please fill in all information.", "Empty Fields", JOptionPane.WARNING_MESSAGE);
            return;
        }
         
         if (isDuplicateItemID(itemID)) {
            JOptionPane.showMessageDialog(view, "Item with the same Item ID already exists!", "Duplicate Item ID", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if(model.getFront() == -1){
            model.setFront(0);
        }
        model.setRear(model.getRear() + 1);
       
        try {
            int id = Integer.parseInt(itemID);

            if (id<=0) {
                JOptionPane.showMessageDialog(view, "Item ID must be greater than zero");
                return;
            }


        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Item ID value must be an number");
            return;
        }
        
        try {
            int qty = Integer.parseInt(quantity);

            if (qty <= 0) {
                JOptionPane.showMessageDialog(view, "Quantity must be greater than zero");
                return;
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view,
                    "Quantity value must be an number");
            return;
        }
        
        try {
            double cp = Double.parseDouble(costPrice);
            
            if (cp <= 0) {
                JOptionPane.showMessageDialog(view,
                        "Cost Price must be greater than zero");
                return;
            }


        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view,
                    "Cost Price value must be a number");
            return;
        }
        
        try {
            double sp = Double.parseDouble(price);

            if (sp <= 0) {
                JOptionPane.showMessageDialog(view,
                        "Selling Price must be greater than zero");
                return;
            }


        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view,
                    "Selling Price value must be a number");
            return;
        }
        
        double profitLoss;
        
        try {
            profitLoss = Double.parseDouble(price) - Double.parseDouble(costPrice);
        } catch (NumberFormatException e) {
            profitLoss = 0.0;
        }
       
        model.getQueue()[model.getRear()][0] = itemID;
        model.getQueue()[model.getRear()][1] = itemName;
        model.getQueue()[model.getRear()][2] = quantity;
        model.getQueue()[model.getRear()][3] = costPrice;
        model.getQueue()[model.getRear()][4] = price;
        model.getQueue()[model.getRear()][5] = category;
        model.getQueue()[model.getRear()][6] = String.valueOf(profitLoss);
       
        String[] record = {
            itemID, itemName, quantity, costPrice, price, category, String.valueOf(profitLoss)
        };
        model.getInventoryList().add(record);
    }
    
    public void dequeue() {
        if(model.getFront() == -1){
            JOptionPane.showMessageDialog(view, "Inventory queue is empty.");
            return;
        }
        String itemID = model.getQueue()[model.getFront()][0];
        String itemName = model.getQueue()[model.getFront()][1];
        String quantity = model.getQueue()[model.getFront()][2];
        String costPrice = model.getQueue()[model.getFront()][3];
        String price = model.getQueue()[model.getFront()][4];
        String category = model.getQueue()[model.getFront()][5];
        String profitLoss = model.getQueue()[model.getFront()][6];
       
        JOptionPane.showMessageDialog(view, "Deleted: " + itemName);
        for(int j=0; j<7; j++){
            model.getQueue()[model.getFront()][j] = null;
        }
        model.setFront(model.getFront() + 1);
        if(model.getFront() > model.getRear()){
            model.setFront(-1);
            model.setRear(-1);
        }
        if(model.getTop() == model.getfullSize() - 1){
            JOptionPane.showMessageDialog(view, "Item deleted history has been full");
        } else {
            model.setTop(model.getTop() + 1);
            model.getStack()[model.getTop()][0] = itemID;
            model.getStack()[model.getTop()][1] = itemName;
            model.getStack()[model.getTop()][2] = quantity;
            model.getStack()[model.getTop()][3] = costPrice;
            model.getStack()[model.getTop()][4] = price;
            model.getStack()[model.getTop()][5] = category;
            model.getStack()[model.getTop()][6] = profitLoss;
        }
    }
    
    public void popFromStack() {
        if(model.getTop() == -1){
            JOptionPane.showMessageDialog(view, "There is not item data in table");
            return;
        }
        String deletedName = model.getStack()[model.getTop()][1];
        JOptionPane.showMessageDialog(view, "Deleted from history: " + deletedName);
        for(int j=0; j<7; j++){
            model.getStack()[model.getTop()][j] = null;
        }
        model.setTop(model.getTop() - 1);
    }
    
    private boolean isDuplicateItemID(String itemID) {
    if (model.getFront() == -1) {
        return false;
    }

    for (int i = model.getFront(); i <= model.getRear(); i++) {
        if (model.getQueue()[i][0] != null &&
            model.getQueue()[i][0].equalsIgnoreCase(itemID)) {
            return true;
        }
    }
    return false;
}
    
    public void loadAdminPanelTable() {
        DefaultTableModel tableModel = (DefaultTableModel) view.getjTable3().getModel();
        tableModel.setRowCount(0);
        if(model.getFront() == -1 || model.getFront() > model.getRear()){
            return;
        }
        for(int i = model.getFront(); i <= model.getRear(); i++){
            if(model.getQueue()[i][0] != null){
                tableModel.addRow(new Object[]{
                    model.getQueue()[i][0], 
                    model.getQueue()[i][1], 
                    model.getQueue()[i][2], 
                    model.getQueue()[i][3], 
                    model.getQueue()[i][4], 
                    model.getQueue()[i][5], 
                    model.getQueue()[i][6]
                });
            }
        }
    }
    
    public void loadDeletedTable() {
        DefaultTableModel tableModel = (DefaultTableModel) view.getjTable2().getModel();
        tableModel.setRowCount(0);
        for(int i = model.getTop(); i >= 0; i--){
            tableModel.addRow(new Object[]{
                model.getStack()[i][0], 
                model.getStack()[i][1], 
                model.getStack()[i][2], 
                model.getStack()[i][3], 
                model.getStack()[i][4], 
                model.getStack()[i][5], 
                model.getStack()[i][6]
            });
        }
    }
    
    
    public int searchName(String searchValue, ArrayList<String[]> inventoryList, int low, int high) {

    if (low > high) {
        return -1;
    }

    int mid = low + (high - low) / 2;

    String midItemName = inventoryList.get(mid)[1];

    int comparison = searchValue.compareToIgnoreCase(midItemName);

    if (comparison == 0) {
        return mid;
    } else if (comparison < 0) {
        return searchName(searchValue, inventoryList, low, mid - 1);
    } else {
        return searchName(searchValue, inventoryList, mid + 1, high);
    }
}

    
    
    public void searchItemFromName(String itemName) {

    ArrayList<String[]> list = model.getInventoryList();

    if (list.isEmpty()) {
        JOptionPane.showMessageDialog(view, "Inventory is empty");
        return;
    }

    for (int i = 0; i < list.size() - 1; i++) {
        for (int j = 0; j < list.size() - i - 1; j++) {
            if (list.get(j)[1].compareToIgnoreCase(list.get(j + 1)[1]) > 0) {

                String[] temp = list.get(j);
                list.set(j, list.get(j + 1));
                list.set(j + 1, temp);
            }
        }
    }

    foundIndex = searchName(itemName, list, 0, list.size() - 1);

    if (foundIndex == -1) {
        JOptionPane.showMessageDialog(view, "Searched item is not available");
        return;
    }

    String[] record = list.get(foundIndex);

    view.setChangedFields(record[0], record[1], record[2], record[3], record[4], record[5]);
}


    
    public void updateItem(String itemID, String itemName, String quantity, String costPrice, String price, String category) {

    if (foundIndex == -1) {
        JOptionPane.showMessageDialog(view, "Please search item name before updating values");
        return;
    }

    ArrayList<String[]> list = model.getInventoryList();

    double profitLoss;
    try {
        profitLoss = Double.parseDouble(price) - Double.parseDouble(costPrice);
    } catch (NumberFormatException e) {
        profitLoss = 0.0;
    }

    
    String[] record = list.get(foundIndex);
    record[0] = itemID;
    record[1] = itemName;
    record[2] = quantity;
    record[3] = costPrice;
    record[4] = price;
    record[5] = category;
    record[6] = String.valueOf(profitLoss);

    
    for (int i = model.getFront(); i <= model.getRear(); i++) {
        if (model.getQueue()[i][0] != null && model.getQueue()[i][0].equals(itemID)) {
            model.getQueue()[i] = record;
            break;
        }
    }

    loadAdminPanelTable();
    JOptionPane.showMessageDialog(view, "Item has been updated");

    foundIndex = -1;
}



    
    
    
    public String[][] getQueue() {
        return model.getQueue();
    }

public int getFront() {
    return model.getFront();
}

public int getRear() {
    return model.getRear();
}
    
}
