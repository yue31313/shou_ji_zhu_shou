package com.example.naoling;

import android.content.ContentValues;  
import android.content.Context;  
import android.database.Cursor;  
import android.database.sqlite.SQLiteDatabase;  
import android.database.sqlite.SQLiteOpenHelper;  
/** 
 * ���崢������� 
 * @author wangyubin 
 * 
 */  
public class DbHelper extends SQLiteOpenHelper {  
  
    private final static String DB_NAME = "alarm.db";  
    private final static int DB_VERSION = 1;  
    private SQLiteDatabase database;  
    private Cursor cursor;  
  
    /** 
     * �����ݿ� 
     *  
     * @param context 
     */  
    public DbHelper(Context context) {  
        super(context, DB_NAME, null, DB_VERSION);  
    }  
  
    @Override  
    public void onCreate(SQLiteDatabase db) {  
        // TODO Auto-generated method stub  
        db.execSQL("CREATE TABLE IF NOT EXISTS " + "ALARM" + "("  
                + "ID VARCHAR(20)," + "DATE TEXT)");  
  
    }  
  
    @Override  
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {  
        // TODO Auto-generated method stub  
  
    }  
  
    /** 
     * �ж��Ƿ���� 
     *  
     * @param id 
     * @return 
     */  
    public boolean isExist(String id) {  
        database = getReadableDatabase();  
        cursor = database.query("ALARM", new String[] { "ID" }, "ID like ?",  
                new String[] { id }, null, null, null);  
        return cursor.moveToNext();  
    }  
  
    /** 
     * ��ѯ�������� 
     *  
     * @return 
     */  
    public Cursor query() {  
        database = getReadableDatabase();  
        cursor = database.query("ALARM", null, null, null, null, null, null);  
        return cursor;  
    }  
  
    /** 
     * ɾ������ 
     *  
     * @param id 
     */  
    public void delete(String id) {  
        database = getWritableDatabase();  
        database.delete("ALARM", "ID like ?", new String[] { id });  
    }  
  
    /** 
     * �������� 
     *  
     * @param id 
     * @param date 
     */  
    public void update(String id, String date) {  
        database = getWritableDatabase();  
        ContentValues cv = new ContentValues();  
        if (isExist(id)) {  
            cv.put("DATE", date);  
            database.update("ALARM", cv, "ID like ?", new String[] { id });  
        } else {  
            cv.put("ID", id);  
            cv.put("DATE", date);  
            database.insert("ALARM", null, cv);  
        }  
    }  
  
    public void closeCursor() {  
        if (null != cursor) {  
            cursor.close();  
        }  
    }  
  
    public void closeDatabase() {  
        if (null != database) {  
            this.database.close();  
        }  
    }  
  
}  
