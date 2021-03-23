package com.shishkindenis.locationtracker_parent.singletons;

import javax.inject.Inject;

public class DateSingleton {
    private String date;

    @Inject
    public DateSingleton() { }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}