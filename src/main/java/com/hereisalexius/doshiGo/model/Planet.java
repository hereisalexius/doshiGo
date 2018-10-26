package com.hereisalexius.doshiGo.model;

public enum Planet {
    LAGNA(0, 0d),
    SUN(1, 5d),
    MOON(2, 5d),
    MARS(3, 3d),
    MERCURY(4, 3d),
    JUPITER(5, 2d),
    VENUS(6, 3d),
    SATURN(7, 2d),
    RAHU(8, 0.5d),
    KETU(9, 0.5d),
    UNKNOWN(10, 0);

    private int id;
    private double price;

    Planet(int id, double price) {
        this.id = id;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public static Planet getById(int id) {
        for (Planet e : Planet.values()) {
            if (e.getId() == id) return e;
        }
        return UNKNOWN;
    }
}
