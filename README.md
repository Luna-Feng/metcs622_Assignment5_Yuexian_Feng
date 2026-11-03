# metcs622_Assignment5_Yuexian_Feng

## Description

This project uses Java to perform CRUD (Create, Read, Update, Delete) operations with MySQL and MongoDB.

I downloaded the Students Performance in Exams dataset from Kaggle. The dataset contains 1,000 student records, including gender, education background, and math, reading, and writing scores.

I imported the dataset into MySQL using the original CSV file (`StudentsPerformance.csv`). I also converted the CSV file into JSON format (`StudentsPerformance.json`) and imported 1,000 student records into MongoDB using MongoDB Compass.

The MySQL database is `homework5_db`, and the table is `students`. The MongoDB database is `homework5_mongo`. The original `students` collection was used for Java CRUD testing, and the `students_json_final` collection was used to verify the JSON import.

The Java program uses JDBC to connect to MySQL and the MongoDB Java Driver to connect to MongoDB. The SQL and MongoDB CRUD operations are implemented in separate Java classes.

## How to Run

1. Open the `metcs622_Assignment5_Yuexian_Feng` project in Visual Studio Code.
2. Make sure MySQL and MongoDB are running.
3. Open the terminal in the project folder containing `pom.xml`.
4. Compile the project:

```bash
mvn clean compile
```

To run the MySQL program, first set the MySQL password in the terminal:

```bash
read -s MYSQL_PASSWORD
export MYSQL_PASSWORD
```

Then run:

```bash
mvn -q compile exec:java -Dexec.mainClass=com.example.homework5.SQLDatabase
```

To run the MongoDB program:

```bash
mvn -q compile exec:java -Dexec.mainClass=com.example.homework5.MongoDatabase
```

## Testing

I tested the CRUD operations in both databases using the student performance dataset.

For MySQL, I used Java JDBC to insert a new student, retrieve the student record, update the exam scores, and delete the test record.

For MongoDB, I used the MongoDB Java Driver to perform the same operations in the `students` collection.

Both databases originally contained 1,000 student records.

I also verified that the `students_json_final` collection contains 1,000 documents imported from the JSON file, with math, reading, and writing scores stored as numeric values.

## Results

**MySQL output:**

```text
Connected to MySQL successfully!
INSERT successful!
UPDATE successful!
DELETE successful!
Total students remaining: 1000
All SQL CRUD operations completed!
```

**MongoDB output:**

```text
Connected to MongoDB successfully!
INSERT successful!
Total students after INSERT: 1001
UPDATE successful! Documents updated: 1
DELETE successful! Documents deleted: 1
Verification successful: Test student deleted.
Total students remaining: 1000
All MongoDB CRUD operations completed!
```

Both Java programs successfully performed the CRUD operations. After deleting the test records, the databases returned to their original 1,000 student records.

## Technologies Used

- Java 21
- MySQL
- MongoDB
- JDBC (MySQL Connector/J)
- MongoDB Java Driver
- Apache Maven
- MySQL Workbench
- MongoDB Compass
- Visual Studio Code
- Python (CSV to JSON conversion)