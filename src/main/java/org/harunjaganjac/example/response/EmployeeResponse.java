package org.harunjaganjac.example.response;

public class EmployeeResponse extends BaseResponse {
    private final String employeeId;

    public EmployeeResponse(String message, boolean success, String employeeId) {
        super(message, success);
        this.employeeId = employeeId;
    }

    public String getEmployeeId() {
        return employeeId;
    }
}
