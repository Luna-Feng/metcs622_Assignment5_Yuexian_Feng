
package com.example.homework5;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import org.bson.Document;
import org.bson.types.ObjectId;

public class MongoDatabase {

    public static void main(String[] args) {

        String connectionString = "mongodb://localhost:27017";
        String databaseName = "homework5_mongo";
        String collectionName = "students";

        try (MongoClient mongoClient =
                MongoClients.create(connectionString)) {

            com.mongodb.client.MongoDatabase database =
                    mongoClient.getDatabase(databaseName);

            MongoCollection<Document> collection =
                    database.getCollection(collectionName);

            System.out.println("Connected to MongoDB successfully!");
            System.out.println("Database: " + databaseName);
            System.out.println("Collection: " + collectionName);
            System.out.println("Total students before CRUD: "
                    + collection.countDocuments());

            // CREATE / INSERT
            Document newStudent = new Document("gender", "female")
                    .append("race/ethnicity", "group B")
                    .append("parental level of education", "some college")
                    .append("lunch", "standard")
                    .append("test preparation course", "completed")
                    .append("math score", 95)
                    .append("reading score", 92)
                    .append("writing score", 94);

            collection.insertOne(newStudent);

            ObjectId studentId = newStudent.getObjectId("_id");

            System.out.println();
            System.out.println("INSERT successful!");
            System.out.println("New student ID: " + studentId);
            System.out.println("Total students after INSERT: "
                    + collection.countDocuments());

            // READ / SELECT
            Document student = collection.find(
                    new Document("_id", studentId)
            ).first();

            System.out.println();
            System.out.println("SELECT - Student Record:");

            if (student != null) {
                printStudent(student);
            } else {
                System.out.println("Student not found.");
                return;
            }

            // UPDATE
            Document updatedScores = new Document("$set",
                    new Document("math score", 98)
                            .append("reading score", 96)
                            .append("writing score", 97));

            UpdateResult updateResult = collection.updateOne(
                    new Document("_id", studentId),
                    updatedScores
            );

            System.out.println();
            System.out.println("UPDATE successful! Documents updated: "
                    + updateResult.getModifiedCount());

            // Verify UPDATE
            Document updatedStudent = collection.find(
                    new Document("_id", studentId)
            ).first();

            System.out.println();
            System.out.println("Updated Student Record:");

            if (updatedStudent != null) {
                printStudent(updatedStudent);
            } else {
                System.out.println("Updated student not found.");
            }

            // DELETE
            DeleteResult deleteResult = collection.deleteOne(
                    new Document("_id", studentId)
            );

            System.out.println();
            System.out.println("DELETE successful! Documents deleted: "
                    + deleteResult.getDeletedCount());

            // Verify DELETE
            Document deletedStudent = collection.find(
                    new Document("_id", studentId)
            ).first();

            if (deletedStudent == null) {
                System.out.println(
                        "Verification successful: Test student deleted.");
            } else {
                System.out.println(
                        "Verification failed: Student still exists.");
            }

            System.out.println("Total students remaining: "
                    + collection.countDocuments());

            System.out.println();
            System.out.println("All MongoDB CRUD operations completed!");

        } catch (Exception e) {
            System.out.println("MongoDB operation failed: "
                    + e.getMessage());
        }
    }

    // Display student information
    private static void printStudent(Document student) {

        System.out.println("ID: "
                + student.getObjectId("_id"));
        System.out.println("Gender: "
                + student.getString("gender"));
        System.out.println("Math: "
                + student.getInteger("math score"));
        System.out.println("Reading: "
                + student.getInteger("reading score"));
        System.out.println("Writing: "
                + student.getInteger("writing score"));
    }
}






