package com.hereisalexius.doshiGo.service;

import com.hereisalexius.doshiGo.model.Planet;
import com.hereisalexius.doshiGo.model.PlanetHoroscopeBean;
import com.hereisalexius.doshiGo.model.PrakritiResultBean;

import java.util.List;

public class PrakritiProvider {

    private List<PlanetHoroscopeBean> horoscope;


    public PrakritiProvider(List<PlanetHoroscopeBean> horoscope) {
        this.horoscope = horoscope;
    }

    public void setHoroscope(List<PlanetHoroscopeBean> horoscope) {
        this.horoscope = horoscope;
    }

    public PrakritiResultBean calc() {
        PrakritiResultBean result = new PrakritiResultBean();

        double water = 0;
        double air = 0;
        double earth = 0;
        double fire = 0;

        for (PlanetHoroscopeBean ph : horoscope) {
            if (!ph.getPlanet().equals(Planet.LAGNA) || !ph.getPlanet().equals(Planet.UNKNOWN)) {
                double planetPrice = ph.getPlanet().getPrice();
                switch (ph.getZodiac().getElement()) {
                    case AIR:
                        air += planetPrice;
                        break;
                    case EARTH:
                        earth += planetPrice;
                        break;
                    case FIRE:
                        fire += planetPrice;
                        break;
                    case WATER:
                        water += planetPrice;
                        break;
                }
            }
        }

        result.setWet(water >= 5);

        earth += water;
        fire += water;

        double total = air + earth + fire;

        result.setAir(air / total);
        result.setEarth(earth / total);
        result.setFire(fire / total);

        return result;
    }


}
