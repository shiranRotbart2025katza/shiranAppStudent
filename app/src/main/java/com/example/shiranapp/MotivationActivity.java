package com.example.shiranapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MotivationActivity extends AppCompatActivity {

    private Button switchButton,addButton;
    TextView myTextView;
    DatabaseHelper dbHelper;
    private int currentCursorPosition = -1;
    private Cursor cursor;

    Context context;
    ActivityResultLauncher<Intent> someActivityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motivation);


        context=this;

        // Initialize DatabaseHelper
        dbHelper = new DatabaseHelper(context);

        myTextView = findViewById(R.id.myTextView);


        switchButton = findViewById(R.id.switchButton);
        switchButton.setOnClickListener(new View.OnClickListener() {


            @Override

            public void onClick(View v) {
                // Get all quotes from the database
                cursor = dbHelper.getAllQuotes();
                if (cursor != null && cursor.getCount() > 0) {
                    if (currentCursorPosition == -1 || !cursor.moveToPosition(currentCursorPosition)) {
                        cursor.moveToFirst();
                        currentCursorPosition = 0;
                    } else if (!cursor.isLast()) {
                        boolean moved = cursor.moveToNext();
                        currentCursorPosition++;
                    } else {
                        cursor.moveToFirst();
                        currentCursorPosition = 0;
                    }

                    String quote = cursor.getString(1); // עמודה 1 מכילה את המשפטים

                    myTextView.setText(quote);
                } else {
                    myTextView.setText("No quotes available.");
                }

            }
        });




        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityForResult();

            }
        });

    someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {


                        Intent data = result.getData();
                        String quote = data.getStringExtra("quote");
                        int userId = data.getIntExtra("userid",-1);

                        if (quote != null && !quote.isEmpty() && userId != -1) {
                            dbHelper.addQuote(quote, userId);
                        }


                    }
                }
            });

        Button goBack = findViewById(R.id.back);

        goBack.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View v){
                // יצירת Intent למעבר בין אקטיביטיס
       finish();
            }
        });



/////
        Button goToJokes = findViewById(R.id.button6);

        goToJokes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //יצירת Intent למעבר בין אקטיביטיס
                Intent intent = new Intent(MotivationActivity.this, JokesActivity.class);
                intent.putExtra("userid",111); //TODOa
                startActivity(intent);
            }
        });



    }


    public void openActivityForResult() {

        Intent intent = new Intent(this, AnotherQuoteActivity.class);
        intent.putExtra("userid", getIntent().getIntExtra("userid", -1));
        someActivityResultLauncher.launch(intent);

    }
}


