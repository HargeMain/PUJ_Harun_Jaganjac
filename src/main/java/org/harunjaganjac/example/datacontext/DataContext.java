package org.harunjaganjac.example.datacontext;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DataContext {
    private static final String CONNECTION_STRING = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "HRManagmentSystem";
    private static MongoClient mongoClient;
    public static MongoDatabase database;

    private static final Logger logger = LoggerFactory.getLogger(DataContext.class);

    public static void connect() {
        try {
            if (mongoClient == null) {
                mongoClient = MongoClients.create(CONNECTION_STRING);
                logger.info("MongoDB connection established.");
            }
            database = mongoClient.getDatabase(DATABASE_NAME);
        } catch (Exception e) {
            logger.error("Error connecting to MongoDB: ", e);
            throw new RuntimeException("MongoDB connection failed", e);
        }
    }

    public static void disconnect() {
        if (mongoClient != null) {
            mongoClient.close();
            logger.info("MongoDB connection closed.");
        }
    }
    public static Logger getLogger(){
       return logger;
    }

    public static MongoDatabase getDatabase() {
        return database;
    }

    public static MongoClient getMongoClient() {
        if (mongoClient == null) {
            synchronized (DataContext.class) {
                if (mongoClient == null) {
                    mongoClient = MongoClients.create(CONNECTION_STRING);
                }
            }
        }
        return mongoClient;
    }
}