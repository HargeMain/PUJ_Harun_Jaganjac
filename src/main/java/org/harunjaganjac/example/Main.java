package org.harunjaganjac.example;

import org.harunjaganjac.example.controllers.EmployeeController;
import org.harunjaganjac.example.models.Employee;
import org.harunjaganjac.example.services.EmployeeProjectService;
import org.harunjaganjac.example.services.EmployeeService;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
      var employeeService = new EmployeeService();
      var employeeProjectService= new EmployeeProjectService();
      var employeeController = new EmployeeController(employeeService,employeeProjectService);
      var allEmployees = employeeController.getAllEmployees();
        System.out.println("All Employees: ");
        for (Employee employee : allEmployees) {
            System.out.println(employee);
        }
        var succeded = employeeController.deleteEmployee("0c1f421d-a2b8-4776-bd25-0a5d799d9f89");
        System.out.println("Employee: "+succeded);
    }


}