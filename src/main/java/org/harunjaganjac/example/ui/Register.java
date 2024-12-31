package org.harunjaganjac.example.ui;

import com.kitfox.svg.app.beans.SVGIcon;
import org.harunjaganjac.example.controllers.UserController;
import org.harunjaganjac.example.helpers.ValidationsHelpers;
import org.harunjaganjac.example.services.UserService;
import org.harunjaganjac.example.staticdata.Titles;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class Register extends JFrame {

    private JPanel panel1;
    private JLabel TitleHeader;
    private JTextField textField1;
    private JButton loginButton;
    private JButton registerButton;
    private JButton hideShowPasswordButton;
    private JPasswordField passwordField1;

    public Register(){
        setTitle(Titles.ApplicationTitle);
        setSize(500,500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setContentPane(panel1);
        setSvgIcons("/images/view-hide-svgrepo-com.svg",false);
        passwordField1.setEchoChar((char)0);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var userServices=new UserService();
                var loginController=new UserController(userServices);
                var username=textField1.getText();
                var password=new String(passwordField1.getPassword());
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
        }
        });
        hideShowPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(passwordField1.getEchoChar()==0){
                    passwordField1.setEchoChar('*');
                   setSvgIcons("/images/view-svgrepo-com.svg",true);
                }else{
                    passwordField1.setEchoChar((char)0);
                    setSvgIcons("/images/view-hide-svgrepo-com.svg",false);
                }
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
        TitleHeader=new JLabel(Titles.ApplicationTitle);
    }
}
