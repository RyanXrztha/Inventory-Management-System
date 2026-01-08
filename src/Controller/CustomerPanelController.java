/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;
import Model.InventoryModel;
import View.CustomerPanel;
import java.util.ArrayList;
import java.util.Queue;
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

    
        public void sortByName() {
        ArrayList<String[]> list = model.getInventoryList();
        int size = list.size();

        for (int step = 1; step < size; step++) {
            String[] key = list.get(step);
            int j = step - 1;

            while (j >= 0 && list.get(j)[1].compareToIgnoreCase(key[1]) > 0) {
                list.set(j + 1, list.get(j));
                j--;
            }

            list.set(j + 1, key);
        }
        loadCustomerTable();
    }



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



    private void mergeSort(ArrayList<String[]> list, int left, int right) {
        if (left < right) {

            int mid = left + (right - left) / 2;

            mergeSort(list, left, mid);

            mergeSort(list, mid + 1, right);

            merge(list, left, mid, right);
        }
    }

    private void merge(ArrayList<String[]> list, int left, int mid, int right) {
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

            if (leftPrice <= rightPrice) {
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

    public void sortBySellingPrice() {
        ArrayList<String[]> list = model.getInventoryList();

        if (list.isEmpty()) {
            return;
        }

        mergeSort(list, 0, list.size() - 1);

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

        // Linear search with partial matching
        int foundIndex = -1;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i)[1].toLowerCase().contains(itemName.toLowerCase())) {
                foundIndex = i;
                break;
            }
        }

        if (foundIndex == -1) {
            JOptionPane.showMessageDialog(view, "No item found matching: " + itemName);
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
        
        String[] foundItem = null;
        
        // Manual circular queue search
        int i = front;
        while(true) {
            if(queue[i] != null && queue[i][1].toLowerCase().contains(itemName.toLowerCase())) {
                foundItem = queue[i];
                break;
            }
            
            if(i == rear) {
                break;
            }
            
            i = (i + 1) % maxSize;
        }
        
        if (foundItem == null) {
            JOptionPane.showMessageDialog(view, "No recent item found matching: " + itemName);
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

}
