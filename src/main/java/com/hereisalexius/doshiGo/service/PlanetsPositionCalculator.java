package com.hereisalexius.doshiGo.service;

import java.io.PrintStream;
import java.util.ResourceBundle;
import swisseph.DblObj;
import swisseph.SweDate;
import swisseph.SwissEph;
import swisseph.SwissLib;

public class PlanetsPositionCalculator {

    public static final int LAGNA = 0;
    public static final int SY = 1;
    public static final int CH = 2;
    public static final int MA = 3;
    public static final int BU = 4;
    public static final int GU = 5;
    public static final int SK = 6;
    public static final int SA = 7;
    public static final int RH = 8;
    public static final int KT = 9;
    public static final int[] houseOwner = new int[]{2, 5, 3, 1, 0, 3, 5, 2, 4, 6, 6, 4};
    public static final int AYANAMSHA = 0;
    public static final int SIDEREAL_TIME = 1;
    public static final int SUN_RISE = 2;
    public static final int SUN_SET = 3;
    public static final int SAVANDAY = 4;
    public static final int DINMANA = 5;
    public static final int RATRIMANA = 6;
    public static final int MISHRAMAANKAALA = 7;
    public static final int ISHTAKAALA = 8;
    public static final int CHAALANA = 9;
    public static final int SURYA_SPASHTA = 10;
    public static final int BHAVALAGNA = 11;
    public static final int HORALAGNA = 12;
    public static final int GHATILAGNA = 13;
    public static final int GHATI_SAVANDAY = 0;
    public static final int PALA_SAVANDAY = 1;
    public static final int VIPALA_SAVANDAY = 2;
    public static final int GHATI_ISHTAKAALA = 3;
    public static final int PALA_ISHTAKAALA = 4;
    public static final int VIPALA_ISHTAKAALA = 5;
    //public String[] natDignityNames;
    public double[] pos = new double[10];
    int[] rashiOfGraha = new int[10];
    boolean[] retr = new boolean[10];
    public SweDate sDate = null;
    private double geoLong;
    private double geoLat;
    private double geoHeight;
    private double temperature;
    private double[] dres = new double[6];
    private StringBuffer serr = new StringBuffer();
    private int[] pl = new int[]{-1, 0, 1, 4, 2, 5, 3, 6, 11};
    PlanetsPositionSorter spos = new PlanetsPositionSorter();
    private SwissEph sw = null;
    private SwissLib sl = null;
    private double[] cusp = new double[13];
    private double[] ac = new double[10];
    private double[] dFields = new double[14];
    private boolean[] isSet = new boolean[14];
    private int[] iFields = new int[10];
    private boolean fieldsAreSet = false;
    private boolean locationHasChanged = true;

    public void setTopo(double d, double d2, double d3) {
        this.geoLong = d;
        this.geoLat = d2;
        this.geoHeight = d3;
        this.locationHasChanged = true;
        this.clearData();
    }

    int grahaCntInHouse(int n) {
        return this.grahaCntInHouse(n, 1);
    }

    int grahaCntInHouse(int n, int n2) {
        return this.grahaCntInHouse(n, 1, 0.0);
    }

    int grahaCntInHouse(int n, int n2, double d) {
        int n3 = (n - 1 + (int) (this.pos[0] * (double) n2 % 360.0) / 30) % 12 + 1;
        double d2 = (double) (n3 - 1) * 30.0 + d;
        double d3 = d2 + 30.0;
        int n4 = 0;
        int n5 = 0;
        while (n5 < 9) {
            if (this.spos.pos[n5] * (double) n2 % 360.0 >= d2 && this.spos.pos[n5] * (double) n2 % 360.0 < d3 || this.spos.pos[n5] * (double) n2 % 360.0 + 360.0 >= d2 && this.spos.pos[n5] * (double) n2 % 360.0 + 360.0 < d3 || this.spos.pos[n5] * (double) n2 % 360.0 - 360.0 >= d2 && this.spos.pos[n5] * (double) n2 % 360.0 - 360.0 < d3) {
                ++n4;
            }
            ++n5;
        }
        return n4;
    }

