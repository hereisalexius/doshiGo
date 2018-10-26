package com.hereisalexius.doshiGo.ctrl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hereisalexius.doshiGo.model.*;
import com.hereisalexius.doshiGo.service.HoroscopeProvider;
import com.hereisalexius.doshiGo.service.PrakritiProvider;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
public class PrakritiController {


    //http://192.168.1.100:8080/new_prakriti?year=1993&month=3&day=22&hour=10&timeZone=2&summerTime=1&nCoord=48.5989&eCoord=31.2919
    @RequestMapping("/horoscope")
    @Deprecated
    public List<PlanetHoroscopeBean> getHoroscope(
            @RequestParam(value = "year") int birthYear,
            @RequestParam(value = "month") int birthMonth,
            @RequestParam(value = "day") int birthDay,
            @RequestParam(value = "hour") double birthHour,
            @RequestParam(value = "timeZone") double timeZone,
            @RequestParam(value = "summerTime") int summerTime,
            @RequestParam(value = "nCoord", defaultValue = "0.0") double nCoord,
            @RequestParam(value = "eCoord", defaultValue = "0.0") double eCoord
    ) {

        BirthDataBean birthData = new BirthDataBean(
                new BirthDate(birthDay, birthMonth, birthYear, birthHour, timeZone, summerTime),
                new BirthLoacation(nCoord, eCoord)
        );


        return new HoroscopeProvider(birthData).calc();
    }

    @RequestMapping("/new_prakriti")
    public PrakritiResultBean getPracriti(
            @RequestParam(value = "year") int birthYear,
            @RequestParam(value = "month") int birthMonth,
            @RequestParam(value = "day") int birthDay,
            @RequestParam(value = "hour") double birthHour,
            @RequestParam(value = "timeZone") double timeZone,
            @RequestParam(value = "summerTime") int summerTime,
            @RequestParam(value = "nCoord", defaultValue = "0.0") double nCoord,
            @RequestParam(value = "eCoord", defaultValue = "0.0") double eCoord
    ) {


        return new PrakritiProvider(
                getHoroscope(
                        birthYear,
                        birthMonth,
                        birthDay,
                        birthHour,
                        timeZone,
                        summerTime,
                        nCoord,
                        eCoord)).calc();
    }


    @RequestMapping("/bdbSample")
    @Deprecated
    public BirthDataBean getBirthDataBeanSample() {
        return new BirthDataBean();
    }

}
