package com.example.shortstory;


import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class Season extends ListActivity{

    private database db;

    private String[] Name;
    private String[] Tedad;
    private String season;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.season);

        db = new database(this);

        refresh();

        setListAdapter(new AA());
    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        Intent i = new Intent(Season.this,Story.class);
        i.putExtra("sea", Name[position]);
        startActivity(i);



    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        finish();
    }
    class AA extends ArrayAdapter<String>
    {

        public AA()
        {

            super(Season.this,R.layout.row_season,Name);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            LayoutInflater in = getLayoutInflater();
            View row = in.inflate(R.layout.row_season, parent, false);

            TextView name = (TextView) row.findViewById(R.id.row_season_txt_name);
            TextView tedad = (TextView) row.findViewById(R.id.row_season_txt_tedad);

            name.setText(Name[position]);
            tedad.setText(Tedad[position]);

            name.setTypeface(Main.font);


            return (row);
        }
    }

    private void refresh()
    {
        db.open();
        int s = db.Season_count("content", "Season");
        Name = new String[s];
        Tedad=new String[s];

        for(int i=0;i<s;i++)
        {

            Name[i]=db.Season_display("content", i);
            Tedad[i]=db.Story_count("content",Name[i].toString())+"";
        }

        db.close();
    }
}
 