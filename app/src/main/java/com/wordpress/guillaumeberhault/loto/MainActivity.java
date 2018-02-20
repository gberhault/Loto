package com.wordpress.guillaumeberhault.loto;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    Vector<Vector<EditText>> cartonInputMatrix;

    Button validate;

    Carton carton;

    private int rowNumber, columnNumber;

    private View[][] id;
    private Vector<Integer> drawnNumbers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        carton = new Carton(3, 9);
        cartonInputMatrix = new Vector<>();
        id = new View[3][9];

        initializeIDsCarton();

        for (int i = 0; i < 3; i++) {
            cartonInputMatrix.add(new Vector<EditText>());
            for (int j = 0; j < 9; j++) {
                cartonInputMatrix.get(i).add((EditText) id[i][j]);
            }
        }

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
        if (drawnNumbers == null)
            drawnNumbers = new Vector<>();

        Button b = (Button) v;
        Integer valueToAddOrRemove = Integer.valueOf(b.getText().toString());
        // Already drawn. Remove it.
        if (drawnNumbers.contains(valueToAddOrRemove)) {
            drawnNumbers.remove(valueToAddOrRemove);
            b.setTextColor(Color.BLACK);
            System.out.println("Remove number " + b.getText().toString());
        } else {
            drawnNumbers.add(valueToAddOrRemove);
            b.setTextColor(Color.RED);
            System.out.println("Add number " + b.getText().toString());
        }
    }

    private void initializeIDsCarton() {

        id[0][0] = findViewById(R.id.c11);
        id[0][1] = findViewById(R.id.c12);
        id[0][2] = findViewById(R.id.c13);
        id[0][3] = findViewById(R.id.c14);
        id[0][4] = findViewById(R.id.c15);
        id[0][5] = findViewById(R.id.c16);
        id[0][6] = findViewById(R.id.c17);
        id[0][7] = findViewById(R.id.c18);
        id[0][8] = findViewById(R.id.c19);

        id[1][0] = findViewById(R.id.c21);
        id[1][1] = findViewById(R.id.c22);
        id[1][2] = findViewById(R.id.c23);
        id[1][3] = findViewById(R.id.c24);
        id[1][4] = findViewById(R.id.c25);
        id[1][5] = findViewById(R.id.c26);
        id[1][6] = findViewById(R.id.c27);
        id[1][7] = findViewById(R.id.c28);
        id[1][8] = findViewById(R.id.c29);

        id[2][0] = findViewById(R.id.c31);
        id[2][1] = findViewById(R.id.c32);
        id[2][2] = findViewById(R.id.c33);
        id[2][3] = findViewById(R.id.c34);
        id[2][4] = findViewById(R.id.c35);
        id[2][5] = findViewById(R.id.c36);
        id[2][6] = findViewById(R.id.c37);
        id[2][7] = findViewById(R.id.c38);
        id[2][8] = findViewById(R.id.c39);
    }
}
