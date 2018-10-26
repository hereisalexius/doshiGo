package com.hereisalexius.doshiGo.model;

public class PrakritiResultBean {

    private boolean wet;
    private double air;
    private double fire;
    private double earth;

    public PrakritiResultBean() {
    }

    public PrakritiResultBean(boolean wet, double air, double fire, double earth) {
        this.wet = wet;
        this.air = air;
        this.fire = fire;
        this.earth = earth;
    }

    public boolean isWet() {
        return wet;
    }

    public void setWet(boolean wet) {
        this.wet = wet;
    }

    public double getAir() {
        return air;
    }

    public void setAir(double air) {
        this.air = air;
    }

    public double getFire() {
        return fire;
    }

    public void setFire(double fire) {
        this.fire = fire;
    }

    public double getEarth() {
        return earth;
    }

    public void setEarth(double earth) {
        this.earth = earth;
    }
}
