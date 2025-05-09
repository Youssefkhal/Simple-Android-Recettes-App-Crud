package com.example.recettesapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button goToRecettesBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goToRecettesBtn = new Button(this);
        goToRecettesBtn.setText("Gérer les Recettes");
        goToRecettesBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, RecetteActivity.class));
        });

        ((android.widget.FrameLayout) findViewById(android.R.id.content)).addView(goToRecettesBtn);
    }
}
