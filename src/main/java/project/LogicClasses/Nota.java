package project.LogicClasses;

/**
 * The Nota class represents an entity that stores information about a student's grades
 * in a specific subject.
 */
public class Nota {
    /**
     * The unique ID of the grade.
     */
    private int notaId;

    /**
     * The unique ID of the student associated with the grade.
     */
    private int studentId;

    /**
     * The unique ID of the subject associated with the grade.
     */
    private int materieId;

    /**
     * The name of the subject associated with the grade.
     */
    private String materieNume;

    /**
     * The value of the grade.
     */
    private int nota;

    /**
     * Constructor for the Nota class.
     *
     * @param notaId      The unique ID of the grade.
     * @param studentId   The unique ID of the student associated with the grade.
     * @param materieId   The unique ID of the subject associated with the grade.
     * @param materieNume The name of the subject associated with the grade.
     * @param nota        The value of the grade.
     */
    public Nota(int notaId, int studentId, int materieId, String materieNume, int nota) {
        this.notaId = notaId;
        this.studentId = studentId;
        this.materieId = materieId;
        this.materieNume = materieNume;
        this.nota = nota;
    }

    /**
     * Method to obtain the ID of the grade.
     *
     * @return The ID of the grade.
     */
    public int getNotaId() {
        return notaId;
    }

    /**
     * Method to obtain the ID of the student associated with the grade.
     *
     * @return The ID of the student associated with the grade.
     */
    public int getStudentId() {
        return studentId;
    }

    /**
     * Method to obtain the ID of the subject associated with the grade.
     *
     * @return The ID of the subject associated with the grade.
     */
    public int getMaterieId() {
        return materieId;
    }

    /**
     * Method to obtain the value of the grade.
     *
     * @return The value of the grade.
     */
    public int getNota() {
        return nota;
    }

    /**
     * Method to obtain the name of the subject associated with the grade.
     *
     * @return The name of the subject associated with the grade.
     */
    public String getMaterieNume() {
        return materieNume;
    }
}

