package org.harunjaganjac.example.helpers;

public final class ValidationsHelpers {
    public static boolean isValidEmail(String email) {
        return email.matches("^(.+)@(.+)$");
    }
    public static boolean isValidPassword(String password) {
        return password.length() >= 8;
    }
    public static boolean isValidUsername(String username) {
        return username.length() >= 5;
    }
    public static boolean isValidRole(String role) {
        return role.equals("admin") || role.equals("user");
    }
    public static boolean isValidId(String id) {
        return id.matches("^[0-9a-fA-F]{24}$");
    }
    public static boolean isValidCreatedAt(String createdAt) {
        return createdAt.matches("^(\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.\\d{3}Z)$");
    }
    public static boolean isValidProjectName(String projectName) {
        return projectName.length() >= 5;
    }
    public static boolean isValidProjectDescription(String projectDescription) {
        return projectDescription.length() >= 10;
    }
    public static boolean isValidProjectStatus(String projectStatus) {
        return projectStatus.equals("active") || projectStatus.equals("inactive");
    }
    public static boolean isValidProjectId(String projectId) {
        return projectId.matches("^[0-9a-fA-F]{24}$");
    }
    public static boolean isValidEmployeeName(String employeeName) {
        return employeeName.length() >= 5;
    }
    public static boolean isValidEmployeeEmail(String employeeEmail) {
        return employeeEmail.matches("^(.+)@(.+)$");
    }
    public static boolean isValidEmployeeId(String employeeId) {
        return employeeId.matches("^[0-9a-fA-F]{24}$");
    }
    public static boolean isValidEmployeeRole(String employeeRole) {
        return employeeRole.equals("admin") || employeeRole.equals("user");
    }
    public static boolean isValidEmployeeProjectId(String employeeProjectId) {
        return employeeProjectId.matches("^[0-9a-fA-F]{24}$");
    }
    public static boolean isValidEmployeeProjectEmployeeId(String employeeProjectEmployeeId) {
        return employeeProjectEmployeeId.matches("^[0-9a-fA-F]{24}$");
    }
    public static boolean isValidEmployeeProjectProjectId(String employeeProjectProjectId) {
        return employeeProjectProjectId.matches("^[0-9a-f)A-F]{24}$");
    }
    public static boolean isPasswordMatch(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }
}
