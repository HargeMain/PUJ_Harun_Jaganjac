package org.harunjaganjac.example.models;

import java.util.List;

public class Project {
    private String id;
    private String name;
    private String description;
    private String startDate;
    private String endDate;
    private List<String> assignedEmployees;

    // Constructor --START--

    public Project(String id, String name, String description, String startDate, String endDate, List<String> assignedEmployees) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.assignedEmployees = assignedEmployees;
    }
    // Constructor --END--
    // Getters and Setters --START--
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public List<String> getAssignedEmployees() {
        return assignedEmployees;
    }

    public void setAssignedEmployees(List<String> assignedEmployees) {
        this.assignedEmployees = assignedEmployees;
    }
    // Getters and Setters --END--
}
