package com.example.cdrennan.autoservicetracker;

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

    //
}
