package com.github.staysgt;

public class Box {
    Box up = null;
    Box down = null;
    Box left = null;
    Box right = null;

    int x;
    int y;
    char letter = 0;

    public Box() {

    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public void setDown(Box down) {
        this.down = down;
    }

    public Box getDown() {
        return down;
    }

    public void setUp(Box up) {
        this.up = up;
    }

    public Box getUp() {
        return up;
    }

    public void setLeft(Box left) {
        this.left = left;
    }

    public Box getLeft() {
        return left;
    }

    public void setRight(Box right) {
        this.right = right;
    }

    public Box getRight() {
        return right;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }

    public char getLetter() {
        return letter;
    }
}


