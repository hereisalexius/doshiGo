package com.hereisalexius.doshiGo.model;

public class BirthDate {
    int day;
    int month;
    int year;
    double hour;
    double timeZone;
    int summerTime;

    public BirthDate() {
    }

    public BirthDate(int day, int month, int year, double hour, double timeZone, int summerTime) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.hour = hour;
        this.timeZone = timeZone;
        this.summerTime = summerTime;
    }


    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getHour() {
        return hour;
    }

    public void setHour(double hour) {
        this.hour = hour;
    }

    public double getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(double timeZone) {
        this.timeZone = timeZone;
    }

    public int getSummerTime() {
        return summerTime;
    }

    public void setSummerTime(int summerTime) {
        this.summerTime = summerTime;
    }
}
