package com.wordpress.guillaumeberhault.loto;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.util.Vector;

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

    @Test
    public void clear() throws Exception {
        fillCarton();
        for (int i = 0; i < rowNumber; i++) {
            for (int j = 0; j < columnNumber; j++) {
                Assert.assertEquals(j*10+i+1, carton.getValueInRow(i, j));
            }
        }

        carton.clear();
        for (int i = 0; i < rowNumber; i++) {
            for (int j = 0; j < columnNumber; j++) {
                Assert.assertEquals(-1, carton.getValueInRow(i, j));
            }
        }
    }

    @Test
    public void checkDrawnNumbers_nothing() throws Exception {
        fillCartonRealExample();
        Assert.assertEquals(Carton.status.nothing, carton.checkDrawnNumbers(new Vector<Integer>()));
    }

    @Test
    public void checkDrawnNumbers_1rowComplete() throws Exception {
        fillCartonRealExample();
        Vector<Integer> drawnNumbers = new Vector<>();

        drawnNumbers.add(9);
        drawnNumbers.add(17);
        drawnNumbers.add(47);
        drawnNumbers.add(50);
        drawnNumbers.add(88);

        Assert.assertEquals(Carton.status.oneRowComplete, carton.checkDrawnNumbers(drawnNumbers));
    }

    @Test
    public void checkDrawnNumbers_2RowsComplete() throws Exception {
        fillCartonRealExample();
        Vector<Integer> drawnNumbers = new Vector<>();

        drawnNumbers.add(9);
        drawnNumbers.add(17);
        drawnNumbers.add(47);
        drawnNumbers.add(50);
        drawnNumbers.add(88);

        drawnNumbers.add(22);
        drawnNumbers.add(38);
        drawnNumbers.add(68);
        drawnNumbers.add(73);
        drawnNumbers.add(83);

        Assert.assertEquals(Carton.status.twoRowsComplete, carton.checkDrawnNumbers(drawnNumbers));
    }

    @Test
    public void checkDrawnNumbers_cartonComplete() throws Exception {
        fillCartonRealExample();
        Vector<Integer> drawnNumbers = new Vector<>();

        drawnNumbers.add(9);
        drawnNumbers.add(17);
        drawnNumbers.add(47);
        drawnNumbers.add(50);
        drawnNumbers.add(88);

        drawnNumbers.add(22);
        drawnNumbers.add(38);
        drawnNumbers.add(68);
        drawnNumbers.add(73);
        drawnNumbers.add(83);

        drawnNumbers.add(3);
        drawnNumbers.add(10);
        drawnNumbers.add(35);
        drawnNumbers.add(46);
        drawnNumbers.add(65);

        Assert.assertEquals(Carton.status.cartonComplete, carton.checkDrawnNumbers(drawnNumbers));
    }

    private void fillCartonRealExample() {
        carton.addToRow(0, 9);
        carton.addToRow(0, 17);
        carton.addToRow(0, 47);
        carton.addToRow(0, 50);
        carton.addToRow(0, 88);

        carton.addToRow(1, 22);
        carton.addToRow(1, 38);
        carton.addToRow(1, 68);
        carton.addToRow(1, 73);
        carton.addToRow(1, 83);

        carton.addToRow(2, 3);
        carton.addToRow(2, 10);
        carton.addToRow(2, 35);
        carton.addToRow(2, 46);
        carton.addToRow(2, 65);
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