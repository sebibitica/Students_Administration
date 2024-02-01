package project;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import project.LogicClasses.Nota;
import project.LogicClasses.Student;
import project.LogicClasses.StudentDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.List;

public class TestApp {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/studentsapp";         private static final String DB_PASSWORD = "Bitica890*!";
    private static final String DB_USER = "root";

    @Test
    public void testAdaugaStudent() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            //students befor
            StudentDAO studentDAO = new StudentDAO(connection);

            List<Student> studentiInainteDeAdaugare = studentDAO.getStudents();

            Student studentToAdd = new Student(0,"John", "Doe", 20, 2, "Computer Science");

            studentDAO.adaugaStudent(studentToAdd);

            List<Student> studentiDupaAdaugare = studentDAO.getStudents();

            Assertions.assertEquals(studentiInainteDeAdaugare.size() + 1, studentiDupaAdaugare.size(), "Studentul nu a fost adaugat.");
            //if the last student is the one we added
            Assertions.assertEquals(studentiDupaAdaugare.get(studentiDupaAdaugare.size() - 1).getNume(), studentToAdd.getNume(), "Studentul nu a fost adaugat.");

            //delete the student
            studentDAO.stergeStudent(studentiDupaAdaugare.get(studentiDupaAdaugare.size() - 1).getStudentId());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testStergeStudent() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            StudentDAO studentDAO = new StudentDAO(connection);
            Student studentToAdd = new Student(0,"Jane", "Doe", 22, 3, "Mathematica");

            studentDAO.adaugaStudent(studentToAdd);
            List<Student> studentiDupaAdaugare = studentDAO.getStudents();
            int studentIdToDelete = studentiDupaAdaugare.get(studentiDupaAdaugare.size() - 1).getStudentId();

            studentDAO.stergeStudent(studentIdToDelete);

            List<Student> studentiDupaStergere = studentDAO.getStudents();

            Assertions.assertEquals(studentiDupaAdaugare.size() - 1, studentiDupaStergere.size(), "Studentul nu a fost sters corect.");

            //verifica daca mai este id ul studentului sters
            for (Student student : studentiDupaStergere) {
                Assertions.assertNotEquals(student.getStudentId(), studentIdToDelete, "Studentul nu a fost sters corect.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAdaugaNota() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            StudentDAO studentDAO = new StudentDAO(connection);

            int nrNoteInainteDeAdaugare = studentDAO.afiseazaNoteleStudentului(3).size();
            Nota notaToAdd = new Nota(0, 3, 4, "", 8);

            studentDAO.adaugaNota(notaToAdd);

            List<Nota> noteAdaugate = studentDAO.afiseazaNoteleStudentului(3);
            int nrNoteDupaAdaugare = noteAdaugate.size();

            // Assert
            Assertions.assertEquals(nrNoteInainteDeAdaugare + 1, nrNoteDupaAdaugare, "Nota nu a fost adaugata.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
