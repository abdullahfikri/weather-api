package dev.mfikri.weather.service;

import dev.mfikri.weather.entity.WeatherEntity;
import jakarta.validation.constraints.NotBlank;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.Duration;
import java.util.List;

@Service
@Validated
public class WeatherServiceImpl implements WeatherService {

    private final RedisTemplate<String, WeatherEntity> redisTemplate;

    private final RestTemplate restTemplate;


    public WeatherServiceImpl(RedisTemplate<String, WeatherEntity> redisTemplate, RestTemplate restTemplate) {
        this.redisTemplate = redisTemplate;
        this.restTemplate = restTemplate;
    }


    public WeatherEntity getWeather (@NotBlank String city, @NotBlank String country)  {
        ValueOperations<String, WeatherEntity> operations = redisTemplate.opsForValue();

        // check to redis if the data forecast for the city and country is available or not;
        WeatherEntity weather = operations.get(city + country);
        if (weather != null) {
        // if available then get it and return the data immediately
            return weather;
        }

        String API_KEY = System.getenv("API_KEY");
        String URL = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/" + city +
                ",+" + country + "?unitGroup=metric&key=" + API_KEY + "&contentType=json";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        RequestEntity<Object> request = new RequestEntity<>(headers, HttpMethod.GET, URI.create(URL));

        ResponseEntity<WeatherEntity> response = restTemplate.exchange(request, new ParameterizedTypeReference<WeatherEntity>() {
        });
        // save asynchronously data forecasting to redis
        WeatherEntity weatherEntity = response.getBody();
        saveWeatherToRedis(city+country, weatherEntity);

        // return data forecasting
        return weatherEntity;
    }

    @Async
    protected void saveWeatherToRedis(String key, WeatherEntity weather) {
        redisTemplate.opsForValue().set(key, weather, Duration.ofHours(24));
    }
}
