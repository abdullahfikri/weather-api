package dev.mfikri.weather.service;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class WeatherServiceImplValidationTest {

    @Autowired
    WeatherService weatherService;

    @Test
    void testBlankArgument() {
        assertThrows(ConstraintViolationException.class, () -> {
            weatherService.getWeather(" ", " ");
        });

        assertThrows(ConstraintViolationException.class, () -> {
            weatherService.getWeather("Medan", " ");
        });

        assertThrows(ConstraintViolationException.class, () -> {
            weatherService.getWeather("", "Indonesia");
        });
    }
}
