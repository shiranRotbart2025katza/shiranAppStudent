package com.example.shiranapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class AnotherQuoteActivity extends AppCompatActivity {

    private EditText editTextQuote;
    private Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int userId = getIntent().getIntExtra("userid", -1);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_another_quote);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextQuote = findViewById(R.id.editTextQuote);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quote = editTextQuote.getText().toString();
                int userId = getIntent().getIntExtra("userid", -1); // קבלת userId

                Intent intent = new Intent();
                intent.putExtra("quote", quote);
                intent.putExtra("userid", userId);
                setResult(RESULT_OK, intent);
                finish();
            }
        });


        Button goBack = findViewById(R.id.back);

        goBack.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View v){
                finish();

            }
        });
    }

    }
