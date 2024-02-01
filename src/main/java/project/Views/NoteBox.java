package project.Views;

import project.LogicClasses.Nota;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * The NoteBox class represents a graphical element to display information about a grade for a specific subject.
 */
public class NoteBox extends HBox {
    /**
     * Creates a new NoteBox with the given grade information.
     *
     * @param nota The Nota object containing information about the grade.
     */
    public NoteBox(Nota nota) {
        super(10); // space between elements
        setAlignment(Pos.CENTER_LEFT);
        setPadding(new Insets(10, 10, 10, 10));

        //set width to 70% of the width of the window
        this.setPrefWidth(800);

        Label numeMaterieLabel = new Label(nota.getMaterieNume());
        numeMaterieLabel.setPadding(new Insets(5));
        numeMaterieLabel.setFont(Font.font("Arial", FontWeight.BOLD,18));

        Label notaLabel = new Label("Nota: " + nota.getNota());
        notaLabel.setPadding(new Insets(5));
        notaLabel.setFont(Font.font("Arial", FontWeight.BOLD,20));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        this.getChildren().addAll(numeMaterieLabel, spacer, notaLabel);

        this.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(5), Insets.EMPTY)));
        this.setMaxWidth(Double.MAX_VALUE);

    }
}
