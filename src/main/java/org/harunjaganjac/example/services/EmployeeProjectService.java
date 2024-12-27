package org.harunjaganjac.example.services;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.harunjaganjac.example.datacontext.DataContext;

import java.util.List;

public final class EmployeeProjectService {
    private final MongoCollection<Document> employeeCollection;
    private final MongoCollection<Document> projectCollection;
    public EmployeeProjectService() {
        DataContext.connect();
        this.employeeCollection = DataContext.database.getCollection("employees");
        this.projectCollection = DataContext.database.getCollection("projects");
    }
    public boolean assignProject(String employeeId, String projectId) {
        try {
            Document employeeDoc = employeeCollection.find(Filters.eq("id", employeeId)).first();
            Document projectDoc = projectCollection.find(Filters.eq("id", projectId)).first();
            if (employeeDoc != null && projectDoc != null) {
                List<String> assignedProjects = (List<String>) employeeDoc.get("assignedProjects");
                assignedProjects.add(projectId);
                List<String> assignedEmployees = (List<String>) projectDoc.get("assignedEmployees");
                assignedEmployees.add(employeeId);
                Bson filter = Filters.eq("id", employeeId);
                Bson updates = Updates.set("assignedProjects", assignedProjects);
                employeeCollection.updateOne(filter, updates);
                DataContext.getLogger().info("Project assigned to employee with ID: " + employeeId);
                return true;
            }
            return false;
        } catch (Exception e) {
            DataContext.getLogger().error("Error assigning project to employee: ", e);
            return false;
        }
    }

}
