package com.github.staysgt;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

public class Grid {
    ArrayList<ArrayList<Box>> boxList = new ArrayList<>();
    ArrayList<String> stringList = new ArrayList<>();

    int wordsRemoved = 0;
    int score = (wordsRemoved * 1000);
    Character c;

    boolean boxFull = false;

    BufferedReader allWords = new BufferedReader(new FileReader("src/main/java/com/github/staysgt/wordlist.txt"));
    ArrayList<Box> grid = new ArrayList<>();

    boolean wordsDeleted = false;

    public Grid() throws FileNotFoundException {
    }

    Box mostRecent;

    /**
     * turns the list of all words into an array
     */
    ArrayList<String> arrayOfWords = new ArrayList<>();
    public void brToArray() throws IOException {
        String line;
        int i = 0;
        while((line = allWords.readLine()) != null) {
            if(line.length() > 1) {
                arrayOfWords.add(i, line);
                i++;
            }
        }
        allWords.close();
    }


    /**
     * finds the potential words based on the most recent addition to the grid
     * @return the potential words from the most recent addition to the grid
     */
    public void potentialWords() {
        boxList = new ArrayList<>();
        stringList = new ArrayList<>();
        // issue when right most
        StringBuilder fullLR = new StringBuilder();
        ArrayList<Box> fullLRBox = new ArrayList<>();
        StringBuilder leftPart = new StringBuilder();
        ArrayList<Box> leftPartBox = new ArrayList<>();
        StringBuilder rightPart = new StringBuilder();
        ArrayList<Box> rightPartBox = new ArrayList<>();
        // issue when bottom
        StringBuilder fullUD = new StringBuilder();
        ArrayList<Box> fullUDBox = new ArrayList<>();
        // issue when top row
        StringBuilder upPart = new StringBuilder();
        ArrayList<Box> upPartBox = new ArrayList<>();
        StringBuilder downPart = new StringBuilder();
        ArrayList<Box> downPartBox = new ArrayList<>();

        // left and right
        Box currentBox = mostRecent;

        // iterates to the leftmost letter connected to the recently added letter
        while(currentBox.getLeft() != null && currentBox.getLetter() != '_') {
            if(currentBox.getLeft().getLetter() != '_') {
                currentBox = currentBox.getLeft();
            } else {
                break;
            }
        }
        Box leftMost = currentBox;

        // adds to the full string from left to right, excluding the rightmost letter
        while(currentBox.getRight()!= null && currentBox.getRight().getLetter() != '_') {
            fullLRBox.add(currentBox);
            fullLR.append(currentBox.getLetter());
            if(currentBox.getRight().getLetter() != '_') {
                currentBox = currentBox.getRight();
            }
        }
        // adds the rightmost letter to the string
        if(currentBox.getRight() != null && currentBox.getRight().getLetter() == '_') {
            fullLRBox.add(currentBox);
            fullLR.append(currentBox.getLetter());
        }

        boxList.add(fullLRBox);
        stringList.add(fullLR.toString());

        // just left part
        if(mostRecent.getLeft() != null) {
            while (leftMost != mostRecent.getRight()) {
                leftPartBox.add(leftMost);
                leftPart.append(leftMost.getLetter());
                leftMost = leftMost.getRight();
            }
        } else {
            leftPart.append(mostRecent.getLetter());
        }
        boxList.add(leftPartBox);
        stringList.add(leftPart.toString());

        // just right part
        Box rightStart = mostRecent;
        while(rightStart!=currentBox.getRight()) {
            rightPartBox.add(rightStart);
            rightPart.append(rightStart.getLetter());
            rightStart = rightStart.getRight();
        }

        boxList.add(rightPartBox);
        stringList.add(rightPart.toString());


        // up and down
        currentBox = mostRecent;
        // iterates to the upmost letter connected to the most recent addition
        while(currentBox.getUp() != null && currentBox.getLetter() != '_') {
            if(currentBox.getUp().getLetter() != '_') {
                currentBox = currentBox.getUp();
            } else {
                break;
            }
        }
        Box upMost = currentBox;

        while(currentBox.getDown()!= null && currentBox.getDown().getLetter() != '_') {
            fullUDBox.add(currentBox);
            fullUD.append(currentBox.getLetter());
            if(currentBox.getDown().getLetter() != '_') {
                currentBox = currentBox.getDown();
            }
        }

        if(currentBox.getDown() != null && currentBox.getDown().getLetter() == '_') {
            fullUDBox.add(currentBox);
            fullUD.append(currentBox.getLetter());
        }
        boxList.add(fullUDBox);
        stringList.add(fullUD.toString());

        // up part
        if(mostRecent.getUp() != null) {
            while (upMost != mostRecent.getDown()) {
                upPartBox.add(upMost);
                upPart.append(upMost.getLetter());
                upMost = upMost.getDown();
            }
        } else {
            upPart.append(mostRecent.getLetter());
        }
        boxList.add(upPartBox);
        stringList.add(upPart.toString());

        // just down part
        Box downStart = mostRecent;
        while(downStart!=currentBox.getDown()) {
            downPartBox.add(downStart);
            downPart.append(downStart.getLetter());
            downStart = downStart.getDown();
        }

        boxList.add(downPartBox);
        stringList.add(downPart.toString());
    }







