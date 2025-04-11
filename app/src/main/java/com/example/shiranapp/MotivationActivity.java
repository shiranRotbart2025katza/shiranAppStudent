package com.example.shiranapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MotivationActivity extends AppCompatActivity {

    private Button switchButton,addButton;
    private boolean isFirstFragment = true;
    private boolean isSecondFragment = true;

    Context context;
    ActivityResultLauncher<Intent> someActivityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motivation); // צריך לשים layout חדש אם תרצה


        context=this;

        switchButton = findViewById(R.id.switchButton);
        addButton = findViewById(R.id.addButton);

        // הצגת ה-Fragment הראשון בפתיחה
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, new FirstFragment())
                .commit();

        switchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // החלפת ה-Fragment
                if (isFirstFragment) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentContainer, new SecoundFragment())
                            .commit();
                } else if (isSecondFragment) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentContainer, new ThirdFragment()) // טוען את ה-Fragment השלישי
                            .commit();
                    isSecondFragment = false;
                } else {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentContainer, new FirstFragment()) // טוען את ה-Fragment הראשון
                            .commit();
                    isFirstFragment = true;
                }
                isFirstFragment = !isFirstFragment;
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityForResult();

            }
        });


    //Instead of onActivityResult() method use this one
    someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {


                        Intent data = result.getData();
                        String quote = data.getStringExtra("quote");


                        Toast.makeText(MotivationActivity.this, quote, Toast.LENGTH_SHORT).show();





                        TextView myTextView = findViewById(R.id.myTextView);

                        // יוצרים את הסטרינג שברצונך להציג
                        String textToDisplay =quote;

                        // מציגים את הסטרינג על ה-TextView
                        myTextView.setText(textToDisplay);




                        // Initialize DatabaseHelper
                        DatabaseHelper dbHelper = new DatabaseHelper(context);

                        // Assuming you have a method to get the current user's ID
                        int userId = data.getIntExtra("userid",-1); // Replace this with your actual method to get user ID

                        // Add the quote to the database
                        if (dbHelper.addQuote(quote, userId)) {
                            Toast.makeText(MotivationActivity.this, "Quote added successfully!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MotivationActivity.this, "Failed to add quote.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }


    public void openActivityForResult() {

        //Instead of startActivityForResult use this one
        Intent intent = new Intent(this, AnotherQuoteActivity.class);
        someActivityResultLauncher.launch(intent);
    }
}


