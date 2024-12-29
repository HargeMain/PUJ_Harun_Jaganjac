package org.harunjaganjac.example.services;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.harunjaganjac.example.datacontext.DataContext;
import org.harunjaganjac.example.helpers.GeneratorHelpers;
import org.harunjaganjac.example.models.Project;
import org.harunjaganjac.example.models.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class UserServices {
    private final MongoCollection<Document> userCollection;

    public UserServices() {
        DataContext.connect();
        this.userCollection = DataContext.database.getCollection("users");
    }

    public User login(String username, String password) {
        try {
            Document doc = userCollection.find(Filters.and(
                    Filters.eq("username", username),
                    Filters.eq("password", password)
            )).first();
            if (doc != null) {
                return new User(
                        doc.getString("username"),
                        doc.getString("email"),
                        doc.getString("password"),
                        doc.getString("role")
                );
            }else{
                return null;
            }
        } catch (Exception e) {
            DataContext.getLogger().error("Error logging in: ", e);
            return null;
        }
    }
    public boolean registerUserWithRole(String username,String password,String email, String role) {
        try {
            // Check if username or email already exists
            if (userCollection.find(Filters.or(
                    Filters.eq("username", username),
                    Filters.eq("password", password)
            )).first() != null) {
                DataContext.getLogger().error("Username or password already exists.");
                return false;
            }

            String newId = GeneratorHelpers.generateId();
            User user = new User(newId, username, email, password, role);
            user.setCreatedAt(LocalDate.now().toString());
            Document doc = new Document("id", user.getId())
                    .append("username", user.getUsername())
                    .append("email", user.getEmail())
                    .append("password", user.getPassword())
                    .append("role", user.getRole())
                   .append("createdAt", user.getCreatedAt());
            userCollection.insertOne(doc);
            DataContext.getLogger().info("User registered with ID: " + newId);
            return true;
        } catch (Exception e) {
            DataContext.getLogger().error("Error registering user: ", e);
            return false;
        }
    }
    public boolean resetPassword(String username, String newPassword) {
        try {
            Document doc = userCollection.find(Filters.eq("username", username)).first();
            if (doc != null) {
                Bson filter = Filters.eq("username", username);
                Bson updates = Updates.set("password", newPassword);
                userCollection.updateOne(filter, updates);
                DataContext.getLogger().info("Password reset for user: " + username);
                return true;
            }
            return false;
        } catch (Exception e) {
            DataContext.getLogger().error("Error resetting password: ", e);
            return false;
        }
    }
    public boolean deleteUser(String username) {
        try {
            userCollection.deleteOne(new Document("username", username));
            DataContext.getLogger().info("User deleted with username: " + username);
            return true;
        } catch (Exception e) {
            DataContext.getLogger().error("Error deleting user: ", e);
            return false;
        }
    }
    public List<User> getAllUsers() {
        try {
            List<User> users = null;
            for (Document doc : userCollection.find()) {
                if (users == null) {
                    users = new ArrayList<>();
                }
                users.add(new User(
                        doc.getString("id"),
                        doc.getString("username"),
                        doc.getString("email"),
                        doc.getString("password"),
                        doc.getString("role"),
                        doc.getString("createdAt")
                ));
            }
            return users;
        } catch (Exception e) {
            DataContext.getLogger().error("Error getting all users: ", e);
            return null;
        }
    }
}