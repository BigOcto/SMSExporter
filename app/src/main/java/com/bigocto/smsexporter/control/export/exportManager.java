package com.bigocto.smsexporter.control.export;

/**
 * Created by zhangyu
 * on 2015/2/9.
 */
public interface ExportManager {

    void write(String content);
    void read();
    void update();
}
