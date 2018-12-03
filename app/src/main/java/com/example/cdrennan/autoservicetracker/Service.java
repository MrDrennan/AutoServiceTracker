package com.example.cdrennan.autoservicetracker;

public class Service {

    private long id;
    private long vehicleId;
    private String name;
    private long milesLeft;
    private long monthsLeft;
    private String description;
    private long monthsInterval;
    private long milesInterval;
    private long usesMilesInterval;

    public long getUsesMilesInterval() {
        return usesMilesInterval;
    }

    public void setUsesMilesInterval(long usesMilesInterval) {
        this.usesMilesInterval = usesMilesInterval;
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
