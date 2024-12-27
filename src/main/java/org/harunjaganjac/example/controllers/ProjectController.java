package org.harunjaganjac.example.controllers;

import org.harunjaganjac.example.models.Project;
import org.harunjaganjac.example.services.EmployeeProjectService;
import org.harunjaganjac.example.services.ProjectService;

import java.util.List;

public final class ProjectController {
    private final ProjectService employeeService;
    private final EmployeeProjectService employeeProjectService;

    public ProjectController(ProjectService employeeService, EmployeeProjectService employeeProjectService) {
        this.employeeService = employeeService;
        this.employeeProjectService = employeeProjectService;
    }
    public boolean createProject(Project newProject) {
        return employeeService.createProject(newProject);
    }
    public Project getProject(String id) {
        return employeeService.getProjectById(id);
    }
    public boolean updateProject(String id, Project updatedProject) {
        return employeeService.updateProject(id, updatedProject);
    }
    public boolean deleteProject(String id) {
        return employeeService.deleteProject(id);
    }
    public List<Project> getAllProjects() {
        return employeeService.getAllProjects();
    }
    public boolean assignEmployee(String projectId, String employeeId) {
        return employeeProjectService.assignProject(projectId, employeeId);
    }
}
