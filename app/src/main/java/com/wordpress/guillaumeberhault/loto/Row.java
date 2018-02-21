package com.wordpress.guillaumeberhault.loto;

import java.util.ArrayList;

/**
 * Created by berhagu1 on 2/13/2018.
 */

class Row {
    private ArrayList<Integer> columns;
    private int columnNumber;

    public Row(int columnNumber) {
        this.columnNumber = columnNumber;
        this.columns = new ArrayList<>();

        for (int i = 0; i < columnNumber; i++) {
            columns.add(new Integer(-1));
        }
    }

    public Integer getColumnValue(int columnIndex) {
        return columns.get(columnIndex);
    }

    public void setColumnValue(int value) {
        columns.set(whichColumn(value), value);
    }

    public void set(int columnIndex, int value) {
        columns.set(columnIndex, value);
    }

    /**
     * @param value
     * @return column index from 0 to Max-1.
     */
    private int whichColumn(int value) {
        if (value <= 0 || value > 89)
            throw new RuntimeException("0 < value < 89");

        if (value < 10)
            return 0;
        else if (value < 20)
            return 1;
        else if (value < 30)
            return 2;
        else if (value < 40)
            return 3;
        else if (value < 50)
            return 4;
        else if (value < 60)
            return 5;
        else if (value < 70)
            return 6;
        else if (value < 80)
            return 7;
        else
            return 8;
    }

}
