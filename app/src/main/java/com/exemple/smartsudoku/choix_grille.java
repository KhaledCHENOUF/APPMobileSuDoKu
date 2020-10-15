package com.example.smartsudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class choix_grille extends AppCompatActivity {


    public EditText grille_n;
    public EditText grille_s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_grille);
        grille_n = findViewById(R.id.number);
        grille_s = findViewById(R.id.size);

    }

    public void valider_grille(View view) {
        // se redereger ver l'activity_jeux
        Intent intent = new Intent(this, activity_jeux.class);
        startActivity(intent);
        String send_url = "http://labsticc.univ-brest.fr/~bounceur/cours/android/tps/sudoku/index.php?v=";
        String send_number = send_url+grille_n.getText().toString();
        //Stocker la taille dans l'attribut static de la classe sendurl
       sendurl.send=send_number;  // numero de la grille
        // Stocker la grille choisie dans l'attribut static de la classe sendurl
        sendurl.size_grille=grille_s.getText().toString(); // taille de la case
    }
}
