package com.bigocto.smsexporter.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.bigocto.smsexporter.app.R;
import com.bigocto.smsexporter.database.DbManager;

/**
 * Launch activity
 * Created by zhangyu_sx
 * on 2015/2/5.
 */
public class LaunchActivity extends Activity {

    private final String SHARE_NAME = "sms_exporter_sharepreferences";
    private final String DB_NAME = "sms_exporter_db";
    private final String TABLE_NAME = "sms_exporter_table";

    private final String FIRST_LAUNCH = "sms_exporter_first_launch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launch_activity);
        TextView launch_button = (TextView) findViewById(R.id.launch_button);
        launch_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(LaunchActivity.this, MainFrameActivity.class);
                startActivity(intent);
            }
        });

        DbManager manager = new DbManager(this);
        SQLiteDatabase db = manager.createDb(DB_NAME, Context.MODE_PRIVATE);

        if (isFirstLaunch()) {
            manager.createTable(db, TABLE_NAME);
        }else {

        }

    }

    /**
     * 判断是否是第一次启动
     *
     * @return
     */
    private boolean isFirstLaunch() {
        SharedPreferences share = getSharedPreferences(SHARE_NAME, Activity.MODE_PRIVATE);
        boolean isFirstLaunch = share.getBoolean(FIRST_LAUNCH, true);
        if (isFirstLaunch) {
            SharedPreferences.Editor editor = share.edit();
            editor.putBoolean(FIRST_LAUNCH, false);
            editor.apply();
        }
        return isFirstLaunch;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
