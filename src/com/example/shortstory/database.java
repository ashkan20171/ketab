package com.example.shortstory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class database extends SQLiteOpenHelper {

    public final String path="data/data/com.example.shortstory/databases/";
    public final String Name="database";


    public SQLiteDatabase mydb;

    public final Context mycontext;


    public database(Context context) {

        super(context,"database",null,1);
        mycontext=context;

    }


    @Override
    public void onCreate(SQLiteDatabase arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub

    }

    public void useable()
    {

        boolean checkdb=checkdb();

        if(checkdb)
        {

        }
        else
        {
            this.getReadableDatabase();

            try
            {

                copydatabase();
            }
            catch(IOException e)
            {

            }
        }
    }


    public void open()
    {
        mydb = SQLiteDatabase.openDatabase(path+Name, null, SQLiteDatabase.OPEN_READWRITE);

    }

    public void close()
    {
        mydb.close();
    }


    public boolean checkdb()
    {

        SQLiteDatabase db=null;

        try
        {
            db = SQLiteDatabase.openDatabase(path+Name, null, SQLiteDatabase.OPEN_READONLY);

        }
        catch(SQLException e)
        {

        }

        return db !=null ? true:false;
    }

    public void copydatabase() throws IOException
    {

        OutputStream myOutput = new FileOutputStream(path+Name);
        byte[] buffer = new byte[1024];
        int lenghth;
        InputStream myInput = mycontext.getAssets().open(Name);

        while ((lenghth = myInput.read(buffer)) > 0)
        {

            myOutput.write(buffer, 0, lenghth);
        }

        myInput.close();
        myOutput.flush();
        myOutput.close();

    }

    // Season Metood
    public Integer Season_count(String table,String filed)
    {

        Cursor cu = mydb.rawQuery("select * from "+table+" group by "+filed, null);
        int s=cu.getCount();
        return s;

    }

    public String Season_display(String table, int row)
    {

        Cursor cu = mydb.rawQuery("select * from "+table+" group by Season ", null);
        cu.moveToPosition(row);
        String s = cu.getString(3);
        return s;
    }

    // Story Metood

    public Integer Story_count(String table,String sea)
    {
        Cursor cu = mydb.rawQuery("select * from "+table+" where Season='"+sea+"' group by Name", null);
        int s=cu.getCount();
        return s;
    }

    public String Story_display(String table, int row,String sea,int filed)
    {

        Cursor cu = mydb.rawQuery("select * from "+table+" where Season='"+sea+"' group by Name", null);
        cu.moveToPosition(row);
        String s = cu.getString(filed);
        return s;
    }

    // Text Metood
    public String Text_display(String table,String sea,String name)
    {

        Cursor cu = mydb.rawQuery("select * from "+table+" where Season='"+sea+"' and Name='"+name+"'", null);
        cu.moveToFirst();
        String s = cu.getString(2);
        return s;
    }

    public void Fav_update(String table,String sea,String name,String v)
    {
        ContentValues cv = new ContentValues();
        cv.put("Fav", v);
        mydb.update("content", cv, "season='"+sea+"' and name='"+name+"'", null);
    }

    // Fav Metood

    public Integer Fav_count(String table)
    {
        Cursor cu = mydb.rawQuery("select * from "+table+" where Fav=1 group by Name", null);
        int s=cu.getCount();
        return s;
    }

    public String Fav_display(String table,int row,int filed)
    {

        Cursor cu = mydb.rawQuery("select * from "+table+" where Fav=1 group by Name", null);
        cu.moveToPosition(row);
        String s = cu.getString(filed);
        return s;
    }

    // Search Metood

    public Integer Search_count(String word, String filed)
    {
        Cursor cu = mydb.rawQuery("select * from content where "+filed+" Like '%"+word+"%'", null);
        int s=cu.getCount();
        return s;
    }

    public String Search_display(int row,int cal,String word, String filed)
    {

        Cursor cu = mydb.rawQuery("select * from content where "+filed+" Like '%"+word+"%'", null);
        cu.moveToPosition(row);
        String s = cu.getString(cal);
        return s;
    }

    // Story All Mettod

    public Integer StoryAll_count(String table)
    {
        Cursor cu = mydb.rawQuery("select * from "+table+" group by Name", null);
        int s=cu.getCount();
        return s;
    }

    public String StoryAll_display(String table,int row,int filed)
    {

        Cursor cu = mydb.rawQuery("select * from "+table+" group by Name order by Name", null);
        cu.moveToPosition(row);
        String s = cu.getString(filed);
        return s;
    }

}