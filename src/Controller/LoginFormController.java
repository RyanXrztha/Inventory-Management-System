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
    public static void validateLogin(LoginForm view) {

        String username = view.getUserName().trim();
        String password = view.getUserPassword().trim();

        // --- VALIDATION HANDLED IN CONTROLLER ONLY ---
        if (username.isEmpty() && password.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Username and Password cannot be empty!", "Empty Fields", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Username cannot be empty!", "Empty Username", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Password cannot be empty!", "Empty Password", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if(username.equals("admin") && password.equals("admin")){
            AdminPanel Admin = new AdminPanel();
            Admin.setVisible(true);
        }
        else{
            CustomerPanel Customer = new CustomerPanel();
            Customer.setVisible(true);
        }

        // If both are filled → Success for now
        JOptionPane.showMessageDialog(view, "Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}
