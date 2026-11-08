
import java.io.*;
import java.util.*;

// Main Application
public class StudentManagementSystem {

    private static final String FILE_NAME = "students.dat";

    // Collection to store student records
    private static HashMap<Integer, Student> students =
            new HashMap<>();

    private static Scanner sc = new Scanner(System.in);

    // Student class
    static class Student implements Serializable {

        private static final long serialVersionUID = 1L;

        private int id;
        private String name;
        private int age;
        private String course;

        public Student(int id, String name,
                       int age, String course) {
            this.id = id;
            this.name = name;
            this.age = age;
            this.course = course;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public String getCourse() {
            return course;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public void setCourse(String course) {
            this.course = course;
        }

        public void display() {
            System.out.println(
                "ID: " + id +
                " | Name: " + name +
                " | Age: " + age +
                " | Course: " + course
            );
        }
    }

    // Add Student
    public static void addStudent() {

        System.out.print("Enter Student ID: ");
        int id = readInt();

        if (students.containsKey(id)) {
            System.out.println("Student ID already exists!");
            return;
        }

        System.out.print("Enter Student Name: ");
        String name = sc.nextLine().trim();

        System.out.print("Enter Age: ");
        int age = readInt();

        System.out.print("Enter Course: ");
        String course = sc.nextLine().trim();

        if (name.isEmpty() || course.isEmpty() || age <= 0) {
            System.out.println("Invalid student details!");
            return;
        }

        Student student = new Student(id, name, age, course);

        students.put(id, student);

        saveToFile();

        System.out.println("Student added successfully!");
    }

    // View All Students
    public static void viewStudents() {

        if (students.isEmpty()) {
            System.out.println("No student records found.");
            return;
        }

        System.out.println("\n--- Student Records ---");

        for (Student student : students.values()) {
            student.display();
        }
    }

    // Search Student
    public static void searchStudent() {

        System.out.print("Enter Student ID: ");
        int id = readInt();

        Student student = students.get(id);

        if (student != null) {
            System.out.println("\nStudent Found:");
            student.display();
        } else {
            System.out.println("Student not found!");
        }
    }

    // Update Student
    public static void updateStudent() {

        System.out.print("Enter Student ID to update: ");
        int id = readInt();

        Student student = students.get(id);

        if (student == null) {
            System.out.println("Student not found!");
            return;
        }

        System.out.print("Enter New Name: ");
        String name = sc.nextLine().trim();

        System.out.print("Enter New Age: ");
        int age = readInt();

        System.out.print("Enter New Course: ");
        String course = sc.nextLine().trim();

        if (name.isEmpty() || course.isEmpty() || age <= 0) {
            System.out.println("Invalid student details!");
            return;
        }

        student.setName(name);
        student.setAge(age);
        student.setCourse(course);

        saveToFile();

        System.out.println("Student updated successfully!");
    }

    // Delete Student
    public static void deleteStudent() {

        System.out.print("Enter Student ID to delete: ");
        int id = readInt();

        if (students.remove(id) != null) {
            saveToFile();
            System.out.println("Student deleted successfully!");
        } else {
            System.out.println("Student not found!");
        }
    }

    // Save Records to File
    public static void saveToFile() {

        try (ObjectOutputStream out =
                new ObjectOutputStream(
                    new FileOutputStream(FILE_NAME))) {

            out.writeObject(students);

        } catch (IOException e) {
            System.out.println("Error saving records: "
                    + e.getMessage());
        }
    }

    // Load Records from File
    @SuppressWarnings("unchecked")
    public static void loadFromFile() {

        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return;
        }

        try (ObjectInputStream in =
                new ObjectInputStream(
                    new FileInputStream(file))) {

            Object data = in.readObject();

            if (data instanceof HashMap<?, ?>) {
                students = (HashMap<Integer, Student>) data;
            }

            System.out.println("Records loaded successfully!");

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading records: "
                    + e.getMessage());
        }
    }

    // Read integer safely
    private static int readInt() {

        while (true) {
            try {
                int value = Integer.parseInt(sc.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.print("Enter a valid number: ");
            }
        }
    }

    // Main Method
    public static void main(String[] args) {

        loadFromFile();

        while (true) {

            System.out.println("\n===== STUDENT MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Search Student");
            System.out.println("4. Update Student");
            System.out.println("5. Delete Student");
            System.out.println("6. Exit");

            System.out.print("Enter your choice: ");

            int choice = readInt();

            switch (choice) {

                case 1:
                    addStudent();
                    break;

                case 2:
                    viewStudents();
                    break;

                case 3:
                    searchStudent();
                    break;

                case 4:
                    updateStudent();
                    break;

                case 5:
                    deleteStudent();
                    break;

                case 6:
                    saveToFile();
                    System.out.println("Thank you!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }
}
