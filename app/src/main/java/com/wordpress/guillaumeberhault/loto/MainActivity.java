package com.wordpress.guillaumeberhault.loto;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private final int buttonWidthdp = 50;
    private int buttonWidthPixel;
    private Button validateCurrentCartonBtn;
    private NumberPicker numberPicker;
    private TextView textView_drawnNumbers, textView_sortedDrawnNumbers;
    private EditText[][] id;

    private ArrayList<Integer> drawnNumbers;
    private GridLayout currentCartonGridLayout;
    private Carton currentCarton;
    private DisplayMetrics metrics;

    private CartonHandler cartonHandler;
    private TextView cartonsStatusTV;
    private GridLayout otherCartonsGridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViewsAndData();

        updateCurrentCartonDisplay(currentCarton);
    }

    // OnClick Methods
    public void validateOrModifyCurrentCarton(View v) {
        if (currentCarton.isModifiable()) {
            retrieveInputValuesForCarton(currentCarton);
            currentCarton.setModifiable(false);
        } else {
            currentCarton.setModifiable(true);
        }

        hideSoftKeyboard(this);
        updateUI();
    }

    public void newCarton(View v) {
        cartonHandler.addNewCarton(3, 9);
        addCartonButton(cartonHandler.getCartonNumber() - 1);
    }

    public void changeCurrentCarton(View v) {
        Button b = (Button) v;
        String buttonText = b.getText().toString();
        int cartonIndex = Integer.valueOf(buttonText);
        currentCarton = cartonHandler.getCarton(cartonIndex - 1);
        updateUI();
    }

    public void addOrRemoveDrawnNumbers(View v) {
        Integer valueToAddOrRemove = numberPicker.getValue();
        // TODO: 03/03/2018 make it better. See comment inside method.
//        updateDrawnNumberCartonDisplay(valueToAddOrRemove);

        // Already drawn. Remove it.
        if (drawnNumbers.contains(valueToAddOrRemove)) {
            drawnNumbers.remove(valueToAddOrRemove);
        } else {
            drawnNumbers.add(valueToAddOrRemove);
        }
        updateUI();
    }

    public void deleteCurrentCarton(View v) {
        if (cartonHandler.getCartonNumber() == 1) {
            CharSequence text = "You need at least 1 carton.";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(this, text, duration);
            toast.show();
        } else {
            cartonHandler.removeCarton(currentCarton);
            currentCarton = cartonHandler.getCarton(0);
            updateUI();
        }
    }

    public void newGame(View v) {
        initializeData();
        updateUI();
    }

    // Others
    @Override
    protected void onResume() {
        super.onResume();

        getWindowManager().getDefaultDisplay().getMetrics(metrics);
    }


    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @return An int value to represent px equivalent to dp depending on device density
     */
    public int convertDpToPixel(int dp) {
        int px = (int) (dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    // Private

    private void initializeViewsAndData() {
        initializeData();

        initializeViews();
    }

    private void initializeData() {
        cartonHandler = new CartonHandler();
        cartonHandler.addNewCarton(3, 9);
        currentCarton = cartonHandler.getCarton(0);

        drawnNumbers = new ArrayList<>();

        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
    }

    private void initializeViews() {
        currentCartonGridLayout = findViewById(R.id.currentCarton);

        validateCurrentCartonBtn = findViewById(R.id.validateCartonBtn);

        textView_drawnNumbers = findViewById(R.id.dn);
        textView_sortedDrawnNumbers = findViewById(R.id.sdn);

        numberPicker = findViewById(R.id.numpicker);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(89);

        cartonsStatusTV = findViewById(R.id.cartonsStatusTV);

        otherCartonsGridLayout = findViewById(R.id.otherCartonsGL);

        buttonWidthPixel = convertDpToPixel(buttonWidthdp);
        int maxColumnNumber = metrics.widthPixels / buttonWidthPixel;
        otherCartonsGridLayout.setColumnCount(maxColumnNumber);
        ((Button) findViewById(R.id.carton1Btn)).setWidth(buttonWidthPixel);

        ((Button) findViewById(R.id.newCartonBtn)).setWidth(buttonWidthPixel);
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

    private void updateUI() {
        updateCurrentCartonDisplay(currentCarton);

        if (currentCarton.isModifiable()) {
            validateCurrentCartonBtn.setText("OK");
            enableAllCartonInputs(true);
        } else {
            validateCurrentCartonBtn.setText("Modify");
            enableAllCartonInputs(false);
        }
        updateDrawnNumbersLists();
        updateCartonStatuses();
        updateOtherCartonsGridLayout();

    }

    private void updateOtherCartonsGridLayout() {
        otherCartonsGridLayout.removeViews(0, otherCartonsGridLayout.getChildCount() - 1);
        for (int i = 0; i < cartonHandler.getCartonNumber(); i++) {
            addCartonButton(i);
        }
    }

    private void addCartonButton(int i) {
        View child = getLayoutInflater().inflate(R.layout.buttonnewcarton, null);
        String CartonIndex = String.valueOf(i + 1);
        Button childButton = child.findViewById(R.id.button);
        childButton.setText(CartonIndex);
        childButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeCurrentCarton(view);
            }
        });
        otherCartonsGridLayout.addView(child, i);
    }

    private void updateCurrentCartonDisplay(Carton carton) {
        id = new EditText[carton.getRowNumber()][carton.getColumnNumber()];

        initializeCurrentGridLayoutWithCarton(carton);

        updateCurrentGridLayoutValues(carton);

        updateCurrentGridLayoutDisplay();
    }

    private void updateCurrentGridLayoutDisplay() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                if (!id[i][j].getText().toString().isEmpty()) {
                    if (drawnNumbers.contains(Integer.valueOf(id[i][j].getText().toString()))) {
                        id[i][j].setBackgroundResource(R.drawable.cartonbox_drawn);
                    } else {
                        id[i][j].setBackgroundResource(R.drawable.cartonbox_non_empty);
                    }
                }
            }
        }
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
                id[i][j].setGravity(Gravity.CENTER);
                id[i][j].setTextSize(18);
                id[i][j].setBackgroundResource(R.drawable.cartonbox_empty);
                id[i][j].setWidth(metrics.widthPixels / currentCarton.getColumnNumber());
                id[i][j].setHeight(metrics.widthPixels / currentCarton.getColumnNumber());
                currentCartonGridLayout.addView(id[i][j]);
            }
        }
    }

    private void updateCartonStatuses() {
        cartonsStatusTV.setText(cartonHandler.getAllCartonsStatus(drawnNumbers));
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

    private void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    private void updateDrawnNumberCartonDisplay(int currentNumber) {
        //TODO  Parameter inside carton. So when carton  / update UI, it has the value and whether it has been drawn already
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

}
