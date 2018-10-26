package com.hereisalexius.doshiGo.model;

public class BirthDataBean {

    private BirthDate date;
    private BirthLoacation location;


    public BirthDataBean() {
        date = new BirthDate();
        location = new BirthLoacation();
    }

    public BirthDataBean(BirthDate date, BirthLoacation location) {
        this.date = date;
        this.location = location;
    }

    public BirthDate getDate() {
        return date;
    }

    public void setDate(BirthDate date) {
        this.date = date;
    }

    public BirthLoacation getLocation() {
        return location;
    }

    public void setLocation(BirthLoacation location) {
        this.location = location;
    }
}
