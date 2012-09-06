package net.codequarry.thecollegiate;

/*
 * Copyright (C) 2008 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Simple class database access helper class. Defines the basic CRUD operations
 * for The Collegiate, and gives the ability to list all classes as well as
 * retrieve or modify a specific class.
 * 
 */
public class ClassesDbAdapter {

	public static final String KEY_ROWID = "_id";
    public static final String KEY_COURSEID = "courseid";
    public static final String KEY_STARTTIME = "starttime";
    public static final String KEY_ENDTIME = "endtime";
    public static final String KEY_LOCATION = "location";
    
    private static final String TAG = "DbAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    /**
     * SQL statement that creates the table
     */
    private static final String DATABASE_CREATE =
        "create table classes (_id integer primary key autoincrement, courseid int not null, " +
        "starttime real not null, endtime real not null, location string not null);";

    private static final String DATABASE_NAME = "data";
    private static final String DATABASE_TABLE = "classes";
    private static final int DATABASE_VERSION = 2;

    private final Context mCtx;

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS classes");
            onCreate(db);
        }
    }

    /**
     * Constructor - takes the context to allow the database to be
     * opened/created
     * 
     * @param ctx the Context within which to work
     */
    public ClassesDbAdapter(Context ctx)
    {
        this.mCtx = ctx;
    }

    /**
     * Open the classes database. If it cannot be opened, try to create a new
     * instance of the database. If it cannot be created, throw an exception to
     * signal the failure
     * 
     * @return this (self reference, allowing this to be chained in an
     *         initialization call)
     * @throws SQLException if the database could be neither opened or created
     */
    public ClassesDbAdapter open() throws SQLException
    {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close()
    {
        mDbHelper.close();
    }


    /**
     * Create a new class using the courseid, starttime, endtime, location. If the class is
     * successfully created return the new rowId for that course, otherwise return
     * a -1 to indicate failure.
     * 
     * @param courseid the courseid of the class
     * @param starttime the starttime of the class
     * @param endtime the endtime of the class
     * @param location the location of the class
     * @return rowId or -1 if failed
     */
    public long createClass(int courseid, double starttime, double endtime, String location)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_COURSEID, courseid);
        initialValues.put(KEY_STARTTIME, starttime);
        initialValues.put(KEY_ENDTIME, endtime);
        initialValues.put(KEY_LOCATION, location);

        return mDb.insert(DATABASE_TABLE, null, initialValues);
    }

    /**
     * Delete the class with the given rowId
     * 
     * @param rowId id of class to delete
     * @return true if deleted, false otherwise
     */
    public boolean deleteClass(long rowId)
    {
        return mDb.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    /**
     * Return a Cursor over the list of all classes in the database
     * 
     * @return Cursor over all classes
     */
    public Cursor fetchAllClasses()
    {
        return mDb.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_COURSEID,
                KEY_STARTTIME, KEY_ENDTIME, KEY_LOCATION}, null, null, null, null, null);
    }

    /**
     * Return a Cursor positioned at the class that matches the given rowId
     * 
     * @param rowId id of class to retrieve
     * @return Cursor positioned to matching class, if found
     * @throws SQLException if class could not be found/retrieved
     */
    public Cursor fetchClass(long rowId) throws SQLException
    {
        Cursor mCursor = mDb.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
		                    KEY_COURSEID, KEY_STARTTIME, KEY_ENDTIME, KEY_LOCATION}, 
		                    KEY_ROWID + "=" + rowId, null, null, null, null, null);
        if (mCursor != null)
        {
            mCursor.moveToFirst();
        }
        
        return mCursor;
    }

    /**
     * Update the class using the details provided. The class to be updated is
     * specified using the rowId, and it is altered to use the params given
     * 
     * @param rowId id of class to update
     * @param courseid value to set courseid to
     * @param starttime value to set start time to
     * @param endtime value to set end time to
     * @param location value to set location to
     * @return true if the class was successfully updated, false otherwise
     */
    public boolean updateClass(long rowId, int courseid, double starttime, double endtime, String location)
    {
        ContentValues args = new ContentValues();
        args.put(KEY_COURSEID, courseid);
        args.put(KEY_STARTTIME, starttime);
        args.put(KEY_ENDTIME, endtime);
        args.put(KEY_LOCATION, location);

        return mDb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }
}
