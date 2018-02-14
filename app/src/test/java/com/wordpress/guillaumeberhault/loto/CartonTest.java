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
        carton.addToRow(1, 1);
        carton.addToRow(1, 11);
        carton.addToRow(1, 21);
        carton.addToRow(1, 31);
        carton.addToRow(1, 41);
        carton.addToRow(1, 51);
        carton.addToRow(1, 61);
        carton.addToRow(1, 71);
        carton.addToRow(1, 81);

        carton.addToRow(2, 2);
        carton.addToRow(2, 12);
        carton.addToRow(2, 22);
        carton.addToRow(2, 32);
        carton.addToRow(2, 42);
        carton.addToRow(2, 52);
        carton.addToRow(2, 62);
        carton.addToRow(2, 72);
        carton.addToRow(2, 82);

        carton.addToRow(3, 3);
        carton.addToRow(3, 13);
        carton.addToRow(3, 23);
        carton.addToRow(3, 33);
        carton.addToRow(3, 43);
        carton.addToRow(3, 53);
        carton.addToRow(3, 63);
        carton.addToRow(3, 73);
        carton.addToRow(3, 83);
    }

}