package com.example.recettesapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBConnect extends SQLiteOpenHelper {
    public DBConnect(@Nullable Context context) {
        super(context, "GestionRecette", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("CREATE TABLE user (_id INTEGER PRIMARY KEY AUTOINCREMENT, email TEXT UNIQUE, password TEXT)");
            db.execSQL("CREATE TABLE recette (_id INTEGER PRIMARY KEY AUTOINCREMENT, titre TEXT, description TEXT, ingredients TEXT, steps TEXT)");
        } catch (Exception e) {
            Log.e("DBConnect", "Error creating database: ", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL("DROP TABLE IF EXISTS user");
            db.execSQL("DROP TABLE IF EXISTS recette");
            onCreate(db);
        } catch (Exception e) {
            Log.e("DBConnect", "Error upgrading database: ", e);
        }
    }}