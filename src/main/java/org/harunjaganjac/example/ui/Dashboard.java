package org.harunjaganjac.example.ui;

import org.harunjaganjac.example.baseform.BaseForm;
import org.harunjaganjac.example.models.User;

import javax.swing.*;

public class Dashboard extends BaseForm {
    private JPanel panel1;
    private JButton logOutButton;
    private JLabel userData;

    public Dashboard(User user){
        super(true);
        userData.setText(user.getRole());
        setContentPane(panel1);
        logOutButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(null,"Logging out");
            new Register(null);
        });
    }
}
