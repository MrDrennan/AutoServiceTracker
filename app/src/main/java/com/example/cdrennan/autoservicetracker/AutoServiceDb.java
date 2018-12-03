package com.example.cdrennan.autoservicetracker;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.util.ArrayList;

public class AutoServiceDb {

    // db constants
    public static final String DB_NAME = "auto_service.db";
    public static final int DB_VERSION = 1;

    // vehicle table constants
    public static final String VEHICLE_TABLE = "vehicle";

    public static final String VEHICLE_ID = "_id";
    public static final int VEHICLE_ID_COL = 0;

    public static final String VEHICLE_NAME = "name";
    public static final int VEHICLE_NAME_COL = 1;

    public static final String VEHICLE_MILEAGE = "mileage";
    public static final int VEHICLE_MILEAGE_COL = 2;

    public static final String VEHICLE_MAKE = "make";
    public static final int VEHICLE_MAKE_COL = 3;

    public static final String VEHICLE_MODEL = "model";
    public static final int VEHICLE_MODEL_COL = 4;

    public static final String VEHICLE_YEAR = "year";
    public static final int VEHICLE_YEAR_COL = 5;

    public static final String VEHICLE_ENGINE = "engine";
    public static final int VEHICLE_ENGINE_COL = 6;

    // service table constants
    public static final String SERVICE_TABLE = "service";

    public static final String SERVICE_ID = "_id";
    public static final int SERVICE_ = 0;

    public static final String SERVICE_VEHICLE_ID = "service_id";
    public static final int SERVICE_VEHICLE_ID_COL = 1;

    public static final String SERVICE_NAME = "name";
    public static final int SERVICE_NAME_COL = 2;

    public static final String SERVICE_MILES_LEFT = "miles_left";
    public static final int SERVICE_MILES_LEFT_COL = 3;

    public static final String SERVICE_MONTHS_LEFT = "months_left";
    public static final int SERVICE_MONTHS_LEFT_COL = 4;

    public static final String SERVICE_DESCRIPIION = "description";
    public static final int SERVICE_DESCRIPTION_COL = 5;

    public static final String SERVICE_MILES_INTERVAL = "miles_interval";
    public static final int SERVICE_MILES_INTERVAL_COL = 6;

    public static final String SERVICE_MONTHS_INTERVAL = "months_interval";
    public static final int SERVICE_MONTHS_INTERVAL_COL = 7;

    public static final String SERVICE_USES_MONTHS_INTERVAL = "uses_months_interval";
    public static final int SERVICE_USES_MONTHS_INTERVAL_COL = 8;

    // serviceLog table constants
    public static final String SERVICE_LOG_TABLE = "service_log";

    public static final String SERVICE_LOG_ID = "_id";
    public static final int SERVICE_LOG_ID_COL = 0;

    public static final String SERVICE_LOG_SERVICE_ID = "service_id";
    public static final int SERVICE_LOG_SERVICE_ID_COL = 1;

    public static final String SERVICE_LOG_COST = "cost";
    public static final int SERVICE_LOG_COST_COL = 2;

    public static final String SERVICE_LOG_MILEAGE_OF_SERVICE = "mileage_of_service";
    public static final int SERVICE_LOG_MILEAGE_OF_SERVICE_COL = 3;

    public static final String SERVICE_LOG_DATE_OF_SERVICE = "date_of_service";
    public static final int SERVICE_LOG_DATE_OF_SERVICE_COL = 4;

    public static final String SERVICE_LOG_NOTES = "notes";
    public static final int SERVICE_LOG_NOTES_COL = 5;

    // CREATE and DROP TABLE statements
    public static final String CREATE_VEHICLE_TABLE =
            "CREATE TABLE " + VEHICLE_TABLE + " (" +
            VEHICLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            VEHICLE_NAME + " TEXT    NOT NULL UNIQUE, " +
            VEHICLE_MILEAGE + " INTEGER NOT NULL, " +
            VEHICLE_MAKE + " TEXT, " +
            VEHICLE_MODEL + " TEXT, " +
            VEHICLE_YEAR + " INTEGER, " +
            VEHICLE_ENGINE + " TEXT);";

