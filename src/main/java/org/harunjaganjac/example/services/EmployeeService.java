package org.harunjaganjac.example.services;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.harunjaganjac.example.datacontext.DataContext;
import org.harunjaganjac.example.helpers.GeneratorHelpers;
import org.harunjaganjac.example.models.Employee;
import org.harunjaganjac.example.response.EmployeeResponse;
import org.harunjaganjac.example.staticdata.CollectionNames;

import java.util.ArrayList;
import java.util.List;

public final class EmployeeService {
    private final MongoCollection<Document> employeeCollection;

    public EmployeeService() {
        DataContext.connect();
        this.employeeCollection = DataContext.database.getCollection(CollectionNames.EMPLOYEES);
    }

    public EmployeeResponse createEmployee(Employee employee) {
        try {
            String newId = GeneratorHelpers.generateId();
            employee.setId(newId);
            Document doc = new Document("id", employee.getId())
                    .append("firstName", employee.getFirstName())
                    .append("lastName", employee.getLastName())
                    .append("position", employee.getPosition())
                    .append("salary", employee.getSalary())
                    .append("assignedProjects", employee.getAssignedProjects())
                    .append("status", employee.getStatus());
            employeeCollection.insertOne(doc);
            DataContext.getLogger().info("Employee created with ID: " + newId);
            return new EmployeeResponse("Employee created successfully", true,newId);
        } catch (Exception e) {
            DataContext.getLogger().error("Error creating employee: ", e);
            return new EmployeeResponse("Error creating employee", false,null);
        }
    }
    public Employee getEmployeeById(String id) {
        try {
            Document doc = employeeCollection.find(Filters.eq("id", id)).first();
            if (doc != null) {
                return new Employee(
                        doc.getString("id"),
                        doc.getString("firstName"),
                        doc.getString("lastName"),
                        doc.getString("position"),
                        doc.getInteger("salary"),
                        (List<String>) doc.get("assignedProjects"),
                        doc.getString("status")
                );
            }
            return null;
        } catch (Exception e) {
            DataContext.getLogger().error("Error getting employee by id: ", e);
            return null;
        }
    }

    public EmployeeResponse updateEmployee(String id, Employee updatedEmployee) {
        try {
            Bson filter = Filters.eq("id", id);
            Bson updates = Updates.combine(
                    Updates.set("firstName", updatedEmployee.getFirstName()),
                    Updates.set("lastName", updatedEmployee.getLastName()),
                    Updates.set("position", updatedEmployee.getPosition()),
                    Updates.set("salary", updatedEmployee.getSalary()),
                    Updates.set("status", updatedEmployee.getStatus())
            );
            employeeCollection.updateOne(filter, updates);
            DataContext.getLogger().info("Employee updated with ID: " + id);
            return new EmployeeResponse("Employee updated successfully", true,id);
        } catch (Exception e) {
            DataContext.getLogger().error("Error updating employee: ", e);
            return new EmployeeResponse("Error updating employee", false,null);
        }
    }

    public boolean deleteEmployee(String id) {
        try {
            employeeCollection.deleteOne(Filters.eq("id", id));
            DataContext.getLogger().info("Employee deleted with ID: " + id);
            return true;
        } catch (Exception e) {
            DataContext.getLogger().error("Error deleting employee: ", e);
            return false;
        }
    }

    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        try {
            for (Document doc : employeeCollection.find()) {
                employees.add(new Employee(
                        doc.getString("id"),
                        doc.getString("firstName"),
                        doc.getString("lastName"),
                        doc.getString("position"),
                        doc.getInteger("salary"),
                        (List<String>) doc.get("assignedProjects"),
                        doc.getString("status")
                ));
            }
        } catch (Exception e) {
            DataContext.getLogger().error("Error getting all employees: ", e);
            return null;
        }
        return employees;
    }
    public List<String> getAllEmployeeIds() {
        List<String> employeeIds = new ArrayList<>();
        try {
            for (Document doc : employeeCollection.find()) {
                employeeIds.add(doc.getString("id"));
            }
        } catch (Exception e) {
            DataContext.getLogger().error("Error getting all employee ids: ", e);
            return null;
        }
        return employeeIds;
    }

    public void disconnect() {
        DataContext.disconnect();
    }
}