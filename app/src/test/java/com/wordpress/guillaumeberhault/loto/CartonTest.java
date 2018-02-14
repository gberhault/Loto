package com.wordpress.guillaumeberhault.loto;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by berhagu1 on 2/13/2018.
 */
public class CartonTest {


    private Carton carton;

    @Before
    public void setUp() throws Exception {
        carton = new Carton();
    }

    @Test(expected = RuntimeException.class)
    public void getValueInRow_wrongColumnIndex_small_RuntimeException() throws Exception {
        carton.getValueInRow(1, 0);
    }

    @Test(expected = RuntimeException.class)
    public void getValueInRow_wrongColumnIndex_overflow_RuntimeException() throws Exception {
        carton.getValueInRow(1, 10);
    }

    @Test(expected = RuntimeException.class)
    public void getValueInRow_wrongRowIndex_overflow_RuntimeException() throws Exception {
        carton.getValueInRow(0, 5);
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
    public void addToRow_Ligne1() throws Exception {
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

        for (int i=1; i<=3; i++){
            for (int j=1; j<10; j++){
                Assert.assertEquals((j-1)*10+i, carton.getValueInRow(i,j));
            }
        }
    }

    @Test(expected = RuntimeException.class)
    public void whichColumn_underflow_runtimeException() throws Exception {
        carton.whichColumn(0);
    }

    @Test(expected = RuntimeException.class)
    public void whichColumn_overflow_runtimeException() throws Exception {
        carton.whichColumn(90);
    }

    @Test
    public void whichColumn_unite() throws Exception {
        Assert.assertEquals(1, carton.whichColumn(5));
    }

    @Test
    public void whichColumn_10() throws Exception {
        Assert.assertEquals(2, carton.whichColumn(10));
    }

    @Test
    public void whichColumn_20() throws Exception {
        Assert.assertEquals(3, carton.whichColumn(20));
    }

    @Test
    public void whichColumn_30() throws Exception {
        Assert.assertEquals(4, carton.whichColumn(30));
    }

    @Test
    public void whichColumn_40() throws Exception {
        Assert.assertEquals(5, carton.whichColumn(40));
    }

    @Test
    public void whichColumn_50() throws Exception {
        Assert.assertEquals(6, carton.whichColumn(50));
    }

    @Test
    public void whichColumn_60() throws Exception {
        Assert.assertEquals(7, carton.whichColumn(60));
    }

    @Test
    public void whichColumn_70() throws Exception {
        Assert.assertEquals(8, carton.whichColumn(70));
    }

    @Test
    public void whichColumn_80() throws Exception {
        Assert.assertEquals(9, carton.whichColumn(80));
    }

}