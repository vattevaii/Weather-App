package com.exweatheria.weatherapp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateClass {
    private Date now;
    private DateFormat df;

    public DateClass(){
        now = new Date();
        df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        TimeZone tz = TimeZone.getTimeZone("UTC");
        df.setTimeZone(tz);
    }
    public String getNow(){
        return df.format(now);
    }
    public String nextDay(){
        Date date = new Date(now.getTime()+ (1000 * 60 * 60 * 24));
        return df.format(date);
    }
    public String next7Day(){
        Date date = new Date(now.getTime()+ (7 * 1000 * 60 * 60 * 24));
        return df.format(date);
    }
}
