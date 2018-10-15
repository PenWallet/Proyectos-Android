package com.example.ofunes.calculadorapro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button b1, b2, b3, b4, b5, b6, b7, b8, b9, b0, bEquals, bAddition, bSubstraction, bDivision, bMultiplication, bDelete;
    TextView txtResults;
    EditText txtNumbers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1 = findViewById(R.id.btn1); b1.setOnClickListener(this);
        b2 = findViewById(R.id.btn2); b2.setOnClickListener(this);
        b3 = findViewById(R.id.btn3); b3.setOnClickListener(this);
        b4 = findViewById(R.id.btn4); b4.setOnClickListener(this);
        b5 = findViewById(R.id.btn5); b5.setOnClickListener(this);
        b6 = findViewById(R.id.btn6); b6.setOnClickListener(this);
        b7 = findViewById(R.id.btn7); b7.setOnClickListener(this);
        b8 = findViewById(R.id.btn8); b8.setOnClickListener(this);
        b9 = findViewById(R.id.btn9); b9.setOnClickListener(this);
        b0 = findViewById(R.id.btn0); b0.setOnClickListener(this);
        bAddition = findViewById(R.id.btnAddition); bAddition.setOnClickListener(this);
        bSubstraction = findViewById(R.id.btnSubstraction); bSubstraction.setOnClickListener(this);
        bDivision = findViewById(R.id.btnDivision); bDivision.setOnClickListener(this);
        bMultiplication = findViewById(R.id.btnMultiplication); bMultiplication.setOnClickListener(this);
        bEquals = findViewById(R.id.btnEquals); bEquals.setOnClickListener(this);
        bDelete = findViewById(R.id.btnDelete); bDelete.setOnClickListener(this);

        txtResults = findViewById(R.id.txtResults);
        txtNumbers = findViewById(R.id.txtNumbers);
    }


    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.btn0: case R.id.btn1: case R.id.btn2: case R.id.btn3: case R.id.btn4: case R.id.btn5: case R.id.btn6: case R.id.btn7: case R.id.btn8: case R.id.btn9:
                //Codigo
            break;

            case R.id.btnAddition:
                //Codigo
            break;

            case R.id.btnSubstraction:
                //Codigo
                break;

            case R.id.btnDivision:
                //Codigo
                break;

            case R.id.btnMultiplication:
                //Codigo
                break;

            case R.id.btnDelete:
                //Codigo
                break;

            case R.id.btnEquals:
                //Codigo
                break;

        }
    }
}
