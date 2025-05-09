package com.example.recettesapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText emailInput, passwordInput;
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailInput = findViewById(R.id.email);
        passwordInput = findViewById(R.id.password);
        registerButton = findViewById(R.id.registerBtn);

        registerButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString();
            String password = passwordInput.getText().toString();

            DBConnect dbHelper = new DBConnect(this);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            Cursor c = db.rawQuery("SELECT * FROM user WHERE email=?", new String[]{email});
            if (c.getCount() == 0) {
                db.execSQL("INSERT INTO user (email, password) VALUES (?, ?)", new Object[]{email, password});
                Toast.makeText(this, "Compte créé", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Email existe déjà", Toast.LENGTH_SHORT).show();
            }

            c.close();
            db.close();
        });
    }
}

