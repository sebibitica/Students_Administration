import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main{

    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentsapp", "root", "root");
            StudentDAO studentDAO = new StudentDAO(connection);

            Scanner scanner = new Scanner(System.in);
            StudentOperations studentOperations = new StudentOperations(studentDAO);

            while (true) {
                afiseazaMeniu();
                int optiune = scanner.nextInt();

                switch (optiune) {
                    case 1:
                        studentOperations.afisareTotiStudentii();
                        break;
                    case 2:
                        studentOperations.cautaStudent(scanner);
                        break;
                    case 3:
                        studentOperations.adaugaStudent(scanner);
                        break;
                    case 4:
                        studentOperations.stergeStudent(scanner);
                        break;
                    case 5:
                        studentOperations.afiseazaNoteleStudentului(scanner);
                        break;
                    case 6:
                        studentOperations.adaugaNota(scanner);
                        break;
                    case 0:
                        System.out.println("La revedere!");
                        System.exit(0);
                    default:
                        System.out.println("Opțiune invalidă. Încearcă din nou.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void afiseazaMeniu() {
        System.out.println("\n===== Meniu =====");
        System.out.println("1. Afiseaza studentii");
        System.out.println("2. Caută student");
        System.out.println("3. Adaugă student");
        System.out.println("4. Sterge student");
        System.out.println("5. Afiseaza notele unui student");
        System.out.println("6. Adauga nota");
        System.out.println("0. Ieșire");
        System.out.print("Alege o opțiune: ");
    }
}