    public static final String CREATE_SERVICE_TABLE =
            "CREATE TABLE " + SERVICE_TABLE + " (" +
            SERVICE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            SERVICE_VEHICLE_ID + " INTEGER NOT NULL, " +
            SERVICE_NAME + " TEXT    NOT NULL UNIQUE, " +
            SERVICE_MILES_LEFT + " INTEGER, " +
            SERVICE_MONTHS_LEFT + " INTEGER, " +
            SERVICE_DESCRIPIION + " TEXT, " +
            SERVICE_MILES_INTERVAL + " INTEGER, " +
            SERVICE_MONTHS_INTERVAL + " INTEGER, " +
            SERVICE_USES_MONTHS_INTERVAL + " INTEGER);";

    public static final String CREATE_SERVICE_LOG_TABLE =
            "CREATE TABLE " + SERVICE_TABLE + " (" +
            SERVICE_LOG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            SERVICE_LOG_SERVICE_ID + " INTEGER NOT NULL, " +
            SERVICE_LOG_COST + " REAL, " +
            SERVICE_LOG_MILEAGE_OF_SERVICE + " INTEGER, " +
            SERVICE_LOG_DATE_OF_SERVICE + " TEXT, " +
            SERVICE_LOG_NOTES + " TEXT);";

    public static final String DROP_VEHICLE_TABLE =
            "DROP TABLE IF EXISTS " + VEHICLE_TABLE;

    public static final String DROP_SERVICE_TABLE =
            "DROP TABLE IF EXISTS " + SERVICE_TABLE;

    public static final String DROP_SERVICE_LOG_TABLE =
            "DROP TABLE IF EXISTS " + SERVICE_LOG_TABLE;

    private static class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name,
                        SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // create tables
            db.execSQL(CREATE_VEHICLE_TABLE);
            db.execSQL(CREATE_SERVICE_TABLE);
            db.execSQL(CREATE_SERVICE_LOG_TABLE);

            // insert default lists
            db.execSQL("INSERT INTO vehicle VALUES (1, 'Car', 100000)");
            db.execSQL("INSERT INTO vehicle VALUES (2, 'Truck', 200000)");

