package com.github.staysgt;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import javax.swing.*;


public class UserInputGUI {

    Grid grid = new Grid();
    boolean gridFull = false;

    boolean firstRun = true;
    char c = 0;
    private static final Scanner scanner = new Scanner(System.in);

    HashMap<Integer, Character> probabilityLetter = new HashMap<>();


    public UserInputGUI() throws FileNotFoundException {
    }

    public void characterFreq() {
        int weight = 0;
        Character[] characters = {'j', 'k', 'q', 'x' ,'z','b', 'c', 'f', 'h', 'm', 'p', 'v', 'w', 'y', 'g', 'd', 'l', 's', 'u', 'n', 'r', 't', 'o', 'a', 'i', 'e'};
        for (int i = 0; i < characters.length; i++) {
            if(i < 5) {
                weight+=1;
                probabilityLetter.put(weight,characters[i]);
            } else if (i < 14) {
                weight+=2;
                probabilityLetter.put(weight,characters[i]);
            } else if (i == 15) {
                weight += 3;
                probabilityLetter.put(weight, characters[i]);
            } else if (i < 19) {
                weight+=4;
                probabilityLetter.put(weight, characters[i]);
            } else if (i< 22) {
                weight+=6;
                probabilityLetter.put(weight, characters[i]);
            } else {
                weight+=8;
                probabilityLetter.put(weight, characters[i]);
            }
//            else if (i < 23) {
//                weight+=8;
//                probabilityLetter.put(weight, characters[i]);
//            } else if (i < 25) {
//                weight+=9;
//                probabilityLetter.put(weight, characters[i]);
//            }

        }
    }



    /**
     * creates the probability for each letter and then picks letters based on that
     * @return a character based on the probability of each letter
     */
    public Character letterGenerator() {
        characterFreq();
        Random r = new Random();
        int num = r.nextInt(1,73);
        Character letter = 0;
        while(letter == 0) {
            if(probabilityLetter.get(num) != null) {
                letter = probabilityLetter.get(num);
            }
            num++;
        }
        return letter;
    }

    public void begin() throws IOException {
        System.out.println("Score: 0");
        System.out.println("Enter desired grid size: ");
        int size = scanner.nextInt();
        grid.populate(size);
        grid.brToArray();
        grid.printGrid();
        firstRun = false;
    }
    public void runAfterPlacement() throws IOException {
        grid.printGrid();
        grid.potentialWords();
        grid.deleteWords();
        System.out.println("Score: " + grid.wordsRemoved * 1000);
        if(grid.wordsDeleted) {
            grid.printGrid();
            grid.wordsDeleted = false;
        }
        userInput();
    }

    public void userInput() throws IOException {
        while(!gridFull) {
            if (!grid.boxFull) {
                c = letterGenerator();
            }
            if (firstRun) begin();
            System.out.println("letter: " + c);
//            System.out.println("x coordinate");
//            int x = scanner.nextInt();
            int x = Integer.parseInt(JOptionPane.showInputDialog(grid.gridToString() + "letter: " + c + "\n x coordinate: "));
            int y = Integer.parseInt(JOptionPane.showInputDialog(grid.gridToString() + "letter: " + c + "\n y coordinate: "));
            while (x >= 8) {
                System.out.println("must be a number between 0 and 7");
                x = scanner.nextInt();
            }
//            System.out.println("y coordinate");
//            int y = scanner.nextInt();
            while (y >= 8) {
                System.out.println("must be a number between 0 and 7");
                y = scanner.nextInt();
            }
            grid.placeLetter(x, y, c);
            if (grid.boxFull) {
                userInput();
            }
            runAfterPlacement();
        }
            System.out.println("grid is full, game over");
            System.out.println("final score" + grid.wordsRemoved * 1000);
    }

    public static void main(String[] args) throws IOException {
        UserInputGUI ui = new UserInputGUI();
        ui.userInput();
    }



}
