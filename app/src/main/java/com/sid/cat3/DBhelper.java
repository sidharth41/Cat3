package com.sid.cat3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

class DBhelper extends SQLiteOpenHelper {
    public DBhelper(@Nullable Context context) {
        super(context, "Sample", null, 1);
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String query ="CREATE TABLE TABLE1 (name TEXT, password TEXT)";

        try{
            sqLiteDatabase.execSQL(query);
        }
        catch (Exception e){

        }


    }
    public void addData(String Name, String Password){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",Name);
        values.put("password",Password);
        sqLiteDatabase.insert("TABLE1",null,values);

    }
    public String getData(String Name){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();


        String selectQuery = "SELECT  * FROM TABLE1  WHERE name = '" + Name+"'";
        Cursor c=sqLiteDatabase.rawQuery(selectQuery,null);
        c.moveToFirst();
        String password=c.getString(1);
        return password;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
