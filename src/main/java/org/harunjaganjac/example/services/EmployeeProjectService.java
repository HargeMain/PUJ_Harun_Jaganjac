package org.harunjaganjac.example.services;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.harunjaganjac.example.datacontext.DataContext;
import org.harunjaganjac.example.staticdata.CollectionNames;

import java.util.ArrayList;
import java.util.List;

public final class EmployeeProjectService {
    private final MongoCollection<Document> employeeCollection;
    private final MongoCollection<Document> projectCollection;
    public EmployeeProjectService() {
        DataContext.connect();
        this.employeeCollection = DataContext.database.getCollection(CollectionNames.EMPLOYEES);
        this.projectCollection = DataContext.database.getCollection(CollectionNames.PROJECTS);
    }
    public boolean removeEmployeeFromProject(String employeeId, String projectId) {
        try {
            Document employeeDoc = employeeCollection.find(Filters.eq("id", employeeId)).first();
            Document projectDoc = projectCollection.find(Filters.eq("id", projectId)).first();
            if (employeeDoc != null && projectDoc != null) {
                List<String> assignedProjects = (List<String>) employeeDoc.get("assignedProjects");
                List<String> assignedEmployees = (List<String>) projectDoc.get("assignedEmployees");

                if (assignedProjects != null) {
                    assignedProjects.remove(projectId);
                    Bson employeeFilter = Filters.eq("id", employeeId);
                    Bson employeeUpdates = Updates.set("assignedProjects", assignedProjects);
                    employeeCollection.updateOne(employeeFilter, employeeUpdates);
                }

                if (assignedEmployees != null) {
                    assignedEmployees.remove(employeeId);
                    Bson projectFilter = Filters.eq("id", projectId);
                    Bson projectUpdates = Updates.set("assignedEmployees", assignedEmployees);
                    projectCollection.updateOne(projectFilter, projectUpdates);
                }

                DataContext.getLogger().info("Employee with ID: " + employeeId + " removed from project with ID: " + projectId);
                return true;
            }
            return false;
        } catch (Exception e) {
            DataContext.getLogger().error("Error removing employee from project: ", e);
            return false;
        }
    }
    public boolean assignProject(String employeeId, String projectId) {
        try {
            Document employeeDoc = employeeCollection.find(Filters.eq("id", employeeId)).first();
            Document projectDoc = projectCollection.find(Filters.eq("id", projectId)).first();
            if (employeeDoc != null && projectDoc != null) {
                List<String> assignedProjects = (List<String>) employeeDoc.get("assignedProjects");
                if (assignedProjects == null) {
                    assignedProjects = new ArrayList<>();
                }
                if (!assignedProjects.contains(projectId)) {
                    assignedProjects.add(projectId);
                }

                List<String> assignedEmployees = (List<String>) projectDoc.get("assignedEmployees");
                if (assignedEmployees == null) {
                    assignedEmployees = new ArrayList<>();
                }
                if (!assignedEmployees.contains(employeeId)) {
                    assignedEmployees.add(employeeId);
                }

                Bson employeeFilter = Filters.eq("id", employeeId);
                Bson employeeUpdates = Updates.set("assignedProjects", assignedProjects);
                employeeCollection.updateOne(employeeFilter, employeeUpdates);

                Bson projectFilter = Filters.eq("id", projectId);
                Bson projectUpdates = Updates.set("assignedEmployees", assignedEmployees);
                projectCollection.updateOne(projectFilter, projectUpdates);

                DataContext.getLogger().info("Project assigned to employee with ID: " + employeeId);
                return true;
            }
            return false;
        } catch (Exception e) {
            DataContext.getLogger().error("Error assigning project to employee: ", e);
            return false;
        }
    }

    public List<String> getAssignedProjects(String employeeId) {
        try {
            Document employeeDoc = employeeCollection.find(Filters.eq("id", employeeId)).first();
            if (employeeDoc != null) {
                return (List<String>) employeeDoc.get("assignedProjects");
            }
            return new ArrayList<>();
        } catch (Exception e) {
            DataContext.getLogger().error("Error getting assigned projects: ", e);
            return new ArrayList<>();
        }
    }

    public List<String> getAssignedEmployees(String projectId) {
        try {
            Document projectDoc = projectCollection.find(Filters.eq("id", projectId)).first();
            if (projectDoc != null) {
                return (List<String>) projectDoc.get("assignedEmployees");
            }
            return new ArrayList<>();
        } catch (Exception e) {
            DataContext.getLogger().error("Error getting assigned employees: ", e);
            return new ArrayList<>();
        }
    }

    public boolean removeProject(String projectId) {
        try {
            Document projectDoc = projectCollection.find(Filters.eq("id", projectId)).first();
            if (projectDoc != null) {
                List<Document> employees = employeeCollection.find(Filters.in("assignedProjects", projectId)).into(new ArrayList<>());
                for (Document employeeDoc : employees) {
                    List<String> assignedProjects = (List<String>) employeeDoc.get("assignedProjects");
                    assignedProjects.remove(projectId);
                    Bson filter = Filters.eq("id", employeeDoc.getString("id"));
                    Bson updates = Updates.set("assignedProjects", assignedProjects);
                    employeeCollection.updateOne(filter, updates);
                }
                projectCollection.deleteOne(Filters.eq("id", projectId));
                DataContext.getLogger().info("Project removed with ID: " + projectId);
                return true;
            }
            return false;
        } catch (Exception e) {
            DataContext.getLogger().error("Error removing project: ", e);
            return false;
        }
    }

    public boolean removeEmployee(String employeeId) {
        try {
            Document employeeDoc = employeeCollection.find(Filters.eq("id", employeeId)).first();
            if (employeeDoc != null) {
                List<Document> projects = projectCollection.find(Filters.in("assignedEmployees", employeeId)).into(new ArrayList<>());
                for (Document projectDoc : projects) {
                    List<String> assignedEmployees = (List<String>) projectDoc.get("assignedEmployees");
                    assignedEmployees.remove(employeeId);
                    Bson filter = Filters.eq("id", projectDoc.getString("id"));
                    Bson updates = Updates.set("assignedEmployees", assignedEmployees);
                    projectCollection.updateOne(filter, updates);
                }
                employeeCollection.deleteOne(Filters.eq("id", employeeId));
                DataContext.getLogger().info("Employee removed with ID: " + employeeId);
                return true;
            }
            return false;
        } catch (Exception e) {
            DataContext.getLogger().error("Error removing employee: ", e);
            return false;
        }
    }
}

