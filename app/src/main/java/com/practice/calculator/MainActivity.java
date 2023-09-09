package com.practice.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    Button btnClear, btnClearAll, btnPercentage, btnDivide, btn7, btn8, btn9, btnMultiply, btn6, btn5, btn4, btnSubtract, btn3, btn2, btn1, btnAdd, btnZero, btnDoubleZero, btnDecimal, btnEquals;

    LinearLayout ll;
    TextView tvUserInput, tvCalculationOutput;

    CalculatorAction calculatorAction;

    Boolean isResultCalculated = false;

    String firstNumber = "", secondNumber = "", outputText = "", inputText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvUserInput = findViewById(R.id.tv_user_input);
        tvCalculationOutput = findViewById(R.id.tv_calculation_output);

        btnClear = findViewById(R.id.btn_clear);
        btnClearAll = findViewById(R.id.btn_clear_all);
        btnPercentage = findViewById(R.id.btn_percentage);
        btnDivide = findViewById(R.id.btn_divide);

        btnClear.setOnClickListener(this);
        btnClearAll.setOnClickListener(this);
        btnPercentage.setOnClickListener(this);
        btnDivide.setOnClickListener(this);

        btn7 = findViewById(R.id.btn_seven);
        btn8 = findViewById(R.id.btn_eight);
        btn9 = findViewById(R.id.btn_nine);
        btnMultiply = findViewById(R.id.btn_multiply);

        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btnMultiply.setOnClickListener(this);

        btn6 = findViewById(R.id.btn_six);
        btn5 = findViewById(R.id.btn_five);
        btn4 = findViewById(R.id.btn_four);
        btnSubtract = findViewById(R.id.btn_minus);

        btn6.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btnSubtract.setOnClickListener(this);


        btn3 = findViewById(R.id.btn_three);
        btn2 = findViewById(R.id.btn_two);
        btn1 = findViewById(R.id.btn_one);
        btnAdd = findViewById(R.id.btn_plus);

        btn3.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btnAdd.setOnClickListener(this);

        btnZero = findViewById(R.id.btn_zero);
        btnDoubleZero = findViewById(R.id.btn_double_zero);
        btnDecimal = findViewById(R.id.btn_decimal);
        btnEquals = findViewById(R.id.btn_equals);

        btnZero.setOnClickListener(this);
        btnDoubleZero.setOnClickListener(this);
        btnDecimal.setOnClickListener(this);
        btnEquals.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        Button clickedButton = (Button) view;
        switch (view.getId()) {
            case R.id.btn_one:
            case R.id.btn_two:
            case R.id.btn_three:
            case R.id.btn_four:
            case R.id.btn_five:
            case R.id.btn_six:
            case R.id.btn_seven:
            case R.id.btn_eight:
            case R.id.btn_nine:
            case R.id.btn_zero:
            case R.id.btn_double_zero:
            case R.id.btn_decimal: {
                if (isResultCalculated) {
                    isResultCalculated = false;
                    clearAll();
                }

                if (calculatorAction == null) {
                    firstNumber = firstNumber + clickedButton.getText();
                } else {
                    secondNumber = secondNumber + clickedButton.getText();

                }
                inputText = inputText + clickedButton.getText();
                setInputText();
                break;
            }

            case R.id.btn_plus: {
                performCalculatorAction(CalculatorAction.ADD);
                break;
            }
            case R.id.btn_minus: {
                performCalculatorAction(CalculatorAction.SUBTRACT);
                break;
            }
            case R.id.btn_multiply: {
                performCalculatorAction(CalculatorAction.MULTIPLY);
                break;
            }
            case R.id.btn_divide: {
                performCalculatorAction(CalculatorAction.DIVIDE);
                break;
            }
            case R.id.btn_equals: {
                if (firstNumber.isEmpty() || secondNumber.isEmpty()) return;
                calculateResult();
                break;

            }

            case R.id.btn_clear_all: {
                if (inputText.isEmpty()) return;
                clearAll();
                break;
            }

            case R.id.btn_clear: {
                if (inputText.isEmpty()) return;
                clearOutputText();
                if (!secondNumber.isEmpty()) {
                    secondNumber = secondNumber.replaceFirst(".$", "");
                    inputText = inputText.replaceFirst(".$", "");
                } else if (calculatorAction != null) {
                    calculatorAction = null;
                    inputText = inputText.replaceFirst(".$", "");
                } else {
                    firstNumber = firstNumber.replaceFirst(".$", "");
                    inputText = inputText.replaceFirst(".$", "");
                }
                setInputText();
                break;
            }

            case R.id.btn_percentage: {
                if (inputText.isEmpty()) return;
                if (calculatorAction == null) {
                    float result = getFirstNumber() / 100;
                    firstNumber = String.valueOf(result);
                    inputText = firstNumber;
                    setInputText();
                } else {
                    if (secondNumber.isEmpty()) return;
                    float result = getSecondNumber() / 100;
                    secondNumber = String.valueOf(result);
                    inputText = firstNumber + getString(calculatorAction.getSymbol()) + secondNumber;
                    setInputText();
                }
                break;
            }
        }
    }


    private void performCalculatorAction(CalculatorAction action) {
        if (inputText.isEmpty()) return;
        clearOutputText();
        if (calculatorAction != null) {
            handleMoreThanTwoNumbers();
        }
        calculatorAction = action;
        inputText = inputText + getString(calculatorAction.getSymbol());
        setInputText();
    }

    private float calculateResult() {
        isResultCalculated = true;
        float result;
        if (calculatorAction == CalculatorAction.ADD) result = getFirstNumber() + getSecondNumber();
        else if (calculatorAction == CalculatorAction.SUBTRACT)
            result = getFirstNumber() - getSecondNumber();
        else if (calculatorAction == CalculatorAction.MULTIPLY)
            result = getFirstNumber() * getSecondNumber();
        else if (calculatorAction == CalculatorAction.DIVIDE)
            result = getFirstNumber() / getSecondNumber();
        else result = 0f;
        outputText = String.valueOf(result);
        setOutputText();
        return result;

    }

    private void clearAll() {
        calculatorAction = null;
        firstNumber = "";
        secondNumber = "";
        inputText = "";
        outputText = "";
        clearInputText();
        clearOutputText();

    }

    private void handleMoreThanTwoNumbers() {
        float result = calculateResult();
        firstNumber = String.valueOf(result);
        secondNumber = "";
        inputText = firstNumber;
        setInputText();
        isResultCalculated = false;

    }


    private float getFirstNumber() {
        return Float.parseFloat(firstNumber);
    }

    private float getSecondNumber() {
        return Float.parseFloat(secondNumber);
    }

    private void setInputText() {
        tvUserInput.setText(inputText);
    }

    private void clearInputText() {
        tvUserInput.setText("");
    }

    private void setOutputText() {
        tvCalculationOutput.setText(outputText);
    }

    private void clearOutputText() {
        tvCalculationOutput.setText("");
    }
}