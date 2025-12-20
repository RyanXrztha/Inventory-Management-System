/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;
import Model.InventoryModel;
import View.AdminPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
/**
 *
 * @author aryan
 */
public class AdminPanelController {
    private InventoryModel model;
    private AdminPanel view;
    
    public AdminPanelController(AdminPanel view) {
        this.model = new InventoryModel();
        this.view = view;
    }
    
    public void enqueue(String itemID, String itemName, String quantity, String costPrice, String price, String category) {
        if(model.getRear() == model.getSize()-1){
            JOptionPane.showMessageDialog(view, "Inventory queue is full!");
            return;
        }
        if(model.getFront() == -1){
            model.setFront(0);
        }
        model.setRear(model.getRear() + 1);
       
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
        if(model.getTop() == model.getStackMax() - 1){
            JOptionPane.showMessageDialog(view, "Deleted history is full");
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
            JOptionPane.showMessageDialog(view, "No records in deleted history!");
            return;
        }
        String deletedName = model.getStack()[model.getTop()][1];
        JOptionPane.showMessageDialog(view, "Deleted from history: " + deletedName);
        for(int j=0; j<7; j++){
            model.getStack()[model.getTop()][j] = null;
        }
        model.setTop(model.getTop() - 1);
    }
    
    public void loadInventoryTable() {
        DefaultTableModel tableModel = (DefaultTableModel) view.getjTable3().getModel();
        tableModel.setRowCount(0);
        if(model.getFront() == -1 || model.getFront() > model.getRear()) return;
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
}
