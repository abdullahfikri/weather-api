package dev.mfikri.weather.service;

import dev.mfikri.weather.model.WeatherModel;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@Service
public class WeatherService {

//    private final RedisTemplate<String, String> redisTemplate;

    private final RestTemplate restTemplate;

    public WeatherService(RestTemplate restTemplate) {
//        this.redisTemplate = redisTemplate;
        this.restTemplate = restTemplate;
    }


    public WeatherModel getWeather (String city, String country)  {

        // check to redis if the data forecast for the city and country is available or not;
        // if available then get it and return the data immediately


        String API_KEY = System.getenv("API_KEY");
        String URL = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/" + city +
                ",+" + country + "?unitGroup=metric&key=" + API_KEY + "&contentType=json";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        RequestEntity<Object> request = new RequestEntity<>(headers, HttpMethod.GET, URI.create(URL));

        ResponseEntity<WeatherModel> response = restTemplate.exchange(request, new ParameterizedTypeReference<WeatherModel>() {
        });
        // save asynchronously data forecasting to redis
        // return data forecasting
        return response.getBody();
    }
}
