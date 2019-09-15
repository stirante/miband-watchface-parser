package com.stirante.watchface.miband;

import java.util.Calendar;
import java.util.Random;

public class WatchState {

    public String hour;
    public String hourOfDay;
    public String minute;
    public String second;
    public boolean isAm;
    public int steps;
    public int stepsGoal;
    public int pulse;
    public int calories;
    public double distance;

    public WatchState() {
        Random rand = new Random();
        Calendar calendar = Calendar.getInstance();
        hour = String.valueOf(calendar.get(Calendar.HOUR));
        hourOfDay = String.valueOf(calendar.get(Calendar.HOUR));
        minute = String.valueOf(calendar.get(Calendar.MINUTE));
        second = String.valueOf(calendar.get(Calendar.SECOND));
        isAm = calendar.get(Calendar.AM_PM) == Calendar.AM;
        steps = rand.nextInt(10000);
        pulse = 60 + rand.nextInt(140);
        calories = rand.nextInt(1000);
        distance = rand.nextInt(10000) / 1000.0;
        stepsGoal = 8000;
    }

}
