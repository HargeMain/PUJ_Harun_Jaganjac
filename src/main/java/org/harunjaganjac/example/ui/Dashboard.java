package org.harunjaganjac.example.ui;

import org.harunjaganjac.example.models.User;

import javax.swing.*;

public class Dashboard extends JFrame {
    private JPanel panel1;
    private JButton logOutButton;
    private JLabel userData;

    public Dashboard(User user){
        setTitle("Dashboard");
        setSize(500,500);
        userData.setText(user.getRole());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setContentPane(panel1);
        logOutButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(null,"Logging out");
            new Register();
        });
    }
}
