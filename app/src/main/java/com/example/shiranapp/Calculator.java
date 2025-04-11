package com.example.shiranapp;

import static com.example.shiranapp.R.id.tvResult;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Calculator extends AppCompatActivity {

    private TextView tvResult ;
    private StringBuilder currentInput = new StringBuilder();
    private double result = 0;
    private String lastOperator = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator);

        View.OnClickListener buttonClickListener = v -> {
            Button button = (Button) v;
            String text = button.getText().toString();
            tvResult= findViewById(R.id.tvResult);
            switch (text) {
                case "+":
                case "-":
                case "*":
                case "/":
                    calculateResult(text);
                    //אם סימן כזה, לך לפעולה זו
                    break;
                case "=":
                    calculateResult("");
                    break;
                case "C":
                    resetCalculator();
                    break;
                default: // Numbers
                    currentInput.append(text);
                    tvResult.setText(currentInput.toString());
                    break;
            }
        };

        int[] buttonIds = {R.id.btn1,R.id.btn2,R.id.btn3,R.id.btn4,R.id.btn5,R.id.btn6,R.id.btn7,R.id.btn0,R.id.btnPlus,R.id.btnMulti,R.id.btnMinus,R.id.btnEqual,R.id.btn7, R.id.btn8, R.id.btn9, R.id.btnDivide,
              };

        for (int id : buttonIds) {
            findViewById(id).setOnClickListener(buttonClickListener);
        }


        Button goBack = findViewById(R.id.back);

        goBack.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View v){
                // יצירת Intent למעבר בין אקטיביטיס
                Intent intent = new Intent(Calculator.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void calculateResult(String operator) {

        double input = currentInput.length() > 0 ? Double.parseDouble(currentInput.toString()) : 0;
        if (input == 0) {
            tvResult.setText("Error");  // מציגים שגיאה ב-TextView
            resetCalculator();  // מאפסים את הכל
            Toast.makeText(getApplicationContext(), "Error: Division by zero", Toast.LENGTH_SHORT).show();
            return;
        }
        switch (lastOperator) {
            case "+":
                result += input;
                break;
            case "-":
                result -= input;
                break;
            case "*":
                result *= input;
                break;
            case "/":
                if (input == 0) {
                    tvResult.setText("Error");  // מציגים שגיאה ב-TextView
                    resetCalculator();  // מאפסים את הכל
                    return;
                }
              else if (input != 0) {
                    result /= input;
                }
                break;
            default:
                result = input;
                break;
        }

        lastOperator = operator;
        currentInput.setLength(0); // Clear current input
        tvResult.setText(operator.isEmpty() ? String.valueOf(result) : "");
    }

    private void resetCalculator() {
        result = 0;
        currentInput.setLength(0);
        lastOperator = "";
        tvResult.setText("0");
    }



}
