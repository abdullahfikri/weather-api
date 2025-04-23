package dev.mfikri.weather.service;

import dev.mfikri.weather.entity.WeatherEntity;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extensions;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.*;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WeatherServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private RedisTemplate<String, WeatherEntity> redisTemplate;


    @Mock
    private ValueOperations<String, WeatherEntity> valueOperations;

    @InjectMocks
    private WeatherServiceImpl weatherServiceImpl;

    @BeforeEach
    void setUp() {
        Mockito.when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        Mockito.doNothing().when(valueOperations).set(Mockito.anyString(), Mockito.any());
    }

    @Test
    void testGetWeatherSuccess() throws IOException, InterruptedException {
        WeatherEntity model = new WeatherEntity();
        ResponseEntity<WeatherEntity> responseEntity = new ResponseEntity<>(model,HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                ArgumentMatchers.<RequestEntity<Object>>any(),
                ArgumentMatchers.<ParameterizedTypeReference<WeatherEntity>>any()
        )).thenReturn(responseEntity);

        WeatherEntity weather = weatherServiceImpl.getWeather("pekanbaru", "indonesia");

        assertSame(model, weather);
        assertNotNull(weather);
        Mockito.verify(restTemplate, Mockito.times(1)).exchange(  ArgumentMatchers.<RequestEntity<Object>>any(),
                ArgumentMatchers.<ParameterizedTypeReference<WeatherEntity>>any());
    }

    @Test
    void testGetWeatherErrorBadRequest() throws IOException, InterruptedException {

        Mockito.when(restTemplate.exchange(
                ArgumentMatchers.<RequestEntity<Object>>any(),
                ArgumentMatchers.<ParameterizedTypeReference<WeatherEntity>>any()
        )).thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));

        HttpClientErrorException httpClientErrorException = assertThrows(HttpClientErrorException.class, () -> {
            weatherServiceImpl.getWeather("invalid", "invalid");
        });
        assertEquals(HttpStatus.BAD_REQUEST, httpClientErrorException.getStatusCode());

        Mockito.verify(restTemplate, Mockito.times(1)).exchange(  ArgumentMatchers.<RequestEntity<Object>>any(),
                ArgumentMatchers.<ParameterizedTypeReference<WeatherEntity>>any());
    }

    @Test
    void testGetWeatherErrorUnauthorized() throws IOException, InterruptedException {
        // key is expired or deleted
        Mockito.when(restTemplate.exchange(
                ArgumentMatchers.<RequestEntity<Object>>any(),
                ArgumentMatchers.<ParameterizedTypeReference<WeatherEntity>>any()
        )).thenThrow(new HttpClientErrorException(HttpStatus.UNAUTHORIZED));

        HttpClientErrorException httpClientErrorException = assertThrows(HttpClientErrorException.class, () -> {
            weatherServiceImpl.getWeather("valid", "valid");
        });
        assertEquals(HttpStatus.UNAUTHORIZED, httpClientErrorException.getStatusCode());

        Mockito.verify(restTemplate, Mockito.times(1)).exchange(  ArgumentMatchers.<RequestEntity<Object>>any(),
                ArgumentMatchers.<ParameterizedTypeReference<WeatherEntity>>any());
    }

    @Test
    void testGetWeatherErrorNotFound() throws IOException, InterruptedException {
        // url is not found
        Mockito.when(restTemplate.exchange(
                ArgumentMatchers.<RequestEntity<Object>>any(),
                ArgumentMatchers.<ParameterizedTypeReference<WeatherEntity>>any()
        )).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        HttpClientErrorException httpClientErrorException = assertThrows(HttpClientErrorException.class, () -> {
            weatherServiceImpl.getWeather("valid", "valid");
        });
        assertEquals(HttpStatus.NOT_FOUND, httpClientErrorException.getStatusCode());

        Mockito.verify(restTemplate, Mockito.times(1)).exchange(  ArgumentMatchers.<RequestEntity<Object>>any(),
                ArgumentMatchers.<ParameterizedTypeReference<WeatherEntity>>any());
    }

    @Test
    void testGetWeatherErrorTooManyRequest() throws IOException, InterruptedException {
        // too many request at one times
        Mockito.when(restTemplate.exchange(
                ArgumentMatchers.<RequestEntity<Object>>any(),
                ArgumentMatchers.<ParameterizedTypeReference<WeatherEntity>>any()
        )).thenThrow(new HttpClientErrorException(HttpStatus.TOO_MANY_REQUESTS));

        HttpClientErrorException httpClientErrorException = assertThrows(HttpClientErrorException.class, () -> {
            weatherServiceImpl.getWeather("valid", "valid");
        });
        assertEquals(HttpStatus.TOO_MANY_REQUESTS, httpClientErrorException.getStatusCode());

        Mockito.verify(restTemplate, Mockito.times(1)).exchange(  ArgumentMatchers.<RequestEntity<Object>>any(),
                ArgumentMatchers.<ParameterizedTypeReference<WeatherEntity>>any());
    }

    @Test
    void testGetWeatherErrorInternalServerError() throws IOException, InterruptedException {
        // too many request at one times
        Mockito.when(restTemplate.exchange(
                ArgumentMatchers.<RequestEntity<Object>>any(),
                ArgumentMatchers.<ParameterizedTypeReference<WeatherEntity>>any()
        )).thenThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

        HttpServerErrorException httpServerErrorException = assertThrows(HttpServerErrorException.class, () -> {
            weatherServiceImpl.getWeather("valid", "valid");
        });
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, httpServerErrorException.getStatusCode());

        Mockito.verify(restTemplate, Mockito.times(1)).exchange(  ArgumentMatchers.<RequestEntity<Object>>any(),
                ArgumentMatchers.<ParameterizedTypeReference<WeatherEntity>>any());
    }


}