package dev.mfikri.weather.service;


import dev.mfikri.weather.entity.WeatherEntity;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

@Validated
public interface WeatherService {

    WeatherEntity getWeather(@NotBlank String city, @NotBlank String country);
}
