package com.github.staysgt;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Grid {
    ArrayList<ArrayList<Box>> boxList = new ArrayList<>();
    ArrayList<String> stringList = new ArrayList<>();


    int wordsRemoved = 0;
    boolean boxFull = false;

    BufferedReader allWords = new BufferedReader(new FileReader("src/main/java/com/github/staysgt/wordlist.txt"));
    ArrayList<Box> grid = new ArrayList<>();

    boolean wordsDeleted = false;

    Box mostRecent;

    public Grid() throws FileNotFoundException {
    }
    ArrayList<String> arrayOfWords = new ArrayList<>();

    /**
     * turns the list of all words into an array
     * @throws IOException uses the list of words txt file that can throw exception
     */
    public void brToArray() throws IOException {
        String line;
        int i = 0;
        while((line = allWords.readLine()) != null) {
            if(line.length() > 2) {
                arrayOfWords.add(i, line);
                i++;
            }
        }
        allWords.close();
    }




    /**
     * finds the potential words based on the most recent addition to the grid
     */
    public void potentialWords() {
        boxList = new ArrayList<>();
        stringList = new ArrayList<>();
        ArrayList<Box> fullLRBox = new ArrayList<>();
        StringBuilder leftPart = new StringBuilder();
        ArrayList<Box> leftPartBox = new ArrayList<>();
        StringBuilder rightPart = new StringBuilder();
        ArrayList<Box> rightPartBox = new ArrayList<>();
        StringBuilder fullUD = new StringBuilder();
        ArrayList<Box> fullUDBox = new ArrayList<>();
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
        StringBuilder fullLR = new StringBuilder();
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
        // makes sure that the word is not already in the list
            if(!stringList.contains(leftPart.toString())) {
                boxList.add(leftPartBox);
                stringList.add(leftPart.toString());
            }

        // just right part
        Box rightStart = mostRecent;
        while(rightStart!=currentBox.getRight()) {
            rightPartBox.add(rightStart);
            rightPart.append(rightStart.getLetter());
            rightStart = rightStart.getRight();
        }
        // makes sure that the word is not already in the list
            if(!stringList.contains(leftPart.toString())) {
                boxList.add(rightPartBox);
                stringList.add(rightPart.toString());
            }
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

        // makes sure that words are not being doubly added
        if(!upPart.toString().contentEquals(fullUD)) {
            boxList.add(upPartBox);
            stringList.add(upPart.toString());
        }

        // just down part (for points)
        Box downStart = mostRecent;
        while(downStart!=currentBox.getDown()) {
            downPartBox.add(downStart);
            downPart.append(downStart.getLetter());
            downStart = downStart.getDown();
        }

        // makes sure words are not being doubly added (for points)
        if(!downPart.toString().contentEquals(fullUD) && !downPart.toString().contentEquals(upPart)) {
            boxList.add(downPartBox);
            stringList.add(downPart.toString());
        }

    }


    /**
     * deletes the words from the grid
     * @return the number of boxes that were changed
     */
    public int deleteWords() {
        ArrayList<Box> resetBoxes = new ArrayList<>();
        for(int i = 0; i < stringList.size(); i++) {
            if(isWord(stringList.get(i), 0, arrayOfWords.size())) {
                wordsDeleted = true;
                wordsRemoved++;
                System.out.println("word deleted: " + stringList.get(i));
                for (int j = 0; j < boxList.get(i).size(); j++) {
                    boxList.get(i).get(j).setLetter('_');
                    if(!resetBoxes.contains(boxList.get(i).get(j))) {
                        resetBoxes.add(boxList.get(i).get(j));
                    }
                }
            }
        }
        return resetBoxes.size();

    }

    /**
     * utilizes binary search in order to effectively traverse the list of words to find if the given word is a word
     * @param givenWord the word being compared to the list of words
     * @param first the lower bound
     * @param last the upper bound
     * @return if the given word is a word
     */
    public boolean isWord(String givenWord, int first, int last) {
        int middle = (first + last)/2;
        String midWord = arrayOfWords.get(middle);
        if(first > last) return false;

        if(givenWord.equals(midWord)) return true;


        if(givenWord.compareTo(midWord) < 0) {
            return isWord(givenWord, first,middle -1);
        } else {
            return isWord(givenWord, middle + 1,last);
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

    /**
     * sets the value of the most recent box
     * @param mostRecent the box that was most recently edited
     */
    public void setMostRecent(Box mostRecent) {
        this.mostRecent = mostRecent;
    }


    /**
     * creates a string that is a visual of what the grid looks like
     * @return a string that represents the grid
     */
    public String gridToString() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < grid.size(); i++) {
            Box currentBox = grid.get(i);
            if(i==0) {
                for (int k = 0; k < grid.size(); k++) {
                    if(k == 0) {
                        str.append("y↓x->").append(k).append("__");
                    } else {
                        str.append("__").append(k).append("___");

                    }
                }
                str.append("\n");
            }
            for (int j = 0; j < grid.size(); j++) {
                if(j == 0) {
                    if (currentBox.getLetter() != 0) {
                        str.append(" ").append(i).append("|__").append(currentBox.getLetter()).append("__|");
                    } else {
                        str.append("|_____|");
                    }
                }
                // add letter
                if(j!=0) {
                    if (currentBox.getLetter() != 0) {
                        str.append("__").append(currentBox.getLetter()).append("__|");
                    } else {
                        str.append("_____|");
                    }
                }

                if(j != (grid.size() - 1)) {
                    currentBox = currentBox.getRight();
                }
            }
            if(i != grid.size() -1) str.append("\n");
        }
        return str.toString();
    }

    /**
     * gets the box at a given index
     * @param index given index
     * @return the box at the given index
     */

    public Box getIndex(int index) {
        return grid.get(index);
    }


    /**
     * places a given letter on the grid at a given x and y coordinate
     * @param xCoord x coordinate value of where the letter is going to be placed
     * @param yCoord y coordinate value of where the letter is going to be placed
     * @param c letter that is going to be placed
     */
    public void placeLetter(int xCoord, int yCoord, char c) {
        Box currentBox = grid.getFirst();
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

    /**
     * sets the value at of a box at a given index in the first row
     * @param i the integer value of the box being altered
     * @param b the box that is being altered
     */

    public void set(int i, Box b) {
        grid.set(i, b);
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

        System.out.println(grid.gridToString());
        grid.setMostRecent(grid.getIndex(2).getRight().getRight().getRight());

        grid.potentialWords();
        for (int i = 0; i < grid.stringList.size(); i++) {
            System.out.println(grid.stringList.get(i));
        }


        System.out.println(grid.gridToString());
        System.out.println(grid.deleteWords());
        System.out.println();

//        System.out.println("score: " + (grid.wordsRemoved * 1000));
        System.out.println(grid.gridToString());



    }
}
