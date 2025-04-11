package dev.mfikri.weather.service;

import dev.mfikri.weather.model.WeatherModel;
import dev.mfikri.weather.util.JsonUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class WeatherService {

    public void getWeather () throws IOException, InterruptedException {
        String api_key = "";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/jakarta?unitGroup=metric&key="+ api_key +"&contentType=json"))
                .method("GET", HttpRequest.BodyPublishers.noBody()).build();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient
                .send(request, HttpResponse.BodyHandlers.ofString());

        WeatherModel weatherModel = JsonUtil.getObjectMapper().readValue(response.body(), WeatherModel.class);

        System.out.println(weatherModel.getResolvedAddress());


        httpClient.close();

    }
}
