package com.example.cdrennan.autoservicetracker;

public class ServiceLog {

    private long id;
    private long serviceId;
    private double cost;
    private long mileageOfService;
    private String dateOfService;
    private String notes;

    public ServiceLog(long id, long serviceId, double cost, long mileageOfService,
                      String dateOfService, String notes) {
        this.id = id;
        this.serviceId = serviceId;
        this.cost = cost;
        this.mileageOfService = mileageOfService;
        this.dateOfService = dateOfService;
        this.notes = notes;
    }

    public ServiceLog() {
    }

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

    public String getDateOfService() {
        return dateOfService;
    }

    public void setDateOfService(String dateOfService) {
        this.dateOfService = dateOfService;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
