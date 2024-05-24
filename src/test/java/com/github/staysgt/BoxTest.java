package com.github.staysgt;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class BoxTest {
    @Test
    void setgetYTest() {
        Box b = new Box();
        b.setY(0);
        Box b2 = new Box();
        b2.setY(3);
        Assertions.assertThat(b.getY()).isEqualTo(0);
        Assertions.assertThat(b2.getY()).isEqualTo(3);
    }

    @Test
    void setgetXTest() {
        Box b = new Box();
        b.setX(0);
        Box b2 = new Box();
        b2.setX(3);
        Assertions.assertThat(b.getX()).isEqualTo(0);
        Assertions.assertThat(b2.getX()).isEqualTo(3);
    }

    @Test
    void setgetDownTest() {
        Box b = new Box();
        Box b2 = new Box();
        b.setDown(b2);
        b2.setDown(b);
        Assertions.assertThat(b.getDown()).isEqualTo(b2);
        Assertions.assertThat(b2.getDown()).isEqualTo(b);
    }

    @Test
    void setgetUpTest() {
        Box b = new Box();
        Box b2 = new Box();
        b.setUp(b2);
        b2.setUp(b);
        Assertions.assertThat(b.getUp()).isEqualTo(b2);
        Assertions.assertThat(b2.getUp()).isEqualTo(b);
    }

    @Test
    void setgetLeftTest() {
        Box b = new Box();
        Box b2 = new Box();
        b.setLeft(b2);
        b2.setLeft(b);
        Assertions.assertThat(b.getLeft()).isEqualTo(b2);
        Assertions.assertThat(b2.getLeft()).isEqualTo(b);
    }

    @Test
    void setgetRightTest() {
        Box b = new Box();
        Box b2 = new Box();
        b.setRight(b2);
        b2.setRight(b);
        Assertions.assertThat(b.getRight()).isEqualTo(b2);
        Assertions.assertThat(b2.getRight()).isEqualTo(b);
    }

    @Test
    void setgetLetterTest() {
        Box b = new Box();
        Box b2 = new Box();
        b.setLetter('a');
        b2.setLetter('b');
        Assertions.assertThat(b.getLetter()).isEqualTo('a');
        Assertions.assertThat(b2.getLetter()).isEqualTo('b');
    }
}
