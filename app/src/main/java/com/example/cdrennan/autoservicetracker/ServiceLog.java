package com.example.cdrennan.autoservicetracker;

public class ServiceLog {

    private long id;
    private long serviceId;
    private double cost;
    private long mileageOfService;
    private long dateOfService;
    private String notes;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getServiceId() {
        return serviceId;
    }

    public void setServiceId(long serviceId) {
        this.serviceId = serviceId;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public long getMileageOfService() {
        return mileageOfService;
    }

    public void setMileageOfService(long mileageOfService) {
        this.mileageOfService = mileageOfService;
    }

    public long getDateOfService() {
        return dateOfService;
    }

    public void setDateOfService(long dateOfService) {
        this.dateOfService = dateOfService;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
