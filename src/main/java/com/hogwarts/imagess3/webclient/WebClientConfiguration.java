package com.hogwarts.imagess3.webclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration {
    private final WebClient client;

    public WebClientConfiguration(@Value("${employee.root.uri}") String uri) {
        this.client = WebClient.create(uri);
    }

    @Bean(name = "employeeWebClient")
    public WebClient getEmployeeWebClient() {
        return client;
    }
}
