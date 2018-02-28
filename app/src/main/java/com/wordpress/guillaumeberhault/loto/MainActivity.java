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

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

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

    private CartonHandler cartonHandler;
    private TextView cartonsStatus;
    private GridLayout otherCartonsGridLayout;
    private Button carton1Btn;


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

                    enableAllCartonInputs(false);

                } else {
                    currentCarton.clear();
                    validateBtn.setText("Validate");

                    enableAllCartonInputs(true);
                }

                currentCarton.display();
            }
        });

        carton1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentCarton = cartonHandler.getCarton(0);
                updateCurrentCartonDisplay(currentCarton);
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

    private void enableAllCartonInputs(boolean enabled) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                id[i][j].setEnabled(enabled);
            }
        }
    }

    private void initializeViewsAndData() {
        initializeData();

        initializeViews();
    }

    private void initializeData() {
        cartonHandler = new CartonHandler();
        cartonHandler.addNewCarton(3, 9);
        currentCarton = cartonHandler.getCarton(0);

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

        cartonsStatus = findViewById(R.id.cartonsStatus);

        otherCartonsGridLayout = findViewById(R.id.otherCartons);

        carton1Btn = findViewById(R.id.carton1);
        carton1Btn.setWidth(metrics.widthPixels / 4);

        ((Button)findViewById(R.id.newCartonBtn)).setWidth(metrics.widthPixels / 4);
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

    public void newCarton(View v) {
        cartonHandler.addNewCarton(3, 9);
        Button child = new Button(this);
        String CartonIndex = "Carton " + String.valueOf(cartonHandler.getCartonNumber());
        child.setText(CartonIndex);
        child.setWidth(metrics.widthPixels / 4);
        child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String buttonText = ((Button) view).getText().toString();
                int cartonIndex = Integer.valueOf(buttonText.split(" ")[1]);
                currentCarton = cartonHandler.getCarton(cartonIndex - 1);
                updateCurrentCartonDisplay(currentCarton);
            }
        });
        otherCartonsGridLayout.addView(child, cartonHandler.getCartonNumber() - 1);
        updateCurrentCartonDisplay(currentCarton);
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
        cartonsStatus.setText(cartonHandler.getAllCartonsStatus(drawnNumbers));
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
