package com.example.shiranapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.activity.EdgeToEdge;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    Button loginButton, registerRedirectButton;
    DatabaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EdgeToEdge.enable(this);

        // טיפול במרווחים עליון ותחתון של המערכת
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // אתחול משתנים
        dbHelper = new DatabaseHelper(this);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        registerRedirectButton = findViewById(R.id.registerRedirectButton);

        // התחלת החלפת התמונות
    //    startImageSwitching();

        // טיפול בקליק על כפתור ההתחברות
        loginButton.setOnClickListener(view -> {
            String mail = email.getText().toString();
            String pass = password.getText().toString();

            if (mail.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "נא למלא את כל השדות", Toast.LENGTH_SHORT).show();
            } else {
                boolean validUser = dbHelper.checkUser(mail, pass);

                if (validUser) {
                    String username = dbHelper.getUsernameByEmail(mail);
                    // שמור את שם המשתמש ב- SharedPreferences
                    SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", username);  // שמירה של המייל כ-"שם משתמש"
                    editor.apply();  // שמירה של השינויים

                    Toast.makeText(this, "התחברת בהצלחה!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, MainActivity.class));

                    // מעבר ל-MainActivity עם הנתונים
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtra("username", username); // שולח את שם המשתמש
                    startActivity(intent);

                } else {
                    Toast.makeText(this, "שם משתמש או סיסמה שגויים", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // מעבר לרשומת משתמש חדשה
        registerRedirectButton.setOnClickListener(view -> startActivity(new Intent(this, RegisterActivity.class)));
    }


}
