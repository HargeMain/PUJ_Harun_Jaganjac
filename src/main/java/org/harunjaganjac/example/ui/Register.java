package org.harunjaganjac.example.ui;

import org.harunjaganjac.example.staticdata.Titles;

import javax.swing.*;

public class Register extends JFrame {

    private JPanel panel1;
    private JLabel TitleHeader;
    private JTextField textField1;
    private JTextField textField2;
    private JButton loginButton;
    private JButton registerButton;

    public Register(){
        setTitle(Titles.ApplicationTitle);
        setSize(500,500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
