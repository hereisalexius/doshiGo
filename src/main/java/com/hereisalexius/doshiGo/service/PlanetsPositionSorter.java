package com.hereisalexius.doshiGo.service;

public class PlanetsPositionSorter {

    double[] pos = new double[9];
    int[] graha = new int[9];


    PlanetsPositionSorter() {
        for (int i = 0; i < graha.length; i++) {
            graha[i] = i;
        }
    }

    public void sort() {
        int i = graha.length;
        do {
            int j = 0;
            for (int k = 0; k < i - 1; k++) {
                if (pos[k] > pos[(k + 1)]) {
                    double d = pos[k];
                    int m = graha[k];
                    pos[k] = pos[(k + 1)];
                    graha[k] = graha[(k + 1)];
                    pos[(k + 1)] = d;
                    graha[(k + 1)] = m;
                    j = 1;
                }
            }
            if (j == 0) {
                return;
            }
            i--;
        } while (i >= 0);
    }

}