    public boolean calc() {
        this.clearData();
        int n = 65794;
        this.sw.swe_set_topo(this.geoLong, this.geoLat, 0.0);
        this.sw.swe_set_sid_mode(1, 0.0, 0.0);
        double d = this.sDate.getJulDay() + this.sDate.getDeltaT();
        this.retr[0] = false;
        int n2 = 1;
        while (n2 < 9) {
            int n3 = this.sw.swe_calc(d, this.pl[n2], n, this.dres, this.serr);
            if (n3 < 0) {
                System.err.println(this.serr.toString());
                return false;
            }
            this.pos[n2] = this.dres[0];
            this.spos.pos[n2 - 1] = this.dres[0];
            this.spos.graha[n2 - 1] = n2 - 1;
            this.rashiOfGraha[n2] = (int) (this.dres[0] / 30.0) + 1;
            this.retr[n2] = this.dres[3] < 0.0;
            ++n2;
        }
        this.pos[9] = (this.pos[8] + 180.0) % 360.0;
        this.spos.pos[8] = this.pos[9];
        this.spos.graha[8] = 8;
        this.rashiOfGraha[9] = (int) (this.pos[9] / 30.0) + 1;
        this.retr[9] = this.retr[8];
        this.spos.sort();
        this.sw.swe_houses(this.sDate.getJulDay(), 65536, this.geoLat, this.geoLong, 65, this.cusp, this.ac);
        this.pos[0] = this.ac[0];
        this.rashiOfGraha[0] = (int) (this.ac[0] / 30.0) + 1;
        return true;
    }

    public double getD(int n) {
        switch (n) {
            case 2: {
                if (this.isSet[2]) {
                    break;
                }
                this.calcSunRiseSet();
                break;
            }
            case 3: {
                if (this.isSet[3]) {
                    break;
                }
                this.calcSunRiseSet();
                break;
            }
            case 4: {
                if (this.isSet[4]) {
                    break;
                }
                this.calcSunRiseSet();
                break;
            }
            case 0: {
                if (this.isSet[0]) {
                    break;
                }
                this.calcAyanamsha();
                break;
            }
            case 1: {
                if (this.isSet[1]) {
                    break;
                }
                this.calcSiderealTime();
                break;
            }
            default: {
                if (this.fieldsAreSet) {
                    break;
                }
                this.calcFields();
                break;
            }
        }
        return this.dFields[n];
    }

    void calcSunRiseSet() {
        int n;
        StringBuffer stringBuffer = new StringBuffer();
        DblObj dblObj = new DblObj();
        DblObj dblObj2 = new DblObj();
        double d = this.sDate.getJulDay();
        d = d % 1.0 > 0.5 ? (double) ((int) d) + 0.5 : (double) ((int) d) - 0.5;
        int n2 = this.sw.swe_rise_trans(d, 0, null, 2, 1, new double[]{this.geoLong, this.geoLat, this.geoHeight}, 1013.25, this.temperature, dblObj, stringBuffer);
        if (n2 < 0) {
            return;
        }
        if (this.sDate.getJulDay() < dblObj.val) {
            dblObj2.val = dblObj.val;
            n2 = this.sw.swe_rise_trans(d -= 1.0, 0, null, 2, 1, new double[]{this.geoLong, this.geoLat, this.geoHeight}, 1013.25, this.temperature, dblObj, stringBuffer);
        } else {
            n2 = this.sw.swe_rise_trans(d + 1.0, 0, null, 2, 1, new double[]{this.geoLong, this.geoLat, this.geoHeight}, 1013.25, this.temperature, dblObj2, stringBuffer);
        }
        if (n2 < 0) {
            return;
        }
        if (dblObj.val == 0.0) {
            n = new SweDate(d).getMonth();
            dblObj.val = n > 3 && n < 10 && this.geoLat < 0.0 || (n < 4 || n > 9) && this.geoLat >= 0.0 ? -2.0 : -1.0;
        }
        this.dFields[2] = dblObj.val;
        this.isSet[2] = true;
        this.dFields[4] = dblObj2.val == 0.0 || dblObj.val == 0.0 ? -1.0 : (dblObj2.val - dblObj.val) * 60.0;
        this.isSet[4] = true;
        n2 = this.sw.swe_rise_trans(d, 0, null, 2, 2, new double[]{this.geoLong, this.geoLat, this.geoHeight}, 1013.25, this.temperature + 6.0, dblObj, stringBuffer);
        if (n2 < 0) {
            return;
        }
        if (dblObj.val == 0.0) {
            n = new SweDate(d).getMonth();
            dblObj.val = n > 3 && n < 10 && this.geoLat < 0.0 || (n < 4 || n > 9) && this.geoLat >= 0.0 ? -2.0 : -1.0;
        }
        this.dFields[3] = dblObj.val;
        this.isSet[3] = true;
        this.locationHasChanged = false;
    }

