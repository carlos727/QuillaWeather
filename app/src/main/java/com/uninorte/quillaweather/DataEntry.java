package com.uninorte.quillaweather;

import java.io.Serializable;

@SuppressWarnings("serial")
public class DataEntry implements Serializable {

    private String day;
    private String min;
    private String max;
    private String night;

    public DataEntry() {

    }

    public DataEntry(String day, String min, String max, String night) {
        this.day = day;
        this.min = min;
        this.max = max;
        this.night = night;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDay() {
        return day;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMin() {
        return min;
    }

    public void setNight(String night) {
        this.night = night;
    }

    public String getNight() {
        return night;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getMax() {
        return max;
    }
}
