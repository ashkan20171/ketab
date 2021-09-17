package com.example.shortstory;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;


public class Search extends ListActivity {



    private database db;
    private String[] Name;
    private String[] season;


    private EditText etxtSearch;
    private RadioButton rbName;
    private RadioButton rbText;
    private TextView txtStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        db=new database(this);

        etxtSearch = (EditText) findViewById(R.id.search_edit_search);
        rbName = (RadioButton) findViewById(R.id.search_rb_text);
        rbText = (RadioButton) findViewById(R.id.search_rb_name);
        txtStatus = (TextView) findViewById(R.id.search_txt_status);

        refresh(etxtSearch.getText().toString(), "Name");

        etxtSearch.addTextChangedListener(new TextWatcher() {


            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

                if(rbName.isChecked()){
                    refresh(etxtSearch.getText().toString(), "Name");

                }
                if (rbText.isChecked()) {
                    refresh(etxtSearch.getText().toString(), "Text");

                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {

            }

            @Override
            public void afterTextChanged(Editable arg0) {

            }
        });


    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        Intent i=new Intent(Search.this,Text.class);
        i.putExtra("sea", season[position]);
        i.putExtra("name",Name[position]);
        Search.this.startActivity(i);


    }



    class AA extends ArrayAdapter<String>{


        public AA(){
            super(Search.this,R.layout.row_search,Name);
        }



        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {


            LayoutInflater in=getLayoutInflater();
            View row=in.inflate(R.layout.row_search, parent,false);

            TextView name=(TextView) row.findViewById(R.id.row_search_txt_name);

            name.setText(season[position]+": "+Name[position]);
            name.setTypeface(Main.font);



            return (row);
        }


    }



    private void refresh(String text1 , String field){

        db.open();

        int s = db.Search_count(text1, field);
        if (etxtSearch.getText().toString().equals("")) {
            s = 0;
            txtStatus.setText("کلمه ای را برای جستجو وارد کنید");
        }else {
            txtStatus.setText(" تعداد "+s+"یافت شد");

        }
        Name=new String[s];
        season=new String[s];

        for(int i=0;i<s;i++){

            Name[i]=db.Search_display(i, 1, text1, field);
            season[i]=db.Search_display(i, 3, text1, field);
        }

        setListAdapter(new AA());
        db.close();

    }


}
