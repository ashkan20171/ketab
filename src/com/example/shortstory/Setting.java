package com.example.shortstory;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class Setting extends Activity{

    private RadioButton rbNazanin,rbSystem,rbHoma,rbKoodak;
    private SeekBar sbSizeFont,sbSpaceLine;
    private ImageView imgSave,imgCancel;
    private TextView txtTest;

    private Typeface font;
    private String sfont;

    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        set();
        laod();

        rbNazanin.setTypeface(Typeface.createFromAsset(getAssets(), "font/nazanin.ttf"));
        rbHoma.setTypeface(Typeface.createFromAsset(getAssets(), "font/homa.ttf"));
        rbKoodak.setTypeface(Typeface.createFromAsset(getAssets(), "font/koodak.ttf"));

        sbSizeFont.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int value,boolean fromUser) {
                txtTest.setTextSize(value);


            }
        });

        sbSpaceLine.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int value,boolean fromUser) {

                txtTest.setLineSpacing(value, 1);
            }
        });
        rbSystem.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                txtTest.setTypeface(null);
                sfont = "system";


            }
        });
        rbNazanin.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                font = Typeface.createFromAsset(getAssets(), "font/nazanin.ttf");
                txtTest.setTypeface(font);
                sfont = "nazanin";
            }
        });
        rbHoma.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                font = Typeface.createFromAsset(getAssets(), "font/homa.ttf");
                txtTest.setTypeface(font);
                sfont = "homa";
            }
        });
        rbKoodak.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                font = Typeface.createFromAsset(getAssets(), "font/koodak.ttf");
                txtTest.setTypeface(font);
                sfont = "koodak";
            }
        });

        imgSave.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                sp = getApplicationContext().getSharedPreferences("setting", 0);

                Editor edit = sp.edit();
                edit.putString("font", sfont);
                if (sfont.equals("system")) {
                    Main.font = null;

                }else {
                    Main.font = Typeface.createFromAsset(getAssets(), "font/"+sfont+".ttf");
                }
                edit.putInt("size", sbSizeFont.getProgress());
                Main.size = sbSizeFont.getProgress();
                edit.putInt("space", sbSpaceLine.getProgress());
                Main.space = sbSpaceLine.getProgress();

                edit.commit();

                Toast.makeText(getApplicationContext(), "تنظیمات با موفقیت ذخیره شد", Toast.LENGTH_SHORT).show();
                finish();

            }
        });

        imgCancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();

            }
        });



    }
    private void laod(){

        sp = getApplicationContext().getSharedPreferences("setting", 0);



        //font
        String g = sp.getString("font", "homa");
        sfont = g;
        if (g.equals("system")) {
            txtTest.setTypeface(null);
            rbSystem.setChecked(true);

        }else {
            font = Typeface.createFromAsset(getAssets(), "font/"+g+".ttf");
            txtTest.setTypeface(font);
            if (g.equals("nazanin")) {
                rbNazanin.setChecked(true);
            }
            if (g.equals("homa")) {
                rbHoma.setChecked(true);
            }
            if (g.equals("koodak")) {
                rbKoodak.setChecked(true);
            }

            //size

            int f = sp.getInt("size", 18);
            txtTest.setTextSize(f);
            sbSizeFont.setProgress(f);

            //space

            int p = sp.getInt("space", 2);
            txtTest.setLineSpacing(p, 1);
            sbSpaceLine.setProgress(f);

        }

    }

    public void set(){

        rbSystem = (RadioButton) findViewById(R.id.setting_rb_system);
        rbNazanin = (RadioButton) findViewById(R.id.setting_rb_nazanin);
        rbHoma = (RadioButton) findViewById(R.id.setting_rb_homa);
        rbKoodak = (RadioButton) findViewById(R.id.setting_rb_koodak);
        txtTest = (TextView) findViewById(R.id.setting_txt_test);
        sbSizeFont = (SeekBar) findViewById(R.id.setting_sb_sizefont);
        sbSpaceLine = (SeekBar) findViewById(R.id.setting_sb_spaceline);
        imgSave = (ImageView) findViewById(R.id.setting_img_save);
        imgCancel = (ImageView) findViewById(R.id.setting_img_cancel);
    }
}
