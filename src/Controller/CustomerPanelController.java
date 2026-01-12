/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;
import Model.InventoryModel;
import View.CustomerPanel;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.swing.JOptionPane;
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
                    list.get(i)[0],
                    list.get(i)[1],
                    list.get(i)[2],
                    list.get(i)[4],
                    list.get(i)[5],
                });
            }
        }
    }
    
    public void loadRecentItemsTable() {
        DefaultTableModel tableModel = (DefaultTableModel) view.getjTable1().getModel();
        tableModel.setRowCount(0);
        
        String[][] queue = model.getRecentItemsQueue();
        int front = model.getQueueFront();
        int rear = model.getQueueRear();
        int maxSize = model.getMaxRecentItems();
        
        // Manual circular queue traversal
        int i = front;
        while(true) {
            if(queue[i] != null) {
                tableModel.addRow(new Object[]{
                    queue[i][0],  // ItemID
                    queue[i][1],  // ItemName
                    queue[i][2],  // Quantity
                    queue[i][4],  // Price
                    queue[i][5]   // Category
                });
            }
            
            if(i == rear) {
                break;
            }
            
            i = (i + 1) % maxSize;  // Circular increment
        }
    }
    
    public void sortByID(boolean ascending) {
        ArrayList<String[]> list = model.getInventoryList();
        int size = list.size();

        for(int step = 0; step < size-1; step++){
            int min_idx = step;
            for(int i = step+1; i < size; i++){
                int comparison = list.get(i)[0].compareToIgnoreCase(list.get(min_idx)[0]);
                if(ascending ? comparison < 0 : comparison > 0){
                    min_idx = i;
                }
            }
            String[] temp = list.get(step);
            list.set(step, list.get(min_idx));
            list.set(min_idx, temp);
        }
        loadCustomerTable();
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
        loadCustomerTable();
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
        loadCustomerTable();
    }



    private void mergeSort(ArrayList<String[]> list, int left, int right, boolean ascending) {
        if (left < right) {

            int mid = left + (right - left) / 2;

            mergeSort(list, left, mid, ascending);

            mergeSort(list, mid + 1, right, ascending);

            merge(list, left, mid, right, ascending);
        }
    }

    private void merge(ArrayList<String[]> list, int left, int mid, int right, boolean ascending) {
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

            double leftPrice = Double.parseDouble(leftArray.get(i)[4]);
            double rightPrice = Double.parseDouble(rightArray.get(j)[4]);
            
            boolean condition = ascending ? leftPrice <= rightPrice : leftPrice >= rightPrice;

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

        mergeSort(list, 0, list.size() - 1, ascending);

        loadCustomerTable();
    }
    
    public void searchItemFromID(String itemID) {
        ArrayList<String[]> list = model.getInventoryList();
        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Inventory is empty");
            return;
        }
    
        // Sort by Item ID
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 0; j < list.size() - i - 1; j++) {
                if (list.get(j)[0].compareToIgnoreCase(list.get(j + 1)[0]) > 0) {
                    String[] temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
    
        int foundIndex = searchID(itemID, list, 0, list.size() - 1);

        if (foundIndex == -1) {
            JOptionPane.showMessageDialog(view, "No item found with ID: " + itemID);
            return;
        }

        String[] record = list.get(foundIndex);
        String message = "Item Found!\n\n" +
                        "Item ID: " + record[0] + "\n" +
                        "Item Name: " + record[1] + "\n" +
                        "Quantity: " + record[2] + "\n" +
                        "Price: " + record[4] + "\n" +
                        "Category: " + record[5];

        JOptionPane.showMessageDialog(view, message, "Search Result", JOptionPane.INFORMATION_MESSAGE);
    }

    private int searchID(String searchValue, ArrayList<String[]> inventoryList, int low, int high) {
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

    public void searchItemFromName(String itemName) {
    ArrayList<String[]> list = model.getInventoryList();
    if (list.isEmpty()) {
        JOptionPane.showMessageDialog(view, "Inventory is empty");
        return;
    }

    // Linear search - Find all matching items
    ArrayList<Integer> foundIndices = new ArrayList<>();
    for (int i = 0; i < list.size(); i++) {
        if (list.get(i)[1].toLowerCase().contains(itemName.toLowerCase())) {
            foundIndices.add(i);
        }
    }

    if (foundIndices.isEmpty()) {
        JOptionPane.showMessageDialog(view, "No item found matching: " + itemName);
        return;
    }

    // If multiple items found
    if (foundIndices.size() > 1) {
        String message = "Multiple items found!\n\n";
        for (int i = 0; i < foundIndices.size(); i++) {
            String[] record = list.get(foundIndices.get(i));
            message += "Item " + (i + 1) + ":\n";
            message += "Item ID: " + record[0] + "\n";
            message += "Item Name: " + record[1] + "\n";
            message += "Quantity: " + record[2] + "\n";
            message += "Price: " + record[4] + "\n";
            message += "Category: " + record[5] + "\n\n";
        }
        message += "Multiple items found. Please search by Item ID for specific details.";
        JOptionPane.showMessageDialog(view, message, "Multiple Search Results", 
                                      JOptionPane.INFORMATION_MESSAGE);
        return;
    }

    // Single item found
    int foundIndex = foundIndices.get(0);
    String[] record = list.get(foundIndex);

    String message = "Item Found!\n\n" +
            "Item ID: " + record[0] + "\n" +
            "Item Name: " + record[1] + "\n" +
            "Quantity: " + record[2] + "\n" +
            "Price: " + record[4] + "\n" +
            "Category: " + record[5];

    JOptionPane.showMessageDialog(view, message, "Search Result", 
                                  JOptionPane.INFORMATION_MESSAGE);
}

    public void searchRecentItemFromID(String itemID) {
        if(model.isRecentQueueEmpty()) {
            JOptionPane.showMessageDialog(view, "Recent items list is empty");
            return;
        }
        
        String[][] queue = model.getRecentItemsQueue();
        int front = model.getQueueFront();
        int rear = model.getQueueRear();
        int maxSize = model.getMaxRecentItems();
        
        String[] foundItem = null;
        
        // Manual circular queue search
        int i = front;
        while(true) {
            if(queue[i] != null && queue[i][0].equalsIgnoreCase(itemID)) {
                foundItem = queue[i];
                break;
            }
            
            if(i == rear) {
                break;
            }
            
            i = (i + 1) % maxSize;
        }
        
        if (foundItem == null) {
            JOptionPane.showMessageDialog(view, "No recent item found with ID: " + itemID);
            return;
        }
        
        String message = "Recent Item Found!\n\n" +
                        "Item ID: " + foundItem[0] + "\n" +
                        "Item Name: " + foundItem[1] + "\n" +
                        "Quantity: " + foundItem[2] + "\n" +
                        "Price: " + foundItem[4] + "\n" +
                        "Category: " + foundItem[5];
        
        JOptionPane.showMessageDialog(view, message, "Search Result", JOptionPane.INFORMATION_MESSAGE);
    }

    public void searchRecentItemFromName(String itemName) {
    if(model.isRecentQueueEmpty()) {
        JOptionPane.showMessageDialog(view, "Recent items list is empty");
        return;
    }

    String[][] queue = model.getRecentItemsQueue();
    int front = model.getQueueFront();
    int rear = model.getQueueRear();
    int maxSize = model.getMaxRecentItems();

    ArrayList<String[]> foundItems = new ArrayList<>();

    // Linear search - Manual circular queue traversal to find all matches
    int i = front;
    while(true) {
        if(queue[i] != null && queue[i][1].toLowerCase().contains(itemName.toLowerCase())) {
            foundItems.add(queue[i]);
        }
        if(i == rear) {
            break;
        }
        i = (i + 1) % maxSize;
    }

    if (foundItems.isEmpty()) {
        JOptionPane.showMessageDialog(view, "No recent item found matching: " + itemName);
        return;
    }

    // If multiple items found
    if (foundItems.size() > 1) {
        String message = "Multiple recent items found!\n\n";
        for (int j = 0; j < foundItems.size(); j++) {
            String[] foundItem = foundItems.get(j);
            message += "Item " + (j + 1) + ":\n";
            message += "Item ID: " + foundItem[0] + "\n";
            message += "Item Name: " + foundItem[1] + "\n";
            message += "Quantity: " + foundItem[2] + "\n";
            message += "Price: " + foundItem[4] + "\n";
            message += "Category: " + foundItem[5] + "\n\n";
        }
        message += "Multiple items found. Please search by Item ID for specific details.";
        JOptionPane.showMessageDialog(view, message, "Multiple Search Results", 
                                      JOptionPane.INFORMATION_MESSAGE);
        return;
    }

    // Single item found
    String[] foundItem = foundItems.get(0);
    String message = "Recent Item Found!\n\n" +
            "Item ID: " + foundItem[0] + "\n" +
            "Item Name: " + foundItem[1] + "\n" +
            "Quantity: " + foundItem[2] + "\n" +
            "Price: " + foundItem[4] + "\n" +
            "Category: " + foundItem[5];

    JOptionPane.showMessageDialog(view, message, "Search Result", 
                                  JOptionPane.INFORMATION_MESSAGE);
}
    
    public void filterByCategory(String category) {
        DefaultTableModel tableModel = (DefaultTableModel) view.getjTable3().getModel();
        tableModel.setRowCount(0);
        
        LinkedList<String[]> categoryList = model.getCategoryLinkedList();
        
        if(categoryList.isEmpty()) {
            JOptionPane.showMessageDialog(view, "No items available");
            return;
        }
        
        if(category.equals("All Categories")) {
            // Show all items (customer view - without cost price and profit/loss)
            for(String[] item : categoryList) {
                if(item[0] != null) {
                    tableModel.addRow(new Object[]{
                        item[0],  // ItemID
                        item[1],  // ItemName
                        item[2],  // Quantity
                        item[4],  // Price (selling price)
                        item[5]   // Category
                    });
                }
            }
        } else {
            // Filter by specific category
            boolean found = false;
            for(String[] item : categoryList) {
                if(item[0] != null && item[5].equalsIgnoreCase(category)) {
                    tableModel.addRow(new Object[]{
                        item[0],  // ItemID
                        item[1],  // ItemName
                        item[2],  // Quantity
                        item[4],  // Price (selling price)
                        item[5]   // Category
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
