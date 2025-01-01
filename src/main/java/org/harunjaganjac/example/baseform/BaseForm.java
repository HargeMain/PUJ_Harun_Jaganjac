package org.harunjaganjac.example.baseform;

import org.harunjaganjac.example.staticdata.Titles;

import javax.swing.*;

public abstract class BaseForm extends JFrame {
    public BaseForm(boolean isDashboard){
        if(isDashboard) {
            setTitle(Titles.DashboardTitle);
        }else{
            setTitle(Titles.ApplicationTitle);
        }
        setSize(500,500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/logo/HR.png"));
            setIconImage(icon.getImage());
        } catch (NullPointerException e) {
            System.err.println("Icon not found: " + e.getMessage());
        }
        setVisible(true);
    }
}
