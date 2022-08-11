package com.mapofzones.statereporter.config;

import com.mapofzones.statereporter.common.properties.EndpointProperties;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@TestConfiguration
public class AppConfig {

    @Bean
    public DataSource dataSource() {
        return Mockito.mock(DataSource.class);
    }

    @Bean
    public EndpointProperties endpointProperties() {
        return new EndpointProperties();
    }
}