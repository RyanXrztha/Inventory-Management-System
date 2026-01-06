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
    private static boolean dataLoaded = false;
    
    public AdminPanelController(AdminPanel view) {
        this.view = view;
        this.model = InventoryModel.geta();
        loadPresentData();
        loadAdminPanelTable();
    }
    
    private void loadPresentData() {
        if(dataLoaded) {
        return;
    }
    
        push("1", "Bottle", "27", "105", "170", "Hydration Product");
        push("2", "Pencil", "100", "5", "10", "Stationery");
        push("3", "Pen", "50", "40", "100", "Stationery");
        push("4", "Bulb", "60", "200", "500", "Electronics");
        push("5", "Study Table", "80", "800", "1800", "Furniture");
    
    dataLoaded = true;
}
    
    public void push(String itemID, String itemName, String quantity, String costPrice, String price, String category) {
        // Check if stack is full
        if(model.getTop() == model.getSize() - 1){
            JOptionPane.showMessageDialog(view, "Inventory stack is full");
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
        
        if (isDuplicateItemID(itemID)) {
            JOptionPane.showMessageDialog(view, "Item with the same Item ID already exists!", "Duplicate Item ID", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
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
            JOptionPane.showMessageDialog(view, "Quantity value must be an number");
            return;
        }
        
        try {
            double cp = Double.parseDouble(costPrice);
            if (cp <= 0) {
                JOptionPane.showMessageDialog(view, "Cost Price must be greater than zero");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Cost Price value must be a number");
            return;
        }
        
        try {
            double sp = Double.parseDouble(price);
            if (sp <= 0) {
                JOptionPane.showMessageDialog(view, "Selling Price must be greater than zero");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Selling Price value must be a number");
            return;
        }
        
        double profitLoss;
        try {
            profitLoss = Double.parseDouble(price) - Double.parseDouble(costPrice);
        } catch (NumberFormatException e) {
            profitLoss = 0.0;
        }
        
        // Push to stack (increment top)
        model.setTop(model.getTop() + 1);
        
        model.getStack()[model.getTop()][0] = itemID;
        model.getStack()[model.getTop()][1] = itemName;
        model.getStack()[model.getTop()][2] = quantity;
        model.getStack()[model.getTop()][3] = costPrice;
        model.getStack()[model.getTop()][4] = price;
        model.getStack()[model.getTop()][5] = category;
        model.getStack()[model.getTop()][6] = String.valueOf(profitLoss);
        
        String[] record = {
            itemID, itemName, quantity, costPrice, price, category, String.valueOf(profitLoss)
        };
        model.getInventoryList().add(record);
        
        model.addToRecentQueue(record);
    }
    
    public void pop() {
        if(model.getTop() == -1){
            JOptionPane.showMessageDialog(view, "Inventory stack is empty.");
            return;
        }
        
        String itemID = model.getStack()[model.getTop()][0];
        String itemName = model.getStack()[model.getTop()][1];
        String quantity = model.getStack()[model.getTop()][2];
        String costPrice = model.getStack()[model.getTop()][3];
        String price = model.getStack()[model.getTop()][4];
        String category = model.getStack()[model.getTop()][5];
        String profitLoss = model.getStack()[model.getTop()][6];
        
        JOptionPane.showMessageDialog(view, "Deleted: " + itemName);
        
        // Clear the top position (no shifting needed in LIFO!)
        for(int j = 0; j < 7; j++){
            model.getStack()[model.getTop()][j] = null;
        }
        
        // Decrement top
        model.setTop(model.getTop() - 1);
        
        // Remove from inventory list
        for(int i = 0; i < model.getInventoryList().size(); i++){
            if(model.getInventoryList().get(i)[0].equals(itemID)){
                model.getInventoryList().remove(i);
                break;
            }
        }
        
        // Push to deleted stack
        if(model.getDeletedTop() == model.getfullSize() - 1){
            JOptionPane.showMessageDialog(view, "Item deleted history is full");
        } else {
            model.setDeletedTop(model.getDeletedTop() + 1);
            model.getDeletedStack()[model.getDeletedTop()][0] = itemID;
            model.getDeletedStack()[model.getDeletedTop()][1] = itemName;
            model.getDeletedStack()[model.getDeletedTop()][2] = quantity;
            model.getDeletedStack()[model.getDeletedTop()][3] = costPrice;
            model.getDeletedStack()[model.getDeletedTop()][4] = price;
            model.getDeletedStack()[model.getDeletedTop()][5] = category;
            model.getDeletedStack()[model.getDeletedTop()][6] = profitLoss;
        }
    }
    
    public void popFromDeletedStack() {
        if(model.getDeletedTop() == -1){
            JOptionPane.showMessageDialog(view, "There is not item data in table");
            return;
        }
        String deletedName = model.getDeletedStack()[model.getDeletedTop()][1];
        JOptionPane.showMessageDialog(view, "Deleted from history: " + deletedName);
        for(int j = 0; j < 7; j++){
            model.getDeletedStack()[model.getDeletedTop()][j] = null;
        }
        model.setDeletedTop(model.getDeletedTop() - 1);
    }
    
    private boolean isDuplicateItemID(String itemID) {
        if (model.getTop() == -1) {
            return false;
        }
        
        for (int i = 0; i <= model.getTop(); i++) {
            if (model.getStack()[i][0] != null &&
                model.getStack()[i][0].equalsIgnoreCase(itemID)) {
                return true;
            }
        }
        return false;
    }
    
    public void loadAdminPanelTable() {
        DefaultTableModel tableModel = (DefaultTableModel) view.getjTable3().getModel();
        tableModel.setRowCount(0);
        
        if(model.getTop() == -1){
            return;
        }
        
        // Display from bottom to top of stack
        for(int i = 0; i <= model.getTop(); i++){
            if(model.getStack()[i][0] != null){
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
    }

    
    public void loadDeletedTable() {
        DefaultTableModel tableModel = (DefaultTableModel) view.getjTable2().getModel();
        tableModel.setRowCount(0);
        if(model.getDeletedTop() == -1) {
        return;
    }
    
    // Load from deleted stack, bottom to top
    for(int i = 0; i <= model.getDeletedTop(); i++) {
        if(model.getDeletedStack()[i][0] != null) {
            tableModel.addRow(new Object[]{
                model.getDeletedStack()[i][0],
                model.getDeletedStack()[i][1],
                model.getDeletedStack()[i][2],
                model.getDeletedStack()[i][3],
                model.getDeletedStack()[i][4],
                model.getDeletedStack()[i][5],
                model.getDeletedStack()[i][6]
            });
        }   
        }
    }
    
    
    public int searchID(String searchValue, ArrayList<String[]> inventoryList, int low, int high) {

    if (low > high) {
        return -1;
    }

    int mid = low + (high - low) / 2;

    String midItemID = inventoryList.get(mid)[0];  // Changed [1] to [0]

    int comparison = searchValue.compareToIgnoreCase(midItemID);

    if (comparison == 0) {
        return mid;
    } else if (comparison < 0) {
        return searchID(searchValue, inventoryList, low, mid - 1);  // Changed method name
    } else {
        return searchID(searchValue, inventoryList, mid + 1, high);  // Changed method name
    }
}

    
    
    public void searchItemFromID(String itemID) {

    ArrayList<String[]> list = model.getInventoryList();

    if (list.isEmpty()) {
        JOptionPane.showMessageDialog(view, "Inventory is empty");
        return;
    }

    // Sort by Item ID instead of Item Name
    for (int i = 0; i < list.size() - 1; i++) {
        for (int j = 0; j < list.size() - i - 1; j++) {
            if (list.get(j)[0].compareToIgnoreCase(list.get(j + 1)[0]) > 0) {  // Changed [1] to [0]

                String[] temp = list.get(j);
                list.set(j, list.get(j + 1));
                list.set(j + 1, temp);
            }
        }
    }

    foundIndex = searchID(itemID, list, 0, list.size() - 1);  // Changed method call

    if (foundIndex == -1) {
        JOptionPane.showMessageDialog(view, "Searched item is not available");
        return;
    }

    String[] record = list.get(foundIndex);

    view.setChangedFields(record[0], record[1], record[2], record[3], record[4], record[5]);
}


    
    public void updateItem(String itemID, String itemName, String quantity, String costPrice, String price, String category) {

        if (foundIndex == -1) {
            JOptionPane.showMessageDialog(view, "Please search item ID before updating values");
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


        for (int i = 0; i <= model.getTop(); i++) {
                if (model.getStack()[i][0] != null && model.getStack()[i][0].equals(itemID)) {
                    model.getStack()[i] = record;
                    break;
                }
            }

            loadAdminPanelTable();
            JOptionPane.showMessageDialog(view, "Item has been updated");
            foundIndex = -1;
    }


    public void loadSortingTable() {
        DefaultTableModel tableModel = (DefaultTableModel) view.getjTable1().getModel();
        tableModel.setRowCount(0);

        if(model.getTop() == -1){
            return;
        }

        ArrayList<String[]> list = model.getInventoryList();

        if(list.isEmpty()){
            return;
        }

        for(int i = 0; i < list.size(); i++){
            if(list.get(i)[0] != null){
                tableModel.addRow(new Object[]{
                    list.get(i)[0],  // ItemID
                    list.get(i)[1],  // ItemName
                    list.get(i)[2],  // Quantity
                    list.get(i)[3],  // CostPrice
                    list.get(i)[4],  // Price
                    list.get(i)[5],  // Category
                    list.get(i)[6]   // Profit/Loss
                });
            }
        }
}
    
    // Sort by ItemID
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
        loadSortingTable();
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
        loadSortingTable();
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
        loadSortingTable();
    }

    // Sort by CostPrice
    public void sortByCostPrice() {
        ArrayList<String[]> list = model.getInventoryList();
        int size = list.size();

        for(int step = 0; step < size-1; step++){
            int min_idx = step;
            for(int i = step+1; i < size; i++){
                if(Double.parseDouble(list.get(i)[3]) < Double.parseDouble(list.get(min_idx)[3])){
                    min_idx = i;
                }
            }
            String[] temp = list.get(step);
            list.set(step, list.get(min_idx));
            list.set(min_idx, temp);
        }
        loadSortingTable();
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
        loadSortingTable();
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
        loadSortingTable();
    }


    public int searchName(String searchValue, ArrayList<String[]> inventoryList, int low, int high) {
        if (low > high) {
            return -1;
        }

        int mid = low + (high - low) / 2;
        String midItemName = inventoryList.get(mid)[1];  // Index 1 for ItemName

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

        // Sort by Item Name
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 0; j < list.size() - i - 1; j++) {
                if (list.get(j)[1].compareToIgnoreCase(list.get(j + 1)[1]) > 0) {  // Index 1 for ItemName
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





        public String[][] getStack() {
            return model.getStack();
        }

        public int getTop() {
            return model.getTop();
    }
    
}
