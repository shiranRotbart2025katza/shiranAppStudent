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
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    private ImageView androidImage;
    Button txtToSpeech;
    TextToSpeech textToSpeech;

    TextView welcomeText;
    SharedPreferences sharedPreferences;

    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

/////////////welcome txt
        welcomeText = findViewById(R.id.welcomeText);

        // בדיקה אם קיבלנו את שם המשתמש מה-Intent
        String username = getIntent().getStringExtra("username");

        // אם שם המשתמש לא הגיע דרך ה-Intent, נבדוק ב-SharedPreferences
        if (username == null) {
            SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            username = sharedPreferences.getString("username", "משתמש");
        }

        // הצגת "שלום [שם המשתמש]" על המסך
        welcomeText.setText("the notebook of: \n" + username );
    /////////////


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
                Intent intent = new Intent(MainActivity.this, TimerActivity.class);
                startActivity(intent);
            }
        });

        Button goToCal = findViewById(R.id.button2);
        goToCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // יצירת Intent למעבר בין אקטיביטיס
                Intent intent = new Intent(MainActivity.this, CalculatorActivity.class);
                startActivity(intent);
            }
        });

        Button goToNewD = findViewById(R.id.button4);
        goToNewD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // יצירת Intent למעבר בין אקטיביטיס
                Intent intent = new Intent(MainActivity.this, NewDriverActivity.class);
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


        //מעבר בין טקסט להקראה על ידי הקשר לאפליקציה ומאזין שמופעל בסיום
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int i) {
                //נוודא שהמנוע מוכן לפני שמריצים אותו

                // אם לא נמצאו הערות, יופעל
                if (i != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.UK);
                }
            }
        });
        String textS = "welcome to the notebook. choose smily face for motivation quotes, choose car for your license update, choose calculator for calculating problems and choose timer for studing while timer running. ";

        // מאזין שאומר לפונקציה לפעול ולהשמיע את הטקסט
        txtToSpeech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textToSpeech.speak(textS, TextToSpeech.QUEUE_FLUSH, null);
            }
        });


    }


///////////////////תפריט///////

    //יצירת תפריט האפליקציה שלי:
    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        // -טוען ומקשר את קובץ ה־XML של התפריט לכאן
        //R.menu.menu_main  שמכיל את הפריטים בתפריט XML
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    // טיפול בלחיצה על פריט בתפריט
public boolean onOptionsItemSelected(MenuItem item)
{
    super.onOptionsItemSelected(item); //עוזר לוודא שיעבוד גם אם לא טיפלנו בכל הדברים האפשריים
    int id = item.getItemId(); //מחזיר את הID של הפריט שנלחץ

    if(id==R.id.action_settings) {
        Intent intent = new Intent(this, ExplnationActivity.class);
        startActivity(intent);

    }
    if(id==R.id.action_odot) {
        Intent intent = new Intent(this, OdotActivity.class);
        startActivity(intent);
    }
    //הID של הפריטים מגיע מהXML של התפריט
    return true;
    }


}


