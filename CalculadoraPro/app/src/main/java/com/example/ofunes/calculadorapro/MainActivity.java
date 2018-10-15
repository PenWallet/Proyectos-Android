package com.example.ofunes.calculadorapro;

import android.content.res.Resources;
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
    Resources res;
    boolean resultadoEnPantalla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        res = getResources();
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

        resultadoEnPantalla = false;
    }


    @Override
    public void onClick(View view) {
        int btnID = view.getId();

        switch(btnID)
        {
            case R.id.btn0: case R.id.btn1: case R.id.btn2: case R.id.btn3: case R.id.btn4: case R.id.btn5: case R.id.btn6: case R.id.btn7: case R.id.btn8: case R.id.btn9:
                if(resultadoEnPantalla)
                {
                    txtNumbers.setText("");
                    txtResults.setText("");
                    bDelete.setText(res.getString(R.string.delete));
                }

                switch(btnID)
                {
                    case R.id.btn0:
                        txtNumbers.setText(txtNumbers.getText()+"0");
                        break;

                    case R.id.btn1:
                        txtNumbers.setText(txtNumbers.getText()+"1");
                        break;

                    case R.id.btn2:
                        txtNumbers.setText(txtNumbers.getText()+"2");
                        break;

                    case R.id.btn3:
                        txtNumbers.setText(txtNumbers.getText()+"3");
                        break;

                    case R.id.btn4:
                        txtNumbers.setText(txtNumbers.getText()+"4");
                        break;

                    case R.id.btn5:
                        txtNumbers.setText(txtNumbers.getText()+"5");
                        break;

                    case R.id.btn6:
                        txtNumbers.setText(txtNumbers.getText()+"6");
                        break;

                    case R.id.btn7:
                        txtNumbers.setText(txtNumbers.getText()+"7");
                        break;

                    case R.id.btn8:
                        txtNumbers.setText(txtNumbers.getText()+"8");
                        break;

                    case R.id.btn9:
                        txtNumbers.setText(txtNumbers.getText()+"9");
                        break;
                }
            break;

            case R.id.btnAddition: case R.id.btnSubstraction: case R.id.btnDivision: case R.id.btnMultiplication:
                if(!resultadoEnPantalla && txtResults.getText().toString().equals(""))
                {
                    if(txtNumbers.getText().toString().equals(""))
                        txtResults.setText("0");
                    else
                        txtResults.setText(txtNumbers.getText());

                    txtNumbers.setText("");

                    switch(btnID)
                    {
                        case R.id.btnAddition:
                            txtResults.setText(txtResults.getText()+" + ");
                            break;

                        case R.id.btnSubstraction:
                            txtResults.setText(txtResults.getText()+" - ");
                            break;

                        case R.id.btnDivision:
                            txtResults.setText(txtResults.getText()+" / ");
                            break;

                        case R.id.btnMultiplication:
                            txtResults.setText(txtResults.getText()+" * ");
                            break;
                    }
                }
            break;

            case R.id.btnDelete:
                txtNumbers.setText("");
                txtResults.setText("");
                break;

            case R.id.btnEquals:
                //Codigo
                break;

        }
    }
}
