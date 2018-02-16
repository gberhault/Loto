package com.wordpress.guillaumeberhault.loto;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    EditText c11, c12, c13, c14, c15, c16, c17, c18, c19, c21, c22, c23, c24, c25, c26, c27, c28, c29, c31, c32, c33, c34, c35, c36, c37, c38, c39;
    Vector<Vector<EditText>> inputCarton;

    Button b1,b2,b3,b4,b5,b6,b7,b8,b9,b10;

    Button validate;

    Carton carton;
    private View[][] id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        carton = new Carton(3, 9);
        inputCarton = new Vector<>();
        id = new View[3][9];

        initializeIDsCarton();

        initializeButtons();

        for (int i = 0; i < 3; i++) {
            inputCarton.add(new Vector<EditText>());
            for (int j = 0; j < 9; j++) {
                inputCarton.get(i).add((EditText) id[i][j]);
            }
        }

        validate = findViewById(R.id.validate);
        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                carton.display();
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 9; j++) {
                        String currentValue = ((EditText) id[i][j]).getText().toString();
                        if (!currentValue.isEmpty()) {
                            carton.addToRow(i, Integer.valueOf(currentValue));
                        }
                    }
                }
                carton.display();
            }
        });

    }

    private void initializeButtons() {
        b1 = (Button) findViewById(R.id.b1);
        b2 = (Button) findViewById(R.id.b2);
        b3 = (Button) findViewById(R.id.b3);
        b4 = (Button) findViewById(R.id.b4);
        b5 = (Button) findViewById(R.id.b5);
        b6 = (Button) findViewById(R.id.b6);
        b7 = (Button) findViewById(R.id.b7);
        b8 = (Button) findViewById(R.id.b8);
        b9 = (Button) findViewById(R.id.b9);
        b10 = (Button) findViewById(R.id.b10);
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
