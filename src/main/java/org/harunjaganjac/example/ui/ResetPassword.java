package org.harunjaganjac.example.ui;

import org.harunjaganjac.example.baseform.BaseForm;
import org.harunjaganjac.example.controllers.UserController;
import org.harunjaganjac.example.helpers.ValidationsHelpers;
import org.harunjaganjac.example.models.User;
import org.harunjaganjac.example.services.UserService;

import javax.swing.*;

public class ResetPassword extends BaseForm {
    private JPanel mainPanel;
    private JTextField newPassword;
    private JButton cancelButton;
    private JButton changeButton;
    private JLabel title;
    private JTextField oldPassword;
    private final UserService userService;

    public ResetPassword(boolean isDashboard, User loggedUser) {
        super(isDashboard);
        userService = new UserService();
        setContentPane(mainPanel);
        title.setText("Reset Password");
        cancelButton.addActionListener(e -> {
            dispose();
            new Dashboard(loggedUser);
        });
        changeButton.addActionListener(e -> {
                    var userController = new UserController(userService);
                    if (!ValidationsHelpers.isValidPassword(oldPassword.getText())) {
                        JOptionPane.showMessageDialog(this, "Old password is not correct", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (!ValidationsHelpers.isValidPassword(newPassword.getText())) {
                        JOptionPane.showMessageDialog(this, "Password length incorrect", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (ValidationsHelpers.isPasswordMatch(newPassword.getText(), oldPassword.getText())) {
                        JOptionPane.showMessageDialog(this, "Password already in the system", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        var response = userController.resetPassword(loggedUser.getUsername(), oldPassword.getText(), newPassword.getText());
                        if (response.isSuccess()) {
                            JOptionPane.showMessageDialog(this, "Password changed successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                            dispose();
                            new Dashboard(loggedUser);
                        } else {
                            JOptionPane.showMessageDialog(this, response.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                }
        );
    }
}
