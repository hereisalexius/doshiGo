package com.hereisalexius.doshiGo.model;

public class BirthLoacation {

    private double nCoord;
    private double eCoord;

    public BirthLoacation() {
    }

    public BirthLoacation(double nCoord, double eCoord) {
        this.nCoord = nCoord;
        this.eCoord = eCoord;
    }

    public double getnCoord() {
        return nCoord;
    }

    public void setnCoord(double nCoord) {
        this.nCoord = nCoord;
    }

    public double geteCoord() {
        return eCoord;
    }

    public void seteCoord(double eCoord) {
        this.eCoord = eCoord;
    }
}
