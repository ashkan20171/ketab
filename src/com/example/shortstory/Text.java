package com.example.shortstory;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Text extends Activity {

    private TextView txtName;
    private TextView txtText;
    private ImageView imgShare;

    private database db;

    private String season;
    private String name;

    private SharedPreferences sp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.text);

        db = new database(this);

        txtName = (TextView) findViewById(R.id.text_txt_name);
        txtText = (TextView) findViewById(R.id.text_txt_text);
        imgShare = (ImageView) findViewById(R.id.text_img_share);




        Bundle ex=getIntent().getExtras();

        season = ex.getString("sea");
        name = ex.getString("name");

        txtText.setTypeface(Main.font);
        txtText.setTextSize(Main.size);
        txtText.setLineSpacing(Main.space, 1);

        Typeface titr =Typeface.createFromAsset(getAssets(),"font/titr.ttf");
        txtName.setTypeface(titr);

        load(season, name);


        imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent Share=new Intent(Intent.ACTION_SEND);
                Share.setType("text/plan");
                Share.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name + ": " + txtName.getText().toString());
                Share.putExtra(Intent.EXTRA_TEXT,txtText.getText().toString());
                startActivity(Intent.createChooser(Share,"اشتراک گذاری با"));
            }
        });
    }

    @Override
    public void onBackPressed() {

        sp=getApplicationContext().getSharedPreferences("continue",0);
        SharedPreferences.Editor edit=sp.edit();
        edit.putString("name",name);
        edit.putString("season", season);

        edit.commit();

        finish();
    }

    private void load(String sea,String Name)
    {

        db.open();
        txtName.setText(Name);
        txtText.setText(db.Text_display("content", sea, Name));
        db.Text_display("content", sea, Name);
        db.close();
    }

}
