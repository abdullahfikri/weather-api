package dev.mfikri.weather.controller;

import dev.mfikri.weather.entity.WeatherEntity;
import dev.mfikri.weather.model.WebResponse;
import dev.mfikri.weather.service.WeatherServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class WeatherController {

    private final WeatherServiceImpl service;

    public WeatherController(WeatherServiceImpl service) {
        this.service = service;
    }

    @GetMapping(path = "/weathers",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<WebResponse<WeatherEntity>> getWeather (@RequestParam("city") String city,
                                                                  @RequestParam String country) {
            WeatherEntity weather = service.getWeather(city, country);
            return ResponseEntity.ok(new WebResponse<>(weather));

    }


}
