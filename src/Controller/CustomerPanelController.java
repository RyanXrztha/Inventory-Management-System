/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;
import Model.InventoryModel;
import View.CustomerPanel;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author aryan
 */
public class CustomerPanelController {
     private InventoryModel model;
    private CustomerPanel view;

    public CustomerPanelController(CustomerPanel view) {
        this.view = view;
        this.model = InventoryModel.getInstance(); 
        loadCustomerTable();
    }

    public void loadCustomerTable() {
        DefaultTableModel dtm = (DefaultTableModel) view.getjTable1().getModel();
        dtm.setRowCount(0);

        if (model.getFront() == -1) return;

        for (int i = model.getFront(); i <= model.getRear(); i++) {
            if (model.getQueue()[i][0] != null) {
                dtm.addRow(new Object[]{
                    model.getQueue()[i][0],
                    model.getQueue()[i][1],
                    model.getQueue()[i][2],
                    model.getQueue()[i][4],
                    model.getQueue()[i][5]
                });
            }
        }
    }
}
