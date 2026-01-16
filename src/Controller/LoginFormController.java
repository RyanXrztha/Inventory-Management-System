/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;
import View.LoginForm;
import javax.swing.JOptionPane;
import View.AdminPanel;
import View.CustomerPanel;
/**
 *
 * @author aryan
 */
public class LoginFormController {
    
    // It validates user and admin login data and directs user or admin to their respective panels.
    public static void Login(LoginForm view) {

        String username = view.getUserName();
        String password = view.getPassword();

        //Checks if both username and password are empty
        if (username.isEmpty() && password.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Please enter both username and password", "Both fields are not entered", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Please enter username", "Username is not entered", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Please enter password", "Password is not entered", JOptionPane.WARNING_MESSAGE);
            return;
        }

        //Directs customer/admin to their panel
        if(username.equalsIgnoreCase("RyanXrztha12") && password.equalsIgnoreCase("admin")){
            JOptionPane.showMessageDialog(view, "Welcome Admin", "Login Sucessful", JOptionPane.INFORMATION_MESSAGE);
            AdminPanel admin = new AdminPanel();
            admin.setVisible(true);
            view.dispose();
        }
        else {
            JOptionPane.showMessageDialog(view, "Welcome to our store", "Login Sucessful", JOptionPane.INFORMATION_MESSAGE);
            CustomerPanel customer = new CustomerPanel();
            customer.setVisible(true);
            view.dispose();
        }
    }
}
