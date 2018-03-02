package com.wordpress.guillaumeberhault.loto;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by berhagu1 on 2/27/2018.
 */
public class CartonHandlerTest {

    private CartonHandler cartonHandler;

    @Before
    public void setUp() throws Exception {
        cartonHandler = new CartonHandler();
    }

    @Test
    public void addNewCarton_ok() throws Exception {
        Assert.assertEquals(0, cartonHandler.getCartonNumber());
        cartonHandler.addNewCarton(3, 9);
        Assert.assertEquals(1, cartonHandler.getCartonNumber());
    }

    @Test
    public void removeCarton_ok() throws Exception {
        cartonHandler.addNewCarton(3, 9);
        Assert.assertEquals(1, cartonHandler.getCartonNumber());
        cartonHandler.removeCarton(0);
        Assert.assertEquals(0, cartonHandler.getCartonNumber());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void removeCarton_wrongIndex_IndexOutOfBandException() throws Exception {
        cartonHandler.removeCarton(0);
    }

    @Test
    public void cartonStatus_noCarton_Empty() throws Exception {
        Assert.assertEquals("", cartonHandler.cartonStatus(0, null));
    }

    @Test
    public void cartonStatus_1Carton_nothing() throws Exception {
        cartonHandler.addNewCarton(3, 9);
        cartonHandler.addValueToCarton(0, 0, 1);
        cartonHandler.addValueToCarton(0, 1, 2);
        cartonHandler.addValueToCarton(0, 2, 3);
        Assert.assertEquals("Carton 1: Nothing yet.", cartonHandler.cartonStatus(0, null));
    }

    @Test
    public void cartonStatus_2Cartons_oneRow() throws Exception {
        cartonHandler.addNewCarton(3, 9);
        cartonHandler.addNewCarton(3, 9);
        cartonHandler.addValueToCarton(1, 0, 1);
        cartonHandler.addValueToCarton(1, 1, 2);
        cartonHandler.addValueToCarton(1, 2, 3);
        ArrayList<Integer> drawnNumbers = new ArrayList<>();
        drawnNumbers.add(1);
        Assert.assertEquals("Carton 2: One row complete.", cartonHandler.cartonStatus(1, drawnNumbers));
    }

    @Test
    public void cartonStatus_2Cartons_twoRows() throws Exception {
        cartonHandler.addNewCarton(3, 9);
        cartonHandler.addNewCarton(3, 9);
        cartonHandler.addValueToCarton(1, 0, 1);
        cartonHandler.addValueToCarton(1, 1, 2);
        cartonHandler.addValueToCarton(1, 2, 3);
        ArrayList<Integer> drawnNumbers = new ArrayList<>();
        drawnNumbers.add(1);
        drawnNumbers.add(2);
        Assert.assertEquals("Carton 2: Two rows complete.", cartonHandler.cartonStatus(1, drawnNumbers));
    }

    @Test
    public void cartonStatus_sCartons_complete() throws Exception {
        cartonHandler.addNewCarton(3, 9);
        cartonHandler.addNewCarton(3, 9);
        cartonHandler.addValueToCarton(1, 0, 1);
        cartonHandler.addValueToCarton(1, 1, 2);
        cartonHandler.addValueToCarton(1, 2, 3);
        ArrayList<Integer> drawnNumbers = new ArrayList<>();
        drawnNumbers.add(1);
        drawnNumbers.add(2);
        drawnNumbers.add(3);
        Assert.assertEquals("Carton 2: Carton complete.", cartonHandler.cartonStatus(1, drawnNumbers));
    }

    @Test
    public void getAllCartonsStatus_noCarton_empty() throws Exception {
        Assert.assertEquals("", cartonHandler.getAllCartonsStatus(null));
    }

    @Test
    public void getAllCartonsStatus_4Cartons_ok() throws Exception {
        cartonHandler.addNewCarton(3, 9);
        cartonHandler.addValueToCarton(0, 0, 10);
        cartonHandler.addValueToCarton(0, 1, 20);
        cartonHandler.addValueToCarton(0, 2, 30);

        cartonHandler.addNewCarton(3, 9);
        cartonHandler.addValueToCarton(1, 0, 1);
        cartonHandler.addValueToCarton(1, 1, 20);
        cartonHandler.addValueToCarton(1, 2, 30);

        cartonHandler.addNewCarton(3, 9);
        cartonHandler.addValueToCarton(2, 0, 1);
        cartonHandler.addValueToCarton(2, 1, 2);
        cartonHandler.addValueToCarton(2, 2, 30);

        cartonHandler.addNewCarton(3, 9);
        cartonHandler.addValueToCarton(3, 0, 1);
        cartonHandler.addValueToCarton(3, 1, 2);
        cartonHandler.addValueToCarton(3, 2, 3);

        ArrayList<Integer> drawnNumbers = new ArrayList<>();
        drawnNumbers.add(1);
        drawnNumbers.add(2);
        drawnNumbers.add(3);

        Assert.assertEquals("Carton 1: Nothing yet.\n" +
                "Carton 2: One row complete.\n" +
                "Carton 3: Two rows complete.\n" +
                "Carton 4: Carton complete.", cartonHandler.getAllCartonsStatus(drawnNumbers));
    }

    @Test
    public void getCartonNumber_0Carton_0() throws Exception {
        Assert.assertEquals(0, cartonHandler.getCartonNumber());
    }

    @Test
    public void getCartonNumber_10Cartons_10() throws Exception {
        for (int i = 0; i < 10; i++) {
            cartonHandler.addNewCarton(3, 9);
        }
        Assert.assertEquals(10, cartonHandler.getCartonNumber());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getCarton_noCarton_IndexOutOfBoundsException() throws Exception {
        Assert.assertNull(cartonHandler.getCarton(0));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getCarton_wrongIndexUnder_IndexOutOfBoundsException() throws Exception {
        Assert.assertNull(cartonHandler.getCarton(-1));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getCarton_wrongIndexOver_IndexOutOfBoundsException() throws Exception {
        Assert.assertNull(cartonHandler.getCarton(100));
    }

    @Test
    public void getCarton_OK() throws Exception {
        cartonHandler.addNewCarton(3, 9);
        Assert.assertNotNull(cartonHandler.getCarton(0));
    }


}