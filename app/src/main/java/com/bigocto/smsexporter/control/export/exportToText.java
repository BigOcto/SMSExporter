package com.bigocto.smsexporter.control.export;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by zhangyu
 * on 2015/2/9.
 */
public class ExportToText implements ExportManager {
    private File mSaveFile;
    public ExportToText(String path, String name) {

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            try {
                File savePath = new File(Environment.getExternalStorageDirectory().getPath() + "/" + path);
                if (!savePath.exists()) {
                    savePath.mkdir();
                }
                mSaveFile = new File(savePath, name + ".txt");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void write(String content) {

        try {
            FileOutputStream outStream = new FileOutputStream(mSaveFile);
            outStream.write("test".getBytes());
            outStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void read() {
        //Todo
    }

    @Override
    public void update() {
        //Todo
    }
}
