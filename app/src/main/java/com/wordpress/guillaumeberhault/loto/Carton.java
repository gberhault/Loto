package com.wordpress.guillaumeberhault.loto;

import java.util.Vector;

/**
 * Created by berhagu1 on 2/13/2018.
 */

public class Carton {

    private Vector<Row> rows;

    private int rowNumber, columnNumber;

    public enum status {
        oneRowComplete, twoRowsComplete, cartonComplete, nothing;
    }

    public status checkDrawnNumbers(Vector<Integer> drawnNumbersList) {

        int row0Status, row1Status, row2Status;

        row0Status = isRowComplete(rows.get(0), drawnNumbersList);
        row1Status = isRowComplete(rows.get(1), drawnNumbersList);
        row2Status = isRowComplete(rows.get(2), drawnNumbersList);

        if (row0Status + row1Status + row2Status == 0)
            return status.nothing;
        else if (row0Status + row1Status + row2Status == 1)
            return status.oneRowComplete;
        else if (row0Status + row1Status + row2Status == 2)
            return status.twoRowsComplete;
        else
            return status.cartonComplete;
    }

    private int isRowComplete(Row row, Vector<Integer> drawnNumbersList) {
        if (drawnNumbersList == null || drawnNumbersList.isEmpty())
            return 0;
        // for all columns
        for (int j = 0; j < 9; j++) {
            boolean found = false;
            // if not empty
            if (row.getColumnValue(j) != -1) {
                for (Integer e :
                        drawnNumbersList) {
                    if (row.getColumnValue(j).equals(e)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    return 0;
                }
            }
        }
        // if we are here, all columns found a match.
        return 1;
    }

    public Carton(int rowNumber, int columnNumber) {
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;
        this.rows = new Vector<>(rowNumber);

        for (int i = 0; i < rowNumber; i++) {
            Row element = new Row(columnNumber);
            rows.add(element);
        }
    }

    /**
     * @param rowIndex from 1 to max.
     * @param value
     */

    public void addToRow(int rowIndex, int value) {
        if (rowIndex < 0 || rowIndex >= 3)
            throw new RuntimeException("0 <= Row index < 3");
        if (value < 0 || value > 89)
            throw new RuntimeException("0 < value < 89");

        rows.get(rowIndex).setColumnValue(value);
    }

    public int getValueInRow(int rowIndex, int columnIndex) {
        if (rowIndex < 0)
            throw new RuntimeException("Row index => 0");
        if (columnIndex < 0 || columnIndex >= columnNumber)
            throw new RuntimeException("0 <= Column Index < " + String.valueOf(columnNumber));

        return rows.get(rowIndex).getColumnValue(columnIndex);
    }


    public void display() {
        for (int i = 0; i < rowNumber; i++) {
            System.out.print("|");
            for (int j = 0; j < columnNumber; j++) {
                if (this.getValueInRow(i, j) != -1) {
                    System.out.print(String.valueOf(this.getValueInRow(i, j)) + "|");
                } else {
                    System.out.print("XX|");
                }
            }
            System.out.println();
        }
    }
}
