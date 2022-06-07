package com.exweatheria.weatherapp;

import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

public class TemperatureModel {
    private String DateTime;
    private String temperature;
    private ImageView data;
    private String weatherCode;
    private String statusText;
    public TemperatureModel(String dateTime, String temperature) {
        DateTime = dateTime;
        this.temperature = temperature;
    }

    public TemperatureModel(String date, String temperature, String weatherCode, String s) {
        this.temperature = temperature;
        this.weatherCode = weatherCode;
        if(s == "1h")
            DateTime = date.substring(5,16);
        if(s == "1d")
            DateTime = date.split("T",2)[0];
        String text = "{\n" +
                "      \"0\": \"Unknown\",\n" +
                "      \"1000\": \"Clear, Sunny\",\n" +
                "      \"1100\": \"Mostly Clear\",\n" +
                "      \"1101\": \"Partly Cloudy\",\n" +
                "      \"1102\": \"Mostly Cloudy\",\n" +
                "      \"1001\": \"Cloudy\",\n" +
                "      \"2000\": \"Fog\",\n" +
                "      \"2100\": \"Light Fog\",\n" +
                "      \"4000\": \"Drizzle\",\n" +
                "      \"4001\": \"Rain\",\n" +
                "      \"4200\": \"Light Rain\",\n" +
                "      \"4201\": \"Heavy Rain\",\n" +
                "      \"5000\": \"Snow\",\n" +
                "      \"5001\": \"Flurries\",\n" +
                "      \"5100\": \"Light Snow\",\n" +
                "      \"5101\": \"Heavy Snow\",\n" +
                "      \"6000\": \"Freezing Drizzle\",\n" +
                "      \"6001\": \"Freezing Rain\",\n" +
                "      \"6200\": \"Light Freezing Rain\",\n" +
                "      \"6201\": \"Heavy Freezing Rain\",\n" +
                "      \"7000\": \"Ice Pellets\",\n" +
                "      \"7101\": \"Heavy Ice Pellets\",\n" +
                "      \"7102\": \"Light Ice Pellets\",\n" +
                "      \"8000\": \"Thunderstorm\"\n" +
                "    }";
        try {
            JSONObject obj = new JSONObject(text);
            statusText = obj.getString(weatherCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public ImageView getData() {
        return data;
    }

    public void setData(ImageView data) {
        this.data = data;
    }

    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String dateTime) {
        DateTime = dateTime;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getStatusText() {
        return statusText;
    }

    public static String statusText(String code){
        String text = "{\n" +
                "      \"0\": \"Unknown\",\n" +
                "      \"1000\": \"Clear, Sunny\",\n" +
                "      \"1100\": \"Mostly Clear\",\n" +
                "      \"1101\": \"Partly Cloudy\",\n" +
                "      \"1102\": \"Mostly Cloudy\",\n" +
                "      \"1001\": \"Cloudy\",\n" +
                "      \"2000\": \"Fog\",\n" +
                "      \"2100\": \"Light Fog\",\n" +
                "      \"4000\": \"Drizzle\",\n" +
                "      \"4001\": \"Rain\",\n" +
                "      \"4200\": \"Light Rain\",\n" +
                "      \"4201\": \"Heavy Rain\",\n" +
                "      \"5000\": \"Snow\",\n" +
                "      \"5001\": \"Flurries\",\n" +
                "      \"5100\": \"Light Snow\",\n" +
                "      \"5101\": \"Heavy Snow\",\n" +
                "      \"6000\": \"Freezing Drizzle\",\n" +
                "      \"6001\": \"Freezing Rain\",\n" +
                "      \"6200\": \"Light Freezing Rain\",\n" +
                "      \"6201\": \"Heavy Freezing Rain\",\n" +
                "      \"7000\": \"Ice Pellets\",\n" +
                "      \"7101\": \"Heavy Ice Pellets\",\n" +
                "      \"7102\": \"Light Ice Pellets\",\n" +
                "      \"8000\": \"Thunderstorm\"\n" +
                "    }";
        try {
            JSONObject obj = new JSONObject(text);
            return obj.getString(code);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "Unknown";
    }
}
