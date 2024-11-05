package com.lirmo.uber.uberApp.configs;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;

// import com.lirmo.uber.uberApp.utils.AppProperties;

@Configuration
// @EnableConfigurationProperties(AppProperties.class)
public class RestClientConfig {

    @Value("${OSRM.base.url}")
    private String OSRM_BASE_URL;

    @Bean
    @Qualifier("DistanceService")
    RestClient getOSRMRestClient() {
        return RestClient.builder().baseUrl(OSRM_BASE_URL).defaultHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .defaultStatusHandler(HttpStatusCode::is5xxServerError, (request, response) -> {
                    throw new RuntimeException("Server Error occured");
                }).build();
    }
}
