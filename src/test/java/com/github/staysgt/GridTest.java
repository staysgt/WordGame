package com.github.staysgt;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

public class GridTest {

    @Test
    void brToArrayTest() throws IOException {
        Grid g = new Grid();
        Grid g2 = new Grid();
        g.brToArray();
        g2.brToArray();
        Assertions.assertThat(g.arrayOfWords.get(0)).isEqualTo("a-horizon");
        Assertions.assertThat(g2.arrayOfWords.get(3)).isEqualTo("aardwolf");
    }

    @Test
    void potentialWordsTest() throws IOException {
        Grid g = new Grid();
        g.brToArray();
        g.populate(6);
        g.getIndex(2).getRight().setLetter('z');
        g.getIndex(2).getRight().getRight().setLetter('a');
        g.getIndex(2).getRight().getRight().getRight().getRight().setLetter('r');
        g.getIndex(2).getRight().getRight().getRight().getUp().setLetter('q');
        g.getIndex(2).getRight().getRight().getRight().setLetter('p');
        g.getIndex(2).getRight().getRight().getRight().getDown().setLetter('o');
        g.getIndex(2).getRight().getRight().getRight().getDown().getDown().setLetter('p');
        g.setMostRecent(g.getIndex(2).getRight().getRight().getRight());
        g.potentialWords();
        ArrayList<String> strings = new ArrayList<>();
        strings.add("zapr");
        strings.add("zap");
        strings.add("qpop");
        strings.add("qp");
        strings.add("pop");
        Assertions.assertThat(g.stringList).isEqualTo(strings);
    }


    @Test
    void deleteWordsTest() throws IOException {
        Grid g = new Grid();
        g.brToArray();
        g.populate(6);
        g.getIndex(2).getRight().setLetter('z');
        g.getIndex(2).getRight().getRight().setLetter('a');
        g.getIndex(2).getRight().getRight().getRight().getRight().setLetter('r');
        g.getIndex(2).getRight().getRight().getRight().getUp().setLetter('q');
        g.getIndex(2).getRight().getRight().getRight().setLetter('p');
        g.getIndex(2).getRight().getRight().getRight().getDown().setLetter('o');
        g.getIndex(2).getRight().getRight().getRight().getDown().getDown().setLetter('p');
        g.setMostRecent(g.getIndex(2).getRight().getRight().getRight());
        g.potentialWords();
        Assertions.assertThat(g.deleteWords()).isEqualTo(5);

    }

    @Test
    void isWordTest() throws IOException {
        Grid g = new Grid();
        g.brToArray();
        Assertions.assertThat(g.isWord("dog", 0, g.arrayOfWords.size())).isEqualTo(true);
        Assertions.assertThat(g.isWord("xxxxxx", 0, g.arrayOfWords.size())).isEqualTo(false);
    }



    @Test
    void populateTest() throws IOException {
        Grid g = new Grid();
        g.brToArray();
        Assertions.assertThat(g.isWord("dog", 0, g.arrayOfWords.size())).isEqualTo(true);
        Assertions.assertThat(g.isWord("xxxxxx", 0, g.arrayOfWords.size())).isEqualTo(false);
    }
    // not completely sure how to test print grid -> more work to do here
    @Test
    void printGridTest() throws IOException {
        Grid g = new Grid();
        g.brToArray();
        g.populate(2);
    }

    @Test
    void gridToStringTest() throws IOException {
        Grid g = new Grid();
        g.brToArray();
        g.populate(1);
        Assertions.assertThat(g.gridToString()).isEqualTo("y↓x->0__\n" + " 0|_____|");
    }
    @Test
    void getSetIndexTest() throws IOException {
        Grid g = new Grid();
        Box b = new Box();
        g.populate(2);
        g.set(1,b);
        Assertions.assertThat(g.getIndex(1)).isEqualTo(b);
    }

    @Test
    void placeLetterTest() throws IOException {
        Grid g = new Grid();
        g.brToArray();
        g.populate(4);
        g.placeLetter(2,2,'a');
        Assertions.assertThat(g.getIndex(2).getRight().getRight().getLetter()).isEqualTo('a');
    }

}
