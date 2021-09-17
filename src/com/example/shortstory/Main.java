package com.example.shortstory;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


public class Main extends Activity {

    private ImageView imgSeason,imgFav,imgSetting,imgSearch,imgAbout;
    private database db;

    public static Typeface font;
    public static int size;
    public static int space;

    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        database db = new database(this);
        db.useable();
        load();

        imgSeason = (ImageView) findViewById(R.id.main_img_season);
        imgSetting = (ImageView) findViewById(R.id.main_img_setting);
        imgFav = (ImageView) findViewById(R.id.main_img_fav);
        imgSearch = (ImageView) findViewById(R.id.main_img_search);
        imgAbout = (ImageView) findViewById(R.id.main_img_aboutwe);

        imgAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent About = new Intent(Main.this, AboutWe.class);
                startActivity(About);
            }
        });



        imgSeason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent Season = new Intent(Main.this, Season.class);
                startActivity(Season);
            }
        });

        imgSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent Setting = new Intent(Main.this, Setting.class);
                startActivity(Setting);
            }
        });

        imgFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent Fav = new Intent(Main.this, Fav.class);
                startActivity(Fav);
            }
        });

        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent Search = new Intent(Main.this, Search.class);
                startActivity(Search);
            }
        });
    }

    public void load()
    {
        sp = getApplicationContext().getSharedPreferences("setting", 0);
        String h = sp.getString("font","nazanin");

        font =Typeface.createFromAsset(getAssets(),"font/"+h+".ttf");
        size=sp.getInt("size",18);
        space=sp.getInt("space",1);
    }
}
