package com.github.staysgt;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

public class Grid {
    // this hashmap has a string that is a potential word and is associated with the boxes that make up that word
    HashMap<ArrayList<Box>, String> potentialWordsMap = new HashMap<>();

    ArrayList<ArrayList<Box>> boxList = new ArrayList<>();
    ArrayList<String> stringList = new ArrayList<>();


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

    public String upDownWord() {
        StringBuilder upDown = new StringBuilder();
        Box currVertical = mostRecent;

        while(currVertical.getLetter() != 0) {
            if(currVertical.getUp() != null) {
                currVertical = currVertical.getUp();
            }
        }

        while(currVertical.getLetter() != 0) {
            upDown.append(currVertical.getLetter());
            if(currVertical.getDown() != null) {
                currVertical = currVertical.getDown();
            }
        }

        return upDown.toString();
    }


    /**
     * finds the potential words based on the most recent addition to the grid
     * @return the potential words from the most recent addition to the grid
     */
    public void potentialWords() {
        ArrayList<String> potentialWords = new ArrayList<>();
        StringBuilder fullLR = new StringBuilder();
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
        while(currentBox.getLeft() != null && currentBox.getLetter() != 0) {
            if(currentBox.getLeft().getLetter() != 0) {
                currentBox = currentBox.getLeft();
            } else {
                break;
            }
        }
        Box leftMost = currentBox;

        // adds to the full string from left to right, excluding the rightmost letter
        while(currentBox.getRight().getLetter() != 0 && currentBox.getRight()!= null) {
            fullLRBox.add(currentBox);
            fullLR.append(currentBox.getLetter());
            if(currentBox.getRight().getLetter() != 0) {
                currentBox = currentBox.getRight();
            }
        }
        // adds the rightmost letter to the string
        if(currentBox.getRight().getLetter() == 0) {
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
        while(currentBox.getUp() != null && currentBox.getLetter() != 0) {
            if(currentBox.getUp().getLetter() != 0) {
                currentBox = currentBox.getUp();
            } else {
                break;
            }
        }
        Box upMost = currentBox;

        while(currentBox.getDown().getLetter() != 0 && currentBox.getDown()!= null) {
            fullUDBox.add(currentBox);
            fullUD.append(currentBox.getLetter());
            if(currentBox.getDown().getLetter() != 0) {
                currentBox = currentBox.getDown();
            }
        }

        if(currentBox.getDown().getLetter() == 0) {
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

    // checks if the word is a real word -> will be updated when i update to use an api
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
        for(String word : stringList) {
            if(isWord(word)) {
                // sets the boxes that contain the letters of the word back to 0
                stringList.removeFirst();
                for (int i = 0; i < boxList.get(0).size(); i++) {
                    boxList.getFirst().get(i).setLetter('_');
                }
                boxList.removeFirst();

            } else {
                // the boxes should not be changed
                stringList.removeFirst();
                boxList.removeFirst();
            }
        }
    }


    // helper - can be deleted when done
    public void printAllWord() {
        for (int i = 0; i < 8; i++) {
            System.out.println(arrayOfWords.get(i));
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
                        System.out.print("_______");
                }
                System.out.println();
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
        grid.brToArray();
        grid.populate(6);
        grid.getIndex(2).getRight().setLetter('z');
        grid.getIndex(2).getRight().getRight().setLetter('a');
        grid.getIndex(2).getRight().getRight().getRight().getRight().setLetter('r');
        grid.getIndex(2).getRight().getRight().getRight().getUp().setLetter('q');
        grid.getIndex(2).getRight().getRight().getRight().setLetter('p');
        grid.getIndex(2).getRight().getRight().getRight().getDown().setLetter('o');
        grid.getIndex(2).getRight().getRight().getRight().getDown().getDown().setLetter('p');

        grid.setMostRecent(grid.getIndex(2).getRight().getRight().getRight());
        grid.printGrid();
        grid.deleteWords();
        System.out.println();
        grid.printGrid();
    }
}
