package uk.ac.aber.dsc.pev2.main_assignment.GUI;

import com.sun.org.apache.bcel.internal.generic.MONITORENTER;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import uk.ac.aber.dsc.pev2.main_assignment.App;
import uk.ac.aber.dsc.pev2.main_assignment.Deck;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class GameMec extends GUI{
    private ArrayList<Deck> cardsOnTable = new ArrayList<>();
    private ImageView cardFrame = new ImageView();
    private ArrayList<Deck> deck = null;
    private StackPane selectedPane;
    private boolean repeat;
    private Deck selected;
    Deck fullDeck = new Deck();

    /**
     * is the default constructor
     *
     * param app The main
     */
    GameMec(App app) {
        this.app = app;
        fullDeck.randomShuffle();
        deck = fullDeck.getShuffledDeck();
        this.app.initialise();
    }

    ///////////////////////////////// Getters and Setters /////////////////////////////////
    ///////////////Getters and setters will get values and sets values/////////////////////

    ArrayList<Deck> getCardsOnTable() {
        return cardsOnTable;
    }

    private Deck getSelected() {
        return selected;
    }

    public ArrayList<Deck> getDeck() {
        return deck;
    }

    private void setSelected(Deck selected) {
        this.selected = selected;
    }

    //////////////////////////////////// Drawing deck ////////////////////////////////////
    private void draw(boolean autoOn) {
        if (deck.size() != 0) {
            cardsOnTable.add(deck.get(0));
            deck.remove(0);
            updateScreen();
        } else {
            if (!autoOn)
                System.err.println("All the deck is on the table");
        }
    }

    /**
     * Updates the screen
     */
    @Override
    public void updateScreen() {

        cardFrame = new ImageView(new Image("cards/s.gif"));
        cardFrame.setPreserveRatio(true);
        cardFrame.setFitWidth(100);

        VBox vBox = new VBox();
        addingCard(vBox);
        app.setRoot(vBox);
    }

    public void addingCard(VBox vBox) {

        int column = 1;
        HBox line = new HBox();
        Label scorLabel = new Label(Integer.toString(cardsOnTable.size()));
        scorLabel.setWrapText(true);
        scorLabel.setStyle("-fx-font-family: \"Verdana\"; -fx-font-size: 20; -fx-text-fill: #000000;");
        ImageView helpCard = new ImageView(new Image("cards/h.gif"));
        helpCard.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> help());
        if (!(deck.size() == 0)) {
            ImageView backside = new ImageView(new Image("cards/b.gif"));
            backside.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                draw(false);
            });
            line.getChildren().add(backside);

        } else {
            line.getChildren().add(new ImageView());
        }
        line.getChildren().add(scorLabel);
        line.getChildren().add(helpCard);
        line.setSpacing(550);
        line.setPadding(new Insets(10));
        vBox.getChildren().add(line);

        if (cardsOnTable != null) {
            line = new HBox();
            for (Deck deck : (ArrayList<Deck>) cardsOnTable.clone()) {
                line.getChildren().add(drawCards(deck));
                if ((column % 13) == 0) {
                    vBox.getChildren().add(line);
                    line = new HBox();
                    column = 0;
                }
                column++;
            }
            vBox.getChildren().add(line);
        }
        if (!validMoves(cardsOnTable)) {
            Button button = new Button("No moves left");
            button.setStyle("-fx-font-family: \"Verdana\"; -fx-font-size: 20; -fx-text-fill: #000000;");
            vBox.getChildren().add(button);
            vBox.setAlignment(Pos.TOP_CENTER);
            System.out.println("No moves left");
            button.setOnAction(event -> app.setScene(new SavedGUI(app, this)));


        }
    }

    private void moseClick(Deck deck, StackPane sp) {
        if (selectedPane != null) {
            selectedPane.getChildren().remove(cardFrame);
        }
        sp.getChildren().add(cardFrame);
        selectedPane = sp;
        if (selected == null) {
            selected = deck;
        } else {
            moveCard(deck);
            selected = null;
            selectedPane.getChildren().remove(cardFrame);
        }
        System.err.println(selected);
        System.out.println(deck);

        if (this.deck.size() > 0) {
            if (deck.getCard().equals("b.gif")) {
                updateScreen();
            }
        }
    }

    private void moveCard(Deck newSelected) {

        int oldSel = cardsOnTable.indexOf(selected);
        int posSel = cardsOnTable.indexOf(newSelected);


        if (((oldSel > posSel) &
                ((oldSel - 1) == posSel) || ((oldSel - 3) == posSel))
                & (selected.getCardSuit().equals(newSelected.getCardSuit()))
                || ((selected.getCardVal().equals(newSelected.getCardVal())))) {
            cardsOnTable.remove(newSelected);
            cardsOnTable.remove(selected);
            cardsOnTable.add(posSel, selected);
            updateScreen();

        } else {
            System.err.println("The cardsOnTable need to be same suit or same value");
        }

    }

    private StackPane drawCards(Deck deck) {
        StackPane stack = new StackPane();
        ImageView iv = new ImageView(new Image(Objects.requireNonNull(getClass().getClassLoader()
                .getResource("cards/" + deck + ".gif")).toString(), true));
        iv.setFitWidth(100);
        iv.setPreserveRatio(true);
        iv.setSmooth(true);
        iv.setCache(true);

        iv.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> moseClick(deck, stack));

        stack.getChildren().addAll(iv);
        return stack;
    }

    /////////////////////////// Automatic and Validator ///////////////////////////////////
    private void autoPlay() {
        ArrayList<Deck> possibleOne = new ArrayList<>();
        ArrayList<Deck> possibleTwo = new ArrayList<>();

        if (repeat) {
            for (Deck deck : cardsOnTable) {
                int cardIndex = cardsOnTable.indexOf(deck);
                if (cardIndex > 2) {
                    Deck fourthLeft = cardsOnTable.get(cardsOnTable.indexOf(deck) - 3);
                    if (deck.getCardSuit().equals(fourthLeft.getCardSuit()) ||
                            deck.getCardVal().equals(fourthLeft.getCardVal())) {
                        possibleTwo.add(cardsOnTable.get(cardsOnTable.indexOf(fourthLeft)));
                        possibleTwo.add(deck);
                    }
                }
                if (cardIndex != 0) {
                    Deck firstLeft = cardsOnTable.get(cardsOnTable.indexOf(deck) - 1);
                    if (deck.getCardSuit().equals(firstLeft.getCardSuit()) ||
                            deck.getCardVal().equals(firstLeft.getCardVal())) {
                        possibleOne.add(cardsOnTable.get(cardsOnTable.indexOf(firstLeft)));
                        possibleOne.add(deck);
                    }
                }
            }
            if (getDeck().size() == 0) {
                repeat = false;
            }

        }
        if (possibleTwo.size() != 0) {
            setSelected(possibleTwo.get(1));
            moveCard(possibleTwo.get(0));
        } else if (possibleOne.size() != 0) {
            setSelected(possibleOne.get(1));
            moveCard(possibleOne.get(0));
        } else repeat = true;
    }

    private boolean validMoves(ArrayList<Deck> decks) {

        if (getDeck().size() != 0) {
            return true;
        }
        for (Deck deck : cardsOnTable) {
            int cardIndex = decks.indexOf(deck);
            if (cardIndex > 2) {
                Deck fourthLeft = decks.get(decks.indexOf(deck) - 3);
                if (deck.getCardSuit().equals(fourthLeft.getCardSuit()) ||
                        deck.getCardVal().equals(fourthLeft.getCardVal())) {
                    return true;
                }
            }
            if (cardIndex != 0) {
                Deck firstLeft = decks.get(decks.indexOf(deck) - 1);
                if (deck.getCardSuit().equals(firstLeft.getCardSuit()) ||
                        deck.getCardVal().equals(firstLeft.getCardVal())) {
                    return true;
                }
            }
        }
        return false;
    }

    /////////////////////////// Method's for commandline //////////////////////////////////
    private void help() {
        final Stage popUpWindow = new Stage();
        popUpWindow.setTitle("Help");
        popUpWindow.initOwner(app.getPrimaryStage());
        VBox popUpVbox = new VBox(20);
        Label label = new Label(
                "-\t The object of the game is to end up with one pile of deck.\n" +
                        "\t And this makes the best is a score of 1. \n\n" +
                        "-\t You are just allowed to move card to the left.\n" +
                        "-\t Selected card on top of first card to the left.\n" +
                        "-\t Selected card on top of fourth card to the left.\n");

        label.setStyle("-fx-font-family: \"Verdana\"; -fx-font-size: 15; -fx-text-fill: #000000;");
        popUpVbox.getChildren().add(label);

        Button oneMove = new Button("Make one move");
        oneMove.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> autoPlay());
        popUpVbox.getChildren().add(oneMove);

        popUpVbox.setAlignment(Pos.CENTER);


        Scene popUpScene = new Scene(popUpVbox, 600, 200);
        popUpWindow.setScene(popUpScene);
        popUpWindow.show();
    }

    private void moveCMD(int i) {
        setSelected(cardsOnTable.get(cardsOnTable.size() - 1));
        Deck newDeck = cardsOnTable.get(cardsOnTable.size() - 1 - i);
        moveCard(newDeck);
        setSelected(cardsOnTable.get(cardsOnTable.size() - 1));

    }

    private void amalgamate() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Type in the card you want to move");
        Deck firstDeck = new Deck();
        firstDeck.setCard(scan.nextLine());
        System.out.println("Type in the card you want to move too");
        Deck secondDeck = new Deck();
        secondDeck.setCard(scan.nextLine());
        setSelected(cardsOnTable.get(cardsOnTable.indexOf(firstDeck)));
        moveCard(cardsOnTable.get(cardsOnTable.indexOf(secondDeck)));
        setSelected(cardsOnTable.get(cardsOnTable.size() - 1));
    }
    ///////////////////////////////////////////////////////////////////////////////////////

    /**
     * Displays the options in command line
     */
    @Override
    public void menuList() {
        System.out.println("1 - Print the pack out (this is so you can check that it plays properly)");
        System.out.println("2 - Deal a card");
        System.out.println("3 - Make a move, move last pile onto previous one");
        System.out.println("4 - Make a move, move last pile back over two piles");
        System.out.println("5 - Amalgamate piles in the middle (by giving value and suit)");
        System.out.println("6 - Print the displayed deck on the command line");
        System.out.println("7 - Play for me once (if two possible moves, makes the ‘furthest’ move)");
        System.out.println("8 - Play for me many times");
    }

    /**
     * @param cmd user inputs
     */
    @Override
    public void cmdInput(String cmd) {
        switch (cmd) {
            case "1":
                System.out.println(getDeck());
                break;
            case "2":
                draw(false);
                if (getSelected() != null) {
                    setSelected(null);
                }
                setSelected(cardsOnTable.get(cardsOnTable.size() - 1));
                updateScreen();
                break;
            case "3":
                moveCMD(1);
                break;
            case "4":
                moveCMD(3);
                break;
            case "5":
                amalgamate();
                break;
            case "6":
                System.out.println(cardsOnTable);
                break;
            case "7":
                autoPlay();
                break;
            case "8":
                do {
                    draw(true);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    autoPlay();
                } while (validMoves(cardsOnTable));
                break;
            default:
                System.out.println("Not a valid option");
        }
        menuList();
    }
    ///////////////////////////////////////////////////////////////////////////////////////
}