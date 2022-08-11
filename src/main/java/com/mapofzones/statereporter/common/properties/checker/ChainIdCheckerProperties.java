package com.mapofzones.statereporter.common.properties.checker;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@ConfigurationProperties(prefix = "checker.chain-id")
public class ChainIdCheckerProperties extends CheckerProperties {

    private Long checkLastDays;
    private String ignoreChainIds;
    private List<String> ignoreChainIdList;

    public List<String> getIgnoreChainIdList() {
        if (ignoreChainIdList == null)
            ignoreChainIdList = Arrays.asList(ignoreChainIds.split(";"));
        return ignoreChainIdList;
    }
}
