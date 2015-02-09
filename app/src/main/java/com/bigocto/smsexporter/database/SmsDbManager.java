package com.bigocto.smsexporter.database;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;
import com.bigocto.smsexporter.model.SmsContent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by zhangyu_sx
 * on 2015/2/4.
 */
public class SmsDbManager {

    final String SMS_URI_ALL = "content://sms/";
    final String SMS_URI_INBOX = "content://sms/inbox";
    final String SMS_URI_SEND = "content://sms/sent";
    final String SMS_URI_DRAFT = "content://sms/draft";
    final String SMS_URI_OUTBOX = "content://sms/outbox";
    final String SMS_URI_FAILED = "content://sms/failed";
    final String SMS_URI_QUEUED = "content://sms/queued";

    final String CONTACT_URL = "content://com.android.contacts/contacts";

    private Context mContext;
    private HashMap<String, SmsContent> mSmsContent = new HashMap<String, SmsContent>();

    public SmsDbManager(Context context) {
        this.mContext = context;
    }

    /**
     * 输入电话号码查找
     *
     * @param search_content 电话
     * @return 搜索结果信息
     */
    public HashMap<String, SmsContent> getSmsWithNumber(String search_content) {

        try {
            Uri uri = Uri.parse(SMS_URI_ALL);
            String[] projection = new String[]{"_id", "address", "person", "body", "date", "type"};

            Cursor cur = mContext.getContentResolver().query(uri, projection, "address = '"+search_content+"'", null, "date desc");      // 获取手机内部短信

            if (cur.moveToFirst()) {
                int index_Address = cur.getColumnIndex("address");
                int index_Person = cur.getColumnIndex("person");
                int index_Body = cur.getColumnIndex("body");
                int index_Date = cur.getColumnIndex("date");
                int index_Type = cur.getColumnIndex("type");


                int id = 0; //记录排序

                do {
                    String strAddress = cur.getString(index_Address);
                    String intPerson = cur.getInt(index_Person) + "";
                    String strBody = cur.getString(index_Body);
                    long longDate = cur.getLong(index_Date);
                    int intType = cur.getInt(index_Type);

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    Date d = new Date(longDate);
                    String strDate = dateFormat.format(d);

                    if (!mSmsContent.containsKey(strAddress)) {
                        SmsContent content = new SmsContent();
                        content.setId(++id);
                        content.setAddress(strAddress);
                        content.setAmount(1);
                        content.setLast_date(strDate);
                        content.setLast_content(strBody);
                        content.setPerson(intPerson);
                        mSmsContent.put(strAddress, content);
                    } else {
                        mSmsContent.get(strAddress).setAmount(mSmsContent.get(strAddress).getAmount() + 1);
                        mSmsContent.put(strAddress, mSmsContent.get(strAddress));
                    }

                } while (cur.moveToNext());

                if (!cur.isClosed()) {
                    cur.close();
                }
            }

        } catch (SQLiteException ex) {
            Log.d("SQLiteException in getSmsInPhone", ex.getMessage());
        }

        return mSmsContent;
    }


    /**
     * 输入姓名查找
     *
     * @param search_content 姓名
     * @return 搜索结果短信信息
     */
    public List<HashMap<String, SmsContent>> getSmsWithName(String search_content) {
        List<HashMap<String, SmsContent>> list = new ArrayList<HashMap<String, SmsContent>>();
       String[] PHONES_PROJECTION = new String[] {
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.Contacts.Photo.PHOTO_ID, ContactsContract.CommonDataKinds.Phone.CONTACT_ID };

            Uri url = Uri.parse(CONTACT_URL);
            ContentResolver resolver = mContext.getContentResolver();
            Cursor cursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,PHONES_PROJECTION, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " = " + "'"+search_content+ "'",null,null);

            if (cursor != null){
                if (cursor.moveToFirst()){
                        String phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        list.add(getSmsWithNumber(phoneNumber));
                }

            }

        if (!cursor.isClosed()) {
            cursor.close();
        }

        return list;
    }

}
