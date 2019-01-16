package com.bokun.bkjcb.voteapp.Sql;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bokun.bkjcb.voteapp.Utils.SqlContants;

/**
 * Created by DengShuai on 2018/10/8.
 * Description :
 */
public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context){
        this(context, "vote.db", null, 1);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SqlContants.CREATE_USER_TABLE);
        sqLiteDatabase.execSQL(SqlContants.CREATE_ACTIVITY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
