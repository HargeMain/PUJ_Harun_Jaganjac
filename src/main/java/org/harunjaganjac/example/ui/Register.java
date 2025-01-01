package org.harunjaganjac.example.ui;

import com.kitfox.svg.app.beans.SVGIcon;
import org.harunjaganjac.example.baseform.BaseForm;
import org.harunjaganjac.example.controllers.UserController;
import org.harunjaganjac.example.helpers.ValidationsHelpers;
import org.harunjaganjac.example.models.User;
import org.harunjaganjac.example.services.UserService;
import org.harunjaganjac.example.staticdata.Titles;

import javax.swing.*;
import java.net.URL;

public class Register extends BaseForm {

    private JPanel mainPanel;
    private JLabel titleHeader;
    private JTextField usernameField;
    private JButton loginButton;
    private JButton registerButton;
    private JButton hideShowPasswordButton;
    private JPasswordField passwordField;
    private final UserService userService;

    public Register(User registerUser){
        super(false);
        setContentPane(mainPanel);
        this.userService=new UserService();
        setSvgIcons("/images/view-hide-svgrepo-com.svg",false);
        passwordField.setEchoChar((char)0);
        if(registerUser!=null){
            usernameField.setText(registerUser.getUsername());
            passwordField.setText(registerUser.getPassword());
        }
        loginButton.addActionListener(e -> {
            var loginController=new UserController(userService);
            var username= usernameField.getText();
            var password=new String(passwordField.getPassword());
            if(!TestValidations(username,password)){
                return;
            }
            var user=loginController.login(username,password);
            if(user!=null){
                dispose();
                new Dashboard(user);
                }else{
                    JOptionPane.showMessageDialog(null,"Login failed");
                }
    });
        registerButton.addActionListener(e -> {
            dispose();
            new UserCreator();
        });
        hideShowPasswordButton.addActionListener(e -> {
            if(passwordField.getEchoChar()==0){
                passwordField.setEchoChar('*');
               setSvgIcons("/images/view-svgrepo-com.svg",true);
            }else{
                passwordField.setEchoChar((char)0);
                setSvgIcons("/images/view-hide-svgrepo-com.svg",false);
            }
        });
    }

    //Inner helpers --START
    private boolean TestValidations(String username,String password){
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
    private void setSvgIcons(String name, boolean isHidden) {
        try {
            URL iconURL = getClass().getResource(name);
            if (iconURL == null) {
                throw new Exception("Resource not found: " + name);
            }
            SVGIcon svgIcon = new SVGIcon();
            svgIcon.setSvgURI(iconURL.toURI());
            svgIcon.setPreferredSize(new java.awt.Dimension(16, 16));
            hideShowPasswordButton.setIcon(svgIcon);
        } catch (Exception e) {
            if (isHidden) {
                hideShowPasswordButton.setText("Show");
            } else {
                hideShowPasswordButton.setText("Hide");
            }
        }
    }
    //Inner helpers --END
    private void createUIComponents() {
        titleHeader =new JLabel(Titles.ApplicationTitle);
    }
}
