import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clasa StudentDAO oferă metode pentru interacțiunea cu baza de date pentru operațiuni legate de studenți și note.
 */
public class StudentDAO {
    /**
     * Conexiunea la baza de date.
     */
    private Connection connection;

    /**
     * Constructorul clasei StudentDAO.
     *
     * @param connection Conexiunea la baza de date.
     */
    public StudentDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Metoda pentru adăugarea unui student în baza de date.
     *
     * @param student Obiectul Student care va fi adăugat.
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
                System.out.println("Student adăugat cu succes!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda pentru căutarea studenților în funcție de un criteriu și o valoare specificată.
     *
     * @param criteriu Criteriul de căutare (nume, prenume, varsta, an_studiu, specializare).
     * @param valoare  Valoarea după care se caută.
     * @return Lista de studenți găsiți.
     */
    public List<Student> cautaStudenti(String criteriu, String valoare) {
        List<Student> studentiGasiti = new ArrayList<>();

        try {
            String query = "SELECT * FROM Student WHERE " + criteriu + " = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, valoare);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int studentId = resultSet.getInt("student_id");
                        String nume = resultSet.getString("nume");
                        String prenume = resultSet.getString("prenume");
                        int varsta = resultSet.getInt("varsta");
                        int anStudiu = resultSet.getInt("an_studiu");
                        String specializare = resultSet.getString("specializare");

                        Student student = new Student(studentId, nume, prenume, varsta, anStudiu, specializare);
                        studentiGasiti.add(student);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return studentiGasiti;
    }

    /**
     * Metoda pentru ștergerea unui student în funcție de ID-ul său.
     *
     * @param studentId ID-ul studentului care va fi șters.
     */
    public void stergeStudent(int studentId) {
        try {
            String query = "DELETE FROM Student WHERE student_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, studentId);

                int affectedRows = preparedStatement.executeUpdate();

                if (affectedRows > 0) {
                    System.out.println("Student șters cu succes!");
                } else {
                    System.out.println("Nu s-a găsit niciun student cu ID-ul specificat.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda pentru afișarea tuturor studenților din baza de date.
     *
     * @return Lista de studenți.
     */
    public List<Student> afiseazaTotiStudentii(){
        try{
            String query = "SELECT * FROM Student";

            try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
                try(ResultSet resultSet = preparedStatement.executeQuery()){
                    List<Student> studenti = new ArrayList<>();

                    while(resultSet.next()){
                        int studentId = resultSet.getInt("student_id");
                        String nume = resultSet.getString("nume");
                        String prenume = resultSet.getString("prenume");
                        int varsta = resultSet.getInt("varsta");
                        int anStudiu = resultSet.getInt("an_studiu");
                        String specializare = resultSet.getString("specializare");

                        Student student = new Student(studentId, nume, prenume, varsta, anStudiu, specializare);
                        studenti.add(student);
                    }

                    return studenti;
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Metoda pentru afișarea notelor unui student în funcție de ID-ul său.
     *
     * @param studentId ID-ul studentului pentru care se vor afișa notele.
     * @return Lista de obiecte Nota asociate studentului.
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
     * Metoda pentru adăugarea unei noi note pentru un student în baza de date.
     *
     * @param nota   Obiectul Nota care va fi adăugat.
     * @param parola Parola pentru verificarea adăugării notei.
     */
    public void adaugaNota(Nota nota, String parola) {
        try {
            // Verifică dacă parola pentru adăugarea notelor este corectă
            if (verificaParolaMaterie(nota.getMaterieId(), parola)) {
                // Verifică dacă materia este în aceeași specializare cu studentul
                if (verificaMaterieSpecializare(nota.getMaterieId(), nota.getStudentId())) {
                    String query = "INSERT INTO Nota (student_id, materie_id, nota) VALUES (?, ?, ?)";

                    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                        preparedStatement.setInt(1, nota.getStudentId());
                        preparedStatement.setInt(2, nota.getMaterieId());
                        preparedStatement.setInt(3, nota.getNota());

                        preparedStatement.executeUpdate();
                        System.out.println("Nota adăugată cu succes!");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Materia nu corespunde cu specializarea studentului. Adăugarea notei a eșuat.");
                }
            } else {
                System.out.println("Parolă de materie invalidă. Adăugarea notei a eșuat.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda pentru verificarea corectitudinii parolei pentru o anumită materie.
     *
     * @param materieId ID-ul materiei.
     * @param parola    Parola pentru verificare.
     * @return `true` dacă parola este corectă, `false` în caz contrar.
     */
    private boolean verificaParolaMaterie(int materieId, String parola) {
        try {
            String query = "SELECT * FROM ParoleMaterii WHERE materie_id = ? AND parola = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, materieId);
                preparedStatement.setString(2, parola);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next(); // Dacă găsește o înregistrare, parola este corectă
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Metoda pentru verificarea dacă materia corespunde specializării studentului.
     *
     * @param materieId ID-ul materiei.
     * @param studentId ID-ul studentului.
     * @return `true` dacă materia corespunde specializării studentului, `false` în caz contrar.
     */
    private boolean verificaMaterieSpecializare(int materieId, int studentId) {
        try {
            String query = "SELECT m.materie_id, s.student_id " +
                    "FROM Materie m " +
                    "JOIN Student s ON m.materie_an = s.an_studiu AND m.materie_nume_specializare = s.specializare " +
                    "WHERE m.materie_id = ? AND s.student_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, materieId);
                preparedStatement.setInt(2, studentId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}

