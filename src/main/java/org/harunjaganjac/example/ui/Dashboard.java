package org.harunjaganjac.example.ui;

import com.kitfox.svg.app.beans.SVGIcon;
import org.harunjaganjac.example.baseform.BaseForm;
import org.harunjaganjac.example.controllers.EmployeeController;
import org.harunjaganjac.example.controllers.ProjectController;
import org.harunjaganjac.example.models.User;
import org.harunjaganjac.example.services.EmployeeProjectService;
import org.harunjaganjac.example.services.EmployeeService;
import org.harunjaganjac.example.services.ProjectService;
import org.harunjaganjac.example.services.UserService;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.List;

public class Dashboard extends BaseForm {
    private JPanel mainPanel;
    private JLabel title;
    private JButton resetPasswordButton;
    private JButton projectsNav;
    private JButton usersNav;
    private JButton applicationGuide;
    private JButton employeesNav;
    private JPanel rotationScreen;
    private JLabel loggedUser;
    private JLabel role;
    private JButton logOutButton;
    private final UserService userService;
    private final ProjectService projectService;
    private final EmployeeProjectService employeeProjectService;
    private final EmployeeService employeeService;

    public Dashboard(User user) {
        super(true);
        setContentPane(mainPanel);
        setSize(1024,1024);
        loggedUser.setText(user.getUsername());
        role.setText(user.getRole());
        this.userService=new UserService();
        this.projectService=new ProjectService();
        this.employeeService=new EmployeeService();
        this.employeeProjectService=new EmployeeProjectService();
        setSvgIcons("/images/2users-svgrepo-com.svg", usersNav);
        setSvgIcons("/images/employees-svgrepo-com.svg", employeesNav);
        setSvgIcons("/images/book-open-svgrepo-com.svg", applicationGuide);
        setSvgIcons("/images/projects-svgrepo-com.svg", projectsNav);
        adjustRoles(user.getRole());
        logOutButton.addActionListener(e -> {
            dispose();
            new Register(user);
        });
        projectsNav.addActionListener(e -> {
            try{
                selectedNav(projectsNav);
                rotationScreen.removeAll();
                rotationScreen.setLayout(new BorderLayout());
                var projectController = new ProjectController(projectService,employeeProjectService);
                var projects = projectController.getAllProjects();
                var table=createGenericTable(projects);
                rotationScreen.add(new JScrollPane(table),BorderLayout.CENTER);
                rotationScreen.revalidate();
                rotationScreen.repaint();
            }catch (Exception ex){
                ex.printStackTrace();
            }
        });
        employeesNav.addActionListener(e -> {
            try{
                selectedNav(employeesNav);
                rotationScreen.removeAll();
                rotationScreen.setLayout(new BorderLayout());
                var employeeController = new EmployeeController(employeeService,employeeProjectService);
                var employees = employeeController.getAllEmployees();
                var table=createGenericTable(employees);
                rotationScreen.add(new JScrollPane(table),BorderLayout.CENTER);
                rotationScreen.revalidate();
                rotationScreen.repaint();
            }catch (Exception ex){
                ex.printStackTrace();
            }
        });
        usersNav.addActionListener(e -> {
            try{
                selectedNav(usersNav);
                rotationScreen.removeAll();
                rotationScreen.setLayout(new BorderLayout());
                var users = userService.getAllUsers();
                var table=createGenericTable(users);
                rotationScreen.add(new JScrollPane(table),BorderLayout.CENTER);
                rotationScreen.revalidate();
                rotationScreen.repaint();
            }catch (Exception ex){
                ex.printStackTrace();
            }

        });
        applicationGuide.addActionListener(e -> {
            try {
                selectedNav(applicationGuide);
                rotationScreen.removeAll();
                rotationScreen.setLayout(new BorderLayout());

                var textArea = new JTextPane();
                textArea.setContentType("text/html");
                textArea.setText(
                        "<html>" +
                                "<body style='font-family: Arial, sans-serif;'>" +
                                "<h1>Welcome to the Application Guide for HR Dashboard!</h1>" +
                                "<p>This application allows you to manage projects, employees, and users. Here is a brief overview of what each role can do:</p>" +
                                "<h2>Superadmin</h2>" +
                                "<ul>" +
                                "<li>Full access to all features.</li>" +
                                "<li>Can create, read, update, and delete (CRUD) users, projects, and employees.</li>" +
                                "</ul>" +
                                "<h2>Admin</h2>" +
                                "<ul>" +
                                "<li>Can manage projects and employees.</li>" +
                                "<li>Can create, read, update, and delete (CRUD) projects and employees.</li>" +
                                "<li>Cannot manage users.</li>" +
                                "</ul>" +
                                "<h2>Default</h2>" +
                                "<ul>" +
                                "<li>Can view data of projects and employees.</li>" +
                                "<li>Cannot access user management features.</li>" +
                                "</ul>" +
                                "<p>Use the navigation buttons to switch between different sections of the application. Each section provides specific functionalities based on your role.</p>" +
                                "</body>" +
                                "</html>"
                );
                textArea.setEditable(false);

                rotationScreen.add(new JScrollPane(textArea), BorderLayout.CENTER);
                rotationScreen.revalidate();
                rotationScreen.repaint();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        resetPasswordButton.addActionListener(e -> {
        dispose();
        new ResetPassword(false,user);
        });
    }

    //Inner helpers --START
    private void setSvgIcons(String path, JButton button) {
        try {
            URL iconURL = getClass().getResource(path);
            if (iconURL != null) {
                SVGIcon svgIcon = new SVGIcon();
                svgIcon.setSvgURI(iconURL.toURI());
                svgIcon.setPreferredSize(new java.awt.Dimension(16, 16));
                button.setIcon(svgIcon);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private <T> JTable createGenericTable(List<T> data) {
        if (data == null || data.isEmpty()) {
            return new JTable();
        }
        Class<?> clazz = data.get(0).getClass();
        Field[] fields = clazz.getDeclaredFields();

        String[] columnNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            columnNames[i] = fields[i].getName();
        }

        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        for (T obj : data) {
            Object[] row = new Object[fields.length];
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                try {
                    Object value = fields[i].get(obj);
                    if (value instanceof List) {
                        row[i] = new JComboBox<>(((List<?>) value).toArray());
                    } else {
                        row[i] = value;
                    }
                } catch (IllegalAccessException e) {
                    row[i] = "Error";
                }
            }
            model.addRow(row);
        }

        JTable table = new JTable(model) {
            @Override
            public TableCellRenderer getCellRenderer(int row, int column) {
                if (getValueAt(row, column) instanceof JComboBox) {
                    return new DefaultTableCellRenderer() {
                        @Override
                        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                            if (value instanceof JComboBox) {
                                JComboBox<?> comboBox = (JComboBox<?>) value;
                                comboBox.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Add padding
                                return comboBox;
                            }
                            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                        }
                    };
                }
                return super.getCellRenderer(row, column);
            }

            @Override
            public TableCellEditor getCellEditor(int row, int column) {
                if (getValueAt(row, column) instanceof JComboBox) {
                    JComboBox<?> comboBox = (JComboBox<?>) getValueAt(row, column);
                    return new DefaultCellEditor(comboBox) {
                        @Override
                        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                            return comboBox;
                        }

                        @Override
                        public Object getCellEditorValue() {
                            return comboBox;
                        }
                    };
                }
                return super.getCellEditor(row, column);
            }
        };

        table.setRowHeight(30);

        return table;
    }
    private void selectedNav(JButton buttonThatWasClicked){
        var buttons = new JButton[]{projectsNav,usersNav, applicationGuide,employeesNav};
        for (var button:buttons) {
            if(button.equals(buttonThatWasClicked)){
                button.setBackground(Color.decode("#800080"));
                button.setForeground(Color.WHITE);

            }else{
                button.setBackground(Color.WHITE);
                button.setForeground(Color.BLACK);
            }
        }

    }
    private void adjustRoles(String role){
        if(!role.equals("superadmin")){
            usersNav.setEnabled(false);
            usersNav.setContentAreaFilled(false);
            usersNav.setFocusPainted(false);
            usersNav.setOpaque(false);
        }
    }

    //Inner helpers --END
}



