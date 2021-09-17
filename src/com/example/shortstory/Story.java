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
import android.widget.RelativeLayout;
import android.widget.TextView;


public class Story extends ListActivity{

    private database db;

    private String[] Name;
    private String[] Fav;
    private String season;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.story);

        db = new database(this);

        Bundle ex = getIntent().getExtras();
        season= ex.getString("sea");

        refresh();

        setListAdapter(new AA());
    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        Intent i = new Intent(Story.this,Text.class);
        i.putExtra("sea",season);
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

            super(Story.this,R.layout.row_story,Name);

        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {


            LayoutInflater in = getLayoutInflater();
            View row = in.inflate(R.layout.row_story, parent,false);

            TextView name = (TextView) row.findViewById(R.id.row_story_txt_name);

            final ImageView fav = (ImageView) row.findViewById(R.id.row_story_img_fav);

            if(Fav[position].equals("1"))
            {

                fav.setImageResource(R.drawable.favon);

            }

            else
            {

                fav.setImageResource(R.drawable.favoff);

            }

            fav.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {


                    db.open();

                    if(Fav[position].equals("1"))
                    {

                        db.Fav_update("content", season, Name[position], "0");
                        fav.setImageResource(R.drawable.favoff);
                        Fav[position]="0";
                    }

                    else
                    {
                        db.Fav_update("content", season, Name[position], "1");
                        fav.setImageResource(R.drawable.favon);
                        Fav[position]="1";

                    }

                    db.close();
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
        int s = db.Story_count("content", season);

        Name = new String[s];
        Fav = new String[s];


        for(int i=0;i<s;i++)
        {

            Name[i]=db.Story_display("content", i,season,1);
            Fav[i]=db.Story_display("content", i, season, 4);
        }

        db.close();
    }
}
