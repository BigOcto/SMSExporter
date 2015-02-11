package com.bigocto.smsexporter.control.export;

import com.bigocto.smsexporter.model.SmsContent;
import com.lowagie.text.DocumentException;

import java.util.List;

/**
 * Created by zhangyu
 * on 2015/2/9.
 */
public interface ExportManager {

    void write(List<SmsContent> list) throws DocumentException;
    void read();
    void update();
}
