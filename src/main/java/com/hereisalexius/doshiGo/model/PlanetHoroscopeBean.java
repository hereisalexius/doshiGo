package com.hereisalexius.doshiGo.model;

public class PlanetHoroscopeBean {
    private Planet planet;
    private Degree degree;
    private Zodiac zodiac;

    public PlanetHoroscopeBean() {
    }

    public PlanetHoroscopeBean(Planet planet, Degree degree, Zodiac zodiac) {
        this.planet = planet;
        this.degree = degree;
        this.zodiac = zodiac;
    }

    public Planet getPlanet() {
        return planet;
    }

    public void setPlanet(Planet planet) {
        this.planet = planet;
    }

    public Degree getDegree() {
        return degree;
    }

    public void setDegree(Degree degree) {
        this.degree = degree;
    }

    public Zodiac getZodiac() {
        return zodiac;
    }

    public void setZodiac(Zodiac zodiac) {
        this.zodiac = zodiac;
    }
}
