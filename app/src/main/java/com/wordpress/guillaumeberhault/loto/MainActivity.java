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

    private ArrayList<Carton> cartonArrayList;

    private Button validateBtn;
    private NumberPicker numberPicker;
    private TextView textView_drawnNumbers, textView_sortedDrawnNumbers;
    private EditText[][] id;

    private ArrayList<Integer> drawnNumbers;
    private boolean oneRowComplete;
    private boolean twoRowsComplete;
    private GridLayout currentCartonGridLayout;
    private Carton currentCarton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViewsAndData();

        updateCurrentCartonDisplay(currentCarton);

        validateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentCarton.display();

                if (validateBtn.getText().equals("Validate")) {
                    validateBtn.setText("Modify");

                    retrieveValueForCarton(currentCarton);
                    updateCurrentCartonDisplay(currentCarton);

                    allCartonInputsEnabled(false);

                } else {
                    currentCarton.clear();
                    validateBtn.setText("Validate");

                    allCartonInputsEnabled(true);
                }

                currentCarton.display();
            }
        });

    }

    private void retrieveValueForCarton(Carton carton) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                String currentValue = id[i][j].getText().toString();
                if (!currentValue.isEmpty()) {
                    carton.addToRow(i, Integer.valueOf(currentValue));
                }
            }
        }
    }

    private void allCartonInputsEnabled(boolean enabled) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                id[i][j].setEnabled(enabled);
            }
        }
    }

    private void initializeViewsAndData() {
        currentCartonGridLayout = findViewById(R.id.currentCarton);

        validateBtn = findViewById(R.id.validate);

        textView_drawnNumbers = findViewById(R.id.dn);
        textView_sortedDrawnNumbers = findViewById(R.id.sdn);

        numberPicker = findViewById(R.id.numpicker);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(89);

        cartonArrayList = new ArrayList<>();
        cartonArrayList.add(new Carton(3, 9));
        currentCarton = cartonArrayList.get(0);

        oneRowComplete = false;
        twoRowsComplete = false;
    }

    private void updateCurrentCartonDisplay(Carton carton) {
        currentCartonGridLayout.removeAllViews();

        currentCartonGridLayout.setRowCount(carton.getRowNumber());
        currentCartonGridLayout.setColumnCount(carton.getColumnNumber());

        drawnNumbers = new ArrayList<>();
        id = new EditText[carton.getRowNumber()][carton.getColumnNumber()];

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                id[i][j] = new EditText(this);
                id[i][j].setInputType(InputType.TYPE_CLASS_NUMBER);
                id[i][j].setWidth(metrics.widthPixels / carton.getColumnNumber());
                if (carton.getValueInRow(i, j) == -1) {
                    id[i][j].setText("");
                } else {
                    id[i][j].setText(String.valueOf(carton.getValueInRow(i, j)));
                }
                currentCartonGridLayout.addView(id[i][j]);
            }
        }
    }

    public void addOrRemoveDrawnNumbers(View v) {
//        Button b = (Button) v;
        Integer valueToAddOrRemove = numberPicker.getValue();
        // Already drawn. Remove it.
        if (drawnNumbers.contains(valueToAddOrRemove)) {
            updateDrawnNumberCartonDisplay(valueToAddOrRemove);
            drawnNumbers.remove(valueToAddOrRemove);
            System.out.println("Remove number " + String.valueOf(valueToAddOrRemove));
        } else {
            drawnNumbers.add(valueToAddOrRemove);
            updateDrawnNumberCartonDisplay(valueToAddOrRemove);
            System.out.println("Add number " + String.valueOf(valueToAddOrRemove));
        }
        updateDrawnNumbersLists();

        checkCartonStatus();
    }

    private void checkCartonStatus() {
        int index = 0;
        for (Carton c :
                cartonArrayList) {
            index++;
            if (!oneRowComplete && c.checkDrawnNumbers(drawnNumbers) == Carton.status.oneRowComplete) {
                oneRowComplete = true;
                Toast.makeText(this, "Carton " + String.valueOf(index) + ": 1 row is complete", Toast.LENGTH_LONG).show();
            } else if (!twoRowsComplete && c.checkDrawnNumbers(drawnNumbers) == Carton.status.twoRowsComplete) {
                twoRowsComplete = true;
                Toast.makeText(this, "Carton " + String.valueOf(index) + ": 2 rows are complete", Toast.LENGTH_LONG).show();
            } else if (c.checkDrawnNumbers(drawnNumbers) == Carton.status.cartonComplete) {
                Toast.makeText(this, "Carton " + String.valueOf(index) + ": COMPLETE", Toast.LENGTH_LONG).show();
            }
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

    private void updateDrawnNumberCartonDisplay(int currentNumber) {
        for (int i = 0; i < currentCarton.getRowNumber(); i++) {
            for (int j = 0; j < currentCarton.getColumnNumber(); j++) {
                if (currentNumber == currentCarton.getValueInRow(i, j)) {
                    if (id[i][j].getTextColors().getDefaultColor() == Color.RED) {
                        id[i][j].setTextColor(Color.BLACK);
                    } else {
                        id[i][j].setTextColor(Color.RED);
                    }
                }
            }
        }

    }

    public void clearCarton(View v) {
        currentCarton.clear();
        drawnNumbers.clear();
        updateDrawnNumbersLists();
        cleanDisplay();
    }

    private void cleanDisplay() {
        for (int i = 0; i < currentCarton.getRowNumber(); i++) {
            for (int j = 0; j < currentCarton.getColumnNumber(); j++) {
                id[i][j].setText("");
            }
        }
    }
}
