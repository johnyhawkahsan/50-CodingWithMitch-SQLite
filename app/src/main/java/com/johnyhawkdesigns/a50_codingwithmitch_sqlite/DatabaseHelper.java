package com.johnyhawkdesigns.a50_codingwithmitch_sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Ahsan on 7/18/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String TABLE_NAME = "people_table";
    private static final String COL1_ID = "ID";
    private static final String COL2_name = "name";

    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" + COL1_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL2_name + " TEXT)";
        db.execSQL(createTableQuery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //This method will store string "item" to our database, the return type is boolean because it will return -1 if data is not inserted.
    public boolean addData(String item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2_name, item); //COL2 is "name" and item is data stored against this column.

        Log.d(TAG, "addData: Adding " + item + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);

        //if data is inserted incorrectly it will return -1
        if (result == -1){
            return false;
        } else {
            return true;
        }
    }


    /**
     * @return returns all data from database
     */
    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * from " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    /**
     *
     * @param name
     * @return return only the ID that matches the name passed in
     */
    public Cursor getItemID(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL1_ID + " FROM " + TABLE_NAME +
                " WHERE " + COL2_name + " = '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }


    /**
     * Updates the name field
     * @param newName
     * New name that needs to be entered
     * @param id
     * ID of the item being modified
     * @param oldName
     * oldName that needs to be updated
     */
    public void updateName(String newName, int id, String oldName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL2_name +
                " = '" + newName + "' WHERE " + COL1_ID + " = '" + id + "'" +
                " AND " + COL2_name + " = '" + oldName + "'";

        //Query Simplified = UPDATE people_table SET name = 'Ahsan' WHERE ID = '1' AND name = 'Ahsaaaaan'

        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: Setting name to " + newName);
        db.execSQL(query);
    }

    /**
     * Delete data
     * @param id
     * ID of the item being deleted
     * @param name
     * oldName that needs to be deleted
     */
    public void deleteName(String name, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Delete FROM " + TABLE_NAME + " WHERE " +
                COL1_ID + "= '" + id + "'" +
                " AND " + COL2_name + " = '" + name + "'";

        Log.d(TAG, "deleteName: query: " + query);
        Log.d(TAG, "deleteName: Deleting " + name + " from database.");
        db.execSQL(query);
    }


}







