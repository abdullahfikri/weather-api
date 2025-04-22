package dev.mfikri.weather.service;

import dev.mfikri.weather.model.WeatherModel;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class WeatherServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private WeatherService weatherService;


    @Test
    void testGetWeatherSuccess() throws IOException, InterruptedException {
        WeatherModel model = new WeatherModel();
        ResponseEntity<WeatherModel> responseEntity = new ResponseEntity<>(model,HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                ArgumentMatchers.<RequestEntity<Object>>any(),
                ArgumentMatchers.<ParameterizedTypeReference<WeatherModel>>any()
        )).thenReturn(responseEntity);

        WeatherModel weather = weatherService.getWeather("pekanbaru", "indonesia");

        assertNotNull(weather);
        Mockito.verify(restTemplate, Mockito.times(1)).exchange(  ArgumentMatchers.<RequestEntity<Object>>any(),
                ArgumentMatchers.<ParameterizedTypeReference<WeatherModel>>any());
    }

    @Test
    void testGetWeatherErrorBadRequest() throws IOException, InterruptedException {

        Mockito.when(restTemplate.exchange(
                ArgumentMatchers.<RequestEntity<Object>>any(),
                ArgumentMatchers.<ParameterizedTypeReference<WeatherModel>>any()
        )).thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));

        HttpClientErrorException httpClientErrorException = assertThrows(HttpClientErrorException.class, () -> {
            weatherService.getWeather("invalid", "invalid");
        });
        assertEquals(HttpStatus.BAD_REQUEST, httpClientErrorException.getStatusCode());

        Mockito.verify(restTemplate, Mockito.times(1)).exchange(  ArgumentMatchers.<RequestEntity<Object>>any(),
                ArgumentMatchers.<ParameterizedTypeReference<WeatherModel>>any());
    }

    @Test
    void testGetWeatherErrorUnauthorized() throws IOException, InterruptedException {
        // key is expired or deleted
        Mockito.when(restTemplate.exchange(
                ArgumentMatchers.<RequestEntity<Object>>any(),
                ArgumentMatchers.<ParameterizedTypeReference<WeatherModel>>any()
        )).thenThrow(new HttpClientErrorException(HttpStatus.UNAUTHORIZED));

        HttpClientErrorException httpClientErrorException = assertThrows(HttpClientErrorException.class, () -> {
            weatherService.getWeather("valid", "valid");
        });
        assertEquals(HttpStatus.UNAUTHORIZED, httpClientErrorException.getStatusCode());

        Mockito.verify(restTemplate, Mockito.times(1)).exchange(  ArgumentMatchers.<RequestEntity<Object>>any(),
                ArgumentMatchers.<ParameterizedTypeReference<WeatherModel>>any());
    }

    @Test
    void testGetWeatherErrorNotFound() throws IOException, InterruptedException {
        // url is not found
        Mockito.when(restTemplate.exchange(
                ArgumentMatchers.<RequestEntity<Object>>any(),
                ArgumentMatchers.<ParameterizedTypeReference<WeatherModel>>any()
        )).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        HttpClientErrorException httpClientErrorException = assertThrows(HttpClientErrorException.class, () -> {
            weatherService.getWeather("valid", "valid");
        });
        assertEquals(HttpStatus.NOT_FOUND, httpClientErrorException.getStatusCode());

        Mockito.verify(restTemplate, Mockito.times(1)).exchange(  ArgumentMatchers.<RequestEntity<Object>>any(),
                ArgumentMatchers.<ParameterizedTypeReference<WeatherModel>>any());
    }

    @Test
    void testGetWeatherErrorTooManyRequest() throws IOException, InterruptedException {
        // too many request at one times
        Mockito.when(restTemplate.exchange(
                ArgumentMatchers.<RequestEntity<Object>>any(),
                ArgumentMatchers.<ParameterizedTypeReference<WeatherModel>>any()
        )).thenThrow(new HttpClientErrorException(HttpStatus.TOO_MANY_REQUESTS));

        HttpClientErrorException httpClientErrorException = assertThrows(HttpClientErrorException.class, () -> {
            weatherService.getWeather("valid", "valid");
        });
        assertEquals(HttpStatus.TOO_MANY_REQUESTS, httpClientErrorException.getStatusCode());

        Mockito.verify(restTemplate, Mockito.times(1)).exchange(  ArgumentMatchers.<RequestEntity<Object>>any(),
                ArgumentMatchers.<ParameterizedTypeReference<WeatherModel>>any());
    }

    @Test
    void testGetWeatherErrorInternalServerError() throws IOException, InterruptedException {
        // too many request at one times
        Mockito.when(restTemplate.exchange(
                ArgumentMatchers.<RequestEntity<Object>>any(),
                ArgumentMatchers.<ParameterizedTypeReference<WeatherModel>>any()
        )).thenThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

        HttpServerErrorException httpServerErrorException = assertThrows(HttpServerErrorException.class, () -> {
            weatherService.getWeather("valid", "valid");
        });
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, httpServerErrorException.getStatusCode());

        Mockito.verify(restTemplate, Mockito.times(1)).exchange(  ArgumentMatchers.<RequestEntity<Object>>any(),
                ArgumentMatchers.<ParameterizedTypeReference<WeatherModel>>any());
    }
}