            // insert sample tasks
            db.execSQL("INSERT INTO service VALUES (1, 1, 'oil change', " +
                    "3100)");
            db.execSQL("INSERT INTO service VALUES (2, 1, 'replace fuel filter', " +
                    "'1200')");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db,
                              int oldVersion, int newVersion) {

            Log.d("Auto Service Tracker", "Upgrading db from version "
                    + oldVersion + " to " + newVersion);

            db.execSQL(AutoServiceDb.DROP_VEHICLE_TABLE);
            db.execSQL(AutoServiceDb.DROP_SERVICE_TABLE);
            db.execSQL(AutoServiceDb.DROP_SERVICE_LOG_TABLE);
            onCreate(db);
        }
    }

    private SQLiteDatabase db;
    private DBHelper dbHelper;

    // constructor
    public AutoServiceDb(Context context) {
        dbHelper = new DBHelper(context, DB_NAME, null, DB_VERSION);
    }

    // private methods
    private void openReadableDB() {
        db = dbHelper.getReadableDatabase();
    }

    private void openWriteableDB() {
        db = dbHelper.getWritableDatabase();
    }

    private void closeDB() {
        if (db != null)
            db.close();
    }

    // public methods
    public ArrayList<Vehicle> getVehicles() {
        ArrayList<Vehicle> lists = new ArrayList<Vehicle>();
        openReadableDB();
        Cursor cursor = db.query(VEHICLE_TABLE,
                null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Vehicle vehicle = new Vehicle();
            vehicle.setId(cursor.getInt(VEHICLE_ID_COL));
            vehicle.setName(cursor.getString(VEHICLE_NAME_COL));
            //vehicle.setMileage(cursor.get);

            lists.add(vehicle);
        }

        if (cursor != null)
            cursor.close();
        closeDB();

        return lists;
    }
    /*
    public List getList(String name) {
        String where = LIST_NAME + "= ?";
        String[] whereArgs = { name };

        openReadableDB();
        Cursor cursor = db.query(LIST_TABLE, null,
                where, whereArgs, null, null, null);
        List list = null;
        cursor.moveToFirst();
        list = new List(cursor.getInt(LIST_ID_COL),
                cursor.getString(LIST_NAME_COL));
        if (cursor != null)
            cursor.close();
        this.closeDB();

        return list;
    }

    public ArrayList<Task> getTasks(String listName) {
        String where =
                TASK_LIST_ID + "= ? AND " +
                        TASK_HIDDEN + "!='1'";
        int listID = getList(listName).getId();
        String[] whereArgs = { Integer.toString(listID) };

        this.openReadableDB();
        Cursor cursor = db.query(TASK_TABLE, null,
                where, whereArgs,
                null, null, null);
        ArrayList<Task> tasks = new ArrayList<Task>();
        while (cursor.moveToNext()) {
            tasks.add(getTaskFromCursor(cursor));
        }
        if (cursor != null)
            cursor.close();
        this.closeDB();

        return tasks;
    }

    public Task getTask(int id) {
        String where = TASK_ID + "= ?";
        String[] whereArgs = { Integer.toString(id) };

        this.openReadableDB();
        Cursor cursor = db.query(TASK_TABLE,
                null, where, whereArgs, null, null, null);
        cursor.moveToFirst();
        Task task = getTaskFromCursor(cursor);
        if (cursor != null)
            cursor.close();
        this.closeDB();

        return task;
    }

    private static Task getTaskFromCursor(Cursor cursor) {
        if (cursor == null || cursor.getCount() == 0){
            return null;
        }
        else {
            try {
                Task task = new Task(
                        cursor.getInt(TASK_ID_COL),
                        cursor.getInt(TASK_LIST_ID_COL),
                        cursor.getString(TASK_NAME_COL),
                        cursor.getString(TASK_NOTES_COL),
                        cursor.getString(TASK_COMPLETED_COL),
                        cursor.getString(TASK_HIDDEN_COL));
                return task;
            }
            catch(Exception e) {
                return null;
            }
        }
    }

    public long insertTask(Task task) {
        ContentValues cv = new ContentValues();
        cv.put(TASK_LIST_ID, task.getListId());
        cv.put(TASK_NAME, task.getName());
        cv.put(TASK_NOTES, task.getNotes());
        cv.put(TASK_COMPLETED, task.getCompletedDate());
        cv.put(TASK_HIDDEN, task.getHidden());

        this.openWriteableDB();
        long rowID = db.insert(TASK_TABLE, null, cv);
        this.closeDB();

        return rowID;
    }

    public int updateTask(Task task) {
        ContentValues cv = new ContentValues();
        cv.put(TASK_LIST_ID, task.getListId());
        cv.put(TASK_NAME, task.getName());
        cv.put(TASK_NOTES, task.getNotes());
        cv.put(TASK_COMPLETED, task.getCompletedDate());
        cv.put(TASK_HIDDEN, task.getHidden());

        String where = TASK_ID + "= ?";
        String[] whereArgs = { String.valueOf(task.getId()) };

        this.openWriteableDB();
        int rowCount = db.update(TASK_TABLE, cv, where, whereArgs);
        this.closeDB();

        return rowCount;
    }

    public int deleteTask(long id) {
        String where = TASK_ID + "= ?";
        String[] whereArgs = { String.valueOf(id) };

        this.openWriteableDB();
        int rowCount = db.delete(TASK_TABLE, where, whereArgs);
        this.closeDB();

        return rowCount;
    }
    */
}
