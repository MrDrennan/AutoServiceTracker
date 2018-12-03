package com.example.cdrennan.autoservicetracker;

public class Vehicle {

    private long id;
    private String name;
    private long mileage;
    private String make;
    private String model;
    private long year;
    private String engine;


    public void setId(long id) {
        this.id = id;
    }

    public void setMileage(long mileage) {
        this.mileage = mileage;
    }

    public void setYear(long year) {
        this.year = year;
    }

    public long getId() {
        return id;
    }

    public long getMileage() {
        return mileage;
    }

    public long getYear() {
        return year;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
