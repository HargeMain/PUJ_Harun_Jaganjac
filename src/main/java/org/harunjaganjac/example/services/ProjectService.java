package org.harunjaganjac.example.services;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.harunjaganjac.example.datacontext.DataContext;
import org.harunjaganjac.example.helpers.GeneratorHelpers;
import org.harunjaganjac.example.models.Employee;
import org.harunjaganjac.example.models.Project;
import org.harunjaganjac.example.staticdata.CollectionNames;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class ProjectService {
    private final MongoCollection<Document> projectCollection;
    public ProjectService() {
        DataContext.connect();
        this.projectCollection = DataContext.database.getCollection(CollectionNames.PROJECTS);
    }
    public boolean createProject(Project project) {
        try {
            String newId = GeneratorHelpers.generateId();
            project.setId(newId);
            Document doc = new Document("id", project.getId())
                    .append("name", project.getName())
                    .append("description", project.getDescription())
                    .append("startDate", LocalDate.now().toString())
                    .append("endDate", project.getEndDate())
                    .append("assignedEmployees", (List<String>)project.getAssignedEmployees());
            projectCollection.insertOne(doc);
            DataContext.getLogger().info("Project created with ID: " + doc.getString("id"));
            return true;
        } catch (Exception e) {
            DataContext.getLogger().error("Error creating project: ", e);
            return false;
        }
    }
    public Project getProjectById(String id) {
        try {
            Document doc = projectCollection.find(new Document("id", id)).first();
            if (doc != null) {
                return new Project(
                        doc.getString("id"),
                        doc.getString("name"),
                        doc.getString("description"),
                        doc.getString("startDate"),
                        doc.getString("endDate"),
                        (List<String>) doc.get("assignedEmployees")
                );
            }
            return null;
        } catch (Exception e) {
            DataContext.getLogger().error("Error getting project by id: ", e);
            return null;
        }
    }
    public boolean updateProject(String id, Project updatedProject) {
        try {
            Bson filter = Filters.eq("id", id);
            Bson updates = Updates.combine(
                    Updates.set("name", updatedProject.getName()),
                    Updates.set("description", updatedProject.getDescription()),
                    Updates.set("startDate", updatedProject.getStartDate()),
                    Updates.set("endDate", updatedProject.getEndDate())
            );
            projectCollection.updateOne(filter, updates);
            DataContext.getLogger().info("Project updated with ID: " + id);
            return true;
        } catch (Exception e) {
            DataContext.getLogger().error("Error updating project: ", e);
            return false;
        }
    }
    public boolean deleteProject(String id) {
        try {
            projectCollection.deleteOne(new Document("_id", id));
            DataContext.getLogger().info("Project deleted with ID: " + id);
            return true;
        } catch (Exception e) {
            DataContext.getLogger().error("Error deleting project: ", e);
            return false;
        }
    }
    public List<Project> getAllProjects() {
        try {
            List<Project> projects = new ArrayList<>();
            for (Document doc : projectCollection.find()) {
                projects.add(new Project(
                        doc.getString("_id"),
                        doc.getString("name"),
                        doc.getString("description"),
                        doc.getString("startDate"),
                        doc.getString("endDate"),
                        (List<String>) doc.get("assignedEmployees")
                ));
            }
            return projects;
        } catch (Exception e) {
            DataContext.getLogger().error("Error getting all projects: ", e);
            return null;
        }
    }

}
