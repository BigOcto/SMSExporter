package com.bigocto.smsexporter.control.export;

import android.os.Environment;
import com.bigocto.smsexporter.model.SmsContent;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


/**
 * Created by zhangyu
 * on 2015/2/11.
 */
public class ExportToPdf implements ExportManager{
    private File mSaveFile;

    public ExportToPdf(String path, String name) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            try {
                File savePath = new File(Environment.getExternalStorageDirectory().getPath() + "/" + path);
                if (!savePath.exists()) {
                    savePath.mkdir();
                }
                mSaveFile = new File(savePath, name + ".pdf");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void write(List<SmsContent> list) throws DocumentException {
        // 新建一个文档，默认是A4纸的大小，4个边框为36
        Document document = new Document();
        // 将文档输出，我们写到文件里面
        PdfWriter writer = null;
        try {
            writer = PdfWriter.getInstance(document, new FileOutputStream("HelloWorld.pdf"));
            writer.setEncryption("Hello".getBytes(), "World".getBytes(), PdfWriter.ALLOW_COPY
                    | PdfWriter.ALLOW_PRINTING, PdfWriter.STANDARD_ENCRYPTION_128);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        document.addTitle("Hello World example");
        document.addAuthor("老紫竹");
        document.addSubject("This example explains how to add metadata.");
        document.addKeywords("iText, Hello World, step 3, metadata");
        document.addCreator("My program using iText");
        // 打开文档
        document.open();
        // 写入数据
        document.add(new Paragraph("Hello World"));
        BaseFont bfChinese = null;
        try {
            bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Font FontChinese = new Font(bfChinese, 12, Font.NORMAL);
        document.add(new Paragraph("老紫竹祝大家新年好!", FontChinese));
        // 关闭文档
        document.close();
    }

    @Override
    public void read() {

    }

    @Override
    public void update() {

    }
}
