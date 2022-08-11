package com.mapofzones.statereporter.services.checkers.chainid;

import com.mapofzones.statereporter.common.dto.CheckStatus;
import com.mapofzones.statereporter.common.properties.checker.ChainIdCheckerProperties;
import com.mapofzones.statereporter.data.entities.Zone;
import com.mapofzones.statereporter.data.repositories.ZoneRepository;
import com.mapofzones.statereporter.services.checkers.Checker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class ChainIdChecker implements Checker {

    private final ZoneRepository zoneRepository;
    private final ChainIdCheckerProperties chainIdCheckerProperties;

    public ChainIdChecker(ZoneRepository zoneRepository, ChainIdCheckerProperties chainIdCheckerProperties) {
        this.zoneRepository = zoneRepository;
        this.chainIdCheckerProperties = chainIdCheckerProperties;
    }

    @Override
    public CheckStatus check() {

        log.info("Ready to get zones");
        List<Zone> zones = zoneRepository.findAll(Sort.by("chainId"));
        List<String> chainIds = zones.stream().map(Zone::getChainId).collect(Collectors.toList());
        Map<String, List<String>> chainIdsMap = ChainIdUtils.turnChainIdsToMap(chainIds);

        log.info("Ready to check");
        List<Zone> newZones = zoneRepository.findAllByCreatedAfter(LocalDateTime.now().minusDays(chainIdCheckerProperties.getCheckLastDays()));

        String message = buildMessage(chainIdsMap, newZones);
        return new CheckStatus(message);
    }

    private String buildMessage(Map<String, List<String>> chainIdsMap, List<Zone> newZones) {

        StringBuilder newZonesString = new StringBuilder("❗ATTENTION❗<New zones/versions has been found</b>\n");
        if (newZones != null && newZones.size() > 0) {
            newZonesString.append("\nNew zones: ");
            newZonesString.append(newZones.stream().map(Zone::getChainId).collect(Collectors.joining(", ")));
        }

        StringBuilder newVersionsString = new StringBuilder();
        if (chainIdsMap != null && chainIdsMap.size() > 0) {
            newVersionsString.append("\nNew versions:\n");
            for (Map.Entry<String, List<String>> currentChainId : chainIdsMap.entrySet()) {
                if (currentChainId.getValue().size() > 1) {
                    if (currentChainId.getValue().stream().noneMatch(chainIdCheckerProperties.getIgnoreChainIdList()::contains)) {
                        newVersionsString.append(" ").append(currentChainId.getKey()).append(":");
                        newVersionsString.append(" [").append(String.join(", ", currentChainId.getValue())).append("];\n");
                    }
                }
            }
        }

        return newZonesString.append(newVersionsString).toString();
    }
}
