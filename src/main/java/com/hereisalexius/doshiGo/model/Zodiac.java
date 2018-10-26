package com.hereisalexius.doshiGo.model;

import static com.hereisalexius.doshiGo.model.Element.*;

public enum Zodiac {
    ARIES(0, FIRE),
    TAURUS(1, EARTH),
    GEMINI(2, AIR),
    CANCER(3, WATER),
    LEO(4, FIRE),
    VIRGO(5, EARTH),
    LIBRA(6, AIR),
    SCORPIO(7, WATER),
    SAGITTARIUS(8, FIRE),
    CAPRICORNUS(9, EARTH),
    ACUARIUS(10, AIR),
    PISCES(11, WATER),
    UNKNOWN(12, Element.UNKNOWN);

    private int id;
    private Element element;

    Zodiac(int id, Element element) {
        this.id = id;
        this.element = element;
    }

    public int getId() {
        return id;
    }

    public Element getElement() {
        return element;
    }

    public static Zodiac getById(int id) {
        for (Zodiac e : Zodiac.values()) {
            if (e.getId() == id) return e;
        }
        return UNKNOWN;
    }
}
