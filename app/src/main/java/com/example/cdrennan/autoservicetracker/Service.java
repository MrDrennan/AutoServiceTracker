package com.example.cdrennan.autoservicetracker;

public class Service {

    private long id;
    private long vehicleId;
    private String name;
    private long milesLeft;
    private long monthsLeft;
    private String description;
    private long milesInterval;
    private long monthsInterval;
    private long usesMonthsInterval; //boolean

    public static final long TRUE = 1;
    public static final long FALSE = 0;

    public Service() {
    }

    public Service(long id, long vehicleId, String name, long milesLeft, long monthsLeft,
                   String description, long milesInterval, long monthsInterval,
                   long usesMonthsInterval) {
        this.id = id;
        this.vehicleId = vehicleId;
        this.name = name;
        this.milesLeft = milesLeft;
        this.monthsLeft = monthsLeft;
        this.description = description;
        this.milesInterval = milesInterval;
        this.monthsInterval = monthsInterval;
        this.usesMonthsInterval = usesMonthsInterval;
    }

    public boolean getUsesMonthsInterval() {
        return usesMonthsInterval == Service.TRUE;
    }

    public void setUsesMonthsInterval(boolean usesMonthsInterval) {
        this.usesMonthsInterval = usesMonthsInterval ? TRUE: FALSE;
    }

    public long getMilesLeft() {
        return milesLeft;
    }

    public long getMonthsLeft() {
        return monthsLeft;
    }

    public void setMilesLeft(long milesLeft) {
        this.milesLeft = milesLeft;
    }

    public void setMonthsLeft(long monthsLeft) {
        this.monthsLeft = monthsLeft;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getMonthsInterval() {
        return monthsInterval;
    }

    public void setMonthsInterval(long monthsInterval) {
        this.monthsInterval = monthsInterval;
    }

    public long getMilesInterval() {
        return milesInterval;
    }

    public void setMilesInterval(long milesInterval) {
        this.milesInterval = milesInterval;
    }

    public long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name;
    }

}
