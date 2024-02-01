package project;

import project.LogicClasses.StudentDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

/**
 * The controller class for the base of the app view.
 */
public class AppController {

    private StudentDAO studentDAO;

    @FXML
    private StackPane contentStack;

    /**
     * Gets the content stack.
     *
     * @return The content stack.
     */
    @FXML
    public StackPane getContentStack() {
        return contentStack;
    }

    /**
     * Initializes the controller with the provided StudentDAO.
     *
     * @param studentDAO The StudentDAO to be used.
     */
    public void initialize(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
        showMainView();
    }

    /**
     * Shows the main view by loading the corresponding FXML file.
     */
    private void showMainView() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main_view.fxml"));
            BorderPane studentListView = fxmlLoader.load();
            contentStack.getChildren().setAll(studentListView); // set view to the main view on the stack

            // set controller:
            MainController mainController = fxmlLoader.getController();
            mainController.setStudentDAO(studentDAO);
            mainController.setAppController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes the last added view from the stack.
     */
    public void removeStudentDetails() {
        // removes the last added view from the stack
        if (contentStack.getChildren().size() > 1) {
            contentStack.getChildren().remove(contentStack.getChildren().size() - 1);
        }
    }
}