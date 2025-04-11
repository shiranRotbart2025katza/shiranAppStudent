package com.example.shiranapp;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

public class TimerPage extends AppCompatActivity {

    private EditText etHours;
    private TextView tvCountdown;
    private Button btnStartTimer;
    private CountDownTimer countDownTimer;

    private ImageView androidImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timerpage);

        // מציאת כל האלמנטים
        etHours = findViewById(R.id.etHours);
        tvCountdown = findViewById(R.id.tvCountdown);
        btnStartTimer = findViewById(R.id.btnStartTimer);
        androidImage = findViewById(R.id.cal);

        RotateAnimation rotate = new RotateAnimation(
                -20,20,
                RotateAnimation.RELATIVE_TO_SELF,0.5f,
                RotateAnimation.RELATIVE_TO_SELF,0.5f

                );
        rotate.setDuration(500);
        rotate.setInterpolator(new LinearInterpolator());
        rotate.setRepeatMode(Animation.REVERSE);
        rotate.setRepeatCount(Animation.INFINITE);

        androidImage.setAnimation(rotate);


        // קליק על הכפתור להפעיל את הטיימר
        btnStartTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // קבלת הזמן שהמשתמש הכניס
                String input = etHours.getText().toString();

                // בדיקה אם הוזן ערך
                if (input.isEmpty()) {
                    Toast.makeText(TimerPage.this, "נא להכניס זמן", Toast.LENGTH_SHORT).show();
                    return;
                }

                // המרת זמן שנשאר לשניות
                int hours = Integer.parseInt(input);
                long timeInMillis = hours * 60 * 60 * 1000; // המרה לשניות

                // הפעלת הטיימר
                startCountdown(timeInMillis);
            }
        });

        Button goBack = findViewById(R.id.back);

        goBack.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View v){
                // יצירת Intent למעבר בין אקטיביטיס
                Intent intent = new Intent(TimerPage.this, MainActivity.class);
                startActivity(intent);
            }
        });







    }

    // הפעלת הטיימר עם הזמן שהוזן
    private void startCountdown(long timeInMillis) {
        // עצירת הטיימר הקודם אם קיים
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        // יצירת טיימר חדש
        countDownTimer = new CountDownTimer(timeInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // חישוב הזמן שנותר (שעות, דקות ושניות)
                String timeRemaining = formatTime(millisUntilFinished);
                tvCountdown.setText("הזמן שנותר: " + timeRemaining);
            }

            @Override
            public void onFinish() {
                // עדכון טקסט כאשר הספירה הסתיימה
                tvCountdown.setText("הזמן הסתיים!");
            }
        };

        // התחלת הטיימר
        countDownTimer.start();
    }

    //  עיצוב הזמן שנותר (שעות, דקות ושניות)
    private String formatTime(long millis) {
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

        // מחזירים את הזמן בפורמט של שעות, דקות ושניות
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}