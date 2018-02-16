package com.wordpress.guillaumeberhault.loto;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by berhagu1 on 2/13/2018.
 */
public class CartonTest {
    private final int rowNumber = 3;
    private final int columnNumber = 9;
    private Carton carton;

    @Before
    public void setUp() throws Exception {
        carton = new Carton(3, 9);
    }

    @Test(expected = RuntimeException.class)
    public void getValueInRow_wrongColumnIndex_small_RuntimeException() throws Exception {
        carton.getValueInRow(1, -1);
    }

    @Test(expected = RuntimeException.class)
    public void getValueInRow_wrongColumnIndex_overflow_RuntimeException() throws Exception {
        carton.getValueInRow(1, 9);
    }

    @Test(expected = RuntimeException.class)
    public void getValueInRow_wrongRowIndex_overflow_RuntimeException() throws Exception {
        carton.getValueInRow(-1, 5);
    }

    @Test(expected = RuntimeException.class)
    public void addToRow_wrongIndex_negative_RuntimeException() throws Exception {
        carton.addToRow(-1, 1);
    }

    @Test(expected = RuntimeException.class)
    public void addToRow_wrongIndex_overflow_RuntimeException() throws Exception {
        carton.addToRow(4, 1);
    }

    @Test(expected = RuntimeException.class)
    public void addToRow_wrongValue_overflow_RuntimeException() throws Exception {
        carton.addToRow(1, 100);
    }

    @Test(expected = RuntimeException.class)
    public void addToRow_wrongValue_underflow_RuntimeException() throws Exception {
        carton.addToRow(1, 0);
    }

    @Test
    public void addToRow() throws Exception {
        fillCarton();

        carton.display();

        for (int i = 0; i < rowNumber; i++) {
            for (int j = 0; j < columnNumber; j++) {
                Assert.assertEquals(j * 10 + i + 1, carton.getValueInRow(i, j));
            }
        }

    }

    private void fillCarton() {
        carton.addToRow(0, 1);
        carton.addToRow(0, 11);
        carton.addToRow(0, 21);
        carton.addToRow(0, 31);
        carton.addToRow(0, 41);
        carton.addToRow(0, 51);
        carton.addToRow(0, 61);
        carton.addToRow(0, 71);
        carton.addToRow(0, 81);

        carton.addToRow(1, 2);
        carton.addToRow(1, 12);
        carton.addToRow(1, 22);
        carton.addToRow(1, 32);
        carton.addToRow(1, 42);
        carton.addToRow(1, 52);
        carton.addToRow(1, 62);
        carton.addToRow(1, 72);
        carton.addToRow(1, 82);

        carton.addToRow(2, 3);
        carton.addToRow(2, 13);
        carton.addToRow(2, 23);
        carton.addToRow(2, 33);
        carton.addToRow(2, 43);
        carton.addToRow(2, 53);
        carton.addToRow(2, 63);
        carton.addToRow(2, 73);
        carton.addToRow(2, 83);
    }

}