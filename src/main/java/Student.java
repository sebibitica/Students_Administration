/**
 * Clasa Student reprezintă o entitate care descrie un student universitar.
 * Aceasta conține informații precum ID-ul studentului, numele, prenumele, vârsta,
 * anul de studiu și specializarea.
 */
public class Student {
    /**
     * ID-ul unic al studentului.
     */
    private int studentId;

    /**
     * Numele studentului.
     */
    private String nume;

    /**
     * Prenumele studentului.
     */
    private String prenume;

    /**
     * Vârsta studentului.
     */
    private int varsta;

    /**
     * Anul de studiu al studentului.
     */
    private int anStudiu;

    /**
     * Specializarea studentului.
     */
    private String specializare;

    /**
     * Constructorul clasei Student.
     *
     * @param studentId    ID-ul unic al studentului.
     * @param nume         Numele studentului.
     * @param prenume      Prenumele studentului.
     * @param varsta       Vârsta studentului.
     * @param anStudiu     Anul de studiu al studentului.
     * @param specializare Specializarea studentului.
     */
    public Student(int studentId, String nume, String prenume, int varsta, int anStudiu, String specializare) {
        this.studentId = studentId;
        this.nume = nume;
        this.prenume = prenume;
        this.varsta = varsta;
        this.anStudiu = anStudiu;
        this.specializare = specializare;
    }

    /**
     * Metoda pentru obținerea ID-ului studentului.
     *
     * @return ID-ul studentului.
     */
    public int getStudentId() {
        return studentId;
    }

    /**
     * Metoda pentru obținerea numelui studentului.
     *
     * @return Numele studentului.
     */
    public String getNume() {
        return nume;
    }

    /**
     * Metoda pentru obținerea prenumelui studentului.
     *
     * @return Prenumele studentului.
     */
    public String getPrenume() {
        return prenume;
    }

    /**
     * Metoda pentru obținerea vârstei studentului.
     *
     * @return Vârsta studentului.
     */
    public int getVarsta() {
        return varsta;
    }

    /**
     * Metoda pentru obținerea anului de studiu al studentului.
     *
     * @return Anul de studiu al studentului.
     */
    public int getAnStudiu() {
        return anStudiu;
    }

    /**
     * Metoda pentru obținerea specializării studentului.
     *
     * @return Specializarea studentului.
     */
    public String getSpecializare() {
        return specializare;
    }

    /**
     * Metoda pentru afișarea informațiilor despre student.
     *
     * @return Șir de caractere reprezentând informațiile despre student.
     */
    @Override
    public String toString() {
        return "-> [" +
                "studentId=" + studentId +
                ", nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                ", varsta=" + varsta +
                ", anStudiu=" + anStudiu +
                ", specializare='" + specializare + '\'' +
                ']';
    }
}

