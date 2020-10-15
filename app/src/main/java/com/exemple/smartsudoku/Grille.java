package com.example.smartsudoku;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import com.muddzdev.styleabletoastlibrary.StyleableToast;

public class Grille extends View {

    private int screenWidth;
    private int screenHeight;
    private int n;
    private String grille;
    private int taille_Case = 0;
    private Paint paint1;   // Pour dessiner la grille (lignes noires)
    private Paint paint2;   // Pour le texte des cases fixes
    private Paint paint3;   // Pour dessiner les lignes rouges (grosse)
    private Paint paint4;   // Pour le texte noir des cases Ã  modifier
    private Paint paint5;   // Pour dessiner la grille (lignes vertes)
    private int[][] matrix = new int[9][9];
    private boolean[][] fixIdx = new boolean[9][9];
    public boolean draw_result = false;
    public boolean grille_result = false;

    public Grille(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public Grille(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.Grille, 0, 0);
        try { taille_Case = a.getInt(R.styleable.Grille_tailleCase, 0); }
        finally { a.recycle(); }

    }

    public Grille(Context context) {
        super(context);
        init();
    }

  //
    public void set_taille(int taille_case) {
        taille_Case = taille_case;
        invalidate();
        requestLayout();
    }

    private void init() {
        //Grille de depart
      //set("000105000140000670080002400063070010900000003010090520007200080026000035000409000");


        //Grille Gagnante
       //set("672145398145983672389762451263574819958621743714398526597236184426817935831459267");

        // Grille Perdante
       //set("672145198145983672389762451263574819958621743714398526597236184426817935831459267");
        // black color
        paint1 = new Paint();
        paint1.setAntiAlias(true);
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setStrokeWidth(4.0f);

        paint2 = new Paint();
        paint2.setAntiAlias(true);
        paint2.setColor(Color.RED);  // red color
        paint2.setTextSize(41.0f);

        // red color
        paint3 = new Paint();
        paint3.setAntiAlias(true);
        paint3.setColor(Color.RED);
        paint3.setStyle(Paint.Style.STROKE);
        paint3.setStrokeWidth(16.0f);

        // white color
        paint4 = new Paint();
        paint4.setAntiAlias(true);
        paint4.setColor(Color.WHITE);
        paint4.setTextSize(41.0f);
        // green color
        paint5 = new Paint();
        paint5.setAntiAlias(true);
        paint5.setColor(Color.GREEN);
        paint5.setStyle(Paint.Style.STROKE);
        paint5.setStrokeWidth(16.0f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        screenWidth = getWidth();
        screenHeight = getHeight();
        int w = Math.min(screenWidth, screenHeight);
        w = w - (w % 9);
        n = w / 9;

        // Dessiner w lignes verticles et w lignes horizontales noires
        // Dessiner 2 lignes rouges verticales et 2 lignes rouges horizontales

        canvas.drawLine(3 * n, 0, 3 * n, 9 * n, paint3);
        canvas.drawLine(6 * n, 0, 6 * n, 9 * n, paint3);
        canvas.drawLine(0, 3 * n, 9 * n, 3 * n, paint3);
        canvas.drawLine(0, 6 * n, 9 * n, 6 * n, paint3);

        // Les contenus des cases
        String s;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                canvas.drawRect(i * n + taille_Case, j * n + taille_Case, (i + 1) * n - taille_Case, (j + 1) *
                        n - taille_Case, paint1);

                s = "" + (matrix[j][i] == 0 ? "" : matrix[j][i]);
                if (fixIdx[j][i])
                    canvas.drawText(s, i * n + (n / 2) - (n / 10), j * n
                            + (n / 2) + (n / 10), paint2);
                else
                    canvas.drawText(s, i * n + (n / 2) - (n / 10), j * n
                            + (n / 2) + (n / 10), paint4);
            }
        }


        if (draw_result) {
            if (grille_result) {
                // dessiner les lignes vertes de resultat
                canvas.drawLine(0, 0, 9 * n, 0, paint5);
                canvas.drawLine(0, 0, 0, 9 * n, paint5);
                canvas.drawLine(9 * n, 0, 9 * n, 9 * n, paint5);
                canvas.drawLine(0, 9 * n, 9 * n, 9 * n, paint5);
                StyleableToast.makeText(getContext(),"Vous êtes gagnant!",R.style.template_G).show();

            } else {
                // dessiner les lignes rouges de resultat
                canvas.drawLine(0, 0, 9 * n, 0, paint3);
                canvas.drawLine(0, 0, 0, 9 * n, paint3);
                canvas.drawLine(9 * n, 0, 9 * n, 9 * n, paint3);
                canvas.drawLine(0, 9 * n, 9 * n, 9 * n, paint3);
                StyleableToast.makeText(getContext(),"Vous êtes perdant ! ",R.style.template_P).show();
            }

        }

    }

    public int getXFromMatrix(int x) {
        // Renvoie l'indice d'une case a partir du pixel x de sa position h
        return (x / n);
    }

    public int getYFromMatrix(int y) {
        // Renvoie l'indice d'une case a partir du pixel y de sa position v

        return (y / n);
    }

    public void set(String s, int i) {
        // Remplir la ieme ligne de la matrice matrix avec un vecteur String s
        int v;
        for (int j = 0; j < 9; j++) {
            v = s.charAt(j) - '0';
            matrix[i][j] = v;
            if (v == 0)
                fixIdx[i][j] = false;
            else
                fixIdx[i][j] = true;
        }
    }

    public void set(String s) {
        // Remplir la matrice matrix a partir d'un vecteur String s
        for (int i = 0; i < 9; i++) {
            set(s.substring(i * 9, i * 9 + 9), i);
        }
        invalidate();
    }

    public void set(int x, int y, int v) {
        // Affecter la valeur v a la case (y, x)
        // y : ligne
        // x : colonne
        matrix[x][y] = v;
    }

    public boolean isNotFix(int x, int y) {
        // Renvoie si la case (y, x) n'est pas fixe
        // A completer
        if (fixIdx[y][x]) return false;
        else return true;
    }

    public boolean gagne() {
        // Verifier si la case n'est pas vide ou bien s'il existe
        // un numÃ©ro double dans chaque ligne ou chaque colonne de la grille
        for (int v = 1; v <= 9; v++) {
            for (int i = 0; i < 9; i++) {
                boolean bx = false;
                boolean by = false;
                for (int j = 0; j < 9; j++) {
                    if (matrix[i][j] == 0) return false;
                    if ((matrix[i][j] == v) && bx) return false;
                    if ((matrix[i][j] == v) && !bx) bx = true;
                    if ((matrix[j][i] == v) && by) return false;
                    if ((matrix[j][i] == v) && !by) by = true;
                }
            }
        }
        // Gagne
        return true;
    }
}