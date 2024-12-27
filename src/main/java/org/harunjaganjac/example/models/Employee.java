package org.harunjaganjac.example.models;

import java.util.List;

public class Employee {
    private String id;
    private String firstName;
    private String lastName;
    private String position;
    private int salary;
    private List<String> assignedProjects;
    private String status;

    // Constructor --START--
    public Employee(String id, String firstName, String lastName, String position, int salary, List<String> assignedProjects, String status) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.salary = salary;
        this.assignedProjects = assignedProjects;
        this.status = status;
    }
    // Constructor --END--

    // Getters and Setters --START--
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public List<String> getAssignedProjects() {
        return assignedProjects;
    }

    public void setAssignedProjects(List<String> assignedProjects) {
        this.assignedProjects = assignedProjects;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "assignedProjects=" + assignedProjects +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", position='" + position + '\'' +
                ", salary=" + salary +
                ", status='" + status + '\'' +
                '}';
    }
    // Getters and Setters --END--
}
