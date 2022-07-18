package com.mapofzones.statereporter.services.checkers;

import com.mapofzones.statereporter.common.dto.CheckStatus;
import com.mapofzones.statereporter.data.entities.ZoneBlocksLog;
import com.mapofzones.statereporter.data.repositories.ZoneBlocksLogRepository;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class UnupdatedZoneChecker implements Checker {

    public static final long HOUR = 3600L * 1000L;
    private final ZoneBlocksLogRepository zoneBlocksLogRepository;

    public UnupdatedZoneChecker(ZoneBlocksLogRepository zoneBlocksLogRepository) {
        this.zoneBlocksLogRepository = zoneBlocksLogRepository;
    }

    @Override
    public CheckStatus check() {

        log.info("Start finding for unupdated zones...");
        log.info("Ready to get zones");
        List<ZoneBlocksLog> zoneBlocksLogs = new ArrayList<>(zoneBlocksLogRepository.getZones());
        log.info("Ready to check timestamps");
        List<ZoneBlocksLog> unupdatedZoneBlocksLogs = getNotUpdatedZones(zoneBlocksLogs);

        CheckStatus checkStatus = new CheckStatus();
        if (!unupdatedZoneBlocksLogs.isEmpty()) {
            checkStatus.setMessage(createNotificationMessage(unupdatedZoneBlocksLogs));
        }

        log.info("Finished!!!");
        log.info(checkStatus.toString());
        log.info("---------------");
        return checkStatus;
    }

    private List<ZoneBlocksLog> getNotUpdatedZones(List<ZoneBlocksLog> zoneBlocksLogs) {
        List<ZoneBlocksLog> notUpdatedZoneBlocksLogs = new ArrayList<>();
        Timestamp hourAgo = new Timestamp(System.currentTimeMillis() - HOUR);
        for (ZoneBlocksLog zoneBlocksLog : zoneBlocksLogs) {
            if (zoneBlocksLog.getLastUpdatedAt().before(hourAgo))
                notUpdatedZoneBlocksLogs.add(zoneBlocksLog);
        }
        return notUpdatedZoneBlocksLogs;
    }

    private String createNotificationMessage(List<ZoneBlocksLog> zoneBlocksLogs) {
        StringBuilder message = new StringBuilder();
        message.append("❗ATTENTION❗<b>No new blocks in the following zones:</b>\n\n<code>Zone</code> | <code>Height</code> | <code>LastUpdatedAt</code>\n");
        for (ZoneBlocksLog zoneBlocksLog : zoneBlocksLogs) {
            message.append(zoneBlocksLog.getName())
                    .append("    ")
                    .append(zoneBlocksLog.getHeight())
                    .append("      ")
                    .append(zoneBlocksLog.getLastUpdatedAt())
                    .append("\n");
        }
        return message.toString();
    }
}
