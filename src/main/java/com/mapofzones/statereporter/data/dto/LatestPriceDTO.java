package com.mapofzones.statereporter.data.dto;
import java.time.LocalDateTime;

public interface LatestPriceDTO {
    String getZone();
    String getBaseDenom();
    LocalDateTime getLatestPriceUpdate();
}
