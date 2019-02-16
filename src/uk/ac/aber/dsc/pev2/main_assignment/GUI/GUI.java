package uk.ac.aber.dsc.pev2.main_assignment.GUI;


import uk.ac.aber.dsc.pev2.main_assignment.App;

public abstract class GUI {
    App app;

    /**
     * Displays the options in command line
     */
    public abstract void menuList();

    /**
     * Updates the screen
     */
    public abstract void updateScreen();

    /**
     *
     * @param cmd user inputs
     */
    public abstract void cmdInput(String cmd);

}