    /**
     * checks if the given word exists within the word list
     * @param str given word
     * @return whether the string is a word
     */

    public boolean isWord(String str) {
        for (String arrayOfWord : arrayOfWords) {
            if(arrayOfWord.equalsIgnoreCase(str)) {
                return true;
            }
        }
        return false;
    }


    /**
     * deletes the words from the grid
     */
    public void deleteWords() {
        for(int i = 0; i < stringList.size(); i++) {
            if(arrayOfWords.contains(stringList.get(i))) {
                wordsDeleted = true;
                wordsRemoved++;
                System.out.println(wordsRemoved);
                for (int j = 0; j < boxList.get(i).size(); j++) {
                    boxList.get(i).get(j).setLetter('_');
                }
            }
        }
    }


    /**
     * populates the grid with empty boxes
     * @param size the dimensions of the grid
     */
    public void populate(int size) {
        Box[][] tempGrid = new Box[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                tempGrid[i][j] = new Box();

                if(j != 0) {
                    tempGrid[i][j - 1].setRight(tempGrid[i][j]);
                    tempGrid[i][j].setLeft(tempGrid[i][j - 1]);
                }
                if(i != 0) {
                    tempGrid[i -1][j].setDown(tempGrid[i][j]);
                    tempGrid[i][j].setUp(tempGrid[i - 1][j]);
                }
            }
            grid.add(tempGrid[i][0]);
        }
    }

    public void setMostRecent(Box mostRecent) {
        this.mostRecent = mostRecent;
    }

    public void printGrid() {
        for (int i = 0; i < grid.size(); i++) {
            Box currentBox = grid.get(i);
            if(i==0) {
                for (int k = 0; k < grid.size(); k++) {
                    if(k == 0) {
                        System.out.print("y↓x->" + k + "__" );
                    } else {
                        System.out.print("__" + k + "___");

                    }
                }
                System.out.println();
            }
            for (int j = 0; j < grid.size(); j++) {
                if(j == 0) {
                    if (currentBox.getLetter() != 0) {
                        System.out.print(" " + i + "|__" + currentBox.getLetter() + "__|");
                    } else {
                        System.out.print("|_____|");
                    }
                }
                // add letter
                if(j!=0) {
                    if (currentBox.getLetter() != 0) {
                        System.out.print("__" + currentBox.getLetter() + "__|");
                    } else {
                        System.out.print("_____|");
                    }
                }

                if(j != (grid.size() - 1)) {
                    currentBox = currentBox.getRight();
                }
            }
            System.out.println();
        }
    }

    public void set(int i, Box b) {
        grid.set(i, b);
    }

    public Box getIndex(int index) {
        return grid.get(index);
    }





    public void placeLetter(int xCoord, int yCoord, char c) {
        Box currentBox = grid.get(0);
        for (int i = 0; i < yCoord; i++) {
            currentBox = currentBox.getDown();
        }
        for (int i = 0; i < xCoord; i++) {
            currentBox = currentBox.getRight();
        }

        if(currentBox.getLetter() != '_') {
            System.out.println("box is full, try again");
            boxFull = true;
            return;
        } else {
            boxFull = false;
        }
        currentBox.setLetter(c);
        mostRecent = currentBox;
    }

    public static void main(String[] args) throws IOException {
        Grid grid = new Grid();
        grid.brToArray();
        grid.populate(6);
        grid.getIndex(2).getRight().setLetter('z');
        grid.getIndex(2).getRight().getRight().setLetter('a');
        grid.getIndex(2).getRight().getRight().getRight().getRight().setLetter('r');
        grid.getIndex(2).getRight().getRight().getRight().getUp().setLetter('q');
        grid.getIndex(2).getRight().getRight().getRight().setLetter('p');
        grid.getIndex(2).getRight().getRight().getRight().getDown().setLetter('o');
        grid.getIndex(2).getRight().getRight().getRight().getDown().getDown().setLetter('p');

        grid.printGrid();
        grid.setMostRecent(grid.getIndex(2).getRight().getRight().getRight());

        grid.potentialWords();
        for (int i = 0; i < 6; i++) {
            System.out.println(grid.stringList.get(i));
        }

        grid.printGrid();
        grid.deleteWords();
        System.out.println();

//        System.out.println("score: " + (grid.wordsRemoved * 1000));
        grid.printGrid();


    }
}
