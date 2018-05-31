package com.my.puzzle;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test_swap()
    {
        Puzzle puzzle=new Puzzle(Constants.PUZZLE_ROW, null);
        int[] data=puzzle.getData();

        puzzle.swap(0, 7);

        assertEquals(8, data[0]);
        assertEquals(1, data[7]);
    }

}