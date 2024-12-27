package org.harunjaganjac.example.controllers;

import org.harunjaganjac.example.models.Employee;
import org.harunjaganjac.example.services.EmployeeProjectService;
import org.harunjaganjac.example.services.EmployeeService;

import java.util.List;

public final class EmployeeController {
    private final EmployeeService employeeService;
    private final EmployeeProjectService employeeProjectService;

    public EmployeeController(EmployeeService employeeService, EmployeeProjectService employeeProjectService) {
        this.employeeService = employeeService;
        this.employeeProjectService = employeeProjectService;
    }
    public boolean createEmployee(Employee newEmployee) {
        return employeeService.createEmployee(newEmployee);
    }
    public Employee getEmployee(String id) {
        return employeeService.getEmployeeById(id);
    }
    public boolean updateEmployee(String id, Employee updatedEmployee) {
        return employeeService.updateEmployee(id, updatedEmployee);
    }
    public boolean deleteEmployee(String id) {
        return employeeService.deleteEmployee(id);
    }
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }
    public boolean assignProject(String employeeId, String projectId) {
        return employeeProjectService.assignProject(employeeId, projectId);
    }


}
