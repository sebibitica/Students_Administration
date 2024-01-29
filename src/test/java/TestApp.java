import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class TestApp {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/studentsapp";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    @Test
    public void testAdaugaStudent() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // Arrange
            StudentDAO studentDAO = new StudentDAO(connection);
            Student studentToAdd = new Student(0,"John", "Doe", 20, 2, "Computer Science");

            // Act
            studentDAO.adaugaStudent(studentToAdd);
            List<Student> studentiAdaugati = studentDAO.cautaStudenti("nume", "John");

            // Assert
            Assertions.assertFalse(studentiAdaugati.isEmpty(), "Studentul adaugat nu a fost gasit.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testStergeStudent() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // Arrange
            StudentDAO studentDAO = new StudentDAO(connection);
            Student studentToAdd = new Student(0,"Jane", "Doe", 22, 3, "Mathematics");

            // Add a student for deletion
            studentDAO.adaugaStudent(studentToAdd);
            List<Student> studentiAdaugati = studentDAO.cautaStudenti("nume", "Jane");
            int studentIdToDelete = studentiAdaugati.get(0).getStudentId();

            // Act
            studentDAO.stergeStudent(studentIdToDelete);
            List<Student> studentiDupaStergere = studentDAO.cautaStudenti("nume", "Jane");

            // Assert
            Assertions.assertTrue(studentiDupaStergere.isEmpty(), "Studentul nu a fost sters corect.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
