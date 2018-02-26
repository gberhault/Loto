package com.wordpress.guillaumeberhault.loto;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
    private DisplayMetrics metrics;


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

                    retrieveInputValuesForCarton(currentCarton);
                    updateCurrentCartonDisplay(currentCarton);

                    EnableAllCartonInputs(false);

                } else {
                    currentCarton.clear();
                    validateBtn.setText("Validate");

                    EnableAllCartonInputs(true);
                }

                currentCarton.display();
            }
        });

    }

    private void retrieveInputValuesForCarton(Carton carton) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                String currentValue = id[i][j].getText().toString();
                if (!currentValue.isEmpty()) {
                    carton.addToRow(i, Integer.valueOf(currentValue));
                }
            }
        }
    }

    private void EnableAllCartonInputs(boolean enabled) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                id[i][j].setEnabled(enabled);
            }
        }
    }

    private void initializeViewsAndData() {
        initializeViews();

        initializeData();
    }

    private void initializeData() {
        cartonArrayList = new ArrayList<>();
        cartonArrayList.add(new Carton(3, 9));
        currentCarton = cartonArrayList.get(0);

        drawnNumbers = new ArrayList<>();

        oneRowComplete = false;
        twoRowsComplete = false;

        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
    }

    private void initializeViews() {
        currentCartonGridLayout = findViewById(R.id.currentCarton);

        validateBtn = findViewById(R.id.validate);

        textView_drawnNumbers = findViewById(R.id.dn);
        textView_sortedDrawnNumbers = findViewById(R.id.sdn);

        numberPicker = findViewById(R.id.numpicker);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(89);
    }

    private void newGame() {
        initializeData();
//        updateViews(); // Update currentCartonDisplay, list of cartons, drawn numbers
    }

    @Override
    protected void onResume() {
        super.onResume();

        getWindowManager().getDefaultDisplay().getMetrics(metrics);
    }

    private void updateCurrentCartonDisplay(Carton carton) {
        id = new EditText[carton.getRowNumber()][carton.getColumnNumber()];

        initializeCurrentGridLayoutWithCarton(carton);

        updateCurrentGridLayoutValues(carton);
    }

    private void updateCurrentGridLayoutValues(Carton carton) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                if (carton.getValueInRow(i, j) == -1) {
                    id[i][j].setText("");
                } else {
                    id[i][j].setText(String.valueOf(carton.getValueInRow(i, j)));
                }
            }
        }
    }

    private void initializeCurrentGridLayoutWithCarton(Carton carton) {
        currentCartonGridLayout.removeAllViews();

        currentCartonGridLayout.setRowCount(carton.getRowNumber());
        currentCartonGridLayout.setColumnCount(carton.getColumnNumber());


        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                id[i][j] = new EditText(this);
                id[i][j].setInputType(InputType.TYPE_CLASS_NUMBER);
                id[i][j].setWidth(metrics.widthPixels / carton.getColumnNumber());
                currentCartonGridLayout.addView(id[i][j]);
            }
        }
    }

    public void addOrRemoveDrawnNumbers(View v) {
        Integer valueToAddOrRemove = numberPicker.getValue();
        updateDrawnNumberCartonDisplay(valueToAddOrRemove);

        // Already drawn. Remove it.
        if (drawnNumbers.contains(valueToAddOrRemove)) {
            drawnNumbers.remove(valueToAddOrRemove);
        } else {
            drawnNumbers.add(valueToAddOrRemove);
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

            updateDrawnNumberDisplay();

            updateSortedDrawnNumberDisplay();
        }
    }

    private void updateSortedDrawnNumberDisplay() {
        ArrayList<Integer> sortedDrawnNumbers = (ArrayList<Integer>) drawnNumbers.clone();
        Collections.sort(sortedDrawnNumbers);
        String sortedDrawnNumbersString = generateStringFromList(sortedDrawnNumbers);
        textView_sortedDrawnNumbers.setText(sortedDrawnNumbersString);
    }

    private void updateDrawnNumberDisplay() {
        String drawnNumberString = generateStringFromList(drawnNumbers);
        textView_drawnNumbers.setText(drawnNumberString);
    }

    @NonNull
    private String generateStringFromList(ArrayList<Integer> drawnNumberList) {
        String drawnNumberString = "";

        for (Integer i :
                drawnNumberList) {
            drawnNumberString = drawnNumberString + i.toString() + " ";
        }
        return drawnNumberString;
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
