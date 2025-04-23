package dev.mfikri.weather.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherEntity {
    private double latitude;
    private double longitude;
    private String resolvedAddress;
    private String address;
    private String timezone;
    private double tzoffset;
    private String description;
    private ArrayList<Day> days;
    private ArrayList<Object> alerts;
    private CurrentConditions currentConditions;

}
