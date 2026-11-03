
package com.example.homework5;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLDatabase {

    private static final String URL =
            "jdbc:mysql://localhost:3306/homework5_db";

    private static final String USERNAME = "root";

    public static void main(String[] args) {

        String password = System.getenv("MYSQL_PASSWORD");

        if (password == null || password.isEmpty()) {
            System.out.println("MYSQL_PASSWORD is not set.");
            return;
        }

        try (Connection connection =
                DriverManager.getConnection(URL, USERNAME, password)) {

            System.out.println("Connected to MySQL successfully!");

            // Demonstrate all four CRUD operations
            int studentId = insertStudent(connection);

            if (studentId > 0) {
                selectStudents(connection, studentId);
                updateStudent(connection, studentId);
                deleteStudent(connection, studentId);
            }

            System.out.println();
            System.out.println("All SQL CRUD operations completed!");

        } catch (SQLException e) {
            System.out.println("MySQL operation failed: " + e.getMessage());
        }
    }

    // CREATE: Insert a new test student
    public static int insertStudent(Connection connection)
            throws SQLException {

        String sql =
                "INSERT INTO students " +
                "(gender, race_ethnicity, parental_level_of_education, " +
                "lunch, test_preparation_course, math_score, " +
                "reading_score, writing_score) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement =
                connection.prepareStatement(
                        sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, "female");
            statement.setString(2, "group C");
            statement.setString(3, "bachelor's degree");
            statement.setString(4, "standard");
            statement.setString(5, "completed");
            statement.setInt(6, 95);
            statement.setInt(7, 92);
            statement.setInt(8, 94);

            int rows = statement.executeUpdate();

            System.out.println();
            System.out.println("INSERT successful! Rows added: " + rows);

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    int studentId = keys.getInt(1);
                    System.out.println("New student ID: " + studentId);
                    return studentId;
                }
            }
        }

        return -1;
    }

    // READ: Display the test student and their scores
    public static void selectStudents(
            Connection connection, int studentId) throws SQLException {

        String sql =
                "SELECT id, gender, math_score, " +
                "reading_score, writing_score " +
                "FROM students WHERE id = ?";

        try (PreparedStatement statement =
                connection.prepareStatement(sql)) {

            statement.setInt(1, studentId);

            try (ResultSet resultSet = statement.executeQuery()) {

                System.out.println();
                System.out.println("SELECT - Student Record:");
                System.out.println("ID | Gender | Math | Reading | Writing");

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String gender = resultSet.getString("gender");
                    int math = resultSet.getInt("math_score");
                    int reading = resultSet.getInt("reading_score");
                    int writing = resultSet.getInt("writing_score");

                    System.out.println(
                            id + " | " + gender + " | " +
                            math + " | " + reading + " | " + writing);
                }
            }
        }
    }

    // UPDATE: Change the test student's scores
    public static void updateStudent(
            Connection connection, int studentId) throws SQLException {

        String sql =
                "UPDATE students " +
                "SET math_score = ?, reading_score = ?, " +
                "writing_score = ? WHERE id = ?";

        try (PreparedStatement statement =
                connection.prepareStatement(sql)) {

            statement.setInt(1, 98);
            statement.setInt(2, 96);
            statement.setInt(3, 97);
            statement.setInt(4, studentId);

            int rows = statement.executeUpdate();

            System.out.println();
            System.out.println("UPDATE successful! Rows updated: " + rows);
        }

        // Verify updated scores
        selectStudents(connection, studentId);
    }

    // DELETE: Remove the test student
    public static void deleteStudent(
            Connection connection, int studentId) throws SQLException {

        String sql = "DELETE FROM students WHERE id = ?";

        try (PreparedStatement statement =
                connection.prepareStatement(sql)) {

            statement.setInt(1, studentId);

            int rows = statement.executeUpdate();

            System.out.println();
            System.out.println("DELETE successful! Rows deleted: " + rows);
        }

        // Verify the test student was deleted
        String verifySQL = "SELECT COUNT(*) FROM students WHERE id = ?";

        try (PreparedStatement statement =
                connection.prepareStatement(verifySQL)) {

            statement.setInt(1, studentId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next() && resultSet.getInt(1) == 0) {
                    System.out.println(
                            "Verification successful: Test student deleted.");
                }
            }
        }

        // Check that the original dataset remains
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "SELECT COUNT(*) FROM students")) {

            if (resultSet.next()) {
                System.out.println(
                        "Total students remaining: " + resultSet.getInt(1));
            }
        }
    }
}






