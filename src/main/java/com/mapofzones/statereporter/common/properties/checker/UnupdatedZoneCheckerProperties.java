package com.mapofzones.statereporter.common.properties.checker;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "checker.unupdated-zone")
public class UnupdatedZoneCheckerProperties extends CheckerProperties {

}
