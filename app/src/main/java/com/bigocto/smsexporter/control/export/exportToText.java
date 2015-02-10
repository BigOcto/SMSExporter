package com.bigocto.smsexporter.control.export;

import android.os.Environment;
import com.bigocto.smsexporter.model.SmsContent;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

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
    public void write(List<SmsContent> list) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(mSaveFile));
            for (SmsContent sms : list) {
//                writer.write(sms.getId());
                writer.write("  ");
                writer.write(sms.getBody());
                writer.write("   " + sms.getType());
                writer.write(" ");
                writer.write(sms.getDate());
                writer.write("\r\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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
