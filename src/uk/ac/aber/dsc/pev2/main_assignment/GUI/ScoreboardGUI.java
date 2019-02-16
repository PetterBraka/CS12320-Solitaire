package uk.ac.aber.dsc.pev2.main_assignment.GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import uk.ac.aber.dsc.pev2.main_assignment.App;
import uk.ac.aber.dsc.pev2.main_assignment.Deck;
import uk.ac.aber.dsc.pev2.main_assignment.ScoreData;

public class ScoreboardGUI extends GUI {

    private App app;
    private Deck deck;
    /**
     * is the default constructor
     * @param app The main
     */
    ScoreboardGUI(App app) {
        this.app = app;
    }

    /**
     * Updates the screen
     */
    @Override
    public void updateScreen() {

        VBox topBox = new VBox(20);
        topBox.setAlignment(Pos.TOP_CENTER);
        topBox.setPadding(new Insets(50));
        Label label = new Label("This is the top ten players");
        label.setStyle("-fx-font-family: \"Verdana\"; -fx-font-size: 20; -fx-text-fill: #000000;");
        topBox.getChildren().add(label);
        HBox scoreboard = new HBox();
        scoreboard.setSpacing(50);
        scoreboard.setAlignment(Pos.CENTER);
        topBox.getChildren().add(scoreboard);

        VBox top5 = new VBox(10);
        VBox rest = new VBox(10);
        for (int i = 0; i < 10; i++) {
            if (i > 4) {
                Label scor = new Label(i + ". " + app.getSaveModule().getScoreData().get(i).toString());
                scor.setStyle("-fx-font-family: \"Verdana\"; -fx-font-size: 15; -fx-text-fill: #000000;");
                rest.getChildren().add(scor);
            } else {
                Label scor = new Label(i + ". " + app.getSaveModule().getScoreData().get(i).toString());
                scor.setStyle("-fx-font-family: \"Verdana\"; -fx-font-size: 15; -fx-text-fill: #000000;");
                top5.getChildren().add(scor);
            }
        }
        top5.setAlignment(Pos.CENTER_LEFT);
        rest.setAlignment(Pos.CENTER_LEFT);
        scoreboard.getChildren().addAll(top5, rest);

        Button backToMenu = new Button("Back");
        backToMenu.setOnAction(event -> app.setScene(new MenuGUI(app)));
        backToMenu.setAlignment(Pos.BASELINE_CENTER);
        backToMenu.setStyle("-fx-font-family: \"Verdana\"; -fx-font-size: 15; -fx-text-fill: #000000;");

        topBox.getChildren().add(backToMenu);

        app.setRoot(topBox);
    }

    /**
     * Displays the options in command line
     */
    @Override
    public void menuList() {
        for (ScoreData scoreData : app.getSaveModule().getScoreData()) {
            System.out.println(scoreData.toString());
        }
        System.out.println("To go back press 1");
    }

    /**
     *
     * @param cmd user inputs
     */
    @Override
    public void cmdInput(String cmd) {
        switch (cmd) {
            case "1":
                app.setScene(new MenuGUI(app));
                break;
            default:
                System.out.println("Not a valid input");
        }
    }


}
