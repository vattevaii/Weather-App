package com.exweatheria.weatherapp;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

public class Forecast {
    private String url;
    private String fields;
    private String latlongPair;
    private String units;
    private String endTime;
    private JSONObject response;
    public Forecast(){
        url="https://api.tomorrow.io/v4/timelines?apikey=uQ5YjGLKTFFygDOOibYS4uspsSRODykU";
        fields = "&fields=temperature,temperatureApparent,weatherCode,precipitationType,precipitationIntensity";
        units = "&units=metric";
    }
    public void workinDay(String lat, String lon, String day,Boolean imperial){
        latlongPair = "&location="+lat+","+lon;
        if(day == "1h")
            endTime = "&timesteps=1h&endTime="+new DateClass().nextDay();
        else if(day == "1d")
            endTime = "&timesteps=1d&endTime="+new DateClass().next7Day();
        else
            endTime = "&timesteps=current";
        if(imperial == true)
            units="&units=imperial";
    }
    public String requestURL(){
        return url+units+latlongPair+fields+endTime;
    }
}
