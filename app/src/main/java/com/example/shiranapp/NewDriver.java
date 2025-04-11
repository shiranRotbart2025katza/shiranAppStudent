package com.example.shiranapp;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;
import java.util.Date;

public class NewDriver extends AppCompatActivity {

    private DatePicker datePicker;
    private TextView tvDaysRemaining;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_driver);

        datePicker = findViewById(R.id.datePicker);
        tvDaysRemaining = findViewById(R.id.tvDaysRemaining);

        datePicker.init(Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
                (view, year, monthOfYear, dayOfMonth) -> {
                    // חישוב התאריך הנוכחי
                    Calendar selectedDate = Calendar.getInstance();
                    selectedDate.set(year, monthOfYear, dayOfMonth);

                    // הוספת 3 חודשים לתאריך שנבחר
                    Calendar threeMonthsLater = (Calendar) selectedDate.clone();
                    threeMonthsLater.add(Calendar.MONTH, 3);

                    // חישוב מספר הימים שנותרו
                    long diffInMillis = threeMonthsLater.getTimeInMillis() - System.currentTimeMillis();
                    long diffInDays = diffInMillis / (24 * 60 * 60 * 1000);

                    if (diffInDays <= 0) {
                        tvDaysRemaining.setText(" הסתיים לך המלווה יום!.");
                    } else if (diffInDays == 1) {
                        tvDaysRemaining.setText("נשאר לך עוד יום למלווה יום!.");
                    } else if (diffInDays > 90) {
                        tvDaysRemaining.setText("תבחרו תאריך אחר- עוד לא היה התאריך שבחרת");
                    } else {
                        // הצגת התוצאה
                        tvDaysRemaining.setText("נותרו " + diffInDays + " ימים עד שיסתיים מלווה היום.");

                    }
                });
        Button goBack = findViewById(R.id.back);

        goBack.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View v){
                // יצירת Intent למעבר בין אקטיביטיס
                Intent intent = new Intent(NewDriver.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }




}