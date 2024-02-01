package project;

import project.LogicClasses.Student;
import project.LogicClasses.StudentDAO;
import project.Views.StudentBox;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.util.List;

/**
 * Controller class for the main application view, handling interactions related to student management.
 * View the list of students, add a new student, search for students.
 */
public class MainController {

    @FXML
    private VBox addStudentVBox;

    @FXML
    private VBox studentsVBox;

    @FXML
    private ImageView imageUVT;

    @FXML
    private ScrollPane scrollableStudentsVBox;

    private StudentDAO studentDAO;

    private AppController appController;

    @FXML
    private TextField numeTextField;

    @FXML
    private TextField prenumeTextField;

    @FXML
    private TextField specializareTextField;

    @FXML
    private TextField anStudiuTextField;

    @FXML
    private TextField varstaTextField;

    @FXML
    private Label errorMessageLabel;

    @FXML
    private HBox searchBox;

    @FXML
    private TextField searchField;

    @FXML
    private ComboBox<Integer> yearComboBox;

    @FXML
    private ComboBox<String> specializationComboBox;

    private List<Student> students;

    /**
     * Sets the AppController for communication between controllers.
     *
     * @param appController The AppController to be set.
     */
    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    /**
     * Sets the StudentDAO for database access.
     *
     * @param studentDAO The StudentDAO to be set.
     */
    public void setStudentDAO(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    /**
     * Handles the button click to display a list of students in a scrollable and clickable format.
     * Retrieves the student data from the database and populates the view with student boxes.
     */
    @FXML
    private void handleAfisareStudentiButton() {
        scrollableStudentsVBox.setVisible(true);
        scrollableStudentsVBox.setManaged(true);
        searchBox.setVisible(true);
        searchBox.setManaged(true);
        addStudentVBox.setVisible(false);
        addStudentVBox.setManaged(false);
        imageUVT.setVisible(false);
        imageUVT.setManaged(false);
        errorMessageLabel.setVisible(false);


        this.students = studentDAO.getStudents();

        // Clear existing content
        studentsVBox.getChildren().clear();
        specializationComboBox.getItems().clear();
        yearComboBox.getItems().clear();
        searchField.clear();

        specializationComboBox.getItems().addAll(studentDAO.getSpecializari());
        specializationComboBox.getItems().add(0,null);
        yearComboBox.getItems().addAll(1,2,3);
        yearComboBox.getItems().add(0,null);



        // Display each student in a box
        for (Student student : students) {
            StudentBox studentBox = new StudentBox(student);
            studentBox.setOnMouseClicked(e -> onStudentBoxClicked(student));
            studentsVBox.getChildren().add(studentBox);
        }

    }

    /**
     * Handles the button click to switch to the page where details about a new student are added.
     * Displays the form for entering student details.
     */
    @FXML
    private void handleAdaugaStudentButton() {
        scrollableStudentsVBox.setVisible(false);
        scrollableStudentsVBox.setManaged(false);
        searchBox.setVisible(false);
        searchBox.setManaged(false);
        addStudentVBox.setVisible(true);
        addStudentVBox.setManaged(true);
        imageUVT.setVisible(false);
        imageUVT.setManaged(false);
    }

    /**
     * Handles the button click to add a new student to the database.
     * Validates input, adds the student to the database, and provides feedback.
     */
    @FXML
    private void handleAdaugaStudentTotalButton() {
        //add student to DB

        String nume = numeTextField.getText();
        if(nume.matches("[0-9]+") || nume.isEmpty()){
            System.out.println("Nume invalid!");
            errorMessageLabel.setText("Nume invalid!");
            errorMessageLabel.setVisible(true);
            return;
        }
        String prenume = prenumeTextField.getText();
        if(prenume.matches("[0-9]+") || prenume.isEmpty()){
            System.out.println("Prenume invalid!");
            errorMessageLabel.setText("Prenume invalid!");
            errorMessageLabel.setVisible(true);
            return;
        }
        String specializare = specializareTextField.getText();
        if(specializare.matches("[0-9]+") || specializare.isEmpty()){
            System.out.println("Specializare invalida!");
            errorMessageLabel.setText("Specializare invalida!");
            errorMessageLabel.setVisible(true);
            return;
        }
        String anStudiu = anStudiuTextField.getText();
        if(!anStudiu.matches("[0-9]+") || anStudiu.isEmpty()){
            System.out.println("An studiu invalid!");
            errorMessageLabel.setText("An studiu invalid!");
            errorMessageLabel.setVisible(true);
            return;
        } else{
            int an = Integer.parseInt(anStudiu);
            if(an < 1 || an > 5){
                System.out.println("An studiu invalid!");
                errorMessageLabel.setText("An studiu invalid!");
                errorMessageLabel.setVisible(true);
                return;
            }
        }
        String varsta = varstaTextField.getText();
        if(!varsta.matches("[0-9]+") || varsta.isEmpty()){
            System.out.println("Varsta invalida!");
            errorMessageLabel.setText("Varsta invalida!");
            errorMessageLabel.setVisible(true);
            return;
        } else{
            int varstaInt = Integer.parseInt(varsta);
            if(varstaInt < 18 || varstaInt > 200){
                System.out.println("Varsta invalida!");
                errorMessageLabel.setText("Varsta invalida!");
                errorMessageLabel.setVisible(true);
                return;
            }
        }

        Student student = new Student(0,nume, prenume,Integer.parseInt(varsta),Integer.parseInt(anStudiu), specializare);
        studentDAO.adaugaStudent(student);
        errorMessageLabel.setText("Student adaugat cu succes!");
        errorMessageLabel.setVisible(true);
        //clear all the text fields
        numeTextField.clear();
        prenumeTextField.clear();
        specializareTextField.clear();
        anStudiuTextField.clear();
        varstaTextField.clear();

    }

    /**
     * Handles the click on a student box, loading and adding a StudentDetailsPage to the application stack.
     * Displays detailed information about the selected student.
     *
     * @param student The selected student for which details will be displayed.
     */
    @FXML
    private void onStudentBoxClicked(Student student){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("student_details.fxml"));
            BorderPane studentDetailsView = loader.load();

            StudentDetailsController detailsController = loader.getController();
            detailsController.setStudent(student);
            detailsController.setAppController(appController);
            detailsController.setStudentDAO(studentDAO);

            for (Node child : appController.getContentStack().getChildren()) {
                child.setVisible(false);
            }

            appController.getContentStack().getChildren().add(studentDetailsView); //add to stack
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the button click to search for students.
     * Filters the list of students based on the search text, year, and specialization.
     */
    @FXML
    private void handleSearch(){
        String searchText = searchField.getText().toLowerCase();
        Integer selectedYear = yearComboBox.getValue();
        String selectedSpecialization = specializationComboBox.getValue();
        updateStudentList(searchText,selectedYear,selectedSpecialization);
    }

    /**
     * Updates the list of students based on the search text, year, and specialization.
     *
     * @param searchText The search text to be matched against the student name.
     * @param selectedYear The year to be matched against the student year.
     * @param selectedSpecialization The specialization to be matched against the student specialization.
     */
    private void updateStudentList(String searchText, Integer selectedYear, String selectedSpecialization) {
        studentsVBox.getChildren().clear();
        for (Student student : students) {
            if (matchesFilters(student, searchText, selectedYear, selectedSpecialization)) {
                StudentBox studentBox = new StudentBox(student);
                studentBox.setOnMouseClicked(e -> onStudentBoxClicked(student));
                studentsVBox.getChildren().add(studentBox);
            }
        }
    }

    /**
     * Checks if a student matches the search text, year, and specialization.
     *
     * @param student The student to be checked.
     * @param searchText The search text to be matched against the student name.
     * @param selectedYear The year to be matched against the student year.
     * @param selectedSpecialization The specialization to be matched against the student specialization.
     * @return True if the student matches the search text, year, and specialization, false otherwise.
     */
    private boolean matchesFilters(Student student, String searchText, Integer selectedYear, String selectedSpecialization) {
        boolean matchesSearchText = student.getNume().toLowerCase().contains(searchText) || student.getPrenume().toLowerCase().contains(searchText);
        boolean matchesYear = selectedYear == null || selectedYear.equals(student.getAnStudiu());
        boolean matchesSpecialization = selectedSpecialization == null || selectedSpecialization.equals(student.getSpecializare());

        return matchesSearchText && matchesYear && matchesSpecialization;
    }


}
