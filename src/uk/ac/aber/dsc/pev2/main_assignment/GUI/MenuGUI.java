package uk.ac.aber.dsc.pev2.main_assignment.GUI;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import uk.ac.aber.dsc.pev2.main_assignment.App;
import uk.ac.aber.dsc.pev2.main_assignment.Deck;


public class MenuGUI extends GUI {

    ///////////////////////////////////// Constructors //////////////////////////////////////

    /**
     * is the default constructor
     * @param app The main
     */
    public MenuGUI(App app) {
        this.app = app;

    }
    ///////////////////////////////////////////////////////////////////////////////////////

    /**
     * Updates the screen
     */
    @Override
    public void updateScreen() {

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        Button goToGame = new Button("Start deck game");
        goToGame.setPrefWidth(200);
        Button goToScoreboard = new Button("Leaderboard");
        goToScoreboard.setPrefWidth(200);
        Button endGame = new Button("Exit");
        endGame.setPrefWidth(200);
        goToGame.setStyle("-fx-font-family: \"Verdana\"; -fx-font-size: 20; -fx-text-fill: #000000;");
        goToScoreboard.setStyle("-fx-font-family: \"Verdana\"; -fx-font-size: 20; -fx-text-fill: #000000;");
        endGame.setStyle("-fx-font-family: \"Verdana\"; -fx-font-size: 20; -fx-text-fill: #000000;");

        goToGame.setOnAction(event -> app.setScene(new GameMec(app)));
        goToScoreboard.setOnAction(event -> app.setScene(new ScoreboardGUI(app)));
        endGame.setOnAction(event -> app.exit());

        vBox.getChildren().addAll(goToGame, goToScoreboard, endGame);
        app.setRoot(vBox);
    }

    /**
     * Displays the options in command line
     */
    @Override
    public void menuList() {
        System.out.println("1 - Start deck game");
        System.out.println("2 - Go to leaderboard");
        System.out.println("3 - Quit");
    }

    /**
     *
     * @param cmd user inputs
     */
    @Override
    public void cmdInput(String cmd) {
        String[] args = cmd.split(" ");
        switch (cmd) {
            case "1":
                app.setScene(new GameMec(app));
                break;
            case "2":
                app.setScene(new ScoreboardGUI(app));
                break;
            case "3":
                app.exit();
                break;
            default:
                System.out.println("Not a valid option");
        }
    }
}
