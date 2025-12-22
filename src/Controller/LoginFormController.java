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

        if (username.isEmpty() && password.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Username and Password cannot be empty!", 
                "Empty Fields", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Username cannot be empty!", 
                "Empty Username", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Password cannot be empty!", 
                "Empty Password", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if(username.equals("admin") && password.equals("admin")){
            JOptionPane.showMessageDialog(view, "Admin Login Successful!", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
            AdminPanel admin = new AdminPanel();
            admin.setVisible(true);
            view.dispose(); // ✅ Close login form
        }
        else {
            JOptionPane.showMessageDialog(view, "Customer Login Successful!", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
            CustomerPanel customer = new CustomerPanel();
            customer.setVisible(true);
            view.dispose(); // ✅ Close login form
        }
    }
}
