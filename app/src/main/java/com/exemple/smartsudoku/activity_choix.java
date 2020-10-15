package com.example.smartsudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.smartsudoku.R.color.white;

public class activity_choix extends AppCompatActivity {

    ListView listView;
    String[] listValue = new String[]
            {
                    "0 // vider case",
                    "1",
                    "2",
                    "3",
                    "4",
                    "5",
                    "6",
                    "7",
                    "8",
                    "9"
            };
    List<String> LISTSTRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix);


        listView = (ListView)findViewById(R.id.listView1);

        LISTSTRING = new ArrayList<String>(Arrays.asList(listValue));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_2, android.R.id.text1, LISTSTRING){

            @Override
            public View getView(int position, View convertView, ViewGroup parent){

                View view = super.getView(position, convertView, parent);

                TextView ListItemShow = (TextView) view.findViewById(android.R.id.text1);

                ListItemShow.setTextColor(Color.parseColor("#FFFFFF"));
                ListItemShow.setTypeface(null, Typeface.BOLD_ITALIC);
                //textView.setTypeface(null, Typeface.BOLD_ITALIC);


                return view;
            }

        };

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();

                if(l==0)  // vider la case
                    intent.putExtra("value","");
                else // remplir la case
                    intent.putExtra("value",(int)l);

                setResult(activity_jeux.RESULT_OK, intent);
                finish();
            }
        });
    }


    }
