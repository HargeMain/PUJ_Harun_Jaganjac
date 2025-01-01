package org.harunjaganjac.example.ui;

import org.harunjaganjac.example.baseform.BaseForm;
import org.harunjaganjac.example.controllers.UserController;
import org.harunjaganjac.example.helpers.ValidationsHelpers;
import org.harunjaganjac.example.services.UserService;
import org.harunjaganjac.example.staticdata.Titles;

import javax.swing.*;

public class UserCreator extends BaseForm {
    private JLabel titleHeader;
    private JTextField usernameField;
    private JButton createUser;
    private JButton returnToLogin;
    private JTextField passwordField;
    private JTextField confirmPasswordField;
    private JTextField emailField;
    private JPanel mainPanel;
    private final UserService userService;

    UserCreator(){
        super(false);
        this.userService=new UserService();
        setContentPane(mainPanel);
        createUser.addActionListener(e -> {
            UserController userController=new UserController(userService);
            var username= usernameField.getText();
            var password= passwordField.getText();
            var confirmPassword= confirmPasswordField.getText();
            var email= emailField.getText();
            if(!TestValidationsUsernamePassword(username,password)){
                return;
            }
            if(!TestValidationsPasswordConfirmPassword(password,confirmPassword)){
                return;
            }
            if(!TestValidationsEmail(email)){
                return;
            }
            var user=userController.createUser(username,password,email,"default");
            if(user.isSuccess()) {
                JOptionPane.showMessageDialog(null, "User created successfully");
                dispose();
                new Register(user.getUser());
            }else{
                JOptionPane.showMessageDialog(null, user.getMessage());
            }
        });
        returnToLogin.addActionListener(e -> {
            new Register(null);
        });
    }

    private void createUIComponents() {
       titleHeader = new JLabel(Titles.CreateUserTitle);
    }
    //Inner helpers --START
    private boolean TestValidationsUsernamePassword(String username,String password){
        if(!ValidationsHelpers.isValidUsername(username)){
            JOptionPane.showMessageDialog(null, "Username is too short", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if(!ValidationsHelpers.isValidPassword(password)){
            JOptionPane.showMessageDialog(null, "Password is too short", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    private boolean TestValidationsPasswordConfirmPassword(String password,String confirmPassword){
        if(!ValidationsHelpers.isPasswordMatch(password,confirmPassword)){
            JOptionPane.showMessageDialog(null, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    private boolean TestValidationsEmail(String email){
        if(!ValidationsHelpers.isValidEmail(email)){
            JOptionPane.showMessageDialog(null, "Email is not valid", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    //Inner helpers --END
}
