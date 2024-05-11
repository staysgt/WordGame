package com.github.staysgt;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;


public class UserInput {

    Grid grid = new Grid();
    boolean gridFull = false;

    boolean firstRun = true;
    char c = 0;

    private static final Scanner scanner = new Scanner(System.in);

    public UserInput() throws FileNotFoundException {
    }

    public void begin() throws IOException {
        System.out.println("Score: 0");
        grid.populate(8);
        grid.brToArray();
        grid.printGrid();
        firstRun = false;
    }
    public void runAfterPlacement() throws IOException {
        grid.printGrid();
        grid.potentialWords();
        // the strings that this is printing out is messed up -> some are blank?
        for (int i = 0; i < 6; i++) {
            System.out.println(grid.stringList.get(i));
        }
        grid.deleteWords();
        if(!grid.wordsDeleted) {
            grid.printGrid();
            grid.wordsDeleted = false;
        }
        System.out.println("Score: " + grid.wordsRemoved * 1000);
        userInput();
    }

    public void userInput() throws IOException {
        if(!grid.boxFull) {
            Random r = new Random();
            c = (char) (r.nextInt(26) + 'a');
        }
        if(firstRun) begin();
        System.out.println("letter: " + c);
        System.out.println("x coordinate");
        int x = scanner.nextInt();
        while(x > 8) {
            System.out.println("must be a number between 0 and 7");
            x = scanner.nextInt();
        }
        System.out.println("y coordinate");
        int y = scanner.nextInt();
        while(y > 8) {
            System.out.println("must be a number between 0 and 7");
            y = scanner.nextInt();
        }
        grid.placeLetter(x,y,c);
        if(grid.boxFull) {
            userInput();
        }
        runAfterPlacement();
    }

    public static void main(String[] args) throws IOException {
        UserInput ui = new UserInput();
        ui.userInput();
    }



}
