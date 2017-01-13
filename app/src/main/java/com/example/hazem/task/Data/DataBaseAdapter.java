package com.example.hazem.task.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Hazem on 1/4/2017.
 */

public class DataBaseAdapter {

    DataBaseHelper dataBaseHelper;

    public DataBaseAdapter(Context context){
        dataBaseHelper = new DataBaseHelper(context);
    }


    public long insertUser(String name,String email,String password){

        SQLiteDatabase sq = dataBaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseHelper.NAME,name);
        contentValues.put(DataBaseHelper.EMAIL,email);
        contentValues.put(DataBaseHelper.PASSWORD,password);
        long id = sq.insert(DataBaseHelper.TABLE_NAME,null,contentValues);

        return id;

    }

    public boolean checkEmail(String email){

        SQLiteDatabase sq = dataBaseHelper.getReadableDatabase();
        String quary = "select "+DataBaseHelper.EMAIL+" from "+DataBaseHelper.TABLE_NAME;

        Cursor cursor = sq.rawQuery(quary,null);

        while (cursor.moveToNext()){
            int emailidx = cursor.getColumnIndex(DataBaseHelper.EMAIL);
            String storedName = cursor.getString(emailidx);
            Log.d("email",storedName);
            if (email.equals(storedName)){
                Log.d("match","success");
                return true;
            }
        }
        return false;
    }

    public boolean checkUser(String email,String password){

        SQLiteDatabase sq = dataBaseHelper.getReadableDatabase();
        String quary = "select "+DataBaseHelper.UID+","+DataBaseHelper.EMAIL+","+DataBaseHelper.PASSWORD+" from "+DataBaseHelper.TABLE_NAME;

        Cursor cursor = sq.rawQuery(quary,null);

        while (cursor.moveToNext()){
            int emailidx = cursor.getColumnIndex(DataBaseHelper.EMAIL);
            int passindx = cursor.getColumnIndex(DataBaseHelper.PASSWORD);
            String storedName = cursor.getString(emailidx);
            String storedPass = cursor.getString(passindx);
            Log.d("email",storedName);
            Log.d("Pass",storedPass);
            if (email.equals(storedName) && password.equals(storedPass)){
                Log.d("match","success");
                return true;
            }
        }
        return false;
    }

    public User logedUserData(String email, String password) {

        SQLiteDatabase sq = dataBaseHelper.getReadableDatabase();
        String quary = "select "+DataBaseHelper.UID+","+DataBaseHelper.NAME+","+DataBaseHelper.EMAIL+","+DataBaseHelper.PASSWORD+" from "+DataBaseHelper.TABLE_NAME;

        Cursor cursor = sq.rawQuery(quary,null);

        while (cursor.moveToNext()){
            int ididx = cursor.getColumnIndex(DataBaseHelper.UID);
            int nameidx = cursor.getColumnIndex(DataBaseHelper.NAME);
            int emailidx = cursor.getColumnIndex(DataBaseHelper.EMAIL);
            int passindx = cursor.getColumnIndex(DataBaseHelper.PASSWORD);
            String storedUserId = cursor.getString(ididx);
            String storedName = cursor.getString(nameidx);
            String storedEmail = cursor.getString(emailidx);
            String storedPass = cursor.getString(passindx);
            Log.d("DataBase",storedUserId);
            Log.d("DataBase",storedName);
            Log.d("DataBase",storedEmail);
            Log.d("DataBase",storedPass);
            if (email.equals(storedEmail) && password.equals(storedPass)){
                Log.d("match","success");
                User user = new User();
                user.setId(storedUserId);
                user.setName(storedName);
                user.setEmai(storedEmail);
                user.setPassword(storedPass);
                return user;
            }
        }
        return null;

    }

    static class DataBaseHelper extends SQLiteOpenHelper{
        private static final String DATABASE_NAME= "usersDataBase";
        private static final String TABLE_NAME= "usersTable";
        private static final int DATABASE_VERSION= 1;
        private static final String UID = "_id";
        private static final String NAME = "name";
        private static final String EMAIL = "email";
        private static final String PASSWORD = "password";
        private static final String USER_ID = "_idOfUser";

        public DataBaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL( "create table usersTable " +
                        "(_id integer primary key autoincrement, name text,email text,password text)"
                );

                Log.d("DATABASE","CREATED");

            }catch (SQLException e){
                Log.d("Error",""+e);
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            try {
                db.execSQL("DROP TABLE  IF EXISTS contacts"+TABLE_NAME);
                onCreate(db);

            }catch (SQLException e){
                Log.d("Error",""+e);
            }
        }

    }

}
