package uk.ac.aber.dsc.pev2.main_assignment;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import uk.ac.aber.dsc.pev2.main_assignment.GUI.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class App extends Application {
    //////////////////////////////////// Variables /////////////////////////////////////////
    private SaveModule saveModule = new SaveModule();
    private Stage primaryStage;
    private static Deck deck;
    private GUI gui;

    ///////////////////////////////// Getters and Setters /////////////////////////////////

    /**
     * will set te scene
     * @param gui The GUI in use
     */
    public void setScene(GUI gui) {
        Platform.runLater(() -> {
            this.gui = gui;
            gui.menuList();
            gui.updateScreen();
        });

    }

    /**
     * Displays the scene
     * @param root a node
     */
    public void setRoot(Parent root){
        Platform.runLater(() -> {
            primaryStage.getScene().setRoot(root);
            primaryStage.show();
        });
    }

    /**
     * gets the stage
     * @return Stage
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * gets teh save
     * @return SaveModule
     */
    public SaveModule getSaveModule() {
        return saveModule;
    }
    ///////////////////////////////////////////////////////////////////////////////////////

    private void cmd(){
        Scanner scan = new Scanner(System.in);
        while (true){
            String answer = scan.nextLine();
            gui.cmdInput(answer);
        }
    }

    public void initialise() {

        deck = new Deck();
        saveModule.load();

    }
    ////////////////////////////////// Main and Start /////////////////////////////////////

    public static void main(String args[]) {
        Application.launch(args);


    }

    /**
     * will start the program
     * @param primaryStage The stage to use
     * @throws Exception if it can't get Stage
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        //initialise();
        saveModule.load();
        MenuGUI menuGUI = new MenuGUI(this);

        StackPane sp = new StackPane();
        Scene primaryScene = new Scene(sp,1350,800);
        primaryStage.setScene(primaryScene);
        primaryStage.setTitle("Petter's deck game");
        primaryStage.show();
        setScene(menuGUI);

        Thread commandLineThread = new Thread(() -> cmd());
        commandLineThread.setName("CMD Thread");
        commandLineThread.start();
    }
    ///////////////////////////////////////////////////////////////////////////////////////

    /**
     * exits the game
     */
    public void exit() {
        saveModule.save();
        System.out.println("Exiting program");
        System.exit(0);
    }
}


