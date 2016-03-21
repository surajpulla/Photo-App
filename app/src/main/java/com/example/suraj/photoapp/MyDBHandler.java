package com.example.suraj.photoapp;

import android.database.sqlite.SQLiteOpenHelper;

import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;
public class MyDBHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION =2;
    private static final String DATABASE_NAME= "products.db";
    public static final String TABLE_PRODUCTS= "products";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PRODUCTNAME="productname";
    public static final String COLUMN_TITLE="title";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query= "CREATE TABLE "+ TABLE_PRODUCTS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COLUMN_PRODUCTNAME + " TEXT, " +
                COLUMN_TITLE + " TEXT " + ")";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_PRODUCTS);
        onCreate(db);
    }
    //adda  new row to the database
    public void addProducts(Products product){
        ContentValues values= new ContentValues();
        values.put(COLUMN_PRODUCTNAME, product.get_productname());
        values.put(COLUMN_TITLE, product.get_title());

        SQLiteDatabase db= getWritableDatabase();
        db.insert(TABLE_PRODUCTS, null, values);
    }
    public  void deleteProducts(String productName,String title){
        SQLiteDatabase db= getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_PRODUCTS
                + " WHERE " + COLUMN_PRODUCTNAME + "=\"" + productName + "\";");
        db.execSQL("DELETE FROM " + TABLE_PRODUCTS
                + " WHERE " + COLUMN_TITLE + "=\"" + title + "\";");
    }
    //print databse as a stirng
    public String databsetoString(){
        String dbString="";
        SQLiteDatabase db=getWritableDatabase();
        String query = "SELECT * FROM "+ TABLE_PRODUCTS+" WHERE 1";

        //Cursor point to a location in your results
        Cursor c= db.rawQuery(query, null);
        //move rto first row in results
        c.moveToFirst();

        while(!c.isAfterLast()){

            if(c.getString(c.getColumnIndex("productname"))!=null){
                dbString+= c.getString(c.getColumnIndex("productname"));
                dbString+="\n";
                c.moveToNext();
            }

        }
        db.close();
        return dbString;
    }

    //print databse as a stirng
    public String databsetoString1(){
        String dbString1="";
        SQLiteDatabase db=getWritableDatabase();
        String query = "SELECT * FROM "+ TABLE_PRODUCTS+" WHERE 1";

        //Cursor point to a location in your results
        Cursor c= db.rawQuery(query,null);
        //move rto first row in results
        c.moveToFirst();

        while(!c.isAfterLast()){

            if(c.getString(c.getColumnIndex("title"))!=null){
                dbString1+= c.getString(c.getColumnIndex("title"));
                dbString1+="\n";
                c.moveToNext();
            }

        }
        db.close();
        return dbString1;
    }

    public  void deleteTable(){
        SQLiteDatabase db= getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_PRODUCTS+ ";");

    }

}
