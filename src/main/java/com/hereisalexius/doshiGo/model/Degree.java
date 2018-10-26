package com.hereisalexius.doshiGo.model;

public class Degree {

    private int degree;
    private int minuta;
    private int secunda;

    public Degree() {
    }

    public Degree(double decimal) {
        fromDecimal(decimal);
    }

    public Degree(int degree, int minuta, int secunda) {
        this.degree = degree;
        this.minuta = minuta;
        this.secunda = secunda;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public int getMinuta() {
        return minuta;
    }

    public void setMinuta(int minuta) {
        this.minuta = minuta;
    }

    public int getSecunda() {
        return secunda;
    }

    public void setSecunda(int secunda) {
        this.secunda = secunda;
    }

    public double asDecimal() {
        return degree + minuta / 60d + secunda / 3600d;
    }

    public void fromDecimal(double formated) {
        degree = (int) formated;
        minuta = (int) ((formated - degree) * 60d);
        secunda = (int) ((formated - degree - minuta / 60d) * 3600d);
    }

    @Override
    public String toString() {
        return degree + " " + minuta + "' " + secunda + "''";
    }

}