    void calcAyanamsha() {
        this.dFields[0] = this.sw.swe_get_ayanamsa(this.sDate.getJulDay() + this.sDate.getDeltaT());
        this.isSet[0] = true;
    }

    void calcSiderealTime() {
        this.dFields[1] = this.sl.swe_sidtime(this.sDate.getJulDay());
        this.isSet[1] = true;
    }

    void calcFields() {
        StringBuffer stringBuffer = new StringBuffer();
        DblObj dblObj = new DblObj();
        DblObj dblObj2 = new DblObj();
        if (!this.isSet[2] || !this.isSet[3]) {
            this.calcSunRiseSet();
        }
        if (this.dFields[2] < 0.0 || this.dFields[3] < 0.0) {
            this.dFields[5] = this.dFields[2] < -1.2 || this.dFields[3] < -1.2 ? 0.0 : 60.0;
        } else {
            this.dFields[5] = (this.dFields[3] - this.dFields[2]) * 60.0;
            if (this.dFields[5] < 0.0) {
                double[] arrd = this.dFields;
                arrd[5] = arrd[5] + 60.0;
            }
        }
        this.dFields[6] = 60.0 - this.dFields[5];
        this.dFields[7] = this.dFields[5] + this.dFields[6] / 2.0;
        if (this.dFields[2] < 0.0) {
            this.dFields[8] = -1.0;
            this.dFields[10] = -1.0;
            this.dFields[11] = -1.0;
            this.dFields[12] = -1.0;
            this.dFields[13] = -1.0;
        } else {
            double[] arrd;
            int n;
            this.dFields[8] = (this.sDate.getHour() - (this.dFields[2] + 0.5) % 1.0 * 24.0) * 2.5;
            if (this.dFields[8] < 0.0) {
                double[] arrd2 = this.dFields;
                arrd2[8] = arrd2[8] + 60.0;
            }
            if ((n = this.sw.swe_calc(this.dFields[2], 0, 0, arrd = new double[6], stringBuffer)) < 0) {
                return;
            }
            if (!this.isSet[0]) {
                this.calcAyanamsha();
            }
            this.dFields[10] = (arrd[0] - this.dFields[0] + 360.0) % 360.0;
            this.dFields[11] = (this.dFields[10] + this.dFields[8] * 6.0) % 360.0;
            this.dFields[12] = (this.dFields[10] + this.dFields[8] * 12.0) % 360.0;
            this.dFields[13] = (this.dFields[10] + this.dFields[8] * 30.0) % 360.0;
        }
        this.fieldsAreSet = true;
    }

    public void clearData() {
        this.fieldsAreSet = false;
        boolean bl = this.isSet[2] && this.isSet[3] && this.isSet[4] && !this.locationHasChanged && this.sDate.getJulDay() >= this.dFields[2] && this.sDate.getJulDay() < this.dFields[2] + this.dFields[4] / 60.0;
        int n = 0;
        while (n < this.isSet.length) {
            this.isSet[n] = false;
            ++n;
        }
        if (bl) {
            this.isSet[2] = true;
            this.isSet[3] = true;
            this.isSet[4] = true;
        }
    }

