import java.util.List;
import java.util.Scanner;

/**
 * Clasa StudentOperations furnizează operații pentru gestionarea studenților și notelor acestora.
 * Aceasta interacționează cu un obiect StudentDAO pentru a accesa și manipula datele.
 */
public class StudentOperations {
    /**
     * Obiectul StudentDAO utilizat pentru accesarea datelor studenților și notelor.
     */
    private StudentDAO studentDAO;

    /**
     * Constructorul clasei StudentOperations.
     *
     * @param studentDAO Obiectul StudentDAO pentru manipularea datelor studenților și notelor.
     */
    public StudentOperations(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    /**
     * Metoda pentru adăugarea unui student.
     *
     * @param scanner Scanner pentru citirea datelor de la utilizator.
     */
    public void adaugaStudent(Scanner scanner) {
        System.out.print("Introdu numele studentului: ");
        String nume = scanner.next();

        System.out.print("Introdu prenumele studentului: ");
        String prenume = scanner.next();

        System.out.print("Introdu varsta studentului: ");
        int varsta = scanner.nextInt();

        System.out.print("Introdu anul de studiu al studentului: ");
        int anStudiu = scanner.nextInt();

        System.out.print("Introdu specializarea studentului: ");
        String specializare = scanner.next();

        Student student = new Student(0,nume, prenume, varsta, anStudiu, specializare);
        studentDAO.adaugaStudent(student);
    }

    /**
     * Metoda pentru afișarea tuturor studenților.
     */
    public void afisareTotiStudentii() {
        List<Student> studenti = studentDAO.afiseazaTotiStudentii();

        if (studenti.isEmpty()) {
            System.out.println("Nu există studenți în baza de date.");
        } else {
            System.out.println("Studenții sunt: ");
            for (Student student : studenti) {
                System.out.println(student);
            }
        }
    }

    /**
     * Metoda pentru căutarea studenților în funcție de un criteriu și o valoare specificată.
     *
     * @param scanner Scanner pentru citirea datelor de la utilizator.
     */
    public void cautaStudent(Scanner scanner) {
        System.out.print("Introdu criteriul de căutare (nume, prenume, varsta, an_studiu, specializare): ");
        String criteriu = scanner.next();
        if(!criteriu.equals("nume") && !criteriu.equals("prenume") && !criteriu.equals("varsta") && !criteriu.equals("an_studiu") && !criteriu.equals("specializare")){
            System.out.println("Criteriu invalid. Încearcă din nou.");
            return;
        }

        System.out.print("Introdu valoarea după care se caută: ");
        String valoare = scanner.next();

        List<Student> studentiGasiti = studentDAO.cautaStudenti(criteriu, valoare);

        if (studentiGasiti.isEmpty()) {
            System.out.println("Nu s-au găsit studenți.");
        } else {
            System.out.println("Studenții găsiți sunt: ");
            for (Student student : studentiGasiti) {
                System.out.println(student);
            }
        }
    }

    /**
     * Metoda pentru ștergerea unui student în funcție de ID-ul său.
     *
     * @param scanner Scanner pentru citirea datelor de la utilizator.
     */
    public void stergeStudent(Scanner scanner) {
        System.out.print("Introdu ID-ul studentului pe care vrei să-l ștergi: ");
        int studentId = scanner.nextInt();

        studentDAO.stergeStudent(studentId);
    }

    /**
     * Metoda pentru afișarea notelor unui student în funcție de ID-ul său.
     *
     * @param scanner Scanner pentru citirea datelor de la utilizator.
     */
    public void afiseazaNoteleStudentului(Scanner scanner) {
        System.out.print("Introdu ID-ul studentului pentru care vrei să afișezi notele: ");
        int studentId = scanner.nextInt();

        List<Nota> note = studentDAO.afiseazaNoteleStudentului(studentId);

        if (note.isEmpty()) {
            System.out.println("Nu există note pentru studentul cu ID-ul specificat.");
        } else {
            System.out.println("Notele studentului sunt: ");
            for (Nota nota : note) {
                System.out.println(nota);
            }
        }
    }


    /**
     * Metoda pentru adăugarea unei noi note pentru un student.
     *
     * @param scanner Scanner pentru citirea datelor de la utilizator.
     */
    public void adaugaNota(Scanner scanner) {
        System.out.print("Introdu ID-ul studentului: ");
        int studentId = scanner.nextInt();

        System.out.print("Introdu ID-ul materiei: ");
        int materieId = scanner.nextInt();


        System.out.print("Introdu nota: ");
        int nota = scanner.nextInt();
        if(nota < 1 || nota > 10){
            System.out.println("Nota invalida. Încearcă din nou.");
            return;
        }

        Nota notaNoua = new Nota(0, studentId, materieId, "", nota);

        System.out.println("Introdu parola: ");
        String parola = scanner.next();

        studentDAO.adaugaNota(notaNoua, parola);
    }
}
