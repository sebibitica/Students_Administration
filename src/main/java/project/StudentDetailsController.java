package project;

import project.LogicClasses.Nota;
import project.LogicClasses.Student;
import project.LogicClasses.StudentDAO;
import project.Views.NoteBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import java.util.List;
import java.util.Optional;

/**
 * Controller class for the detailed view of a student, managing interactions related to student details, grades, and deletion.
 */
public class StudentDetailsController {

    @FXML
    private Label nameLabel;

    @FXML
    private Label specializationLabel;

    @FXML
    private Label yearLabel;

    @FXML
    private Label ageLabel;

    @FXML
    private AppController appController;

    @FXML
    private ScrollPane gradesScroll;

    @FXML
    private VBox gradesVBox;

    @FXML
    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    private Student student;

    private StudentDAO studentDAO;

    /**
     * Sets the StudentDAO for accessing student-related database operations.
     *
     * @param studentDAO The StudentDAO instance.
     */
    public void setStudentDAO(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    /**
     * Sets the student whose details are displayed in this controller.
     *
     * @param student The Student instance.
     */
    public void setStudent(Student student) {
        this.student = student;
        updateDetails();
    }

    /**
     * Updates the displayed details with the information of the currently selected student.
     */
    private void updateDetails() {
        nameLabel.setText(student.getNume() + " " + student.getPrenume());
        specializationLabel.setText("Specializare: " + student.getSpecializare());
        yearLabel.setText("Anul: " + student.getAnStudiu());
        ageLabel.setText("Varsta: " + student.getVarsta());
    }

    /**
     * Handles the button click to navigate back to the main view.
     * Removes the student details view from the stack and sets the main view visible.
     */
    @FXML
    private void handleBackButton() {
        appController.removeStudentDetails();
        //set back first view to visible
        for (Node child : appController.getContentStack().getChildren()) {
            child.setVisible(true);
        }
    }

    /**
     * Handles the button click to delete the current student.
     * Requests password input for confirmation and deletes the student from the database if authorized.
     */
    @FXML
    private void handleDeleteStudent() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Confirmare Ștergere Student");
        dialog.setHeaderText("Autentificare necesară");

        ButtonType passwordButtonType = new ButtonType("Confirmare", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(passwordButtonType, ButtonType.CANCEL);

        PasswordField passwordField = new PasswordField();
        dialog.getDialogPane().setContent(passwordField);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == passwordButtonType) {
                return passwordField.getText();
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(password -> {
            System.out.println("Parola introdusă: " + password);
            System.out.println("Parola necesară: 4444");

            if ("4444".equals(password)) {
                //password correct -> delete student from DB
                studentDAO.stergeStudent(student.getStudentId());
                handleBackButton();
                System.out.println("Ștergere student autorizată.");
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Parolă incorectă!");
                alert.showAndWait();
            }
        });
    }

    /**
     * Handles the button click to add a grade for the current student.
     * Opens a dialog to input details about the grade and adds it to the database if valid.
     * Requests subject password input for confirmation.
     */
    @FXML
    private void handleAddGrade() {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Adaugă Notă Student");
        dialog.setHeaderText("Introduceți detaliile notei");

        ButtonType confirmButtonType = new ButtonType("Confirmare", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, ButtonType.CANCEL);

        ComboBox<String> subjectComboBox = new ComboBox<>();
        subjectComboBox.getItems().addAll(studentDAO.getMateriiCompatibileCuSpecializarea(student.getStudentId())); // Aici trebuie să returnați numele materiilor din baza de date

        TextField gradeField = new TextField();
        gradeField.setPromptText("Nota");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Parola Materiei");

        // add gridpane to dialog
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(new Label("Materie:"), 0, 0);
        gridPane.add(subjectComboBox, 1, 0);
        gridPane.add(new Label("Notă:"), 0, 1);
        gridPane.add(gradeField, 1, 1);
        gridPane.add(new Label("Parola:"), 0, 2);
        gridPane.add(passwordField, 1, 2);

        dialog.getDialogPane().setContent(gridPane);

        final Button btConfirm = (Button) dialog.getDialogPane().lookupButton(confirmButtonType);

        btConfirm.addEventFilter(ActionEvent.ACTION, event -> {
            String selectedSubject = subjectComboBox.getValue();
            String grade = gradeField.getText();
            String subjectPassword = passwordField.getText();

            if (selectedSubject != null && !grade.isEmpty() && !subjectPassword.isEmpty() && grade.matches("[1-9]|10")) {
                if (studentDAO.verificaParolaMaterie(studentDAO.getMaterieIdFromName(selectedSubject), subjectPassword)) {
                    Nota nota = new Nota(0, student.getStudentId(), studentDAO.getMaterieIdFromName(selectedSubject), selectedSubject, Integer.parseInt(grade));
                    studentDAO.adaugaNota(nota);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Notă adăugată cu succes!");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Parolă incorectă!");
                    alert.showAndWait();
                    event.consume();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Completați toate câmpurile corect!");
                alert.showAndWait();
                event.consume();
            }
        });

        dialog.showAndWait();
    }

    /**
     * Handles the button click to view and display the grades of the current student.
     * Retrieves the student's grades from the database and populates the view.
     */
    @FXML
    private void handleViewGrades() {
        gradesScroll.setVisible(true);

        List<Nota> note = studentDAO.afiseazaNoteleStudentului(student.getStudentId());

        gradesVBox.getChildren().clear();

        for (Nota nota : note) {
            NoteBox noteBox = new NoteBox(nota);
            gradesVBox.getChildren().add(noteBox);
        }
    }
}

