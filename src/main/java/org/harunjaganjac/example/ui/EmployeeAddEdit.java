package org.harunjaganjac.example.ui;

import org.harunjaganjac.example.baseform.BaseForm;
import org.harunjaganjac.example.baseitem.ComboBoxItem;
import org.harunjaganjac.example.controllers.EmployeeController;
import org.harunjaganjac.example.controllers.ProjectController;
import org.harunjaganjac.example.models.Employee;
import org.harunjaganjac.example.services.EmployeeProjectService;
import org.harunjaganjac.example.services.EmployeeService;
import org.harunjaganjac.example.services.ProjectService;

import javax.swing.*;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeAddEdit extends BaseForm {

    private JPanel mainPanel;
    private JLabel titleHeader;
    private JTextField usernameField;
    private JTextField lastName;
    private JComboBox position;
    private JTextField salary;
    private JButton createEmployee;
    private JButton returnToLogin;
    private JLabel assignProjects;
    private JList<ComboBoxItem> projectsList;
    private EmployeeService employeeService;
    private EmployeeProjectService employeeProjectService;
    private ProjectService projectService;

    public EmployeeAddEdit(String title, String id) {
        super(false);
        setContentPane(mainPanel);
        employeeService = new EmployeeService();
        employeeProjectService = new EmployeeProjectService();
        projectService = new ProjectService();
        position.addItem("Software Engineer");
        position.addItem("Support");
        position.addItem("Manager");
        salary.setText("60000");
        if (id.equals("")) {
            titleHeader.setText("New employee");
            populateProjects(List.of());
        } else {
            titleHeader.setText("Edit employee");
            var employeeController = new EmployeeController(employeeService, employeeProjectService);
            var employee = employeeController.getEmployee(id);
            usernameField.setText(employee.getFirstName());
            lastName.setText(employee.getLastName());
            position.setSelectedItem(employee.getPosition());
            salary.setText(String.valueOf(employee.getSalary()));
            populateProjects(employee.getAssignedProjects());
        }
        returnToLogin.addActionListener(e -> dispose());

        createEmployee.addActionListener(e -> {
            var employeeController = new EmployeeController(employeeService, employeeProjectService);
            var firstName = usernameField.getText();
            var lastName = this.lastName.getText();
            var position = (String) this.position.getSelectedItem();
            var salary = Integer.parseInt(this.salary.getText());
            if (firstName.isEmpty() || lastName.isEmpty() || position.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill all fields");
                return;
            }
            var assignedProjects = projectsList.getSelectedValuesList().stream()
                    .map(ComboBoxItem::getId)
                    .collect(Collectors.toList());
            if (id.isEmpty()) {
                var employee = employeeController.createEmployee(new Employee(firstName, lastName, position, salary, assignedProjects, "Active"));
                if (employee.getEmployeeId().isEmpty()) {
                    JOptionPane.showMessageDialog(null, employee.getMessage());
                    return;
                }
                if (employee.isSuccess()) {
                    for (String projectId : assignedProjects) {
                        employeeProjectService.assignProject(employee.getEmployeeId(), projectId);
                    }
                    JOptionPane.showMessageDialog(null, "Employee created successfully");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, employee.getMessage());
                }
            } else {
                var updateEmployee = new Employee(firstName, lastName, position, salary, assignedProjects, "Active");
                var employee = employeeController.updateEmployee(id, updateEmployee);
                if (employee.isSuccess()) {
                    var assignedProjectsOld = employeeProjectService.getAssignedProjects(id);
                    for (String projectId : assignedProjectsOld) {
                        if (!assignedProjects.contains(projectId)) {
                            employeeProjectService.removeEmployeeFromProject(id, projectId);
                        }
                    }
                    for (String projectId : assignedProjects) {
                        if (!assignedProjectsOld.contains(projectId)) {
                            employeeProjectService.assignProject(id, projectId);
                        }
                    }
                    JOptionPane.showMessageDialog(null, "Employee updated successfully");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, employee.getMessage());
                }
            }
        });

        position.addActionListener(e -> updateSalary());
    }

    private void updateSalary() {
        String selectedPosition = (String) position.getSelectedItem();
        if (selectedPosition != null) {
            switch (selectedPosition) {
                case "Software Engineer":
                    salary.setText("60000");
                    break;
                case "Support":
                    salary.setText("40000");
                    break;
                case "Manager":
                    salary.setText("80000");
                    break;
            }
        }
    }

    private void populateProjects(List<String> assignedProjects) {
        var projectsController = new ProjectController(projectService, employeeProjectService);
        var projects = projectsController.getAllProjects().stream()
                .map(proj -> new ComboBoxItem(proj.getId(), proj.getName()))
                .collect(Collectors.toList());
        DefaultListModel<ComboBoxItem> model = new DefaultListModel<>();
        for (ComboBoxItem item : projects) {
            model.addElement(item);
        }
        projectsList.setModel(model);
        projectsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        if (!assignedProjects.isEmpty()) {
            for (String assignedItem : assignedProjects) {
                for (int i = 0; i < model.getSize(); i++) {
                    ComboBoxItem element = model.getElementAt(i);
                    if (element.getId() != null && element.getId().equals(assignedItem)) {
                        projectsList.addSelectionInterval(i, i);
                    }
                }
            }
        }
    }
}