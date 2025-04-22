package dev.mfikri.weather.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mfikri.weather.model.WeatherModel;
import dev.mfikri.weather.model.WebResponse;
import dev.mfikri.weather.service.WeatherService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.MockMvcBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SpringBootTest
@AutoConfigureMockMvc
class WeatherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private WeatherService weatherService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        WeatherModel model = new WeatherModel();
        model.setAddress("City, Country");

        Mockito.when(weatherService.getWeather(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(model);
    }

    @Test
    void getWeather() throws Exception {
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
            WebResponse<WeatherModel> response = objectMapper.readValue(responseJson, new TypeReference<WebResponse<WeatherModel>>() {
            });
            assertNotNull(response.getData());
            assertInstanceOf(WeatherModel.class, response.getData());

            Mockito.verify(weatherService).getWeather(Mockito.anyString(), Mockito.anyString());
        });
    }
}