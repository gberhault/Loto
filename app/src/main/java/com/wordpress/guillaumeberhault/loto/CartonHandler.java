package com.wordpress.guillaumeberhault.loto;

import java.util.ArrayList;

/**
 * Created by berhagu1 on 2/27/2018.
 */

public class CartonHandler {
    private ArrayList<Carton> cartonArrayList;

    public CartonHandler() {
        this.cartonArrayList = new ArrayList<>();
    }

    public void addNewCarton(int rowNumber, int columnNumber) {
        cartonArrayList.add(new Carton(rowNumber, columnNumber));
    }

    public void removeCarton(int index) {
        cartonArrayList.remove(index);
    }

    public void removeCarton(Carton c) {
        cartonArrayList.remove(c);
    }

    public String cartonStatus(int cartonIndex, ArrayList<Integer> drawnNumberList) {
        String status = "";
        try {
            Carton carton = cartonArrayList.get(cartonIndex);
            switch (carton.checkDrawnNumbers(drawnNumberList)) {
                case oneRowComplete:
                    status = "Carton " + String.valueOf(cartonIndex + 1) + ": One row complete.";
                    break;
                case twoRowsComplete:
                    status = "Carton " + String.valueOf(cartonIndex + 1) + ": Two rows complete.";
                    break;
                case cartonComplete:
                    status = "Carton " + String.valueOf(cartonIndex + 1) + ": Carton complete.";
                    break;
                default:
                    status = "Carton " + String.valueOf(cartonIndex + 1) + ": Nothing yet.";
                    break;
            }
        } catch (IndexOutOfBoundsException e) {
            return status;
        }
        return status;
    }

    public String getAllCartonsStatus(ArrayList<Integer> drawnNumberList) {
        String allcartonStatusString = "";
        for (int i = 0; i < cartonArrayList.size(); i++) {
            allcartonStatusString += cartonStatus(i, drawnNumberList) + "\n";
        }
        return allcartonStatusString.trim();
    }

    public int getCartonNumber() {
        return cartonArrayList.size();
    }

    public Carton getCarton(int index) {
        return cartonArrayList.get(index);
    }

    public void addValueToCarton(int cartonIndex, int row, int value) {
        cartonArrayList.get(cartonIndex).addToRow(row, value);
    }
}
