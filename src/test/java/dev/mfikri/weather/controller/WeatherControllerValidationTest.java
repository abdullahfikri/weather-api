package dev.mfikri.weather.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mfikri.weather.entity.WeatherEntity;
import dev.mfikri.weather.model.WebResponse;
import dev.mfikri.weather.service.WeatherServiceImpl;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class WeatherControllerValidationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WeatherServiceImpl weatherServiceImpl;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void parameterAllBlank() throws Exception {

        mockMvc.perform(
                get("/weathers")
                        .param("city", " ")
                        .param("country", "")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isBadRequest(),
                content().string(Matchers.containsString("must not be blank"))
        ).andExpect(result -> {
            System.out.println(result.getResponse().getContentAsString());
        });
    }

    @Test
    void parameterCityBlank() throws Exception {

        mockMvc.perform(
                get("/weathers")
                        .param("city", " ")
                        .param("country", "Indonesia")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isBadRequest(),
                content().string(Matchers.containsString("city: must not be blank"))
        ).andExpect(result -> {
            System.out.println(result.getResponse().getContentAsString());
        });
    }

    @Test
    void parameterCountryBlank() throws Exception {

        mockMvc.perform(
                get("/weathers")
                        .param("city", "Poso")
                        .param("country", "")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isBadRequest(),
                content().string(Matchers.containsString("country: must not be blank"))
        ).andExpect(result -> {
            System.out.println(result.getResponse().getContentAsString());
        });
    }
}