    public int calcNaturalDignity(int n, int n2) {
        switch (n) {
            case 0: {
                double d = this.pos[1] * (double) n2 % 360.0;
                if (d >= 0.0 && d < 30.0) {
                    return 9;
                }
                if (d >= 120.0 && d < 140.0) {
                    return 8;
                }
                if (d >= 140.0 && d < 150.0) {
                    return 7;
                }
                if (d >= 90.0 && d < 120.0 || d >= 210.0 && d < 270.0 || d >= 330.0 && d < 360.0) {
                    return 5;
                }
                if (d >= 60.0 && d < 90.0 || d >= 150.0 && d < 180.0) {
                    return 4;
                }
                if (d >= 30.0 && d < 60.0 || d >= 270.0 && d < 330.0) {
                    return 3;
                }
                if (d < 180.0 || d >= 210.0) {
                    break;
                }
                return 1;
            }
            case 1: {
                double d = this.pos[2] * (double) n2 % 360.0;
                if (d >= 30.0 && d < 33.0) {
                    return 9;
                }
                if (d >= 33.0 && d < 60.0) {
                    return 8;
                }
                if (d >= 90.0 && d < 120.0) {
                    return 7;
                }
                if (d >= 60.0 && d < 90.0 || d >= 120.0 && d < 180.0) {
                    return 5;
                }
                if (d >= 0.0 && d < 30.0 || d >= 180.0 && d < 210.0 || d >= 240.0 && d < 360.0) {
                    return 4;
                }
                if (d < 210.0 || d >= 240.0) {
                    break;
                }
                return 1;
            }
            case 2: {
                double d = this.pos[3] * (double) n2 % 360.0;
                if (d >= 270.0 && d < 300.0) {
                    return 9;
                }
                if (d >= 0.0 && d < 12.0) {
                    return 8;
                }
                if (d >= 12.0 && d < 30.0 || d >= 210.0 && d < 240.0) {
                    return 7;
                }
                if (d >= 120.0 && d < 150.0 || d >= 240.0 && d < 270.0 || d >= 330.0 && d < 360.0) {
                    return 5;
                }
                if (d >= 30.0 && d < 60.0 || d >= 180.0 && d < 210.0 || d >= 300.0 && d < 330.0) {
                    return 4;
                }
                if (d >= 60.0 && d < 90.0 || d >= 150.0 && d < 180.0) {
                    return 3;
                }
                if (d < 90.0 || d >= 120.0) {
                    break;
                }
                return 1;
            }
            case 3: {
                double d = this.pos[4] * (double) n2 % 360.0;
                if (d >= 150.0 && d < 165.0) {
                    return 9;
                }
                if (d >= 165.0 && d < 170.0) {
                    return 8;
                }
                if (d >= 60.0 && d < 90.0 || d >= 170.0 && d < 180.0) {
                    return 7;
                }
                if (d >= 30.0 && d < 60.0 || d >= 120.0 && d < 150.0 || d >= 180.0 && d < 210.0) {
                    return 5;
                }
                if (d >= 0.0 && d < 30.0 || d >= 210.0 && d < 330.0) {
                    return 4;
                }
                if (d >= 90.0 && d < 120.0) {
                    return 3;
                }
                if (d < 330.0 || d >= 360.0) {
                    break;
                }
                return 1;
            }
            case 4: {
                double d = this.pos[5] * (double) n2 % 360.0;
                if (d >= 90.0 && d < 120.0) {
                    return 9;
                }
                if (d >= 240.0 && d < 250.0) {
                    return 8;
                }
                if (d >= 250.0 && d < 270.0 || d >= 330.0 && d < 360.0) {
                    return 7;
                }
                if (d >= 0.0 && d < 30.0 || d >= 120.0 && d < 150.0 || d >= 210.0 && d < 240.0) {
                    return 5;
                }
                if (d >= 300.0 && d < 330.0) {
                    return 4;
                }
                if (d >= 30.0 && d < 90.0 || d >= 150.0 && d < 210.0) {
                    return 3;
                }
                if (d < 270.0 || d >= 300.0) {
                    break;
                }
                return 1;
            }
            case 5: {
                double d = this.pos[6] * (double) n2 % 360.0;
                if (d >= 330.0 && d < 360.0) {
                    return 9;
                }
                if (d >= 180.0 && d < 195.0) {
                    return 8;
                }
                if (d >= 30.0 && d < 60.0 || d >= 195.0 && d < 210.0) {
                    return 7;
                }
                if (d >= 60.0 && d < 90.0 || d >= 270.0 && d < 330.0) {
                    return 5;
                }
                if (d >= 0.0 && d < 30.0 || d >= 210.0 && d < 270.0) {
                    return 4;
                }
                if (d >= 90.0 && d < 150.0) {
                    return 3;
                }
                if (d < 150.0 || d >= 180.0) {
                    break;
                }
                return 1;
            }
            case 6: {
                double d = this.pos[7] * (double) n2 % 360.0;
                if (d >= 180.0 && d < 210.0) {
                    return 9;
                }
                if (d >= 300.0 && d < 320.0) {
                    return 8;
                }
                if (d >= 270.0 && d < 300.0 || d >= 320.0 && d < 330.0) {
                    return 7;
                }
                if (d >= 30.0 && d < 90.0 || d >= 150.0 && d < 180.0) {
                    return 5;
                }
                if (d >= 240.0 && d < 270.0 || d >= 330.0 && d < 360.0) {
                    return 4;
                }
                if (d >= 90.0 && d < 150.0 || d >= 210.0 && d < 240.0) {
                    return 3;
                }
                if (d < 0.0 || d >= 30.0) {
                    break;
                }
                return 1;
            }
            case 7: {
                double d = this.pos[8] * (double) n2 % 360.0;
                if (d >= 30.0 && d < 60.0) {
                    return 9;
                }
                if (d >= 60.0 && d < 90.0) {
                    return 8;
                }
                if ((d < 240.0 || d >= 270.0) && (d < 300.0 || d >= 330.0)) {
                    break;
                }
                return 7;
            }
            case 8: {
                double d = this.pos[9] * (double) n2 % 360.0;
                if (d >= 210.0 && d < 240.0) {
                    return 9;
                }
                if (d >= 240.0 && d < 270.0) {
                    return 8;
                } else {
                    break;
                }
            }
        }
        return 0;
    }

