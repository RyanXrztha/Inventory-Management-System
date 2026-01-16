/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;
import Model.InventoryModel;
import View.AdminPanel;
import java.util.ArrayList;
import java.util.LinkedList;
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
    
    // It initializes the controller and loads initial inventory data.
    public AdminPanelController(AdminPanel view) {
        this.view = view;
        this.model = InventoryModel.geta();
        loadPresentData();
        loadAdminPanelTable();
    }
    
    // It loads 5 initial data
    private void loadPresentData() {
        if(dataLoaded) {
        return;
        }
    
        push("1", "Bottle", "27", "105", "170", "Drinks");
        push("2", "Pencil", "100", "5", "10", "Stationery");
        push("3", "Copy", "50", "40", "100", "Stationery");
        push("4", "Bulb", "60", "200", "500", "Electronics");
        push("5", "Study Table", "80", "800", "1800", "Furniture");
    
        dataLoaded = true;
        
        if(model.getCategoryLinkedList().isEmpty()) {
            for(String[] item : model.getInventoryList()) {
                String[] copy = new String[7];
                for (int j = 0; j < 7; j++) {
                    copy[j] = item[j];
                }
                model.getCategoryLinkedList().add(copy);
            }
        }
    }
    
    // It adds a new item into the inventory
    public void push(String itemID, String itemName, String quantity, String costPrice, String price, String category) {
        
        // It checks whether inventory table space is available.
        if(model.getTop() == model.getSize() - 1){
            JOptionPane.showMessageDialog(view, "Inventory stack is full");
            return;
        }
        
        // They validates input fields
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
        
        // It calculates profit or loss
        double profitLoss;
        try {
            profitLoss = Double.parseDouble(price) - Double.parseDouble(costPrice);
        } catch (NumberFormatException e) {
            profitLoss = 0.0;
        }
        
        // It stores the item in the stack.
        model.setTop(model.getTop() + 1);
        
        model.getStack()[model.getTop()][0] = itemID;
        model.getStack()[model.getTop()][1] = itemName;
        model.getStack()[model.getTop()][2] = quantity;
        model.getStack()[model.getTop()][3] = costPrice;
        model.getStack()[model.getTop()][4] = price;
        model.getStack()[model.getTop()][5] = category;
        model.getStack()[model.getTop()][6] = String.valueOf(profitLoss);
        
        String[] record = {itemID, itemName, quantity, costPrice, price, category, String.valueOf(profitLoss)};
        
        model.getInventoryList().add(record);
        
        model.addToRecentCirQueue(record);
        
                
        String[] categoryCopy = new String[7];
            for (int j = 0; j < 7; j++) {
                categoryCopy[j] = record[j];
            }
            model.getCategoryLinkedList().add(categoryCopy);
    }
    
    // It removes the last item from inventory and stores it in history table.
    public void pop() {
        
        // It checks whether inventory is empty
        if(model.getTop() == -1){
            JOptionPane.showMessageDialog(view, "Inventory table is empty.");
            return;
        }

        // It stores item data before deletion
        String itemID = model.getStack()[model.getTop()][0];
        String itemName = model.getStack()[model.getTop()][1];
        String quantity = model.getStack()[model.getTop()][2];
        String costPrice = model.getStack()[model.getTop()][3];
        String price = model.getStack()[model.getTop()][4];
        String category = model.getStack()[model.getTop()][5];
        String profitLoss = model.getStack()[model.getTop()][6];

        JOptionPane.showMessageDialog(view, "Deleted: " + itemName);

        // It clears the item from stack
        for(int j = 0; j < 7; j++){
            model.getStack()[model.getTop()][j] = null;
        }
        model.setTop(model.getTop() - 1);

        for(int i = 0; i < model.getInventoryList().size(); i++){
            if(model.getInventoryList().get(i) != null && model.getInventoryList().get(i)[0] != null && model.getInventoryList().get(i)[0].equals(itemID)){
                model.getInventoryList().remove(i);
                break;
            }
        }

        model.removeFromRecentCirQueue(itemID);
        model.rebuildRecentCirQueue();

        if(model.getDeletedTop() == model.getfullSize() - 1){
            JOptionPane.showMessageDialog(view, "Item deleted history is full");
        } else {
            // It moves the item into deleted history
            model.setDeletedTop(model.getDeletedTop() + 1);
            model.getDeletedStack()[model.getDeletedTop()][0] = itemID;
            model.getDeletedStack()[model.getDeletedTop()][1] = itemName;
            model.getDeletedStack()[model.getDeletedTop()][2] = quantity;
            model.getDeletedStack()[model.getDeletedTop()][3] = costPrice;
            model.getDeletedStack()[model.getDeletedTop()][4] = price;
            model.getDeletedStack()[model.getDeletedTop()][5] = category;
            model.getDeletedStack()[model.getDeletedTop()][6] = profitLoss;
        }

        for(int i = 0; i < model.getCategoryLinkedList().size(); i++){
            if(model.getCategoryLinkedList().get(i) != null && model.getCategoryLinkedList().get(i)[0] != null && model.getCategoryLinkedList().get(i)[0].equals(itemID)){
                model.getCategoryLinkedList().remove(i);
                break;
            }
        }
    }
    

    // It restores the most recently deleted item back to inventory.
    public void recoverItem() {
        // It checks whether deleted history is empty
        if(model.getDeletedTop() == -1){
            JOptionPane.showMessageDialog(view, "No items in deleted history table to recover");
            return;
        }

        // It copies the deleted item back to inventory
        String itemID = model.getDeletedStack()[model.getDeletedTop()][0];
        String itemName = model.getDeletedStack()[model.getDeletedTop()][1];
        String quantity = model.getDeletedStack()[model.getDeletedTop()][2];
        String costPrice = model.getDeletedStack()[model.getDeletedTop()][3];
        String price = model.getDeletedStack()[model.getDeletedTop()][4];
        String category = model.getDeletedStack()[model.getDeletedTop()][5];
        String profitLoss = model.getDeletedStack()[model.getDeletedTop()][6];

        if (isDuplicateItemID(itemID)) {
            JOptionPane.showMessageDialog(view, "Cannot recover: Item with ID '" + itemID + "' already exists in inventory!", "Duplicate Item ID", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if(model.getTop() == model.getSize() - 1){
            JOptionPane.showMessageDialog(view, "Inventory stack is full. Cannot recover item.");
            return;
        }

        model.setTop(model.getTop() + 1);
        model.getStack()[model.getTop()][0] = itemID;
        model.getStack()[model.getTop()][1] = itemName;
        model.getStack()[model.getTop()][2] = quantity;
        model.getStack()[model.getTop()][3] = costPrice;
        model.getStack()[model.getTop()][4] = price;
        model.getStack()[model.getTop()][5] = category;
        model.getStack()[model.getTop()][6] = profitLoss;

        String[] record = {itemID, itemName, quantity, costPrice, price, category, profitLoss};
        model.getInventoryList().add(record);

        model.addToRecentCirQueue(record);
        
        String[] categoryCopy = new String[7];
        for (int j = 0; j < 7; j++) {
            categoryCopy[j] = record[j];
        }
        model.getCategoryLinkedList().add(categoryCopy);

         // It removes the item from deleted history
        for(int j = 0; j < 7; j++){
            model.getDeletedStack()[model.getDeletedTop()][j] = null;
        }
        model.setDeletedTop(model.getDeletedTop() - 1);

        JOptionPane.showMessageDialog(view, "Item '" + itemName + "' has been recovered successfully!");

        loadAdminPanelTable();
        loadDeletedTable();
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
    
    // It checks whether an item ID already exists.
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
    
    // It loads inventory data in the admin table
    public void loadAdminPanelTable() {
        DefaultTableModel tableModel = (DefaultTableModel) view.getjTable3().getModel();
        tableModel.setRowCount(0);
        
        if(model.getTop() == -1){
            return;
        }
        
        for(int i = model.getTop(); i >= 0; i--){
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
    
    for(int i = model.getDeletedTop(); i >= 0; i--) {
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
    
    
    // It searches an item using binary search.
    public int searchID(String searchValue, ArrayList<String[]> inventoryList, int low, int high) {

        if (low > high) {
            return -1;
        }

        int mid = low + (high - low) / 2;

        String midItemID = inventoryList.get(mid)[0];

        int comparison = searchValue.compareToIgnoreCase(midItemID);

        if (comparison == 0) {
            return mid;
        } else if (comparison < 0) {
            return searchID(searchValue, inventoryList, low, mid - 1);
        } else {
            return searchID(searchValue, inventoryList, mid + 1, high);
        }
    }

    
    
    public void searchItemFromID(String itemID) {

        ArrayList<String[]> list = model.getInventoryList();

        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Inventory is empty");
            return;
        }

        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 0; j < list.size() - i - 1; j++) {
                if (list.get(j)[0].compareToIgnoreCase(list.get(j + 1)[0]) > 0) {

                    String[] temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }

        foundIndex = searchID(itemID, list, 0, list.size() - 1);

        if (foundIndex == -1) {
            JOptionPane.showMessageDialog(view, "Searched item is not available");
            return;
        }

        String[] record = list.get(foundIndex);
        String message = """
                         Item has been found!
                         Item ID: """ + record[0] + "\n" +
                        "Item Name: " + record[1] + "\n" +
                        "Quantity: " + record[2] + "\n" +
                        "Cost Price: " + record[3] + "\n" +
                        "Selling Price: " + record[4] + "\n" +
                        "Category: " + record[5] + "\n" +
                        "Profit/Loss: " + record[6];

        JOptionPane.showMessageDialog(view, message, "Search Result", JOptionPane.INFORMATION_MESSAGE);
        view.setChangedFields(record[0], record[1], record[2], record[3], record[4], record[5]);
    }


    
    public void updateItem(String itemID, String itemName, String quantity, String costPrice, String price, String category) {
        if (foundIndex == -1) {
            JOptionPane.showMessageDialog(view, "Please search item ID before updating values");
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

        ArrayList<String[]> list = model.getInventoryList();

        double profitLoss;
        try {
            profitLoss = Double.parseDouble(price) - Double.parseDouble(costPrice);
        } catch (NumberFormatException e) {
            profitLoss = 0.0;
        }

        String[] oldRecord = list.get(foundIndex);
        String oldItemID = oldRecord[0];

        String[] newRecord = {itemID, itemName, quantity, costPrice, price, category, String.valueOf(profitLoss)};

        list.set(foundIndex, newRecord);

        for (int i = 0; i <= model.getTop(); i++) {
            if (model.getStack()[i][0] != null && model.getStack()[i][0].equals(oldItemID)) {
                model.getStack()[i][0] = itemID;
                model.getStack()[i][1] = itemName;
                model.getStack()[i][2] = quantity;
                model.getStack()[i][3] = costPrice;
                model.getStack()[i][4] = price;
                model.getStack()[i][5] = category;
                model.getStack()[i][6] = String.valueOf(profitLoss);
                break;
            }
        }

        for (int i = 0; i < model.getCategoryLinkedList().size(); i++) {
            if (model.getCategoryLinkedList().get(i)[0] != null && model.getCategoryLinkedList().get(i)[0].equals(oldItemID)) {
                String[] categoryRecord = new String[7];
                categoryRecord[0] = itemID;
                categoryRecord[1] = itemName;
                categoryRecord[2] = quantity;
                categoryRecord[3] = costPrice;
                categoryRecord[4] = price;
                categoryRecord[5] = category;
                categoryRecord[6] = String.valueOf(profitLoss);
                model.getCategoryLinkedList().set(i, categoryRecord);
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
                tableModel.addRow(new Object[]{list.get(i)[0], list.get(i)[1], list.get(i)[2], list.get(i)[3], list.get(i)[4], list.get(i)[5], list.get(i)[6]});
            }
        }
    }

    // It sorts items by ID.
    public void sortByID(boolean ascending) {
        ArrayList<String[]> list = model.getInventoryList();
        int size = list.size();

        for(int step = 0; step < size - 1; step++){
            int min_idx = step;

            for(int i = step + 1; i < size; i++){
                int idI = Integer.parseInt(list.get(i)[0]);
                int idMin = Integer.parseInt(list.get(min_idx)[0]);

                if(ascending ? idI < idMin : idI > idMin){
                    min_idx = i;
                }
            }

            String[] temp = list.get(step);
            list.set(step, list.get(min_idx));
            list.set(min_idx, temp);
        }
        loadSortingTable();
    }

    public void sortByName(boolean ascending) {
        ArrayList<String[]> list = model.getInventoryList();
        int size = list.size();

        for (int step = 1; step < size; step++) {
            String[] key = list.get(step);
            int j = step - 1;

            while (j >= 0) {
                int comparison = list.get(j)[1].compareToIgnoreCase(key[1]);
                if(ascending ? comparison > 0 : comparison < 0) {
                    list.set(j + 1, list.get(j));
                    j--;
                } else {
                    break;
                }
            }

            list.set(j + 1, key);
        }

        loadSortingTable();
    }

    public void sortByQuantity(boolean ascending) {
        ArrayList<String[]> list = model.getInventoryList();
        int size = list.size();

        for(int step = 0; step < size-1; step++){
            int min_idx = step;
            for(int i = step+1; i < size; i++){
                int qtyI = Integer.parseInt(list.get(i)[2]);
                int qtyMin = Integer.parseInt(list.get(min_idx)[2]);
                if(ascending ? qtyI < qtyMin : qtyI > qtyMin){
                    min_idx = i;
                }
            }
            String[] temp = list.get(step);
            list.set(step, list.get(min_idx));
            list.set(min_idx, temp);
        }
        loadSortingTable();
    }

    public void sortByCostPrice(boolean ascending) {
        ArrayList<String[]> list = model.getInventoryList();
        int size = list.size();

        for(int step = 0; step < size-1; step++){
            int min_idx = step;
            for(int i = step+1; i < size; i++){
                double priceI = Double.parseDouble(list.get(i)[3]);
                double priceMin = Double.parseDouble(list.get(min_idx)[3]);
                if(ascending ? priceI < priceMin : priceI > priceMin){
                    min_idx = i;
                }
        }
            String[] temp = list.get(step);
            list.set(step, list.get(min_idx));
            list.set(min_idx, temp);
        }
        loadSortingTable();
    }

    private void mergeSort(ArrayList<String[]> list, int left, int right, int columnIndex, boolean ascending) {
        if (left < right) {

            int mid = left + (right - left) / 2;

            mergeSort(list, left, mid, columnIndex, ascending);

            mergeSort(list, mid + 1, right, columnIndex, ascending);

            merge(list, left, mid, right, columnIndex, ascending);
        }
    }

    private void merge(ArrayList<String[]> list, int left, int mid, int right, int columnIndex, boolean ascending) {

        int n1 = mid - left + 1;
        int n2 = right - mid;

        ArrayList<String[]> leftArray = new ArrayList<>();
        ArrayList<String[]> rightArray = new ArrayList<>();

        for (int i = 0; i < n1; i++) {
            leftArray.add(list.get(left + i));
        }
        for (int j = 0; j < n2; j++) {
            rightArray.add(list.get(mid + 1 + j));
        }

        int i = 0, j = 0;
        int k = left;

        while (i < n1 && j < n2) {
            double leftValue = Double.parseDouble(leftArray.get(i)[columnIndex]);
            double rightValue = Double.parseDouble(rightArray.get(j)[columnIndex]);
            
            boolean condition = ascending ? leftValue <= rightValue : leftValue >= rightValue;

            if (condition) {
                list.set(k, leftArray.get(i));
                i++;
            } else {
                list.set(k, rightArray.get(j));
                j++;
            }
            k++;
        }

        while (i < n1) {
            list.set(k, leftArray.get(i));
            i++;
            k++;
        }

        while (j < n2) {
            list.set(k, rightArray.get(j));
            j++;
            k++;
        }
    }

    public void sortBySellingPrice(boolean ascending) {
        ArrayList<String[]> list = model.getInventoryList();

        if (list.isEmpty()) {
            return;
        }

        mergeSort(list, 0, list.size() - 1, 4, ascending);

        loadSortingTable();
    }

    public void sortByProfitLoss(boolean ascending) {
        ArrayList<String[]> list = model.getInventoryList();

        if (list.isEmpty()) {
            return;
        }

        mergeSort(list, 0, list.size() - 1, 6, ascending);

        loadSortingTable();
    }


    

    public void searchItemFromName(String itemName) {
    ArrayList<String[]> list = model.getInventoryList();
    if (list.isEmpty()) {
        JOptionPane.showMessageDialog(view, "Inventory is empty");
        return;
    }

    ArrayList<Integer> foundIndices = new ArrayList<>();
    for (int i = 0; i < list.size(); i++) {
        if (list.get(i)[1].toLowerCase().contains(itemName.toLowerCase())) {
            foundIndices.add(i);
        }
    }

    if (foundIndices.isEmpty()) {
        JOptionPane.showMessageDialog(view, "No item found with name: " + itemName);
        foundIndex = -1;
        return;
    }

    if (foundIndices.size() > 1) {
        String message = "Multiple items found!\n";
        for (int i = 0; i < foundIndices.size(); i++) {
            String[] record = list.get(foundIndices.get(i));
            message += "Item " + (i + 1) + ":\n";
            message += "Item ID: " + record[0] + "\n";
            message += "Item Name: " + record[1] + "\n";
            message += "Quantity: " + record[2] + "\n";
            message += "Cost Price: " + record[3] + "\n";
            message += "Selling Price: " + record[4] + "\n";
            message += "Category: " + record[5] + "\n";
            message += "Profit/Loss: " + record[6] + "\n\n";
        }
        message += "Multiple items found. Please search by Item ID to update a specific item.";
        JOptionPane.showMessageDialog(view, message, "Multiple Search Results", JOptionPane.INFORMATION_MESSAGE);
        
        foundIndex = -1;
        return;
    }

    foundIndex = foundIndices.get(0);
    String[] record = list.get(foundIndex);

    String message = """
                     Item has been found!
                     Item ID: """ + record[0] + "\n" +
            "Item Name: " + record[1] + "\n" +
            "Quantity: " + record[2] + "\n" +
            "Cost Price: " + record[3] + "\n" +
            "Selling Price: " + record[4] + "\n" +
            "Category: " + record[5] + "\n" +
            "Profit/Loss: " + record[6];

    JOptionPane.showMessageDialog(view, message, "Search Result", JOptionPane.INFORMATION_MESSAGE);

    view.setChangedFields(record[0], record[1], record[2], record[3], record[4], record[5]);
}



    public void searchDeletedItemByID(String itemID) {
        if(model.getDeletedTop() == -1) {
            JOptionPane.showMessageDialog(view, "Deleted items history is empty");
            return;
        }

        ArrayList<String[]> deletedList = new ArrayList<>();
        for(int i = 0; i <= model.getDeletedTop(); i++) {
            if(model.getDeletedStack()[i][0] != null) {
                deletedList.add(model.getDeletedStack()[i]);
            }
        }

        for (int i = 0; i < deletedList.size() - 1; i++) {
            for (int j = 0; j < deletedList.size() - i - 1; j++) {
                if (deletedList.get(j)[0].compareToIgnoreCase(deletedList.get(j + 1)[0]) > 0) {
                    String[] temp = deletedList.get(j);
                    deletedList.set(j, deletedList.get(j + 1));
                    deletedList.set(j + 1, temp);
                }
            }
        }

        int index = searchID(itemID, deletedList, 0, deletedList.size() - 1);

        if (index == -1) {
            JOptionPane.showMessageDialog(view, "No deleted item found with ID: " + itemID);
            return;
        }

        String[] record = deletedList.get(index);
        String message = "Deleted Item Found!\n\n" +
                        "Item ID: " + record[0] + "\n" +
                        "Item Name: " + record[1] + "\n" +
                        "Quantity: " + record[2] + "\n" +
                        "Cost Price: " + record[3] + "\n" +
                        "Selling Price: " + record[4] + "\n" +
                        "Category: " + record[5] + "\n" +
                        "Profit/Loss: " + record[6];

        JOptionPane.showMessageDialog(view, message, "Deleted Item Search Result", JOptionPane.INFORMATION_MESSAGE);
    }

    public void searchDeletedItemByName(String itemName) {
    if(model.getDeletedTop() == -1) {
        JOptionPane.showMessageDialog(view, "Deleted items history is empty");
        return;
    }

    ArrayList<Integer> foundIndices = new ArrayList<>();
    
    for (int i = 0; i <= model.getDeletedTop(); i++) {
        if (model.getDeletedStack()[i][1] != null &&
            model.getDeletedStack()[i][1].toLowerCase().contains(itemName.toLowerCase())) {
            foundIndices.add(i);
        }
    }

    if (foundIndices.isEmpty()) {
        JOptionPane.showMessageDialog(view, "No deleted item found matching: " + itemName);
        return;
    }

    if (foundIndices.size() > 1) {
        String message = "Multiple deleted items found!\n\n";
        for (int i = 0; i < foundIndices.size(); i++) {
            String[] record = model.getDeletedStack()[foundIndices.get(i)];
            message += "Item " + (i + 1) + ":\n";
            message += "Item ID: " + record[0] + "\n";
            message += "Item Name: " + record[1] + "\n";
            message += "Quantity: " + record[2] + "\n";
            message += "Cost Price: " + record[3] + "\n";
            message += "Selling Price: " + record[4] + "\n";
            message += "Category: " + record[5] + "\n";
            message += "Profit/Loss: " + record[6] + "\n\n";
        }
        message += "Multiple items found. Please search by Item ID for specific details.";
        JOptionPane.showMessageDialog(view, message, "Multiple Deleted Items Found", JOptionPane.INFORMATION_MESSAGE);
        return;
    }

    // Single item are found
    String[] record = model.getDeletedStack()[foundIndices.get(0)];

    String message = "Deleted Item Found!\n\n" +
            "Item ID: " + record[0] + "\n" +
            "Item Name: " + record[1] + "\n" +
            "Quantity: " + record[2] + "\n" +
            "Cost Price: " + record[3] + "\n" +
            "Selling Price: " + record[4] + "\n" +
            "Category: " + record[5] + "\n" +
            "Profit/Loss: " + record[6];

    JOptionPane.showMessageDialog(view, message, "Deleted Item Search Result", JOptionPane.INFORMATION_MESSAGE);
}


    
    // It filters items based on category.
    public void filterByCategory(String category) {
        DefaultTableModel tableModel = (DefaultTableModel) view.getjTable4().getModel();
        tableModel.setRowCount(0);

        LinkedList<String[]> categoryList = model.getCategoryLinkedList();

        if(categoryList.isEmpty()) {
            JOptionPane.showMessageDialog(view, "No items available");
            return;
        }

        if(category.equals("All Categories")) {
            for(int i = categoryList.size() - 1; i >= 0; i--) {
                String[] item = categoryList.get(i);
                if(item[0] != null) {
                    tableModel.addRow(new Object[]{
                        item[0], item[1], item[2], item[3], item[4], item[5], item[6]
                    });
                }
            }
        } else {
            boolean found = false;
            for(int i = categoryList.size() - 1; i >= 0; i--) {
                String[] item = categoryList.get(i);
                if(item[0] != null && item[5].equalsIgnoreCase(category)) {
                    tableModel.addRow(new Object[]{
                        item[0], item[1], item[2], item[3], item[4], item[5], item[6]
                    });
                    found = true;
                }
            }

            if(!found) {
                JOptionPane.showMessageDialog(view, "No items found in category: " + category);
            }
        }
    }      
}
