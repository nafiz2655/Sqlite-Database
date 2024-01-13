package com.example.sqlitedatabase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.core.app.NavUtils;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class ImageDatabase extends SQLiteAssetHelper {
    public ImageDatabase(Context context) {
        super(context, "image.db", null,1);
    }

    public Cursor getData(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * from image_insart", null);
        return cursor;
    }
}
