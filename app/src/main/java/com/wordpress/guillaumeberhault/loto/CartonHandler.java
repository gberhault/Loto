package com.wordpress.guillaumeberhault.loto;

import android.content.Context;
import android.os.Build;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by berhagu1 on 2/27/2018.
 */

public class CartonHandler {
    private Locale currentLocale;
    private Context context;
    private ArrayList<Carton> cartonArrayList;

    public CartonHandler(Context context) {
        this.cartonArrayList = new ArrayList<>();
        this.context = context;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            this.currentLocale = context.getResources().getConfiguration().getLocales().get(0);
        } else {
            this.currentLocale = context.getResources().getConfiguration().locale;
        }
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
                    status = "Carton " + String.valueOf(cartonIndex + 1) + ": " + context.getString(R.string.one_row_complete) + ".";
                    break;
                case twoRowsComplete:
                    status = "Carton " + String.valueOf(cartonIndex + 1) + ": " + context.getString(R.string.two_rows_complete) + ".";
                    break;
                case cartonComplete:
                    status = "Carton " + String.valueOf(cartonIndex + 1) + ": " + context.getString(R.string.carton_complete) + ".";
                    break;
                default:
                    status = "Carton " + String.valueOf(cartonIndex + 1) + ": " + context.getString(R.string.nothing_yet) + ".";
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
