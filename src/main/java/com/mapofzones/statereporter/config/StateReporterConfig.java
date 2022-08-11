package com.mapofzones.statereporter.config;

import com.mapofzones.statereporter.common.properties.EndpointProperties;
import com.mapofzones.statereporter.common.properties.checker.ChainIdCheckerProperties;
import com.mapofzones.statereporter.common.properties.checker.IbcDataCheckerProperties;
import com.mapofzones.statereporter.common.properties.checker.UnupdatedZoneCheckerProperties;
import com.mapofzones.statereporter.data.repositories.*;
import com.mapofzones.statereporter.services.checkers.Checker;
import com.mapofzones.statereporter.services.checkers.UnupdatedZoneChecker;
import com.mapofzones.statereporter.services.checkers.chainid.ChainIdChecker;
import com.mapofzones.statereporter.services.checkers.ibcdata.IbcDataChecker;
import com.mapofzones.statereporter.services.checkers.ibcdata.ibcclient.LcdClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.time.Duration;

@Configuration
public class StateReporterConfig {

    @Bean
    public EndpointProperties endpointProperties() {
        return new EndpointProperties();
    }

    @Bean
    public UnupdatedZoneCheckerProperties unupdatedZoneCheckerProperties() {
        return new UnupdatedZoneCheckerProperties();
    }

    @Bean
    public IbcDataCheckerProperties ibcDataCheckerProperties() {
        return new IbcDataCheckerProperties();
    }

    @Bean
    public ChainIdCheckerProperties chainIdCheckerProperties() {
        return new ChainIdCheckerProperties();
    }

    @Bean
    public Checker unupdatedZoneChecker(ZoneBlocksLogRepository zoneBlocksLogRepository) {
        return new UnupdatedZoneChecker(zoneBlocksLogRepository);
    }

    @Bean
    public Checker ibcDataChecker(ZoneRepository zoneRepository,
                                  IbcClientRepository ibcClientRepository,
                                  IbcConnectionRepository ibcConnectionRepository,
                                  IbcChannelRepository ibcChannelRepository,
                                  LcdClient lcdClient) {
        return new IbcDataChecker(zoneRepository, ibcClientRepository, ibcConnectionRepository, ibcChannelRepository, lcdClient);
    }

    @Bean
    public Checker chainIdChecker(ZoneRepository zoneRepository, ChainIdCheckerProperties chainIdCheckerProperties) {
        return new ChainIdChecker(zoneRepository, chainIdCheckerProperties);
    }

    @Bean
    public RestTemplate lcdClientRestTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .additionalMessageConverters(new StringHttpMessageConverter(StandardCharsets.UTF_8))
                .setConnectTimeout(Duration.ofSeconds(15))
                .setReadTimeout(Duration.ofSeconds(15))
                .build();
    }

    @Bean
    public LcdClient lcdClient(@Qualifier("lcdClientRestTemplate") RestTemplate restTemplate,
                               EndpointProperties endpointProperties) {
        return new LcdClient(restTemplate, endpointProperties);
    }

}
