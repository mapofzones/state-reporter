package com.mapofzones.statereporter.common.properties.checker;

import lombok.Getter;
import lombok.Setter;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "checker.unupdated-price")
public class UnupdatedPriceCheckerProperties extends CheckerProperties {
    private Duration allowedTime;
}
