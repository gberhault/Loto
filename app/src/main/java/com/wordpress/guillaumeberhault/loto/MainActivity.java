package com.wordpress.guillaumeberhault.loto;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    ArrayList<ArrayList<EditText>> cartonInputMatrix;

    Button validate, clear;

    Carton carton;

    NumberPicker numberPicker;

    TextView textView_drawnNumbers, textView_sortedDrawnNumbers;

    private EditText[][] id;
    private ArrayList<Integer> drawnNumbers;
    private boolean oneRowComplete;
    private boolean twoRowsComplete;
    private GridLayout currentCarton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentCarton = findViewById(R.id.currentCarton);

        currentCarton.setRowCount(carton.getRowNumber());
        currentCarton.setColumnCount(carton.getColumnNumber());

        drawnNumbers = new ArrayList<>();
        id = new EditText[3][9];
        cartonInputMatrix = new ArrayList<>();

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        for (int i = 0; i < 3; i++) {
            cartonInputMatrix.add(new ArrayList<EditText>());
            for (int j = 0; j < 9; j++) {
                id[i][j] = new EditText(this);
                id[i][j].setInputType(InputType.TYPE_CLASS_NUMBER);
                id[i][j].setWidth(metrics.widthPixels/carton.getColumnNumber());
                currentCarton.addView(id[i][j]);
                cartonInputMatrix.get(i).add(id[i][j]);
            }
        }


        this.clear = findViewById(R.id.clear);

        this.oneRowComplete = false;
        this.twoRowsComplete = false;

        textView_drawnNumbers = findViewById(R.id.dn);
        textView_sortedDrawnNumbers = findViewById(R.id.sdn);

        numberPicker = findViewById(R.id.numpicker);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(89);

        carton = new Carton(3, 9);


        validate = findViewById(R.id.validate);
        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                carton.display();

                if (validate.getText().equals("Validate")) {
                    validate.setText("Modify");

                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 9; j++) {
                            String currentValue = ((EditText) id[i][j]).getText().toString();
                            if (!currentValue.isEmpty()) {
                                carton.addToRow(i, Integer.valueOf(currentValue));
                            }
                        }
                    }

                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 9; j++) {
                            if (carton.getValueInRow(i, j) == -1) {
                                cartonInputMatrix.get(i).get(j).setText("");
                            } else {
                                cartonInputMatrix.get(i).get(j).setText(String.valueOf(carton.getValueInRow(i, j)));
                            }
                            cartonInputMatrix.get(i).get(j).setEnabled(false);
                        }
                    }
                } else {
                    carton.clear();
                    validate.setText("Validate");
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 9; j++) {
                            cartonInputMatrix.get(i).get(j).setEnabled(true);
                        }
                    }
                }

                carton.display();
            }
        });

    }

    public void addOrRemoveDrawnNumbers(View v) {
//        Button b = (Button) v;
        Integer valueToAddOrRemove = numberPicker.getValue();
        // Already drawn. Remove it.
        if (drawnNumbers.contains(valueToAddOrRemove)) {
            updateCartonDisplay(valueToAddOrRemove);
            drawnNumbers.remove(valueToAddOrRemove);
            System.out.println("Remove number " + String.valueOf(valueToAddOrRemove));
        } else {
            drawnNumbers.add(valueToAddOrRemove);
            updateCartonDisplay(valueToAddOrRemove);
            System.out.println("Add number " + String.valueOf(valueToAddOrRemove));
        }
        updateDrawnNumbersLists();

        if (!oneRowComplete && carton.checkDrawnNumbers(drawnNumbers) == Carton.status.oneRowComplete) {
            oneRowComplete = true;
            Toast.makeText(this, "One row is complete", Toast.LENGTH_LONG).show();
        } else if (!twoRowsComplete && carton.checkDrawnNumbers(drawnNumbers) == Carton.status.twoRowsComplete) {
            twoRowsComplete = true;
            Toast.makeText(this, "Two rows are complete", Toast.LENGTH_LONG).show();
        } else if (carton.checkDrawnNumbers(drawnNumbers) == Carton.status.cartonComplete) {
            Toast.makeText(this, "Carton is complete", Toast.LENGTH_LONG).show();
        }
    }

    private void updateDrawnNumbersLists() {
        if (drawnNumbers != null) {
            textView_drawnNumbers.setText("");
            textView_sortedDrawnNumbers.setText("");

            String drawnNumberString = "";
            String sortedDrawnNumbersString = "";

            for (Integer i :
                    drawnNumbers) {
                drawnNumberString = drawnNumberString + i.toString() + " ";
            }
            textView_drawnNumbers.setText(drawnNumberString);

            ArrayList<Integer> sortedDrawnNumbers = (ArrayList<Integer>) drawnNumbers.clone();
            Collections.sort(sortedDrawnNumbers);
            for (Integer i :
                    sortedDrawnNumbers) {
                sortedDrawnNumbersString += i.toString() + " ";
            }
            textView_sortedDrawnNumbers.setText(sortedDrawnNumbersString);
        }
    }

    private void updateCartonDisplay(int currentNumber) {
        for (int i = 0; i < carton.getRowNumber(); i++) {
            for (int j = 0; j < carton.getColumnNumber(); j++) {
                if (currentNumber == carton.getValueInRow(i, j)) {
                    if (cartonInputMatrix.get(i).get(j).getTextColors().getDefaultColor() == Color.RED) {
                        cartonInputMatrix.get(i).get(j).setTextColor(Color.BLACK);
                    } else {
                        cartonInputMatrix.get(i).get(j).setTextColor(Color.RED);
                    }
                }
            }
        }

    }

    public void clearCarton(View v) {
        carton.clear();
        drawnNumbers.clear();
        updateDrawnNumbersLists();
        cleanDisplay();
    }

    private void cleanDisplay() {
        for (int i = 0; i < carton.getRowNumber(); i++) {
            for (int j = 0; j < carton.getColumnNumber(); j++) {
                cartonInputMatrix.get(i).get(j).setText("");
            }
        }
    }
}
