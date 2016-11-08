package com.sopt_semi5_remind.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sopt_semi5_remind.main.ItemData;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * DB에 대한 함수가 정의된 곳
 *
 */
public class DbOpenHelper {

    private static final String DATABASE_NAME = "memberlist.db";
    private static final int DATABASE_VERSION = 1;
    public static SQLiteDatabase mDB;
    private DatabaseHelper mDBHelper;
    private Context mCtx;

    private ArrayList<ItemData> itemDatas = null;

    private class DatabaseHelper extends SQLiteOpenHelper {

        // 생성자
        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        // 최초 DB를 만들때 한번만 호출된다.
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DataBases.CreateDB._CREATE);

        }

        // 버전이 업데이트 되었을 경우 DB를 다시 만들어 준다.
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+ DataBases.CreateDB._TABLENAME);
            onCreate(db);
        }
    }

    public DbOpenHelper(Context context){
        this.mCtx = context;
    }

    public DbOpenHelper open() throws SQLException {
        mDBHelper = new DatabaseHelper(mCtx, DATABASE_NAME, null, DATABASE_VERSION);
        mDB = mDBHelper.getWritableDatabase();
        return this;
    }
    /**
     * DB에 데이터 추가
    */
    public void DbInsert(ItemData itemData){

        mDB = mDBHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name",itemData.name);
        values.put("phoneNum",itemData.phoneNum);
        values.put("icon",itemData.icon);

        mDB.insert("memberinfo",null,values);

    }

    /**
     * DB항목 업그레이드 - 수정할 때 사용
     */
    public void DbUpdate(String id,String name, String phoneNum, String icon){

        ContentValues values = new ContentValues();
        values.put("name",name);
        values.put("phoneNum",phoneNum);
        values.put("icon",icon);

        mDB.update("memberinfo", values, "_id=?", new String[]{id});

    }

    /**
     * 항목 삭제하는 함수
     * @param id
     */
    public void DbDelete(String id) {
        mDB.delete("memberinfo", "_id=?", new String[]{id});
    }

    /**
     * 리스트뷰에 뿌릴 때
     * @return
     */
    public ArrayList<ItemData> DbMainSelect(){
        SQLiteDatabase getDb;
        getDb = mDBHelper.getReadableDatabase();
        Cursor c = getDb.rawQuery( "select * from memberinfo" , null);

        itemDatas = new ArrayList<ItemData>();
//
//        Log.i("myTag" , "갯수 : " + String.valueOf(c.getCount()));

        while(c.moveToNext()){
            int _id = c.getInt(c.getColumnIndex("_id"));
            String name = c.getString(c.getColumnIndex("name"));
            String phoneNum = c.getString(c.getColumnIndex("phoneNum"));
            int icon = c.getInt(c.getColumnIndex("icon"));


            ItemData listViewItem = new ItemData();

            listViewItem.id = _id;
            listViewItem.name = name;
            listViewItem.phoneNum = phoneNum;
            listViewItem.icon = icon;

            itemDatas.add(listViewItem);

        }


        return itemDatas;
    }

    public void close(){
        mDB.close();
    }

}
