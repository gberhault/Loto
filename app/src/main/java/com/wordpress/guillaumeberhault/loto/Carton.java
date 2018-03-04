package com.wordpress.guillaumeberhault.loto;

import java.util.ArrayList;

/**
 * Created by berhagu1 on 2/13/2018.
 */

public class Carton {

    private ArrayList<Row> rowVector;

    private int rowNumber, columnNumber;

    private boolean modifiable;

    public Carton(int rowNumber, int columnNumber) {
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;
        this.rowVector = new ArrayList<>(rowNumber);
        this.modifiable = true;

        for (int i = 0; i < rowNumber; i++) {
            Row element = new Row(columnNumber);
            rowVector.add(element);
        }
    }

    public boolean isModifiable() {
        return modifiable;
    }

    public void setModifiable(boolean modifiable) {
        this.modifiable = modifiable;
    }

    public status checkDrawnNumbers(ArrayList<Integer> drawnNumbersList) {

        int row0Status, row1Status, row2Status;

        row0Status = isRowComplete(rowVector.get(0), drawnNumbersList);
        row1Status = isRowComplete(rowVector.get(1), drawnNumbersList);
        row2Status = isRowComplete(rowVector.get(2), drawnNumbersList);

        if (row0Status + row1Status + row2Status == 0)
            return status.nothing;
        else if (row0Status + row1Status + row2Status == 1)
            return status.oneRowComplete;
        else if (row0Status + row1Status + row2Status == 2)
            return status.twoRowsComplete;
        else
            return status.cartonComplete;
    }

    private int isRowComplete(Row row, ArrayList<Integer> drawnNumbersList) {
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

    /**
     * @param rowIndex from 1 to max.
     * @param value
     */

    public void addToRow(int rowIndex, int value) {
        if (rowIndex < 0 || rowIndex >= 3)
            throw new RuntimeException("0 <= Row index < 3");
        if (value < 0 || value > 89)
            throw new RuntimeException("0 < value < 89");

        rowVector.get(rowIndex).setColumnValue(value);
    }

    public int getValueInRow(int rowIndex, int columnIndex) {
        if (rowIndex < 0)
            throw new RuntimeException("Row index => 0");
        if (columnIndex < 0 || columnIndex >= columnNumber)
            throw new RuntimeException("0 <= Column Index < " + String.valueOf(columnNumber));

        return rowVector.get(rowIndex).getColumnValue(columnIndex);
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

    public void clear() {
        for (int i = 0; i < rowNumber; i++) {
            for (int j = 0; j < columnNumber; j++) {
                rowVector.get(i).set(j, -1);
            }
        }
    }

    public enum status {
        oneRowComplete, twoRowsComplete, cartonComplete, nothing;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public int getColumnNumber() {
        return columnNumber;
    }
}
