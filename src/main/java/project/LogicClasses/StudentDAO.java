package project.LogicClasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The StudentDAO class provides methods for interacting with the database for operations related to students and grades.
 */
public class StudentDAO {
    /**
     * The connection to the database.
     */
    private Connection connection;

    /**
     * Constructor for the StudentDAO class.
     *
     * @param connection The connection to the database.
     */
    public StudentDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Adds a student to the database.
     *
     * @param student The Student object to be added.
     */
    public void adaugaStudent(Student student) {
        try {
            String query = "INSERT INTO Student (nume, prenume, varsta, an_studiu, specializare) " +
                    "VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, student.getNume());
                preparedStatement.setString(2, student.getPrenume());
                preparedStatement.setInt(3, student.getVarsta());
                preparedStatement.setInt(4, student.getAnStudiu());
                preparedStatement.setString(5, student.getSpecializare());

                preparedStatement.executeUpdate();
                System.out.println("Student added successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a student based on their ID.
     *
     * @param studentId The ID of the student to be deleted.
     */
    public void stergeStudent(int studentId) {
        try {
            String query = "DELETE FROM Student WHERE student_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, studentId);

                int affectedRows = preparedStatement.executeUpdate();

                if (affectedRows > 0) {
                    System.out.println("Student deleted successfully!");
                } else {
                    System.out.println("No student found with the specified ID.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves a list of all students from the database.
     *
     * @return The list of students.
     */
    public List<Student> getStudents() {
        try {
            String query = "SELECT * FROM Student";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    List<Student> students = new ArrayList<>();

                    while (resultSet.next()) {
                        int studentId = resultSet.getInt("student_id");
                        String nume = resultSet.getString("nume");
                        String prenume = resultSet.getString("prenume");
                        int varsta = resultSet.getInt("varsta");
                        int anStudiu = resultSet.getInt("an_studiu");
                        String specializare = resultSet.getString("specializare");

                        Student student = new Student(studentId, nume, prenume, varsta, anStudiu, specializare);
                        students.add(student);
                    }

                    return students;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Retrieves a list of grades for a specific student based on their ID.
     *
     * @param studentId The ID of the student for whom the grades will be displayed.
     * @return The list of Nota objects associated with the student.
     */
    public List<Nota> afiseazaNoteleStudentului(int studentId) {
        List<Nota> note = new ArrayList<>();

        try {
            String query = "SELECT n.*, m.nume_materie FROM Nota n JOIN Materie m ON n.materie_id = m.materie_id WHERE student_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, studentId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int notaId = resultSet.getInt("nota_id");
                        int materieId = resultSet.getInt("materie_id");
                        String materieNume = resultSet.getString("nume_materie");
                        int nota = resultSet.getInt("nota");

                        Nota notaObiect = new Nota(notaId, studentId, materieId, materieNume, nota);
                        note.add(notaObiect);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return note;
    }

    /**
     * Adds a new grade for a student to the database.
     *
     * @param nota The Nota object to be added.
     */
    public void adaugaNota(Nota nota) {
        try {
            String query = "INSERT INTO Nota (student_id, materie_id, nota) VALUES (?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, nota.getStudentId());
                preparedStatement.setInt(2, nota.getMaterieId());
                preparedStatement.setInt(3, nota.getNota());

                preparedStatement.executeUpdate();
                System.out.println("Grade added successfully!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks the correctness of the password for a specific subject.
     *
     * @param materieId The ID of the subject.
     * @param parola    The password for verification.
     * @return `true` if the password is correct, `false` otherwise.
     */
    public boolean verificaParolaMaterie(int materieId, String parola) {
        try {
            String query = "SELECT * FROM ParoleMaterii WHERE materie_id = ? AND parola = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, materieId);
                preparedStatement.setString(2, parola);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next(); // If it finds a record, the password is correct
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves the subject ID based on its name.
     *
     * @param materieNume The name of the subject.
     * @return The ID of the subject, or -1 if not found.
     */
    public int getMaterieIdFromName(String materieNume) {
        try {
            String query = "SELECT materie_id FROM Materie WHERE nume_materie = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, materieNume);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt("materie_id");
                    } else {
                        return -1;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Retrieves a list of subjects compatible with the specialization and year of a specific student.
     *
     * @param studentId The ID of the student.
     * @return The list of compatible subject names.
     */
    public List<String> getMateriiCompatibileCuSpecializarea(int studentId) {
        try {
            String query = "SELECT m.nume_materie FROM Materie m JOIN Student s ON m.materie_an = s.an_studiu AND m.materie_nume_specializare = s.specializare WHERE s.student_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, studentId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    List<String> materiiCompatibile = new ArrayList<>();

                    while (resultSet.next()) {
                        String materieNumeSpecializare = resultSet.getString("nume_materie");
                        materiiCompatibile.add(materieNumeSpecializare);
                    }

                    return materiiCompatibile;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Retrieves a list of distinct specializations from the Student table.
     *
     * @return The list of distinct specializations.
     */
    public List<String> getSpecializari() {
        try {
            String query = "SELECT DISTINCT specializare FROM Student";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    List<String> specializari = new ArrayList<>();

                    while (resultSet.next()) {
                        String specializare = resultSet.getString("specializare");
                        specializari.add(specializare);
                    }

                    return specializari;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}