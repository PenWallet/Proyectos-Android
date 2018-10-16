package com.example.ofunes.calculadora;

import android.app.Activity;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnResult;
    TextView txtResult;
    EditText txtN1, txtN2;
    RadioButton rBtnSumar, rBtnRestar, rBtnMult, rBtnDiv;
    double n1, n2;
    Resources res;
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnResult = (Button)findViewById(R.id.btnCalcular);
        txtResult = (TextView)findViewById(R.id.txtResult);
        txtN1 = (EditText) findViewById(R.id.txtN1);
        txtN2 = (EditText)findViewById(R.id.txtN2);
        rBtnSumar = (RadioButton)findViewById(R.id.rBtnSumar);
        rBtnRestar = (RadioButton)findViewById(R.id.rBtnRestar);
        rBtnMult = (RadioButton)findViewById(R.id.rBtnMult);
        rBtnDiv = (RadioButton)findViewById(R.id.rBtnDiv);
        res = getResources();
        btnResult.setOnClickListener(this);
        rBtnSumar.setChecked(true);
        toast = Toast.makeText(this,"", Toast.LENGTH_SHORT);
    }


    @Override
    public void onClick(View view) {
        hideKeyboard(this);
        if(txtN1.getText().toString().matches(""))
            showToast(0);
        else if(txtN2.getText().toString().matches(""))
            showToast(0);
        else
        {
            n1 = Double.parseDouble(txtN1.getText().toString());
            n2 = Double.parseDouble(txtN2.getText().toString());
            if(rBtnSumar.isChecked())
                txtResult.setText(String.valueOf(n1+n2));
            else if(rBtnRestar.isChecked())
                txtResult.setText(String.valueOf(n1-n2));
            else if(rBtnMult.isChecked())
                txtResult.setText(String.valueOf(n1*n2));
            else if(rBtnDiv.isChecked())
            {
                if(n2 == 0)
                    showToast(1);
                else
                    txtResult.setText(String.valueOf(n1/n2));
            }

        }
    }

    public void showToast(int id)
    {
        switch(id)
        {
            case 0:
                toast.setText(res.getString(R.string.txtInputError));
                toast.show();
                break;
            case 1:
                toast.setText(res.getString(R.string.txtInputDivisionError));
                toast.show();
                break;
        }
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
