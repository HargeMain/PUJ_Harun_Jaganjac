package org.harunjaganjac.example.ui;

import com.kitfox.svg.app.beans.SVGIcon;
import org.harunjaganjac.example.baseform.BaseForm;
import org.harunjaganjac.example.controllers.EmployeeController;
import org.harunjaganjac.example.controllers.ProjectController;
import org.harunjaganjac.example.controllers.UserController;
import org.harunjaganjac.example.models.Employee;
import org.harunjaganjac.example.models.Project;
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
    private JLabel moduleName;
    private final UserService userService;
    private final ProjectService projectService;
    private final EmployeeProjectService employeeProjectService;
    private final EmployeeService employeeService;

    public Dashboard(User user) {
        super(true);
        setContentPane(mainPanel);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(1024, screenSize.height);
        loggedUser.setText(user.getUsername());
        role.setText(user.getRole().toUpperCase());
        this.userService = new UserService();
        this.projectService = new ProjectService();
        this.employeeService = new EmployeeService();
        this.employeeProjectService = new EmployeeProjectService();
        JOptionPane.showMessageDialog(this,"WELCOME TO THE HR DASHBOARD "+user.getUsername()+"!"+"\nPlease note that this application is for demonstration purposes only. Do not use real data.\nOpen the application guide to learn more about the functionalities.");
        setSvgIcons("/images/2users-svgrepo-com.svg", usersNav);
        setSvgIcons("/images/employees-svgrepo-com.svg", employeesNav);
        setSvgIcons("/images/book-open-svgrepo-com.svg", applicationGuide);
        setSvgIcons("/images/projects-svgrepo-com.svg", projectsNav);
        adjustRoles(user.getRole());
        logOutButton.addActionListener(e -> {
            dispose();
            new Register(user);
        });
        projectsNav.addActionListener(e -> loadProjectsTable(user.getRole()));
        employeesNav.addActionListener(e -> loadEmployeesTable(user.getRole()));
        usersNav.addActionListener(e -> loadUsersTable(user.getRole()));
        applicationGuide.addActionListener(e -> loadApplicationGuide());
        resetPasswordButton.addActionListener(e -> {
            dispose();
            new ResetPassword(false, user);
        });
    }

    private void loadProjectsTable(String role) {
        try {
            selectedNav(projectsNav);
            rotationScreen.removeAll();
            rotationScreen.setLayout(new BorderLayout());
            var projectController = new ProjectController(projectService, employeeProjectService);
            var projects = projectController.getAllProjects();
            var table = createGenericTable(projects, role);
            addCrudButton(rotationScreen, "Add Project", () -> {
                ProjectAddEdit projectAddEdit = new ProjectAddEdit("New project", "");
                projectAddEdit.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                        refreshProjectsTable();
                    }
                });
            },role);
            rotationScreen.add(new JScrollPane(table), BorderLayout.CENTER);
            rotationScreen.revalidate();
            rotationScreen.repaint();
            moduleName.setText("Projects");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void loadEmployeesTable(String role) {
        try {
            selectedNav(employeesNav);
            rotationScreen.removeAll();
            rotationScreen.setLayout(new BorderLayout());
            var employeeController = new EmployeeController(employeeService, employeeProjectService);
            var employees = employeeController.getAllEmployees();
            var table = createGenericTable(employees, role);
            addCrudButton(rotationScreen, "Add Employee", () -> {
                EmployeeAddEdit employeeAddEdit = new EmployeeAddEdit("New employee", "");
                employeeAddEdit.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                        refreshEmployeesTable();
                    }
                });
            },role);
            rotationScreen.add(new JScrollPane(table), BorderLayout.CENTER);
            rotationScreen.revalidate();
            rotationScreen.repaint();
            moduleName.setText("Employees");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void loadUsersTable(String role) {
        try {
            selectedNav(usersNav);
            rotationScreen.removeAll();
            rotationScreen.setLayout(new BorderLayout());
            var users = userService.getAllUsers();
            var table = createGenericTable(users, role);
            addCrudButton(rotationScreen, "Add User", () -> {
                UserCreator userAddEdit = new UserCreator(true);
                userAddEdit.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                        refreshUsersTable();
                    }
                });
            },role);
            rotationScreen.add(new JScrollPane(table), BorderLayout.CENTER);
            rotationScreen.revalidate();
            rotationScreen.repaint();
            moduleName.setText("Users");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void loadApplicationGuide() {
        try {
            selectedNav(applicationGuide);
            rotationScreen.removeAll();
            rotationScreen.setLayout(new BorderLayout());

            var textArea = new JTextPane();
            textArea.setContentType("text/html");
            textArea.setText(
                    "<html>" +
                            "<head>" +
                            "<style>" +
                            "@keyframes fadeIn {" +
                            "  from { opacity: 0; }" +
                            "  to { opacity: 1; }" +
                            "}" +
                            "body {" +
                            "  font-family: Arial, sans-serif;" +
                            "  background-color: #f0f0f0;" +
                            "  animation: fadeIn 2s ease-in-out;" +
                            "}" +
                            "h1, h2 {" +
                            "  color: #800080;" +
                            "  animation: fadeIn 2s ease-in-out;" +
                            "}" +
                            "ul {" +
                            "  list-style-type: none;" +
                            "  padding: 0;" +
                            "}" +
                            "li {" +
                            "  background: #800080;" +
                            "  color: white;" +
                            "  margin: 5px 0;" +
                            "  padding: 10px;" +
                            "  border-radius: 5px;" +
                            "  animation: fadeIn 2s ease-in-out;" +
                            "}" +
                            "p {" +
                            "  animation: fadeIn 2s ease-in-out;" +
                            "}" +
                            "</style>" +
                            "</head>" +
                            "<body>" +
                            "<h1>Welcome to the Application Guide for HR Dashboard!</h1>" +
                            "<p>This application allows you to manage projects, employees, and users. Here is a brief overview of what each role can do:</p>" +
                            "<h2>Superadmin</h2>" +
                            "<ul>" +
                            "<li>Full access to all features.</li>" +
                            "<li>Can create, read, update, and delete (CRUD)  projects, and employees.</li>" +
                            "<li>Can control access to the app(user- can add them, can delete them)</li>" +
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
            moduleName.setText("Application Guide");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void addCrudButton(JPanel panel, String buttonText, Runnable action,String role) {
        JButton button = new JButton(buttonText);
        if (role.equals("default")) {
            button.setEnabled(false);
        }
        button.addActionListener(e -> action.run());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(button);
        panel.add(buttonPanel, BorderLayout.NORTH);
    }

    private void refreshProjectsTable() {
        loadProjectsTable(role.getText());
    }

    private void refreshEmployeesTable() {
        loadEmployeesTable(role.getText());
    }

    private void refreshUsersTable() {
        loadUsersTable(role.getText());
    }

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

    private <T> JTable createGenericTable(List<T> data, String role) {
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
        if (!role.equals("default")) {
            JPopupMenu popupMenu = new JPopupMenu();
            JMenuItem addItem = new JMenuItem("Add");
            JMenuItem editItem = new JMenuItem("Edit");
            JMenuItem deleteItem = new JMenuItem("Delete");

            popupMenu.add(addItem);
            popupMenu.add(editItem);
            popupMenu.add(deleteItem);

            if(clazz.equals(User.class)){
                editItem.setVisible(false);
            }

            table.setComponentPopupMenu(popupMenu);
            table.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(java.awt.event.MouseEvent e) {
                    int row = table.rowAtPoint(e.getPoint());
                    table.getSelectionModel().setSelectionInterval(row, row);
                    if (e.getButton() == java.awt.event.MouseEvent.BUTTON3) {
                        popupMenu.show(table, e.getX(), e.getY());
                    }
                }
            });
            table.setSelectionBackground(Color.decode("#800080"));
            table.setSelectionForeground(Color.WHITE);

            addItem.addActionListener(e -> {
                if (clazz.equals(Project.class)) {
                    ProjectAddEdit projectAddEdit = new ProjectAddEdit("New project", "");
                    projectAddEdit.addWindowListener(new java.awt.event.WindowAdapter() {
                        @Override
                        public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                            refreshProjectsTable();
                        }
                    });
                } else if (clazz.equals(Employee.class)) {
                    EmployeeAddEdit employeeAddEdit = new EmployeeAddEdit("New employee", "");
                    employeeAddEdit.addWindowListener(new java.awt.event.WindowAdapter() {
                        @Override
                        public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                            refreshEmployeesTable();
                        }
                    });
                }else if(clazz.equals(User.class)){
                    UserCreator userAddEdit = new UserCreator(true);
                    userAddEdit.addWindowListener(new java.awt.event.WindowAdapter() {
                        @Override
                        public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                            refreshUsersTable();
                        }
                    });
                }
            });

            editItem.addActionListener(e -> {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    Object id = table.getValueAt(selectedRow, 0);
                    if (clazz.equals(Project.class)) {
                        ProjectAddEdit projectAddEdit = new ProjectAddEdit("Edit project", (String) id);
                        projectAddEdit.addWindowListener(new java.awt.event.WindowAdapter() {
                            @Override
                            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                                refreshProjectsTable();
                            }
                        });
                    } else if (clazz.equals(Employee.class)) {
                        EmployeeAddEdit employeeAddEdit = new EmployeeAddEdit("Edit employee", (String) id);
                        employeeAddEdit.addWindowListener(new java.awt.event.WindowAdapter() {
                            @Override
                            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                                refreshEmployeesTable();
                            }
                        });
                    }
                }
            });
            deleteItem.addActionListener(e -> {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete " + table.getValueAt(selectedRow, 1) + "?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                    if (response == JOptionPane.YES_OPTION) {
                        Object id = table.getValueAt(selectedRow, 0);
                        if (clazz.equals(User.class)) {
                            var userController = new UserController(userService);
                            userController.deleteUser((String) id);
                            usersNav.doClick();
                        } else if (clazz.equals(Project.class)) {
                            var projectController = new ProjectController(projectService, employeeProjectService);
                            projectController.deleteProject((String) id);
                            projectsNav.doClick();
                        } else if (clazz.equals(Employee.class)) {
                            var employeeController = new EmployeeController(employeeService, employeeProjectService);
                            employeeController.deleteEmployee((String) id);
                            employeesNav.doClick();
                        }
                        model.removeRow(selectedRow);
                    }
                }
            });
        }
        return table;
    }

    private void selectedNav(JButton buttonThatWasClicked) {
        var buttons = new JButton[]{projectsNav, usersNav, applicationGuide, employeesNav};
        for (var button : buttons) {
            if (button.equals(buttonThatWasClicked)) {
                button.setBackground(Color.decode("#800080"));
                button.setForeground(Color.WHITE);
            } else {
                button.setBackground(Color.WHITE);
                button.setForeground(Color.decode("#800080"));
            }
        }
    }

    private void adjustRoles(String role) {
        if (!role.equals("superadmin")) {
            usersNav.setEnabled(false);
            usersNav.setContentAreaFilled(false);
            usersNav.setFocusPainted(false);
            usersNav.setOpaque(false);
        }
    }
}