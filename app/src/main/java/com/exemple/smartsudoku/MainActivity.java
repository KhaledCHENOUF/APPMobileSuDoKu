package com.example.smartsudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    // rediriger vers l'activité about
    public void openActivity_about(View view) {
        Intent intent1 = new Intent(this, activity_about.class);
        startActivity(intent1);
    }
    // rediriger vers l'activité choix_grille
    public void openActivity_jeu(View view) {
        Intent intent2 = new Intent(this, choix_grille.class);
        startActivity(intent2);
    }
}
