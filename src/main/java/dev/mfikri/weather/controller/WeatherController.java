package dev.mfikri.weather.controller;

import dev.mfikri.weather.model.WeatherModel;
import dev.mfikri.weather.model.WebResponse;
import dev.mfikri.weather.service.WeatherService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
@RestController
public class WeatherController {

    private final WeatherService service;

    public WeatherController(WeatherService service) {
        this.service = service;
    }

    @GetMapping(path = "/weathers",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<WebResponse<WeatherModel>> getWeather (@RequestParam("city") String city,
                                                                @RequestParam String country) {
            WeatherModel weather = service.getWeather(city, country);
            return ResponseEntity.ok(new WebResponse<>(weather));

    }


}
