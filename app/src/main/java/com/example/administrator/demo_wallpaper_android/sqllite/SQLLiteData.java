package com.example.administrator.demo_wallpaper_android.sqllite;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.example.administrator.demo_wallpaper_android.model.Item_General;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Administrator on 11/18/2017.
 */

public class SQLLiteData {
    public static final String PATH = Environment.getDataDirectory().getPath()+"/data/com.example.administrator.demo_wallpaper_android/database/";
    public static final String  DB_NAME = "datageneral.sqlite";
    public static final String  TABLE_NAME = "general";
    public static final String  NAME = "name";
    public static final String  IMAGE = "image";
    public static final String  ID = "id";
    public static final String  CID = "cid";
    private Context context;
    private SQLiteDatabase database;

    public SQLLiteData(Context context) {
        this.context = context;
        copyDatabaseToProject();
    }
    private void  copyDatabaseToProject(){
        try {
            File file = new File(PATH+DB_NAME);
            if (file.exists()){
                return;
            }
            file.getParentFile().mkdirs();
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            InputStream inputStream = context.getAssets().open(DB_NAME);
            byte []b= new byte[1024];
            int count = inputStream.read(b);
            while (count!=-1){
                fileOutputStream.write(b,0,count);
                count= inputStream.read(b);
            }
            fileOutputStream.close();
            inputStream.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public  void openDataBase(){
        database = context.openOrCreateDatabase(PATH+DB_NAME,Context.MODE_PRIVATE,null);

    }
    public void closeDataBase(){
        database.close();
    }

    public ArrayList<Item_General> getDataGeneral(){
        ArrayList<Item_General> arrGeneral = new ArrayList<>();
        openDataBase();
        Cursor cursor = database.query(TABLE_NAME,null,null,null,null,null,null);
        int indexID = cursor.getColumnIndex(ID);
        int indexName = cursor.getColumnIndex(NAME);
        int indexImage= cursor.getColumnIndex(IMAGE);
        int indexCid = cursor.getColumnIndex(CID);
        cursor.moveToFirst();
        while (cursor.isAfterLast()== false){
            int id=cursor.getInt(indexID);
            String name = cursor.getString(indexName);
            String image = cursor.getString(indexImage);
            int cid = cursor.getInt(indexCid);
            Item_General general = new Item_General(id,name,image,cid);
            arrGeneral.add(general);
            cursor.moveToNext();
        }
        closeDataBase();
        return arrGeneral;
    }

    public long insert(Item_General item_general){
        ContentValues values = new ContentValues();
        values.put(NAME,item_general.getName());
        values.put(IMAGE,item_general.getImage());
        values.put(CID,item_general.getCid());
        openDataBase();
        long id = database.insert(TABLE_NAME, null, values);
        closeDataBase();
        return id;
    }
    public int delete(int id){
        String[] args = {id+""};
        openDataBase();
        int rows = database.delete(TABLE_NAME,ID+"=?",args);
        closeDataBase();
        return rows;
    }

}
