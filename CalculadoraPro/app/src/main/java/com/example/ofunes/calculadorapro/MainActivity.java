package com.example.ofunes.calculadorapro;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {
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
        bDelete = findViewById(R.id.btnDelete); bDelete.setOnClickListener(this); bDelete.setOnLongClickListener(this);
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
            //En caso de que se haya pulsado un botón de número o el punto
            case R.id.btn0: case R.id.btn1: case R.id.btn2: case R.id.btn3: case R.id.btn4: case R.id.btn5: case R.id.btn6: case R.id.btn7: case R.id.btn8: case R.id.btn9: case R.id.btnDot:
                /*Solo funcionará el botón si ha escrito menos de 16 números, si ha escrito
                 15 números y hay un '-' al principio, o si el resultado ya está en pantalla */
                if(txtNumbers.getText().length() < 16 || (txtNumbers.getText().length() == 16 && txtNumbers.getText().toString().toCharArray()[0] == '-') || resultadoEnPantalla)
                {
                    //Si el resultado ya está en pantalla
                    if(resultadoEnPantalla)
                    {
                        //Se limpia lo que haya en ambos textBoxes, se cambia el botón a <- y se actualiza la variable
                        resetearTxtNumbers();
                        resetearTxtResults();
                        bDelete.setText(res.getString(R.string.delete));
                        resultadoEnPantalla = false;
                    }

                    //Dependiendo de qué botón numérico o el punto haya pulsado
                    switch(btnID)
                    {
                        //Se escribe en txtNumbers el número o punto pulsado
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
                }
            break;

            //Si se ha pulsado un botón de operación
            case R.id.btnAddition: case R.id.btnSubstraction: case R.id.btnDivision: case R.id.btnMultiplication:
                //Para que el botón reaccione, eliminamos la posibilidad de que haya únicamente símbolos no numéricos en txtNumbers
                if(!txtNumbers.getText().toString().equals("-") && !txtNumbers.getText().toString().equals("-.") && !txtNumbers.getText().toString().equals("."))
                {
                    //Para que el botón haga algo, no debe estar el resultado en pantalla ni debe haber
                    //nada en la parte de arriba de las operaciones
                    if (!resultadoEnPantalla && txtResults.getText().toString().equals(""))
                    {
                        //Se toma como n1 lo que haya escrito en txtNumbers y se coloca en txtResults
                        n1 = Double.parseDouble(txtNumbers.getText().toString());
                        txtResults.setText(txtNumbers.getText());

                        //Se resetea a 0 el texto que haya en txtNumbers
                        resetearTxtNumbers();

                        //Dependiendo de qué botón de operación haya pulsado
                        switch (btnID)
                        {
                            //Se toma en una variable qué operador ha sido pulsado
                            case R.id.btnAddition: operador = '+'; break;
                            case R.id.btnSubstraction: operador = '-'; break;
                            case R.id.btnDivision: operador = '/'; break;
                            case R.id.btnMultiplication: operador = '*'; break;
                        }

                        //Y se coloca junto al texto (que es n1) que había previamente en txtResults
                        txtResults.setText(txtResults.getText() + " " + operador + " ");
                    }
                    else if (resultadoEnPantalla) //En caso de que el resultado ya esté en pantalla
                    {
                        /* Se resetea la variable de resultadoEnPantalla ya que se van a realizar
                           operaciones sobre el resultado anterior
                         */
                        resultadoEnPantalla = false;
                        bDelete.setText(res.getString(R.string.delete));
                        txtResults.setText(txtNumbers.getText());
                        n1 = resultado;
                        resultado = 0;

                        //Se resete txtNumbers a 0
                        resetearTxtNumbers();

                        //Dependiendo de qué botón de operación haya pulsado
                        switch (btnID)
                        {
                            //Se toma en una variable qué operador ha sido pulsado
                            case R.id.btnAddition: operador = '+'; break;
                            case R.id.btnSubstraction: operador = '-'; break;
                            case R.id.btnDivision: operador = '/'; break;
                            case R.id.btnMultiplication: operador = '*'; break;
                        }

                        //Y se coloca junto al texto (que es n1) que había previamente en txtResults
                        txtResults.setText(txtResults.getText() + " " + operador + " ");
                    }
                    else if (operador != '0' && !resultadoEnPantalla) //Si ya se ha pulsado un operador pero no se ha pulsado el igual
                    {
                        //Se toma como n2 lo que haya en txtNumbers escrito
                        n2 = Double.parseDouble(txtNumbers.getText().toString());

                        //Se hace la operación pendiente con el operador que ya hubiese en la variable
                        switch (operador)
                        {
                            case '+': n1 = n1 + n2; break;
                            case '-': n1 = n1 - n2; break;
                            case '*': n1 = n1 * n2; break;
                            case '/': n1 = n1 / n2; break;
                        }

                        //Y se cambia al operador que se haya pulsado
                        switch (btnID)
                        {
                            case R.id.btnAddition: operador = '+'; break;
                            case R.id.btnSubstraction: operador = '-'; break;
                            case R.id.btnDivision: operador = '/'; break;
                            case R.id.btnMultiplication: operador = '*'; break;
                        }

                        //Finalmente se coloca en txtResults
                        txtResults.setText(String.valueOf(n1) + " " + operador + " ");

                        //Y se resetea txtNumbers a 0
                        resetearTxtNumbers();
                    }
                }
            break;

            //En caso de haber pulsado el botón de borrar
            case R.id.btnDelete:
                //En caso de que el resultado esté en pantalla
                if(resultadoEnPantalla)
                {
                    /*El botón tendrá como texto 'C', por lo que se resetea a '<-', se resetean los
                    TextBoxes y las variables */
                    bDelete.setText(res.getString(R.string.delete));
                    resultadoEnPantalla = false;
                    resetearTxtNumbers();
                    resetearTxtResults();
                    n1 = 0;
                    n2 = 0;
                    operador = '0';
                }
                else //Si el resultado no está en pantalla
                {
                    //Si txtNumbers solo tiene una longitud de 1, entonces se resetea txtNumbers a 0
                    if(txtNumbers.getText().toString().length() == 1)
                    {
                        resetearTxtNumbers();
                    }
                    else //Si no mide solo 1
                    {
                        //Se le quita el último número del string
                        txtNumbers.setText(txtNumbers.getText().toString().substring(0, txtNumbers.getText().toString().length() - 1));
                    }
                }

                break;

            //En caso de pulsar el botón de igual
            case R.id.btnEquals:
                //Se comprueba que en txtResults haya valores numéricos, si no, no entra y no hace nada
                if(!txtResults.getText().toString().equals("") && !txtNumbers.getText().toString().equals("-") && !txtNumbers.getText().toString().equals("."))
                {
                    //En caso de que el resultado ya esté en pantalla
                    if(resultadoEnPantalla)
                    {
                        //Se toma como n1 el resultado anterior y se coloca en txtResults junto al operador
                        n1 = resultado;
                        txtResults.setText(String.valueOf(n1)+" "+operador+" "+n2);
                    }
                    else //Si el resultado no está en pantalla
                    {
                        /*Se actualiza la variable correspondiente, se cambia el botón de borrar
                        de '<-' a 'C', se toma como n2 el texto que haya en txtNumbers, y se coloca
                        el texto que haya en txtNumbers tras lo que ya haya en txtResults
                         */
                        resultadoEnPantalla = true;
                        bDelete.setText(res.getString(R.string.deleteAll));
                        n2 = Double.parseDouble(txtNumbers.getText().toString());
                        txtResults.setText(txtResults.getText()+txtNumbers.getText().toString());
                    }

                    //Dependiendo de qué operador se eligiese, se hace la operación correspondiente
                    switch(operador)
                    {
                        case '+': resultado = n1 + n2; break;
                        case '-': resultado = n1 - n2; break;
                        case '*': resultado = n1 * n2; break;
                        case '/': resultado = n1 / n2; break;
                    }
                    //Y se coloca en txtNumbers el resulado
                    txtNumbers.setText(String.valueOf(resultado));
                }
                break;

            //En caso de pulsar el botón de '+/-'
            case R.id.btnPlusMinus:
                //En caso de que lo único que haya en txtNumbers sea un 0
                if(txtNumbers.getText().toString().equals("0"))
                    txtNumbers.setText("-"); //Se cambia el texto por un '-'
                //Si no, si el primer carácter de la cadena de txtNumbers no es '-'
                else if(txtNumbers.getText().toString().toCharArray()[0] != '-')
                    txtNumbers.setText("-"+txtNumbers.getText()); //Se le añade al principio
                //Si no, si lo único que hay es '-'
                else if(txtNumbers.getText().toString().equals("-"))
                    resetearTxtNumbers(); //Se resetea txtNumbers a '0'
                //Si no, entonces es que tiene '-' al principio del string
                else
                    txtNumbers.setText(txtNumbers.getText().toString().substring(1)); //Se elimina el primer carácter de la cadena
                break;
        }
    }

    @Override
    public boolean onLongClick(View view) {
        /*El botón tendrá como texto 'C', por lo que se resetea a '<-', se resetean los
         TextBoxes y las variables */
        bDelete.setText(res.getString(R.string.delete));
        resultadoEnPantalla = false;
        resetearTxtNumbers();
        resetearTxtResults();
        n1 = 0;
        n2 = 0;
        operador = '0';

        return false;
    }

    /**
     * Procedimiento que limpia el textBox de los resultados
     */
    private void resetearTxtResults()
    {
        txtResults.setText("");
    }

    /**
     * Procedimiento que limpia el textBox de los números y pone un 0
     */
    private void resetearTxtNumbers()
    {
        txtNumbers.setText("0");
    }

    /**
     * Función que escribe en pantalla en el textBox de los números el número que se le pase
     * En caso de que solo hay un 0 en pantalla, escribe el número, si no, lo añade al string.
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
