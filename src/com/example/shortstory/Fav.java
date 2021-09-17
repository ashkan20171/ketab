package com.example.shortstory;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class Fav extends ListActivity{

    private database db;

    private String[] Name;
    private String[] Fav;
    private String[] Season;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.fav);

        db = new database(this);


        refresh();

        setListAdapter(new AA());
    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        Intent i = new Intent(Fav.this,Text.class);
        i.putExtra("sea",Season[position]);
        i.putExtra("name",Name[position]);

        startActivity(i);
    }

    public void onBackPressed() {

        super.onBackPressed();
        finish();
    }


    class AA extends ArrayAdapter<String>
    {

        public AA()
        {

            super(Fav.this,R.layout.row_fav,Name);

        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {


            LayoutInflater in = getLayoutInflater();
            View row = in.inflate(R.layout.row_fav, parent,false);

            TextView name = (TextView) row.findViewById(R.id.row_fav_txt_name);

            final ImageView del = (ImageView) row.findViewById(R.id.row_fav_img_delfav);


            del.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    db.open();
                    db.Fav_update("content", Season[position], Name[position], "0");
                    db.close();
                    refresh();

                }
            });

            name.setText(Name[position]);

            name.setTypeface(Main.font);



            return (row);
        }
    }

    private void refresh()
    {
        db.open();
        int s = db.Fav_count("content");

        if(s==0)
        {
            finish();
            Toast.makeText(this,"لیست علافه مندی خالی است", Toast.LENGTH_LONG).show();
        }

        Name = new String[s];
        Fav = new String[s];
        Season= new String[s];


        for(int i=0;i<s;i++)
        {

            Name[i]=db.Fav_display("content", i, 1);
            Fav[i]=db.Fav_display("content", i, 4);
            Season[i]=db.Fav_display("content", i, 3);


        }
        setListAdapter(new AA());
        db.close();
    }
}
