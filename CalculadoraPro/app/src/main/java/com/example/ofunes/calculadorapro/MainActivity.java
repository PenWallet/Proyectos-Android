package com.example.ofunes.calculadorapro;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button b1, b2, b3, b4, b5, b6, b7, b8, b9, b0, bEquals, bAddition, bSubstraction, bDivision, bMultiplication, bDelete, bDot, bPlusMinus;
    TextView txtResults;
    EditText txtNumbers;
    Resources res;
    boolean resultadoEnPantalla;
    int btnID;
    double n1, n2, resultado = 0;
    char operador = '0'; //Contiene el operador que se va a usar para operar. En caso de que no se haya pulsado aún, contiene '0'

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
        bDot = findViewById(R.id.btnDot); bDot.setOnClickListener(this);
        bAddition = findViewById(R.id.btnAddition); bAddition.setOnClickListener(this);
        bSubstraction = findViewById(R.id.btnSubstraction); bSubstraction.setOnClickListener(this);
        bDivision = findViewById(R.id.btnDivision); bDivision.setOnClickListener(this);
        bMultiplication = findViewById(R.id.btnMultiplication); bMultiplication.setOnClickListener(this);
        bEquals = findViewById(R.id.btnEquals); bEquals.setOnClickListener(this);
        bDelete = findViewById(R.id.btnDelete); bDelete.setOnClickListener(this);
        bPlusMinus = findViewById(R.id.btnPlusMinus); bPlusMinus.setOnClickListener(this);

        txtResults = findViewById(R.id.txtResults);
        txtNumbers = findViewById(R.id.txtNumbers);

        resultadoEnPantalla = false;
    }


    @Override
    public void onClick(View view) {
        btnID = view.getId();

        switch(btnID)
        {
            case R.id.btn0: case R.id.btn1: case R.id.btn2: case R.id.btn3: case R.id.btn4: case R.id.btn5: case R.id.btn6: case R.id.btn7: case R.id.btn8: case R.id.btn9: case R.id.btnDot:
                if(resultadoEnPantalla)
                {
                    resetearTxtNumbers();
                    resetearTxtResults();
                    bDelete.setText(res.getString(R.string.delete));
                    resultadoEnPantalla = false;
                }

                switch(btnID)
                {
                    case R.id.btn0: escribirNumero(0); break;

                    case R.id.btn1: escribirNumero(1); break;

                    case R.id.btn2: escribirNumero(2); break;

                    case R.id.btn3: escribirNumero(3); break;

                    case R.id.btn4: escribirNumero(4); break;

                    case R.id.btn5: escribirNumero(5); break;

                    case R.id.btn6: escribirNumero(6); break;

                    case R.id.btn7: escribirNumero(7); break;

                    case R.id.btn8: escribirNumero(8); break;

                    case R.id.btn9: escribirNumero(9); break;

                    case R.id.btnDot:
                        if(txtNumbers.getText().toString().equals("0"))
                            txtNumbers.setText("0.");
                        else if(!txtNumbers.getText().toString().contains("."))
                            txtNumbers.setText(txtNumbers.getText()+".");
                        break;
                }
            break;

            case R.id.btnAddition: case R.id.btnSubstraction: case R.id.btnDivision: case R.id.btnMultiplication:
                //Para que el botón haga algo, no debe estar el resultado en pantalla ni debe haber
                //nada en la parte de arriba de las operaciones
                if(!txtNumbers.getText().toString().equals("-") && !txtNumbers.getText().toString().equals("-.") && !txtNumbers.getText().toString().equals("."))
                {
                    if (!resultadoEnPantalla && txtResults.getText().toString().equals("")) {
                        if (txtNumbers.getText().toString().equals("")) {
                            txtResults.setText("0");
                            n1 = 0;
                        } else {
                            n1 = Double.parseDouble(txtNumbers.getText().toString());
                            txtResults.setText(txtNumbers.getText());
                        }

                        resetearTxtNumbers();

                        switch (btnID) {
                            case R.id.btnAddition:
                                operador = '+';
                                break;

                            case R.id.btnSubstraction:
                                operador = '-';
                                break;

                            case R.id.btnDivision:
                                operador = '/';
                                break;

                            case R.id.btnMultiplication:
                                operador = '*';
                                break;
                        }

                        txtResults.setText(txtResults.getText() + " " + operador + " ");
                    } else if (resultadoEnPantalla) {
                        resultadoEnPantalla = false;
                        bDelete.setText(res.getString(R.string.delete));
                        txtResults.setText(txtNumbers.getText());
                        n1 = resultado;
                        resultado = 0;

                        resetearTxtNumbers();

                        switch (btnID) {
                            case R.id.btnAddition:
                                operador = '+';
                                break;

                            case R.id.btnSubstraction:
                                operador = '-';
                                break;

                            case R.id.btnDivision:
                                operador = '/';
                                break;

                            case R.id.btnMultiplication:
                                operador = '*';
                                break;
                        }

                        txtResults.setText(txtResults.getText() + " " + operador + " ");
                    } else if (operador != '0' && !resultadoEnPantalla) {
                        n2 = Double.parseDouble(txtNumbers.getText().toString());

                        switch (operador) {
                            case '+':
                                n1 = n1 + n2;
                                break;
                            case '-':
                                n1 = n1 - n2;
                                break;
                            case '*':
                                n1 = n1 * n2;
                                break;
                            case '/':
                                n1 = n1 / n2;
                                break;
                        }

                        switch (btnID) {
                            case R.id.btnAddition:
                                operador = '+';
                                break;
                            case R.id.btnSubstraction:
                                operador = '-';
                                break;
                            case R.id.btnDivision:
                                operador = '/';
                                break;
                            case R.id.btnMultiplication:
                                operador = '*';
                                break;
                        }

                        txtResults.setText(String.valueOf(n1) + " " + operador + " ");

                        resetearTxtNumbers();
                    }
                }
            break;

            case R.id.btnDelete:
                if(resultadoEnPantalla)
                {
                    bDelete.setText(res.getString(R.string.delete));
                    resultadoEnPantalla = false;
                    resetearTxtNumbers();
                    resetearTxtResults();
                    n1 = 0;
                    n2 = 0;
                    operador = '0';
                }
                else
                {
                    if(txtNumbers.getText().toString().length() == 1)
                    {
                        resetearTxtNumbers();
                    }
                    else
                    {
                        //Se le quita el último número del string
                        txtNumbers.setText(txtNumbers.getText().toString().substring(0, txtNumbers.getText().toString().length() - 1));
                    }
                }

                break;

            case R.id.btnEquals:
                if(!txtResults.getText().toString().equals("") && !txtNumbers.getText().toString().equals("-"))
                {
                    if(resultadoEnPantalla)
                    {
                        n1 = resultado;
                        txtResults.setText(String.valueOf(n1)+" "+operador+" "+n2);
                    }
                    else
                    {
                        resultadoEnPantalla = true;
                        bDelete.setText(res.getString(R.string.deleteAll));
                        n2 = Double.parseDouble(txtNumbers.getText().toString());
                        txtResults.setText(txtResults.getText()+txtNumbers.getText().toString());
                    }

                    switch(operador)
                    {
                        case '+': resultado = n1 + n2; break;
                        case '-': resultado = n1 - n2; break;
                        case '*': resultado = n1 * n2; break;
                        case '/': resultado = n1 / n2; break;
                    }
                    txtNumbers.setText(String.valueOf(resultado));
                }
                break;

            case R.id.btnPlusMinus:
                if(txtNumbers.getText().toString().equals("0"))
                    txtNumbers.setText("-");
                else if(txtNumbers.getText().toString().toCharArray()[0] != '-')
                    txtNumbers.setText("-"+txtNumbers.getText());
                else if(txtNumbers.getText().toString().equals("-"))
                    resetearTxtNumbers();
                else
                    txtNumbers.setText(txtNumbers.getText().toString().substring(1));


                break;
        }
    }

    /**
     * Procedimiento que limpia el textBox de los resultados
     */
    private void resetearTxtResults()
    {
        txtResults.setText("");
    }

    /**
     * Procedimiento que limpia el textBox de los números
     */
    private void resetearTxtNumbers()
    {
        txtNumbers.setText("0");
    }

    /**
     * Función que escribe en pantalla en el textBox de los números el número que se le pase
     *
     * @param n Número a escribir en pantalla
     */
    public void escribirNumero(int n)
    {
        if(txtNumbers.getText().toString().equals("0"))
            txtNumbers.setText(String.valueOf(n));
        else
            txtNumbers.setText(txtNumbers.getText()+String.valueOf(n));
    }
}
