package uk.ac.aber.dsc.pev2.main_assignment;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class SaveModule {

    private ArrayList<ScoreData> scoreData = new ArrayList<>();

    public ArrayList<ScoreData> getScoreData() {
        return scoreData;
    }

    /**
     * Saving data
     */
    void save() {
        Collections.sort(scoreData);
        File scores = new File("src.uk.ac.aber.dsc.pev2.main_assignment.leaderBoard");
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(scores));

            for (int i = 0; i < 10 && i < scoreData.size(); i++) {
                pw.println(scoreData.get(i).getName());
                pw.println(scoreData.get(i).getScore());
            }

            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * loads
     */
    void load() {
        File scores = new File("src\\uk\\ac\\aber\\dsc\\pev2\\main_assignment\\leaderBoard");
        int i = 0;
        try {
            Scanner infile = new Scanner(new FileReader(scores));

            while (i < 10) {
                String name = infile.nextLine();
                int score = Integer.parseInt(infile.nextLine());
                scoreData.add(new ScoreData(name, score));
                i++;
            }

            infile.close();
        } catch (FileNotFoundException e) {
            System.err.println("you need the whole path to the file");
            e.printStackTrace();
        }
        Collections.sort(scoreData);
    }
}
