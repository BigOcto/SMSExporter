package com.bigocto.smsexporter.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by zhangyu_sx
 * on 2015/2/5.
 */
public class DbManager {
    Context mContext;
    public DbManager(Context context) {
        this.mContext = context;
    }

    public SQLiteDatabase createDb(String db_name, int db_madel){
        return mContext.openOrCreateDatabase(db_name, db_madel, null);
    }
    public void createTable(SQLiteDatabase db, String table_name) {
        db.execSQL("DROP TABLE IF EXISTS " + table_name);
        db.execSQL("CREATE TABLE " + table_name + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, address TEXT, person TEXT, amount INTEGER, last_content TEXT, last_date TEXT)");
    }
}
