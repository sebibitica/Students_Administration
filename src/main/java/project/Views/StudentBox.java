package project.Views;

import project.LogicClasses.Student;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * The StudentBox class represents a graphical element to display information about a student.
 */
public class StudentBox extends HBox{
    /**
     * Creates a new StudentBox with the given student information.
     *
     * @param student The Student object containing information about the student.
     */
    public StudentBox(Student student) {
        super(10); // space between elements
        setAlignment(Pos.CENTER_LEFT);
        setPadding(new Insets(10, 10, 10, 10));

        //set width to 70% of the width of the window
        this.setPrefWidth(0.7 * 1200);

        // Label for ID with white background
        Label idLabel = new Label("ID: " + student.getStudentId());
        idLabel.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        idLabel.setPadding(new Insets(5)); // padding around the ID text
        idLabel.setFont(Font.font("Arial", FontWeight.BOLD,18));

        Label nameLabel = new Label(student.getNume() + " " + student.getPrenume());
        nameLabel.setPadding(new Insets(5)); // padding around the name text
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD,20));

        // Spacer to push the details label to the right
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Label for the rest of the student's details
        Label detailsLabel = new Label("Specializare: " + student.getSpecializare() + ", An: " + student.getAnStudiu());
        detailsLabel.setPadding(new Insets(5)); // padding around the details text
        detailsLabel.setFont(Font.font("Arial", FontWeight.BOLD,18));

        // Add the labels and spacer to `HBox`
        this.getChildren().addAll(idLabel, nameLabel, spacer, detailsLabel);

        // Set the style for `StudentBox`
        this.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(5), Insets.EMPTY)));
        this.setMaxWidth(Double.MAX_VALUE);

        //if u hover over the student box it will change color to a darker gray
        this.setOnMouseEntered(e -> {
            this.setBackground(new Background(new BackgroundFill(Color.GRAY, new CornerRadii(5), Insets.EMPTY)));
        });
        //if u hover out of the student box it will change color to a lighter gray
        this.setOnMouseExited(e -> {
            this.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(5), Insets.EMPTY)));
        });
    }
}
