package org.harunjaganjac.example.controllers;

import org.harunjaganjac.example.models.Project;
import org.harunjaganjac.example.response.ProjectResponse;
import org.harunjaganjac.example.response.RegisterResponse;
import org.harunjaganjac.example.services.EmployeeProjectService;
import org.harunjaganjac.example.services.ProjectService;

import java.util.List;

public final class ProjectController {
    private final ProjectService projectService;
    private final EmployeeProjectService employeeProjectService;

    public ProjectController(ProjectService projectService, EmployeeProjectService employeeProjectService) {
        this.projectService = projectService;
        this.employeeProjectService = employeeProjectService;
    }
    public ProjectResponse createProject(Project newProject) {
        return projectService.createProject(newProject);
    }
    public Project getProject(String id) {
        return projectService.getProjectById(id);
    }
    public ProjectResponse updateProject(String id, Project updatedProject) {
        return projectService.updateProject(id, updatedProject);
    }
    public boolean deleteProject(String id) {
        var result=employeeProjectService.removeProject(id);
        if(result){
            return projectService.deleteProject(id);
        }
        return false;
    }
    public List<String> getAssignedEmployees(String projectId) {
        return employeeProjectService.getAssignedEmployees(projectId);
    }

    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }
    public boolean assignEmployee(String projectId, String employeeId) {
        return employeeProjectService.assignProject(projectId, employeeId);
    }
}
