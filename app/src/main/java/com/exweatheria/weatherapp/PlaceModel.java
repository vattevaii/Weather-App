package com.exweatheria.weatherapp;

import java.util.Comparator;

public class PlaceModel implements Comparable<PlaceModel> {
    private String place;
    private String latitude;
    private String longitude;
    private String state;
    private String stateAbv;

    public PlaceModel() {
        this.place = "";
        this.latitude = "";
        this.longitude = "";
        this.state = "";
        this.stateAbv = "";
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getState() {
        return stateAbv;
    }

    public String getLState() {
        return this.state;
    }
    public void setState(String state) {
        this.state = state;
        if(state.toLowerCase().startsWith("new"))
            this.stateAbv = "NSW";
        if(state.toLowerCase().startsWith("vic"))
            this.stateAbv = "VIC";
        if(state.toLowerCase().startsWith("aus"))
            this.stateAbv = "ACT";
        if(state.toLowerCase().startsWith("queen"))
            this.stateAbv = "QLD";
        if(state.toLowerCase().startsWith("tas"))
            this.stateAbv = "TAS";
        if(state.toLowerCase().startsWith("north"))
            this.stateAbv = "NT";
        if(state.toLowerCase().startsWith("south"))
            this.stateAbv = "SA";
        if(state.toLowerCase().startsWith("west"))
            this.stateAbv = "WA";

    }

    @Override
    public int compareTo(PlaceModel o) {
        String compareQuantity = ((PlaceModel) o).stateAbv;
        //Ascending
        return this.stateAbv.compareTo(compareQuantity);
    }
    public static Comparator<PlaceModel> PlaceComparator = new Comparator<PlaceModel>() {
        @Override
        public int compare(PlaceModel o1, PlaceModel o2) {
            String pName1 = o1.getPlace().toLowerCase();
            String pName2 = o2.getPlace().toLowerCase();
            //Ascending
            return pName1.compareTo(pName2);
        }
    };

}
