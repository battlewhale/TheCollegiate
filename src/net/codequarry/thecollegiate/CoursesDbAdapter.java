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
 * Simple course database access helper class. Defines the basic CRUD operations
 * for The Collegiate, and gives the ability to list all courses as well as
 * retrieve or modify a specific course.
 * 
 */
public class CoursesDbAdapter {

	public static final String KEY_ROWID = "_id";
	public static final String KEY_TITLE = "title";
    public static final String KEY_PROFESSOR = "professor";
    public static final String KEY_COURSECODE = "coursecode";
    
    private static final String TAG = "DbAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    /**
     * SQL statement that creates the table
     */
    private static final String DATABASE_CREATE =
        "create table courses (_id integer primary key autoincrement, " +
        "title text not null, professor text not null, coursecode text not null);";

    private static final String DATABASE_NAME = "data";
    private static final String DATABASE_TABLE = "courses";
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
            db.execSQL("DROP TABLE IF EXISTS courses");
            onCreate(db);
        }
    }

    /**
     * Constructor - takes the context to allow the database to be
     * opened/created
     * 
     * @param ctx the Context within which to work
     */
    public CoursesDbAdapter(Context ctx)
    {
        this.mCtx = ctx;
    }

    /**
     * Open the notes database. If it cannot be opened, try to create a new
     * instance of the database. If it cannot be created, throw an exception to
     * signal the failure
     * 
     * @return this (self reference, allowing this to be chained in an
     *         initialization call)
     * @throws SQLException if the database could be neither opened or created
     */
    public CoursesDbAdapter open() throws SQLException
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
     * Create a new course using the title, professor, and coursecode. If the course is
     * successfully created return the new rowId for that course, otherwise return
     * a -1 to indicate failure.
     * 
     * @param title the title of the course
     * @param professor the professor of the course
     * @param coursecode the coursecode of the course
     * @return rowId or -1 if failed
     */
    public long createCourse(String title, String professor, String coursecode)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TITLE, title);
        initialValues.put(KEY_PROFESSOR, professor);
        initialValues.put(KEY_COURSECODE, coursecode);

        return mDb.insert(DATABASE_TABLE, null, initialValues);
    }

    /**
     * Delete the course with the given rowId
     * 
     * @param rowId id of course to delete
     * @return true if deleted, false otherwise
     */
    public boolean deleteCourse(long rowId)
    {
        return mDb.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    /**
     * Return a Cursor over the list of all courses in the database
     * 
     * @return Cursor over all courses
     */
    public Cursor fetchAllCourses()
    {
        return mDb.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_TITLE,
                KEY_PROFESSOR, KEY_COURSECODE}, null, null, null, null, null);
    }

    /**
     * Return a Cursor positioned at the course that matches the given rowId
     * 
     * @param rowId id of course to retrieve
     * @return Cursor positioned to matching course, if found
     * @throws SQLException if course could not be found/retrieved
     */
    public Cursor fetchCourse(long rowId) throws SQLException
    {
        Cursor mCursor = mDb.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
		                    KEY_TITLE, KEY_PROFESSOR, KEY_COURSECODE}, 
		                    KEY_ROWID + "=" + rowId, null, null, null, null, null);
        if (mCursor != null)
        {
            mCursor.moveToFirst();
        }
        
        return mCursor;
    }

    /**
     * Update the course using the details provided. The course to be updated is
     * specified using the rowId, and it is altered to use the title and body
     * values passed in
     * 
     * @param rowId id of course to update
     * @param title value to set course title to
     * @param professor value to set course professor to
     * @param coursecode value to set course coursecode to
     * @return true if the course was successfully updated, false otherwise
     */
    public boolean updateCourse(long rowId, String title, String professor, String coursecode)
    {
        ContentValues args = new ContentValues();
        args.put(KEY_TITLE, title);
        args.put(KEY_PROFESSOR, professor);
        args.put(KEY_COURSECODE, coursecode);

        return mDb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }
}
