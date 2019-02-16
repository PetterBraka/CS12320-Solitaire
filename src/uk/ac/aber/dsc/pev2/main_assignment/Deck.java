package uk.ac.aber.dsc.pev2.main_assignment;

import uk.ac.aber.dsc.pev2.main_assignment.Cards.Suit;
import uk.ac.aber.dsc.pev2.main_assignment.Cards.Value;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Deck {
    private String cardVal;
    private String cardSuit;
    private ArrayList<Deck> deck;
    private ArrayList<Deck> shuffledDeck = new ArrayList<>();

    /**
     * Default constructor
     */
    public Deck() {
        deck = new ArrayList<>();
        for (int i = 2; i < 10; i++) {
            for (Suit s : Suit.values()) {
                cardSuit = s.toString();
                cardVal = Integer.toString(i);
                deck.add(new Deck(cardVal, cardSuit));
            }
        }
        for (Value v : Value.values()){
            for (Suit s : Suit.values()) {
                cardSuit = s.toString();
                cardVal = v.toString();
                deck.add(new Deck(cardVal, cardSuit));
            }
        }
    }

    /**
     *
     * @param type The value of the card
     * @param suit The Suit of the card
     */
    private Deck(String type, String suit) {
        cardVal = type;
        cardSuit = suit;
    }

    ///////////////////////////////// Getters and Setters /////////////////////////////////
    public String getCardVal() {
        return cardVal;
    }

    public String getCardSuit() {
        return cardSuit;
    }

    public String getCard() {
        return cardVal + cardSuit;
    }

    public ArrayList<Deck> getDeck() {
        return deck;
    }

    public ArrayList<Deck> getShuffledDeck() {
        return shuffledDeck;
    }

    public void setCard(String url){
        this.cardVal = Character.toString(url.charAt(0));
        this.cardSuit = Character.toString(url.charAt(1));
    }
    ///////////////////////////////////////////////////////////////////////////////////////

    /**
     * Shuffles the deck
     */
    public void randomShuffle() {
        shuffledDeck = deck;
        Collections.shuffle(shuffledDeck);
        System.err.println("Starting shuffling");
        System.out.println("Shuffling completed");
    }

    /**
     * loads card from file
     * @param filename name of the file
     * @throws IOException if it can't find the file
     */
    void loadCard(String filename) throws IOException {
        deck = new ArrayList<>();

        try (FileReader fr = new FileReader(filename);
             Scanner infile = new Scanner(fr)) {
            while (deck.size() < 52) {
                this.cardVal = infile.nextLine();
                this.cardSuit = infile.nextLine();
                Deck newDeck = new Deck(cardVal, cardSuit);
                this.deck.add(newDeck);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        Deck deck = Deck.class.cast(o);
        return cardVal.equals(deck.cardVal) & cardSuit.equals(deck.cardSuit);
    }

    /**
     * Creates a string
     * @return prints card value, card suit and add the file extension
     */
    @Override
    public String toString() {
        return cardVal + cardSuit ;
    }
}
