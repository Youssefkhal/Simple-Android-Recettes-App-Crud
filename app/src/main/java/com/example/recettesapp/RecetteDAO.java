package com.example.recettesapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class RecetteDAO {
    private DBConnect dbHelper;

    public RecetteDAO(Context context) {
        dbHelper = new DBConnect(context);
    }

    public void ajouterRecette(Recette recette) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("titre", recette.getTitre());
        values.put("description", recette.getDescription());
        db.insert("recette", null, values);
        db.close();
    }

    public ArrayList<Recette> getAllRecettes() {
        ArrayList<Recette> recettes = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM recette", null);
        while (cursor.moveToNext()) {
            Recette r = new Recette(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2)
            );
            recettes.add(r);
        }
        cursor.close();
        db.close();
        return recettes;
    }

    public void updateRecette(Recette recette) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("titre", recette.getTitre());
        values.put("description", recette.getDescription());
        db.update("recette", values, "_id=?", new String[]{String.valueOf(recette.getId())});
        db.close();
    }

    public void supprimerRecette(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("recette", "_id=?", new String[]{String.valueOf(id)});
        db.close();
    }
}
