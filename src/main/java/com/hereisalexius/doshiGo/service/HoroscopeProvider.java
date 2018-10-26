package com.hereisalexius.doshiGo.service;

import com.hereisalexius.doshiGo.model.*;
import swisseph.SweDate;

import java.util.ArrayList;
import java.util.List;

public class HoroscopeProvider {

    private BirthDataBean input;

    public HoroscopeProvider(BirthDataBean input) {
        this.input = input;
    }

    public BirthDataBean getInput() {
        return input;
    }

    public void setInput(BirthDataBean input) {
        this.input = input;
    }

    public List<PlanetHoroscopeBean> calc() {

        SweDate swedate = new SweDate();

        swedate.setDate(
                input.getDate().getYear(),
                input.getDate().getMonth(),
                input.getDate().getDay(),
                input.getDate().getHour() - input.getDate().getTimeZone() - (double) input.getDate().getSummerTime());

        PlanetsPositionCalculator calculator = new PlanetsPositionCalculator(
                swedate,
                input.getLocation().geteCoord(),
                input.getLocation().getnCoord());

        List<PlanetHoroscopeBean> result = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            result.add(makePanetHoroscopeBean(Planet.getById(i), calculator.pos[i]));
        }
        return result;
    }


    private PlanetHoroscopeBean makePanetHoroscopeBean(Planet planet, double position) {
        if (planet == Planet.UNKNOWN || position < 0.0D) {
            return new PlanetHoroscopeBean(Planet.UNKNOWN, new Degree(position), Zodiac.UNKNOWN);
        } else {
            int d = (int) position % 30;
            int m = (int) (position % 1.0D * 60.0D);
            int s = (int) (position * 60.0D % 1.0D * 60.0D);
            Degree degree = new Degree(d, m, s);

            Zodiac zodiac = Zodiac.getById(((int) (position / 30.0D)));
            return new PlanetHoroscopeBean(planet, degree, zodiac);
        }
    }
}
