package com.wordpress.guillaumeberhault.loto;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String TOTAL_CARTON_NUMBER = "TOTAL_CARTON_NUMBER";
    public static final String DRAWN_NUMBERS = "DRAWN_NUMBERS";
    private final int buttonWidthdp = 50;
    private String minStr = "1";
    private String maxStr = "89";
    private int buttonWidthPixel;
    private Button validateCurrentCartonBtn;
    private NumberPicker numberPicker;
    private TextView textView_drawnNumbers, drawnNumbers_title;
    private EditText[][] id;

    private ArrayList<Integer> drawnNumbers;
    private GridLayout currentCartonGridLayout;
    private Carton currentCarton;
    private DisplayMetrics metrics;

    private CartonHandler cartonHandler;
    private TextView cartonsStatusTV;
    private GridLayout otherCartonsGridLayout;
    private boolean drawnNumbersToBeDisplayed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initializeViewsAndData();

        updateCurrentCartonDisplay(currentCarton);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer;
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent settingsActivity = new Intent(this, SettingsActivity.class);
            startActivity(settingsActivity);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_about) {
            DialogFragment newFragment = new AboutFragment();
            newFragment.show(getSupportFragmentManager(), "missiles");
        } else if (id == R.id.nav_newgame) {
            newGame();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
        updateUI();
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

    private void newGame() {
        initializeData();
        updateUI();
    }

    // Others
    @Override
    protected void onResume() {
        super.onResume();

        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        updateUI();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("TOTAL_CARTON_NUMBER", cartonHandler.getCartonNumber());
        outState.putIntegerArrayList("DRAWN_NUMBERS", drawnNumbers);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        for (int i = 0; i < savedInstanceState.getInt(TOTAL_CARTON_NUMBER); i++) {
            cartonHandler.addNewCarton(3, 9);
        }
        drawnNumbers = savedInstanceState.getIntegerArrayList(DRAWN_NUMBERS);
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
        cartonHandler = new CartonHandler(this);
        cartonHandler.addNewCarton(3, 9);
        currentCarton = cartonHandler.getCarton(0);

        drawnNumbers = new ArrayList<>();

        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        this.drawnNumbersToBeDisplayed = true;
    }

    private void initializeViews() {
        currentCartonGridLayout = findViewById(R.id.currentCarton);

        validateCurrentCartonBtn = findViewById(R.id.validateCartonBtn);

        drawnNumbers_title = findViewById(R.id.dnTitle);
        textView_drawnNumbers = findViewById(R.id.dn);

        numberPicker = findViewById(R.id.numpicker);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(89);

        cartonsStatusTV = findViewById(R.id.cartonsStatusTV);

        otherCartonsGridLayout = findViewById(R.id.otherCartonsGL);

        buttonWidthPixel = convertDpToPixel(buttonWidthdp);
        int maxColumnNumber = (int) (metrics.widthPixels * 0.75) / buttonWidthPixel;
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
                        id[i][j].setBackgroundResource(R.drawable.whitebox_black_borders);
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
                id[i][j].setFilters(new InputFilter[]{new InputFilterMinMax(minStr, maxStr)});
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
            if (drawnNumbersToBeDisplayed)
                updateDrawnNumberDisplay();
            else
                updateSortedDrawnNumberDisplay();
        }
    }

    private void updateSortedDrawnNumberDisplay() {
        ArrayList<Integer> sortedDrawnNumbers = (ArrayList<Integer>) drawnNumbers.clone();
        Collections.sort(sortedDrawnNumbers);
        String sortedDrawnNumbersString = generateStringFromList(sortedDrawnNumbers);
        textView_drawnNumbers.setText(sortedDrawnNumbersString);

        drawnNumbers_title.setText(R.string.sorted_drawn_numbers);
    }

    private void updateDrawnNumberDisplay() {
        String drawnNumberString = generateStringFromList(drawnNumbers);
        textView_drawnNumbers.setText(drawnNumberString);

        drawnNumbers_title.setText(R.string.drawn_numbers);
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
        // Check if no view has focus:
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void changeDrawnNumberDisplay(View view) {
        drawnNumbersToBeDisplayed = !drawnNumbersToBeDisplayed;
        updateUI();
    }
}
