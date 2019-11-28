package com.fcpc.chibogservices.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CoreDB extends SQLiteOpenHelper {

    private static final String DB_NAME = "Chibog.db";
    private static final int VERSION = 1;

    protected class UserInformation{
        public static final String TABLE_NAME = "tbl_user_info";
        
    }

    public CoreDB(Context ct){
        super(ct,DB_NAME,null,VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
