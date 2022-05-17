package com.mapofzones.statereporter.services.heightchecker;

import com.mapofzones.statereporter.common.dto.CheckStatus;
import com.mapofzones.statereporter.data.entities.Zone;
import com.mapofzones.statereporter.data.repositories.ZoneRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UnupdatedZoneFinder implements IUnupdatedZoneFinder{

    public static final long HOUR = 3600L * 1000L;
    private final ZoneRepository zoneRepository;

    public UnupdatedZoneFinder(ZoneRepository zoneRepository) {
        this.zoneRepository = zoneRepository;
    }

    @Override
    public CheckStatus findUnupdatedZones() {

        log.info("Start finding for unupdated zones...");
        log.info("Ready to get zones");
        List<Zone> zones = new ArrayList<>(zoneRepository.getZones());
        log.info("Ready to check timestamps");
        List<Zone> unupdatedZones = getNotUpdatedZones(zones);

        CheckStatus checkStatus = new CheckStatus();
        if (unupdatedZones.isEmpty()) {
            checkStatus.setIsOk(true);
        } else {
            checkStatus.setIsOk(false);
            checkStatus.setMessage(createNotificationMessage(unupdatedZones));
        }

        log.info("Finished!!!");
        log.info(checkStatus.toString());
        log.info("---------------");
        return checkStatus;
    }

    private List<Zone> getNotUpdatedZones(List<Zone> zones) {
        List<Zone> notUpdatedZones = new ArrayList<>();
        Timestamp hourAgo = new Timestamp(System.currentTimeMillis() - HOUR);
        for (Zone zone : zones) {
            if (zone.getLastUpdatedAt().before(hourAgo))
                notUpdatedZones.add(zone);
        }
        return notUpdatedZones;
    }

    private String createNotificationMessage(List<Zone> zones) {
        StringBuilder message = new StringBuilder();
        message.append("❗ATTENTION❗<b>No new blocks in the following zones:</b>\n\n<code>Zone</code> | <code>Height</code> | <code>LastUpdatedAt</code>\n");
        for (Zone zone : zones) {
            message.append(zone.getName())
                    .append("    ")
                    .append(zone.getHeight())
                    .append("      ")
                    .append(zone.getLastUpdatedAt())
                    .append("\n");
        }
        return message.toString();
    }
}
