package com.github.staysgt;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Grid {
    BufferedReader allWords = new BufferedReader(new FileReader("src/main/java/com/github/staysgt/wordlist.txt"));
    ArrayList<Box> grid = new ArrayList<>();

    public Grid() throws FileNotFoundException {
    }

    Random r = new Random();
    char c = (char)(r.nextInt(26) + 'a');

    Box mostRecent;

    ArrayList<String> arrayOfWords = new ArrayList<>();
    public void brToArray() throws IOException {
        String line;
        int i = 0;
        while((line = allWords.readLine()) != null) {
            arrayOfWords.add(i, line);
            i++;
        }
        allWords.close();
    }

    public ArrayList<String> getWord() {
        ArrayList<String> result = new ArrayList<>();
        StringBuilder leftToRight = new StringBuilder();
        StringBuilder upDown = new StringBuilder();

        Box currHorizontal = mostRecent;
        Box currVertical = mostRecent;

        // gets the current to be the leftmost box
        while(currHorizontal.getLetter() != 0) {
            if(currHorizontal.getLeft() != null) {
                currHorizontal = currHorizontal.getLeft();
            }
        }

        // adds the leftmost letter to the list
        while(currHorizontal.getLetter() != 0) {
            leftToRight.append(currHorizontal.getLetter());
            if(currHorizontal.getRight() != null) {
                currHorizontal = currHorizontal.getRight();
            }
        }

//        while(currVertical.getLetter() != 0) {
//            if(currVertical.getUp() != null) {
//                currVertical = currVertical.getUp();
//            }
//        }
//
//        while(currVertical.getLetter() != 0) {
//            upDown.append(currVertical.getLetter());
//            if(currVertical.getDown() != null) {
//                currVertical = currVertical.getDown();
//            }
//        }

        result.add(leftToRight.toString());
//        result.add(upDown.toString());
        return result;
    }

    // checks if the word is a real word
    public boolean isWord(String str) {
        for (String arrayOfWord : arrayOfWords) {
            if (str.equalsIgnoreCase(arrayOfWord)) {
                return true;
            }
        }
        return false;
    }



//    @Override
//    public String toString(){
//        for (int i = 0; i < grid.size(); i++) {
//
//        }
//        return "";
//    }


    // helper - can be deleted when done
    public void printAllWord() {
        for (int i = 0; i < 8; i++) {
            System.out.println(arrayOfWords.get(i));
        }
    }

//    public void deleteWord(Boolean isHorizontal) {
//        if(isHorizontal) {
//        }
//    }

    /**
     * populates the grid with empty boxes
     * @param size the dimensions of the grid
     */
    public void populateGrid(int size) {
        Box previousRow = null;
        Box currentBox = new Box();
        for (int i = 0; i < size; i++) {
            // tracks the starting box of the current row -> useful for setting previous row
            Box currentRowStart = currentBox;
            Box nextRow = null;
            if(i != size -1) nextRow = new Box();
            grid.add(currentBox);
            // creates a next row unless the current row is the last row in the grid
            for (int j = 0; j < size; j++) {
                // creates next box unless the next box is at the end of the current row
                Box nextBox = (j != size - 1) ? new Box() : null;
                if (j == 0) {
                    // when the box is the first in the row, no left neighbor
                    currentBox.setRight(nextBox);
                } else {
                    // when the box is in the middle
                    currentBox.setRight(nextBox);
                    if(nextBox!= null) nextBox.setLeft(currentBox);
                }

                    if(i == 0) {
                        // first row, no row above
                        if(nextRow != null) currentBox.setDown(nextRow);
                    } else {
                        // middle rows
                        if(previousRow != null) currentBox.setUp(previousRow);
                        if(nextRow != null) currentBox.setDown(nextRow);
                    }

                    if (previousRow != null) previousRow = previousRow.getRight();
                    if (nextRow != null && j != size-1) nextRow = nextRow.getRight();
                    if (nextBox!= null) currentBox = nextBox;
            }
            previousRow = currentRowStart;
            currentBox = nextRow;
        }
    }

    public void setMostRecent(Box mostRecent) {
        this.mostRecent = mostRecent;
    }

    public void printGrid() {
        for (int i = 0; i < grid.size(); i++) {
            Box currentBox = grid.get(i);
            if(i==0) {
                System.out.println("_________________________");
            }
            for (int j = 0; j < grid.size(); j++) {
                if(j == 0) {
                    if (currentBox.getLetter() != 0) {
                        System.out.print("|__" + currentBox.getLetter() + "__|");
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

    public static void main(String[] args) throws IOException {
        Grid grid = new Grid();
//        grid.brToArray();
        grid.populateGrid(4);
        grid.getIndex(2).getRight().setLetter('z');
        grid.setMostRecent(grid.getIndex(2).getRight());
        System.out.println(grid.getWord());
        grid.printGrid();

//        grid.printAllWord();
//        System.out.println(grid.c);
    }
}