    public int nameToIdx(String string) {
        if ((string = string.toUpperCase()).length() > 1) {
            if (string.startsWith("SY") || string.startsWith("SU")) {
                return 1;
            }
            if (string.startsWith("CH")) {
                return 2;
            }
            if (string.startsWith("MA")) {
                return 3;
            }
            if (string.startsWith("BU")) {
                return 4;
            }
            if (string.startsWith("GU")) {
                return 5;
            }
            if (string.startsWith("SK") || string.startsWith("SHU")) {
                return 6;
            }
            if (string.startsWith("SA") || string.startsWith("SHA")) {
                return 7;
            }
            if (string.startsWith("RH") || string.startsWith("RAH")) {
                return 8;
            }
            if (string.startsWith("KT") || string.startsWith("KE")) {
                return 9;
            }
        }
        return 0;
    }

    public void updateLanguage(ResourceBundle resourceBundle) {
        //this.natDignityNames = new String[]{resourceBundle.getString("dignUnknown"), resourceBundle.getString("dignDebilitated"), resourceBundle.getString("dignExtremeEnemy"), resourceBundle.getString("dignEnemy"), resourceBundle.getString("dignNeutral"), resourceBundle.getString("dignFriend"), resourceBundle.getString("dignExtremeFriend"), resourceBundle.getString("dignOwnSign"), resourceBundle.getString("dignMula"), resourceBundle.getString("dignExalted")};
    }

    public PlanetsPositionCalculator(SweDate sweDate, double d, double d2, ResourceBundle resourceBundle) {
        //this.natDignityNames = new String[]{resourceBundle.getString("dignUnknown"), resourceBundle.getString("dignDebilitated"), resourceBundle.getString("dignExtremeEnemy"), resourceBundle.getString("dignEnemy"), resourceBundle.getString("dignNeutral"), resourceBundle.getString("dignFriend"), resourceBundle.getString("dignExtremeFriend"), resourceBundle.getString("dignOwnSign"), resourceBundle.getString("dignMula"), resourceBundle.getString("dignExalted")};
        this.sw = new SwissEph(".:./ephe:/users/ephe2/:/users/ephe/:c\\:\\\\ephe:d\\:\\\\ephe:http\\://localhost/datafiles:http\\://www.th-mack.de/datafiles");
        this.sl = new SwissLib();
        this.sw.setHttpBufSize(300);
        this.sDate = sweDate;
        this.geoLong = d;
        this.geoLat = d2;
        this.temperature = 8.0;
        int n = 0;
        while (n < this.isSet.length) {
            this.isSet[n] = false;
            ++n;
        }
        this.calc();
    }

    public PlanetsPositionCalculator(SweDate sweDate, double d, double d2) {
        //this.natDignityNames = new String[]{resourceBundle.getString("dignUnknown"), resourceBundle.getString("dignDebilitated"), resourceBundle.getString("dignExtremeEnemy"), resourceBundle.getString("dignEnemy"), resourceBundle.getString("dignNeutral"), resourceBundle.getString("dignFriend"), resourceBundle.getString("dignExtremeFriend"), resourceBundle.getString("dignOwnSign"), resourceBundle.getString("dignMula"), resourceBundle.getString("dignExalted")};
        //this.sw = new SwissEph(".:./ephe:/users/ephe2/:/users/ephe/:c\\:\\\\ephe:d\\:\\\\ephe:http\\://localhost/datafiles:http\\://www.th-mack.de/datafiles");
        //this.sw = new SwissEph();
        this.sw = new SwissEph("D:\\Program Files\\Jastrology\\ephe");
        this.sl = new SwissLib();
        this.sw.setHttpBufSize(300);
        this.sDate = sweDate;
        this.geoLong = d;
        this.geoLat = d2;
        this.temperature = 8.0;
        int n = 0;
        while (n < this.isSet.length) {
            this.isSet[n] = false;
            ++n;
        }
        this.calc();
    }
}
