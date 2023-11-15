package com.mapofzones.statereporter.services.checkers.price;

import com.mapofzones.statereporter.common.dto.CheckStatus;
import com.mapofzones.statereporter.common.properties.checker.UnupdatedPriceCheckerProperties;
import com.mapofzones.statereporter.data.dto.LatestPriceDTO;
import com.mapofzones.statereporter.data.repositories.ZoneRepository;
import com.mapofzones.statereporter.services.checkers.Checker;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class UnupdatedPriceChecker implements Checker {

    private final ZoneRepository zoneRepository;
    private final UnupdatedPriceCheckerProperties unupdatedPriceCheckerProperties;

    public UnupdatedPriceChecker(ZoneRepository zoneRepository, UnupdatedPriceCheckerProperties unupdatedPriceCheckerProperties) {
        this.zoneRepository = zoneRepository;
        this.unupdatedPriceCheckerProperties = unupdatedPriceCheckerProperties;
    }

    @Override
    public CheckStatus check() {
        List<LatestPriceDTO> latestPrices = zoneRepository.findLatestPrices();

        List<LatestPriceDTO> unupdatedPrices = latestPrices.stream()
                .filter(latestPrice -> latestPrice.getLatestPriceUpdate().isBefore(LocalDateTime.now().minusHours(unupdatedPriceCheckerProperties.getAllowedTime().toHours())))
                .collect(Collectors.toList());

        if (unupdatedPrices.size() == 0) {
            return new CheckStatus();
        }

        String message = buildMessage(unupdatedPrices);
        return new CheckStatus(message);
    }

    private String buildMessage(List<LatestPriceDTO> unupdatedPrices) {

        StringBuilder message = new StringBuilder();
        message.append(
                "❗ATTENTION❗<b>Failed to update prices for these zones:</b>\n\n<code>Zone</code> | <code>BaseDenom</code> | <code>PriceUpdatedAt </code>\n");

        for (LatestPriceDTO latestPrice : unupdatedPrices) {
            message.append(latestPrice.getZone())
                    .append("    ")
                    .append(latestPrice.getBaseDenom())
                    .append("      ")
                    .append(latestPrice.getLatestPriceUpdate().atZone(ZoneId.of("UTC")).toInstant().toString())
                    .append("\n");
        }

        return message.toString();
    }
}
