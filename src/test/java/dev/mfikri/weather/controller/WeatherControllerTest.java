package dev.mfikri.weather.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mfikri.weather.entity.WeatherEntity;
import dev.mfikri.weather.model.WebResponse;
import dev.mfikri.weather.service.WeatherService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class WeatherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private WeatherServiceImpl weatherServiceImpl;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {

    }

    @Test
    void getWeather() throws Exception {
        WeatherEntity model = new WeatherEntity();
        model.setAddress("City, Country");

        Mockito.when(weatherServiceImpl.getWeather(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(model);

        mockMvc.perform(
                get("/weathers")
                        .param("city", Mockito.anyString())
                        .param("country", Mockito.anyString())
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isOk(),
                header().string(HttpHeaders.CONTENT_TYPE, Matchers.containsString(MediaType.APPLICATION_JSON_VALUE))
        ).andExpect(result -> {
            String responseJson = result.getResponse().getContentAsString();
            WebResponse<WeatherEntity> response = objectMapper.readValue(responseJson, new TypeReference<WebResponse<WeatherEntity>>() {
            });
            assertNotNull(response.getData());
            assertEquals(model, response.getData());
            assertInstanceOf(WeatherEntity.class, response.getData());

            Mockito.verify(weatherServiceImpl).getWeather(Mockito.anyString(), Mockito.anyString());
        });
    }

    @Test
    void parametersError() throws Exception{
        mockMvc.perform(
                get("/weathers")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isBadRequest(),
                content().string(Matchers.containsString("parameter type String is not present"))
        );
    }


}