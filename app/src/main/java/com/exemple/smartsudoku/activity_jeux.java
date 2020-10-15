package com.example.smartsudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;


import com.muddzdev.styleabletoastlibrary.StyleableToast;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class activity_jeux<line> extends AppCompatActivity {
    private SeekBar Set_t;
    public Grille grilleView;
    private float x;
    private float y;
    private int ligne;
    private  int colonne;
    private boolean fixe;
    private int taille;
    public static final int REQUEST_CODE = 1;


    public activity_jeux() throws IOException {
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jeux);

        grilleView = (Grille) findViewById(R.id.view_grille);
        grilleView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event){
                x= event.getX();
                y= event.getY();
                if (event.getAction()== MotionEvent.ACTION_UP){
                    ligne=grilleView.getXFromMatrix((int)x);
                    colonne=grilleView.getYFromMatrix((int)y);
                    fixe=grilleView.isNotFix(ligne,colonne);
                    if(fixe) getNumber();
                    else
                       StyleableToast.makeText(getApplicationContext(),"Case fixe",R.style.templateToast).show();
                }
                    return true;
            }
        });
        // modifier la taille des cases
        taille = Integer.parseInt(sendurl.size_grille);


        // dans le cas ou il insert une valeur sup a 30 ou inf a 0
        if(taille >= 30){
             taille =30;
        }
        if(taille <= 0){
            taille =0;
        }
        // modification de la taille
        grilleView.set_taille(taille);


        //  URL
        new JsonTask().execute(sendurl.send);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE) {
            if (resultCode == activity_choix.RESULT_OK) {
                //reçevoir le numéro choisi
                int result = data.getIntExtra("value", 0);
                grilleView.set(colonne, ligne, result);
                grilleView.invalidate();
            }
        }
    }


    public void getNumber() {
        // click des cases
        Intent intent = new Intent(this, activity_choix.class);
        startActivityForResult(intent,REQUEST_CODE);
    }
    public void Result_Game(View view) {
        grilleView.grille_result = grilleView.gagne();
        grilleView.draw_result = true;
        grilleView.invalidate();
    }

    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;


            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer grille_content = new StringBuffer();
                String ligne = "";

                while ((ligne = reader.readLine()) != null) {
                    grille_content.append(ligne+"\n");
                }

                return grille_content.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
           // modifier le contenue de la grille avec le string result
            grilleView.set(result);
             grilleView.invalidate();
        }

    }









}







