package com.example.shiranapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.speech.tts.TextToSpeech;
import android.widget.ImageView;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    private ImageView androidImage;
    Button txtToSpeech;
    TextToSpeech textToSpeech;

    TextView greetingTextView;
    SharedPreferences sharedPreferences;


    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        txtToSpeech = findViewById(R.id.txtToSpeech);
        Button goToTimer = findViewById(R.id.button);


        androidImage = findViewById(R.id.kid);

        RotateAnimation rotate = new RotateAnimation(
                -20, 20,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f

        );
        rotate.setDuration(500);
        rotate.setInterpolator(new LinearInterpolator());
        rotate.setRepeatMode(Animation.REVERSE);
        rotate.setRepeatCount(Animation.INFINITE);

        androidImage.setAnimation(rotate);


        goToTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // יצירת Intent למעבר בין אקטיביטיס
                Intent intent = new Intent(MainActivity.this, TimerPage.class);
                startActivity(intent);
            }
        });
        Button goToCal = findViewById(R.id.button2);

        goToCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // יצירת Intent למעבר בין אקטיביטיס
                Intent intent = new Intent(MainActivity.this, Calculator.class);
                startActivity(intent);
            }
        });
        Button goToNewD = findViewById(R.id.button4);

        goToNewD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // יצירת Intent למעבר בין אקטיביטיס
                Intent intent = new Intent(MainActivity.this, NewDriver.class);
                startActivity(intent);
            }
        });

        Button goToMotivation = findViewById(R.id.button5);

        goToMotivation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // יצירת Intent למעבר בין אקטיביטיס
                Intent intent = new Intent(MainActivity.this, MotivationActivity.class);
                intent.putExtra("userid",111); //TODO
                startActivity(intent);
            }
        });


        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {

                // if No error is found then only it will run
                if (i != TextToSpeech.ERROR) {
                    // To Choose language of speech
                    textToSpeech.setLanguage(Locale.UK);
                }
            }
        });
        String textS = "welcome to the student, what do you want to do?";

        // Adding OnClickListener
        txtToSpeech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textToSpeech.speak(textS, TextToSpeech.QUEUE_FLUSH, null);
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
public boolean onOptionsItemSelected(MenuItem item)
{
    super.onOptionsItemSelected(item);
    int id = item.getItemId();
    if(id==R.id.action_settings) {
        Toast.makeText(this, "You selected login", Toast.LENGTH_SHORT).show();
    }
    return true;
    }
}


