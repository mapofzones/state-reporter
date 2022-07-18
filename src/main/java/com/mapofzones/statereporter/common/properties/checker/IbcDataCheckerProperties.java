package com.mapofzones.statereporter.common.properties.checker;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "checker.ibc-data")
public class IbcDataCheckerProperties extends CheckerProperties {
}
