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
import org.harunjaganjac.example.response.RegisterResponse;
import org.harunjaganjac.example.staticdata.CollectionNames;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class UserService {
    private final MongoCollection<Document> userCollection;

    public UserService() {
        DataContext.connect();
        this.userCollection = DataContext.database.getCollection(CollectionNames.USERS);
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
            DataContext.getLogger().error("Error logging in:  ", e);
            return null;
        }
    }
    public RegisterResponse registerUserWithRole(String username, String password, String email, String role) {
        try {
            // Check if username or password already exists
            if (userCollection.find(Filters.and(
                    Filters.eq("username", username),
                    Filters.eq("password", password)
            )).first() != null) {
                DataContext.getLogger().error("User with this data  already exists.");
                return new RegisterResponse("User with this data already exists.", false, null);
            }
            //Check if email already exists
            if (userCollection.find(Filters.eq("email", email)).first() != null) {
                DataContext.getLogger().error("User with this email already exists.");
                return new RegisterResponse("User with this email already exists.", false, null);
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
            return new RegisterResponse("User registered successfully", true, new User(user.getUsername(), user.getEmail(), user.getPassword()));
        } catch (Exception e) {
            DataContext.getLogger().error("Error registering user: ", e);
            return new RegisterResponse("Error registering user", false, null);
        }
    }
    public RegisterResponse resetPassword(String username,String oldPassword, String newPassword) {
        try {
            Document doc = userCollection.find(Filters.and(
                    Filters.eq("username", username),
                    Filters.eq("password", oldPassword)
            )).first();
            if (doc != null) {
                if(oldPassword.equals(newPassword)){
                    return new RegisterResponse("The new password is the same as the old password", false, null);
                }
                Document user = userCollection.find(Filters.eq("password", newPassword)).first();
                if(user != null){
                    return new RegisterResponse("The new password is already in the system", false, null);
                }
                Bson filter = Filters.eq("username", username);
                Bson updates = Updates.set("password", newPassword);
                userCollection.updateOne(filter, updates);
                DataContext.getLogger().info("Password reset for user: " + username);
                return new RegisterResponse("Password reset successfully", true, null);
            }else{
                return new RegisterResponse("The old password for user doesnt match", false, null);
            }
        } catch (Exception e) {
            DataContext.getLogger().error("Error resetting password: ", e);
            return new RegisterResponse("Error resetting password", false, null);
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
            List<User> users = new ArrayList<>();
            for (Document doc : userCollection.find()) {
                if(doc.getString("role").equals("superadmin"))
                    continue;
                users.add(new User(
                        doc.getString("id"),
                        doc.getString("username"),
                        doc.getString("email"),
                        doc.getString("password"),
                        doc.getString("createdAt"),
                        doc.getString("role")
                ));
            }
            return users;
        } catch (Exception e) {
            DataContext.getLogger().error("Error getting all users: ", e);
            return null;
        }
    }
}