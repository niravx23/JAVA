package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {


    public DbHelper(@Nullable Context context){
        super(context,"Login.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase myDb) {
        myDb.execSQL("create Table users(username Text primary key,password Text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase myDb, int oldVersion, int newVersion) {
        myDb.execSQL("drop Table if exists users");
    }

    public Boolean insertData(String username, String password){
        SQLiteDatabase myDb = this.getWritableDatabase() ;
        ContentValues contentvalues = new ContentValues();
        contentvalues.put("username", username);
        contentvalues.put("password",password) ;
        long result = myDb.insert("users", null, contentvalues);

        if(result == -1){
            return false ;
        }
        else return true ;
    }


    public Boolean checkusername(String username){
        SQLiteDatabase myDb = this.getWritableDatabase() ;

        Cursor cursor = myDb.rawQuery("select * from users where username = ?", new String[] {username});

        if(cursor.getCount()>=1){
                return true ;
        }
        else return false;
    }

    public Boolean checkUsernamePassword(String username, String password){

         SQLiteDatabase myDb = this.getWritableDatabase();
         Cursor cursor = myDb.rawQuery("select * from users where username = ? and password =  ?",  new String[] {username,password}) ;
        if(cursor.getCount()>0){
            return true ;
        }
        else{
            return false;
        }

    }
}
