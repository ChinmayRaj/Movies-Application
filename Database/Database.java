package com.example.movieapplication.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {

    public Database(Context applicationContext,  String UserDetail,  Object o, int i) {
        super(applicationContext, UserDetail, (SQLiteDatabase.CursorFactory)o, i);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String qry1= "create table userdetail(username text,email text,password text)";
        db.execSQL(qry1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void register(String username,String email,String password){
        ContentValues cv=new ContentValues();
        cv.put("username",username);
        cv.put("email",email);
        cv.put("password",password);
        SQLiteDatabase db=getWritableDatabase();
        db.insert("userdetail",null,cv);
        db.close();
    }
    public String getemail(String username,String password){
        String email="";
        String str[]=new String[2];
        str[0]=username;
        str[1]=password;
        SQLiteDatabase db=getReadableDatabase();
        @SuppressLint("Recycle") Cursor c=db.rawQuery("select email from userdetail where username=? and password=?",str);
        if(c.moveToFirst()){
            email=c.getString(0);
        }
        return email;
    }
    public int login(String username,String password){
        int result=0;
        String str[]=new String[2];
        str[0]=username;
        str[1]=password;
        SQLiteDatabase db=getReadableDatabase();
        @SuppressLint("Recycle") Cursor c=db.rawQuery("select * from userdetail where username=? and password=?",str);
        if(c.moveToFirst()){
            result=1;
        }
        return result;
    }
}
