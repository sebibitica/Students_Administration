package project.LogicClasses;

/**
 * The Student class represents an entity that describes a university student.
 * It contains information such as the student's ID, name, surname, age,
 * year of study, and specialization.
 */
public class Student {
    /**
     * The unique ID of the student.
     */
    private int studentId;

    /**
     * The name of the student.
     */
    private String nume;

    /**
     * The surname of the student.
     */
    private String prenume;

    /**
     * The age of the student.
     */
    private int varsta;

    /**
     * The year of study of the student.
     */
    private int anStudiu;

    /**
     * The specialization of the student.
     */
    private String specializare;

    /**
     * Constructor for the Student class.
     *
     * @param studentId    The unique ID of the student.
     * @param nume         The name of the student.
     * @param prenume      The surname of the student.
     * @param varsta       The age of the student.
     * @param anStudiu     The year of study of the student.
     * @param specializare The specialization of the student.
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
     * Method to obtain the ID of the student.
     *
     * @return The ID of the student.
     */
    public int getStudentId() {
        return studentId;
    }

    /**
     * Method to obtain the name of the student.
     *
     * @return The name of the student.
     */
    public String getNume() {
        return nume;
    }

    /**
     * Method to obtain the surname of the student.
     *
     * @return The surname of the student.
     */
    public String getPrenume() {
        return prenume;
    }

    /**
     * Method to obtain the age of the student.
     *
     * @return The age of the student.
     */
    public int getVarsta() {
        return varsta;
    }

    /**
     * Method to obtain the year of study of the student.
     *
     * @return The year of study of the student.
     */
    public int getAnStudiu() {
        return anStudiu;
    }

    /**
     * Method to obtain the specialization of the student.
     *
     * @return The specialization of the student.
     */
    public String getSpecializare() {
        return specializare;
    }
}

