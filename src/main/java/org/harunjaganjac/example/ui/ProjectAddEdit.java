package org.harunjaganjac.example.ui;

import org.harunjaganjac.example.baseform.BaseForm;
import org.harunjaganjac.example.baseitem.ComboBoxItem;
import org.harunjaganjac.example.controllers.EmployeeController;
import org.harunjaganjac.example.controllers.ProjectController;
import org.harunjaganjac.example.models.Project;
import org.harunjaganjac.example.services.EmployeeProjectService;
import org.harunjaganjac.example.services.EmployeeService;
import org.harunjaganjac.example.services.ProjectService;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectAddEdit extends BaseForm {

    private JPanel mainPanel;
    private JLabel titleHeader;
    private JTextField usernameField;
    private JButton createProject;
    private JButton returnToLogin;
    private JTextArea textArea1;
    private JSpinner startDate;
    private JLabel employees;
    private JList<ComboBoxItem> employeesList;
    private JSpinner endDate;
    private EmployeeService employeeService;
    private ProjectService projectService;
    private EmployeeProjectService employeeProjectService;

    public ProjectAddEdit(String title, String id) {
        super(false);
        setContentPane(mainPanel);
        setDateFormat();
        employeeService = new EmployeeService();
        projectService = new ProjectService();
        employeeProjectService = new EmployeeProjectService();
        if (id.equals("")) {
            titleHeader.setText("New project");
            populateEmployees(id, List.of());
        } else {
            titleHeader.setText("Edit project");
            var projectController = new ProjectController(projectService, employeeProjectService);
            var project = projectController.getProject(id);
            usernameField.setText(project.getName());
            textArea1.setText(project.getDescription());
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                if (project.getStartDate() != null) {
                    startDate.setValue(dateFormat.parse(project.getStartDate()));
                }
                if (project.getEndDate() != null) {
                    endDate.setValue(dateFormat.parse(project.getEndDate()));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            populateEmployees(id, project.getAssignedEmployees());
        }
        returnToLogin.addActionListener(e -> dispose());

        createProject.addActionListener(e -> {
            var projectController = new ProjectController(projectService, employeeProjectService);
            var name = usernameField.getText();
            var description = textArea1.getText();
            var startDate = new SimpleDateFormat("yyyy-MM-dd").format((Date) this.startDate.getValue());
            var endDate = new SimpleDateFormat("yyyy-MM-dd").format((Date) this.endDate.getValue());
            if (name.isEmpty() || description.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill all fields");
                return;
            }
            var assignedEmployees = employeesList.getSelectedValuesList().stream()
                    .map(ComboBoxItem::getId)
                    .collect(Collectors.toList());
            if (id.isEmpty()) {
                var project = projectController.createProject(new Project(name, description, startDate, endDate, assignedEmployees));
                if (project.getProjectId().isEmpty()) {
                    JOptionPane.showMessageDialog(null, project.getMessage());
                    return;
                }
                if (project.isSuccess()) {
                    for (String employeeId : assignedEmployees) {
                        employeeProjectService.assignProject(employeeId, project.getProjectId());
                    }
                    JOptionPane.showMessageDialog(null, "Project created successfully");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, project.getMessage());
                }
            } else {
                var updateProject = new Project(name, description, startDate, endDate, assignedEmployees);
                var project = projectController.updateProject(id, updateProject);
                if (project.isSuccess()) {
                    var assignedEmployeesOld = employeeProjectService.getAssignedEmployees(id);
                    for (String employeeId : assignedEmployeesOld) {
                        if (!assignedEmployees.contains(employeeId)) {
                            employeeProjectService.removeEmployeeFromProject(employeeId, id);
                        }
                    }
                    for (String employeeId : assignedEmployees) {
                        if (!assignedEmployeesOld.contains(employeeId)) {
                            employeeProjectService.assignProject(employeeId, id);
                        }
                    }
                    JOptionPane.showMessageDialog(null, "Project updated successfully");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, project.getMessage());
                }
            }
        });
    }

    // Inner helpers --START
    private void setDateFormat() {
        if (startDate != null) {
            SpinnerDateModel dateModel1 = new SpinnerDateModel();
            startDate.setModel(dateModel1);
            JSpinner.DateEditor editor1 = new JSpinner.DateEditor(startDate, "yyyy-MM-dd");
            startDate.setEditor(editor1);
        }

        if (endDate != null) {
            SpinnerDateModel dateModel2 = new SpinnerDateModel();
            endDate.setModel(dateModel2);
            JSpinner.DateEditor editor2 = new JSpinner.DateEditor(endDate, "yyyy-MM-dd");
            endDate.setEditor(editor2);
        }
    }

    private void populateEmployees(String projectId, List<String> assignedEmployees) {
        var employeeController = new EmployeeController(employeeService, employeeProjectService);
        var employees = employeeController.getAllEmployees().stream()
                .map(emp -> new ComboBoxItem(emp.getId(), emp.getFirstName()))
                .collect(Collectors.toList());
        DefaultListModel<ComboBoxItem> model = new DefaultListModel<>();
        for (ComboBoxItem item : employees) {
            model.addElement(item);
        }
        employeesList.setModel(model);
        employeesList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        if (!projectId.isEmpty()) {
            for (String assignedItem : assignedEmployees) {
                for (int i = 0; i < model.getSize(); i++) {
                    ComboBoxItem element = model.getElementAt(i);
                    if (element.getId() != null && element.getId().equals(assignedItem)) {
                        employeesList.addSelectionInterval(i, i);
                    }
                }
            }
        }
    }
    // Inner helpers --END
}