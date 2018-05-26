package com.example.vietpc.thisinh;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class SQLlite extends SQLiteOpenHelper {
    private static final String table_name = "table_name";
    private static final String sbd = "sbd";
    private static final String ten = "ten";
    private static final String toan = "toan";
    private static final String ly = "ly";
    private static final String hoa = "hoa";
    public SQLlite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = String.format("create table if not exists %s ( %s Integer primary key,%s Text, %s float, %s float, %s float ); ",table_name,sbd,ten,toan,ly,hoa);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists " + table_name);
        onCreate(db);
    }

    public void addThiSinh(thiSinh ts){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(sbd,ts.getSbd());
        values.put(ten,ts.getTen());
        values.put(toan,ts.getToan());
        values.put(ly,ts.getLy());
        values.put(hoa,ts.getHoa());

        db.insert(table_name,null,values);
        db.close();
    }

    public List<thiSinh> getAllThisinh(){
        List<thiSinh> thiSinhs = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sql =  " Select * from " + table_name;
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor != null){
            while (cursor.moveToNext()){
                thiSinh ts = new thiSinh(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getFloat(2),
                        cursor.getFloat(3),
                        cursor.getFloat(4));
                thiSinhs.add(ts);
            }
        }
        return  thiSinhs;
    }

    public void deleteThiSinh(int sbdThiSinh){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("Delete from " + table_name + " where " + sbd + " = " + sbdThiSinh );
        db.close();
    }

    public void deleteTS(thiSinh ts){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("Delete from " + table_name + " where " + toan + "+" + ly + "+" + "hoa " + " < " + ts.getTongDiem() + ";"  );
        db.close();
    }

    public void updateThiSinh(thiSinh ts){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(sbd,ts.getSbd());
        values.put(ten,ts.getTen());
        values.put(toan,ts.getToan());
        values.put(ly,ts.getLy());
        values.put(hoa,ts.getHoa());
        db.update(table_name,values,sbd + "=?",new String[]{ts.getSbd()+ ""});
        db.close();
    }
}
