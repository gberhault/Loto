package com.wordpress.guillaumeberhault.loto;

/**
 * Created by berhagu1 on 2/13/2018.
 */

public class Carton {
    private Row l1, l2, l3;

    public Carton() {
        this.l1 = new Row();
        this.l2 = new Row();
        this.l3 = new Row();
    }

    public void addToRow(int rowIndex, int value) {
        if (rowIndex <= 0 || rowIndex > 3)
            throw new RuntimeException("1 <= Row index <= 3");
        if (value < 0 || value > 89)
            throw new RuntimeException("0 < value < 89");

        if (rowIndex == 1) {
            switch (whichColumn(value)) {
                case 1:
                    l1.setCol1(value);
                    break;
                case 2:
                    l1.setCol2(value);
                    break;
                case 3:
                    l1.setCol3(value);
                    break;
                case 4:
                    l1.setCol4(value);
                    break;
                case 5:
                    l1.setCol5(value);
                    break;
                case 6:
                    l1.setCol6(value);
                    break;
                case 7:
                    l1.setCol7(value);
                    break;
                case 8:
                    l1.setCol8(value);
                    break;
                case 9:
                    l1.setCol9(value);
                    break;
            }
        } else if (rowIndex == 2) {
            switch (whichColumn(value)) {
                case 1:
                    l2.setCol1(value);
                    break;
                case 2:
                    l2.setCol2(value);
                    break;
                case 3:
                    l2.setCol3(value);
                    break;
                case 4:
                    l2.setCol4(value);
                    break;
                case 5:
                    l2.setCol5(value);
                    break;
                case 6:
                    l2.setCol6(value);
                    break;
                case 7:
                    l2.setCol7(value);
                    break;
                case 8:
                    l2.setCol8(value);
                    break;
                case 9:
                    l2.setCol9(value);
                    break;
            }
        } else {
            switch (whichColumn(value)) {
                case 1:
                    l3.setCol1(value);
                    break;
                case 2:
                    l3.setCol2(value);
                    break;
                case 3:
                    l3.setCol3(value);
                    break;
                case 4:
                    l3.setCol4(value);
                    break;
                case 5:
                    l3.setCol5(value);
                    break;
                case 6:
                    l3.setCol6(value);
                    break;
                case 7:
                    l3.setCol7(value);
                    break;
                case 8:
                    l3.setCol8(value);
                    break;
                case 9:
                    l3.setCol9(value);
                    break;
            }
        }
    }

    public int getValueInRow(int rowIndex, int columnIndex) {
        if (rowIndex <= 0)
            throw new RuntimeException("Row index > 0");
        if (columnIndex <= 0 || columnIndex > 9)
            throw new RuntimeException("0 < Column Index < 9");

        if (rowIndex == 1)
            switch (columnIndex) {
                case 1:
                    return (l1.getCol1());
                case 2:
                    return (l1.getCol2());
                case 3:
                    return (l1.getCol3());
                case 4:
                    return (l1.getCol4());
                case 5:
                    return (l1.getCol5());
                case 6:
                    return (l1.getCol6());
                case 7:
                    return (l1.getCol7());
                case 8:
                    return (l1.getCol8());
                case 9:
                    return (l1.getCol9());
            }

        else if (rowIndex == 2)
            switch (columnIndex) {
                case 1:
                    return (l2.getCol1());
                case 2:
                    return (l2.getCol2());
                case 3:
                    return (l2.getCol3());
                case 4:
                    return (l2.getCol4());
                case 5:
                    return (l2.getCol5());
                case 6:
                    return (l2.getCol6());
                case 7:
                    return (l2.getCol7());
                case 8:
                    return (l2.getCol8());
                case 9:
                    return (l2.getCol9());
            }

        else
            switch (columnIndex) {
                case 1:
                    return (l3.getCol1());
                case 2:
                    return (l3.getCol2());
                case 3:
                    return (l3.getCol3());
                case 4:
                    return (l3.getCol4());
                case 5:
                    return (l3.getCol5());
                case 6:
                    return (l3.getCol6());
                case 7:
                    return (l3.getCol7());
                case 8:
                    return (l3.getCol8());
                case 9:
                    return (l3.getCol9());
            }

        return -1;
    }

    protected int whichColumn(int value) {
        if (value <= 0 || value > 89)
            throw new RuntimeException("0 < value < 89");

        if (value < 10)
            return 1;
        else if (value < 20)
            return 2;
        else if (value < 30)
            return 3;
        else if (value < 40)
            return 4;
        else if (value < 50)
            return 5;
        else if (value < 60)
            return 6;
        else if (value < 70)
            return 7;
        else if (value < 80)
            return 8;
        else
            return 9;
    }
}
