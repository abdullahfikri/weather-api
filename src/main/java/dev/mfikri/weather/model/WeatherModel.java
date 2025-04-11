package dev.mfikri.weather.model;

import java.util.ArrayList;

public class WeatherModel{
    private double latitude;
    private double longitude;
    private String resolvedAddress;
    private String timezone;
    private double tzoffset;
    private String description;
    private ArrayList<Day> days;
    private ArrayList<Object> alerts;
    private CurrentConditions currentConditions;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getResolvedAddress() {
        return resolvedAddress;
    }

    public void setResolvedAddress(String resolvedAddress) {
        this.resolvedAddress = resolvedAddress;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public double getTzoffset() {
        return tzoffset;
    }

    public void setTzoffset(double tzoffset) {
        this.tzoffset = tzoffset;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Day> getDays() {
        return days;
    }

    public void setDays(ArrayList<Day> days) {
        this.days = days;
    }

    public ArrayList<Object> getAlerts() {
        return alerts;
    }

    public void setAlerts(ArrayList<Object> alerts) {
        this.alerts = alerts;
    }


    public CurrentConditions getCurrentConditions() {
        return currentConditions;
    }

    public void setCurrentConditions(CurrentConditions currentConditions) {
        this.currentConditions = currentConditions;
    }
}
