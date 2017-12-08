package com.example.mahbub.simplecalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import 	java.util.regex.Pattern;
import  java.util.regex.Matcher;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView display;
    Button btnOne, btnTwo, btnThree, btnFour, btnFive, btnSix, btnSeven, btnEight, btnNine, btnZero;
    Button buttonPlus, buttonMinus, buttonMulti, buttonDivide, buttonDot, buttonEqual, buttonClear, buttonBack;

    double result = 0.0;
    boolean firstOperation = true;
    String operation = "";
    String scOperationPoint = "";
    boolean lockOperator = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.text_view_display);

        btnOne = findViewById(R.id.button_one);
        btnTwo = findViewById(R.id.button_two);
        btnThree = findViewById(R.id.button_three);
        btnFour = findViewById(R.id.button_four);
        btnFive = findViewById(R.id.button_five);
        btnSix = findViewById(R.id.button_six);
        btnSeven = findViewById(R.id.button_seven);
        btnEight = findViewById(R.id.button_eight);
        btnNine = findViewById(R.id.button_nine);
        btnZero = findViewById(R.id.button_zero);

        buttonPlus = findViewById(R.id.button_plus);
        buttonMinus = findViewById(R.id.button_minus);
        buttonMulti = findViewById(R.id.button_multi);
        buttonDivide = findViewById(R.id.button_division);
        buttonDot = findViewById(R.id.button_dot);
        buttonEqual = findViewById(R.id.button_equal);
        buttonClear = findViewById(R.id.button_clear);
        buttonBack = findViewById(R.id.button_back_arrow);

        btnOne.setOnClickListener(this);
        btnTwo.setOnClickListener(this);
        btnThree.setOnClickListener(this);
        btnFour.setOnClickListener(this);
        btnFive.setOnClickListener(this);
        btnSix.setOnClickListener(this);
        btnSeven.setOnClickListener(this);
        btnEight.setOnClickListener(this);
        btnNine.setOnClickListener(this);
        btnZero.setOnClickListener(this);

        buttonPlus.setOnClickListener(this);
        buttonMinus.setOnClickListener(this);
        buttonMulti.setOnClickListener(this);
        buttonDivide.setOnClickListener(this);
        buttonDot.setOnClickListener(this);
        buttonEqual.setOnClickListener(this);
        buttonClear.setOnClickListener(this);
        buttonBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == btnOne){
            addToDisplay("1");
        } else if( view == btnTwo){
            addToDisplay("2");
        } else if( view == btnThree){
            addToDisplay("3");
        } else if( view == btnFour){
            addToDisplay("4");
        } else if( view == btnFive){
            addToDisplay("5");
        } else if( view == btnSix){
            addToDisplay("6");
        } else if( view == btnSeven){
            addToDisplay("7");
        } else if( view == btnEight){
            addToDisplay("8");
        } else if( view == btnNine){
            addToDisplay("9");
        } else if( view == btnZero){
            addToDisplay("0");
        } else if( view == buttonBack){
            backClickAction();
        } else if( view == buttonClear){
            refresh();
        } else if( view == buttonPlus){
            operatorHandle("+");
        } else if( view == buttonMinus){
            operatorHandle("-");
        } else if( view == buttonMulti){
            operatorHandle("*");
        } else if( view == buttonDivide){
            operatorHandle("÷");
        } else if( view == buttonDot){
            validationForDot();
        } else if( view == buttonEqual){
            equalClickAction();
        }
    }



    public void validationForDot(){
        String current = display.getText().toString().trim();
        Pattern p = Pattern.compile("(\\d+(?:\\.\\d+)?)");
        Matcher m = p.matcher(current);

        String last = "";
        while (m.find()){
            last = m.group(1);
//            Toast.makeText(MainActivity.this, last, Toast.LENGTH_SHORT).show();
        }
        if (last.contains(".") || current.endsWith(".")) {

        } else {
            addToDisplay(".");
        }
    }

    private void backClickAction() {
        if (display.getText().length() > 0 && operatorHandleValidity()){
            String current = display.getText().toString();
            display.setText(method(current));
        } else {
            Toast.makeText(MainActivity.this, "Please Clear All", Toast.LENGTH_SHORT).show();
        }
    }
    public String method(String str) {
        if (str != null && str.length() > 0) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    public void addToDisplay(String value)
    {
        lockOperator = false;
        if (operation == "=")
        {
            refresh();
            display.setText(String.valueOf(result));
        }
        else if (display.getText() != "0" || value == ".")
        {
            display.setText(display.getText() + value);
        }
        else
        {
            display.setText(value);
        }
    }

    public void refresh()
    {
        display.clearComposingText();
        display.setText("0");
        result = 0.0;
        firstOperation = true;
        operation = "";
        scOperationPoint = "";
        lockOperator = false;
    }

    public void equalClickAction(){
        completeOperation();
        display.setText(display.getText() + "\n" + String.valueOf(result));
        operation = "=";
    }


    public void operatorHandle(String op)
    {
        if (operation == "=")
        {
            display.setText("");
            display.setText(String.valueOf(result) + op);
            operation = op;
        }
        else if (operatorHandleValidity())
        {
            if (firstOperation)
            {
                String current = display.getText().toString().trim();
                Pattern p = Pattern.compile("(\\d+(?:\\.\\d+)?)");
                Matcher m = p.matcher(current);

                String last = "";
                while (m.find()){
                    last = m.group(1);
//                      Toast.makeText(MainActivity.this, last, Toast.LENGTH_SHORT).show();
                }
                double secondOperand = Double.valueOf(last);
                result = result + secondOperand;
                display.setText(display.getText() + op);
                firstOperation = false;
                operation = op;
            }
            else
            {
                completeOperation();
                operation = op;
                display.setText(display.getText() + op);
            }
        }
    }

    public Boolean operatorHandleValidity()
    {
        if (display.getText().toString().equals("0") || display.getText().toString().endsWith("+") || display.getText().toString().endsWith("-")
                || display.getText().toString().endsWith("*") || display.getText().toString().endsWith("÷") || display.getText().toString().endsWith("/")
                || lockOperator ) {
            return false;
        }
        return true;
    }

    public void completeOperation()
    {
        String current = display.getText().toString().trim();
        Pattern p = Pattern.compile("(\\d+(?:\\.\\d+)?)");
        Matcher m = p.matcher(current);

        String last = "";
        while (m.find()){
            last = m.group(1);
//                      Toast.makeText(MainActivity.this, last, Toast.LENGTH_SHORT).show();
        }
        double secondOperand = Double.valueOf(last);

        if (operation == "+")
        {
            result = result + secondOperand;
        }
        else if (operation == "-")
        {
            result = result - secondOperand;
        }
        else if (operation == "*")
        {
            result = result * secondOperand;
        }
        else
        {
            result = result / secondOperand;
        }
    }

}
