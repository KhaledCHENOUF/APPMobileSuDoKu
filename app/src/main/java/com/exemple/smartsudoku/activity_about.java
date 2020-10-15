package com.example.smartsudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class activity_about extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }
    // rediriger vers l'activité Main
    public void openActivity_main(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
