package uk.ac.aber.dsc.pev2.main_assignment.GUI;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import uk.ac.aber.dsc.pev2.main_assignment.App;
import uk.ac.aber.dsc.pev2.main_assignment.ScoreData;

import java.util.Collections;

public class SavedGUI extends GUI {
    private GameMec gameMec;

    /**
     *
     * @param app main
     * @param gameMec passes the info from the Game
     */
    SavedGUI(App app, GameMec gameMec){
        this.app = app;
        this.gameMec = gameMec;
    }

    /**
     * Updates the screen
     */
    @Override
    public void updateScreen() {
        VBox savedVbox = new VBox();
        savedVbox.setAlignment(Pos.CENTER);
        Label label = new Label("Enter name of player");
        label.setStyle("-fx-font-family: \"Verdana\"; -fx-font-size: 20; -fx-text-fill: #000000;");
        savedVbox.getChildren().add(label);

        TextField inputName = new TextField();
        inputName.setPromptText("Name");
        inputName.setMaxWidth(200);

        Button save = new Button("Confirm");

        save.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            saveConfirm(inputName);
        });
        inputName.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER){
                saveConfirm(inputName);
            }
        });
        HBox inputAnswer = new HBox();
        inputAnswer.setAlignment(Pos.CENTER);
        inputAnswer.getChildren().addAll(inputName, save);

        savedVbox.getChildren().addAll(inputAnswer);
        app.setRoot(savedVbox);

    }

    private void saveConfirm(TextField inputName) {
        app.getSaveModule().getScoreData().add(new ScoreData(inputName.getText(),
                gameMec.getCardsOnTable().size()));
        Collections.sort(app.getSaveModule().getScoreData());
        new MenuGUI(app).updateScreen();
    }

    /**
     * Displays the options in command line
     */
    @Override
    public void menuList() {
        System.out.println("Enter the name of the player");
    }

    /**
     *
     * @param cmd user inputs
     */
    @Override
    public void cmdInput(String cmd) {
        try {
            app.getSaveModule().getScoreData().add(new ScoreData(cmd, gameMec.getCardsOnTable().size()));
            Collections.sort(app.getSaveModule().getScoreData());
            app.setScene(new MenuGUI(app));
        } catch (Exception e) {
            System.out.println("Syntax Error, could not save data");
        }
    }
}
