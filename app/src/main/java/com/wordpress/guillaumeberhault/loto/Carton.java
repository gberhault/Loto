package com.wordpress.guillaumeberhault.loto;

import java.util.Vector;

/**
 * Created by berhagu1 on 2/13/2018.
 */

public class Carton {

    private Vector<Row> rows;

    private int rowNumber, columnNumber;

    public Carton(int rowNumber, int columnNumber) {
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;
        this.rows = new Vector<>(rowNumber);

        for (int i = 0; i < rowNumber; i++){
            Row element = new Row(columnNumber);
            rows.add(element);
        }
    }

    /**
     *
     * @param rowIndex from 1 to max.
     * @param value
     */
    public void addToRow(int rowIndex, int value) {
        if (rowIndex <= 0 || rowIndex > 3)
            throw new RuntimeException("1 <= Row index <= 3");
        if (value < 0 || value > 89)
            throw new RuntimeException("0 < value < 89");

        rows.get(rowIndex-1).setColumnValue(value);
    }

    public int getValueInRow(int rowIndex, int columnIndex) {
        if (rowIndex < 0)
            throw new RuntimeException("Row index => 0");
        if (columnIndex < 0 || columnIndex >= columnNumber)
            throw new RuntimeException("0 <= Column Index < "+String.valueOf(columnNumber));

        return rows.get(rowIndex).getColumnValue(columnIndex);
    }


    public void display() {
        for (int i = 0; i < rowNumber; i++) {
            System.out.print("|");
            for (int j = 0; j < columnNumber; j++) {
                System.out.print(String.valueOf(this.getValueInRow(i, j)) + "|");
            }
            System.out.println();
        }
    }
}
