/**
 * Clasa Nota reprezintă o entitate care stochează informații despre notele unui student
 * la o anumită materie.
 */
public class Nota {
    /**
     * ID-ul unic al notei.
     */
    private int notaId;

    /**
     * ID-ul unic al studentului asociat notei.
     */
    private int studentId;

    /**
     * ID-ul unic al materiei asociate notei.
     */
    private int materieId;

    /**
     * Numele materiei asociate notei.
     */
    private String materieNume;

    /**
     * Valoarea notei.
     */
    private int nota;

    /**
     * Constructorul clasei Nota.
     *
     * @param notaId      ID-ul unic al notei.
     * @param studentId   ID-ul unic al studentului asociat notei.
     * @param materieId   ID-ul unic al materiei asociate notei.
     * @param materieNume Numele materiei asociate notei.
     * @param nota        Valoarea notei.
     */
    public Nota(int notaId, int studentId, int materieId, String materieNume, int nota) {
        this.notaId = notaId;
        this.studentId = studentId;
        this.materieId = materieId;
        this.materieNume = materieNume;
        this.nota = nota;
    }

    /**
     * Metoda pentru obținerea ID-ului notei.
     *
     * @return ID-ul notei.
     */
    public int getNotaId() {
        return notaId;
    }

    /**
     * Metoda pentru obținerea ID-ului studentului asociat notei.
     *
     * @return ID-ul studentului asociat notei.
     */
    public int getStudentId() {
        return studentId;
    }

    /**
     * Metoda pentru obținerea ID-ului materiei asociate notei.
     *
     * @return ID-ul materiei asociate notei.
     */
    public int getMaterieId() {
        return materieId;
    }

    /**
     * Metoda pentru obținerea valorii notei.
     *
     * @return Valoarea notei.
     */
    public int getNota() {
        return nota;
    }

    /**
     * Metoda pentru obținerea numelui materiei asociate notei.
     *
     * @return Numele materiei asociate notei.
     */
    public String getMaterieNume() {
        return materieNume;
    }

    /**
     * Metoda pentru reprezentarea sub formă de șir de caractere a notei și materiei.
     *
     * @return Șir de caractere reprezentând numele materiei și valoarea notei.
     */
    @Override
    public String toString() {
        return materieNume + " " + nota;
    }
}

