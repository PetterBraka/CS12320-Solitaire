package uk.ac.aber.dsc.pev2.main_assignment;

public class ScoreData implements Comparable<ScoreData>{

    private String name;
    private int score;

    /**
     *
     * @param name Players name
     * @param score Players score
     */
    public ScoreData(String name, int score) {
        this.name = name;
        this.score = score;
    }

    ///////////////////////////////// Getters and Setters /////////////////////////////////

    /**
     * gets the name of the player
     * @return the name
     */
    String getName() {
        return name;
    }

    /**
     * gets the score of the player
     * @return the score
     */
    int getScore() {
        return score;
    }

    /**
     * sets the Name
     * @param name Name of player
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * sets the score
     * @param score Score of player
     */
    public void setScore(int score) {
        this.score = score;
    }
    ///////////////////////////////////////////////////////////////////////////////////////

    @Override
    public int compareTo(ScoreData o) {
        return score-o.score;
    }

    /**
     * Makes an string
     * @return the Name and score
     */
    @Override
    public String toString() {
        return name + " " + score + " ";
    }
}
