package com.example.cdrennan.autoservicetracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
    public static final int SERVICE_ID_COL = 0;

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
        ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
        openReadableDB();
        Cursor cursor = db.query(VEHICLE_TABLE,
                null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Vehicle vehicle = new Vehicle();
            vehicle.setId(cursor.getLong(VEHICLE_ID_COL));
            vehicle.setName(cursor.getString(VEHICLE_NAME_COL));
            vehicle.setMileage(cursor.getLong(VEHICLE_MILEAGE_COL));
            vehicle.setMake(cursor.getString(VEHICLE_MAKE_COL));
            vehicle.setModel(cursor.getString(VEHICLE_MODEL_COL));
            vehicle.setYear(cursor.getLong(VEHICLE_YEAR_COL));
            vehicle.setEngine(cursor.getString(VEHICLE_ENGINE_COL));

            vehicles.add(vehicle);
        }

        if (cursor != null)
            cursor.close();
        closeDB();

        return vehicles;
    }

    public Vehicle getVehicle(String name) {
        String where = VEHICLE_NAME + "= ?";
        String[] whereArgs = { name };

        openReadableDB();
        Cursor cursor = db.query(VEHICLE_TABLE, null,
                where, whereArgs, null, null, null);
        Vehicle vehicle = null;
        cursor.moveToFirst();
        vehicle = new Vehicle(cursor.getLong(VEHICLE_ID_COL),
                cursor.getString(VEHICLE_NAME_COL));
        vehicle.setMileage(cursor.getLong(VEHICLE_MILEAGE_COL));
        vehicle.setMake(cursor.getString(VEHICLE_MAKE_COL));
        vehicle.setModel(cursor.getString(VEHICLE_MODEL_COL));
        vehicle.setYear(cursor.getLong(VEHICLE_YEAR_COL));
        vehicle.setEngine(cursor.getString(VEHICLE_ENGINE_COL));

        if (cursor != null)
            cursor.close();
        this.closeDB();

        return vehicle;
    }

    public ArrayList<Service> getServices(String vehicleName) {
        String where =
                SERVICE_VEHICLE_ID + "= ?";
        long vehicleId = getVehicle(vehicleName).getId();
        String[] whereArgs = { Long.toString(vehicleId) };

        this.openReadableDB();
        Cursor cursor = db.query(SERVICE_TABLE, null,
                where, whereArgs,
                null, null, null);
        ArrayList<Service> services = new ArrayList<Service>();
        while (cursor.moveToNext()) {
            services.add(getServiceFromCursor(cursor));
        }
        if (cursor != null)
            cursor.close();
        this.closeDB();

        return services;
    }

    public ServiceLog getServiceLog(long id) {
        String where = SERVICE_LOG_ID + "= ?";
        String[] whereArgs = { Long.toString(id) };

        this.openReadableDB();
        Cursor cursor = db.query(SERVICE_LOG_TABLE,
                null, where, whereArgs, null, null, null);
        cursor.moveToFirst();

        ServiceLog serviceLog = new ServiceLog(
                cursor.getLong(SERVICE_LOG_ID_COL),
                cursor.getLong(SERVICE_LOG_SERVICE_ID_COL),
                cursor.getDouble(SERVICE_LOG_COST_COL),
                cursor.getLong(SERVICE_LOG_MILEAGE_OF_SERVICE_COL),
                cursor.getString(SERVICE_LOG_DATE_OF_SERVICE_COL),
                cursor.getString(SERVICE_LOG_NOTES_COL));

        if (cursor != null)
            cursor.close();
        this.closeDB();

        return serviceLog;
    }

    public ArrayList<ServiceLog> getServiceLogs(String serviceName) {
        String where =
                SERVICE_LOG_SERVICE_ID + "= ?";
        long serviceId = getService(serviceName).getId();
        String[] whereArgs = { Long.toString(serviceId) };

        this.openReadableDB();
        Cursor cursor = db.query(SERVICE_TABLE, null,
                where, whereArgs,
                null, null, null);

        ArrayList<ServiceLog> serviceLogs = new ArrayList<ServiceLog>();
        while (cursor.moveToNext()) {
            ServiceLog serviceLog = new ServiceLog(
                    cursor.getLong(SERVICE_LOG_ID_COL),
                    cursor.getLong(SERVICE_LOG_SERVICE_ID_COL),
                    cursor.getDouble(SERVICE_LOG_COST_COL),
                    cursor.getLong(SERVICE_LOG_MILEAGE_OF_SERVICE_COL),
                    cursor.getString(SERVICE_LOG_DATE_OF_SERVICE_COL),
                    cursor.getString(SERVICE_LOG_NOTES_COL));

            serviceLogs.add(serviceLog);
        }
        if (cursor != null)
            cursor.close();
        this.closeDB();

        return serviceLogs;
    }

    public Service getService(String serviceName) {
        String where = SERVICE_NAME + "= ?";
        String[] whereArgs = { serviceName };

        this.openReadableDB();
        Cursor cursor = db.query(SERVICE_TABLE,
                null, where, whereArgs, null, null, null);
        cursor.moveToFirst();
        Service service = getServiceFromCursor(cursor);
        if (cursor != null)
            cursor.close();
        this.closeDB();

        return service;
    }

    public Service getService(long id) {
        String where = SERVICE_ID + "= ?";
        String[] whereArgs = { Long.toString(id) };

        this.openReadableDB();
        Cursor cursor = db.query(SERVICE_TABLE,
                null, where, whereArgs, null, null, null);
        cursor.moveToFirst();
        Service service = getServiceFromCursor(cursor);
        if (cursor != null)
            cursor.close();
        this.closeDB();

        return service;
    }

    private static Service getServiceFromCursor(Cursor cursor) {
        if (cursor == null || cursor.getCount() == 0){
            return null;
        }
        else {
            try {
                Service service = new Service(
                        cursor.getLong(SERVICE_ID_COL),
                        cursor.getLong(SERVICE_VEHICLE_ID_COL),
                        cursor.getString(SERVICE_NAME_COL),
                        cursor.getLong(SERVICE_MILES_LEFT_COL),
                        cursor.getLong(SERVICE_MONTHS_LEFT_COL),
                        cursor.getString(SERVICE_DESCRIPTION_COL),
                        cursor.getLong(SERVICE_MILES_INTERVAL_COL),
                        cursor.getLong(SERVICE_MONTHS_INTERVAL_COL),
                        cursor.getLong(SERVICE_USES_MONTHS_INTERVAL_COL));
                return service;
            }
            catch(Exception e) {
                return null;
            }
        }
    }

    public long insertVehicle(Vehicle vehicle) {
        ContentValues cv = new ContentValues();
        cv.put(VEHICLE_NAME, vehicle.getName());
        cv.put(VEHICLE_MILEAGE, vehicle.getMileage());
        cv.put(VEHICLE_MAKE, vehicle.getMake());
        cv.put(VEHICLE_MODEL, vehicle.getModel());
        cv.put(VEHICLE_YEAR, vehicle.getYear());
        cv.put(VEHICLE_ENGINE, vehicle.getEngine());

        this.openWriteableDB();
        long rowID = db.insert(VEHICLE_TABLE, null, cv);
        this.closeDB();

        return rowID;
    }

    public long insertService(Service service) {
        ContentValues cv = new ContentValues();
        cv.put(SERVICE_VEHICLE_ID, service.getVehicleId());
        cv.put(SERVICE_NAME, service.getName());
        cv.put(SERVICE_MILES_LEFT, service.getMilesLeft());
        cv.put(SERVICE_MONTHS_LEFT, service.getMonthsLeft());
        cv.put(SERVICE_DESCRIPIION, service.getDescription());
        cv.put(SERVICE_MILES_INTERVAL, service.getMilesInterval());
        cv.put(SERVICE_MONTHS_INTERVAL, service.getMonthsInterval());
        cv.put(SERVICE_USES_MONTHS_INTERVAL, service.getUsesMonthsInterval());

        this.openWriteableDB();
        long rowID = db.insert(SERVICE_TABLE, null, cv);
        this.closeDB();

        return rowID;
    }

    public long insertServiceLog(ServiceLog serviceLog) {
        ContentValues cv = new ContentValues();
        cv.put(SERVICE_LOG_SERVICE_ID, serviceLog.getServiceId());
        cv.put(SERVICE_LOG_COST, serviceLog.getCost());
        cv.put(SERVICE_LOG_MILEAGE_OF_SERVICE, serviceLog.getMileageOfService());
        cv.put(SERVICE_LOG_DATE_OF_SERVICE, serviceLog.getDateOfService());
        cv.put(SERVICE_LOG_NOTES, serviceLog.getNotes());

        this.openWriteableDB();
        long rowID = db.insert(SERVICE_LOG_TABLE, null, cv);
        this.closeDB();

        return rowID;
    }

    public int updateVehicle(Vehicle vehicle) {
        ContentValues cv = new ContentValues();
        cv.put(VEHICLE_NAME, vehicle.getName());
        cv.put(VEHICLE_MILEAGE, vehicle.getMileage());
        cv.put(VEHICLE_MAKE, vehicle.getMake());
        cv.put(VEHICLE_MODEL, vehicle.getModel());
        cv.put(VEHICLE_YEAR, vehicle.getYear());
        cv.put(VEHICLE_ENGINE, vehicle.getEngine());

        String where = VEHICLE_ID + "= ?";
        String[] whereArgs = { String.valueOf(vehicle.getId()) };

        this.openWriteableDB();
        int rowCount = db.update(VEHICLE_TABLE, cv, where, whereArgs);
        this.closeDB();

        return rowCount;
    }

    public int updateService(Service service) {
        ContentValues cv = new ContentValues();
        cv.put(SERVICE_VEHICLE_ID, service.getVehicleId());
        cv.put(SERVICE_NAME, service.getName());
        cv.put(SERVICE_MILES_LEFT, service.getMilesLeft());
        cv.put(SERVICE_MONTHS_LEFT, service.getMonthsLeft());
        cv.put(SERVICE_DESCRIPIION, service.getDescription());
        cv.put(SERVICE_MILES_INTERVAL, service.getMilesInterval());
        cv.put(SERVICE_MONTHS_INTERVAL, service.getMonthsInterval());
        cv.put(SERVICE_USES_MONTHS_INTERVAL, service.getUsesMonthsInterval());

        String where = SERVICE_ID + "= ?";
        String[] whereArgs = { String.valueOf(service.getId()) };

        this.openWriteableDB();
        int rowCount = db.update(SERVICE_TABLE, cv, where, whereArgs);
        this.closeDB();

        return rowCount;
    }

    public int updateServiceLog(ServiceLog serviceLog) {
        ContentValues cv = new ContentValues();
        cv.put(SERVICE_LOG_SERVICE_ID, serviceLog.getServiceId());
        cv.put(SERVICE_LOG_COST, serviceLog.getCost());
        cv.put(SERVICE_LOG_MILEAGE_OF_SERVICE, serviceLog.getMileageOfService());
        cv.put(SERVICE_LOG_DATE_OF_SERVICE, serviceLog.getDateOfService());
        cv.put(SERVICE_LOG_NOTES, serviceLog.getNotes());

        String where = SERVICE_LOG_ID + "= ?";
        String[] whereArgs = { String.valueOf(serviceLog.getId()) };

        this.openWriteableDB();
        int rowCount = db.update(SERVICE_LOG_TABLE, cv, where, whereArgs);
        this.closeDB();

        return rowCount;
    }

    public int deleteVehicle(long id) {
        String where = VEHICLE_ID + "= ?";
        String[] whereArgs = { String.valueOf(id) };

        this.openWriteableDB();
        int rowCount = db.delete(VEHICLE_TABLE, where, whereArgs);
        this.closeDB();

        return rowCount;
    }

    public int deleteService(long id) {
        String where = SERVICE_ID + "= ?";
        String[] whereArgs = { String.valueOf(id) };

        this.openWriteableDB();
        int rowCount = db.delete(SERVICE_TABLE, where, whereArgs);
        this.closeDB();

        return rowCount;
    }

    public int deleteServiceLog(long id) {
        String where = SERVICE_LOG_ID + "= ?";
        String[] whereArgs = { String.valueOf(id) };

        this.openWriteableDB();
        int rowCount = db.delete(SERVICE_LOG_TABLE, where, whereArgs);
        this.closeDB();

        return rowCount;
    }
}
