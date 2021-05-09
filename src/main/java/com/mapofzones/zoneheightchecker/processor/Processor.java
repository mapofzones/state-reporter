package com.mapofzones.zoneheightchecker.processor;

import com.mapofzones.zoneheightchecker.data.entities.Zone;
import com.mapofzones.zoneheightchecker.data.repositories.ZoneRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class Processor {
    public static final long HOUR = 3600L * 1000L;
    private final ZoneRepository zoneRepository;

    public Processor(ZoneRepository zoneRepository) {
        this.zoneRepository = zoneRepository;
    }

    public void doScript() {
        System.out.println("Starting...");

        List<Zone> zones = new ArrayList<>();
        System.out.println("ready to get zones");
        zones.addAll(zoneRepository.getZones());
        System.out.println("ready to check timestamps");
        List<Zone> unupdatedZones = getUnupdatedZones(zones);
        System.out.println("ready to notify if needed");
        callNotifier(unupdatedZones);
        System.out.println("Finished!");
        System.out.println("---------------");
    }

    private List<Zone> getUnupdatedZones(List<Zone> zones) {
        List<Zone> unupdatedZones = new ArrayList<>();
        Timestamp hourAgo = new Timestamp(System.currentTimeMillis() - HOUR);
        for (Zone zone : zones) {
            if (zone.getLastUpdatedAt().before(hourAgo))
                unupdatedZones.add(zone);
        }
        return unupdatedZones;
    }

    private void callNotifier(List<Zone> zones) {
        if (zones == null || zones.size() == 0)
            return;
        String message = createNotificationMessage(zones);
        notify(message);
    }

    private String createNotificationMessage(List<Zone> zones) {
        StringBuilder message = new StringBuilder();
        message.append("No new blocks in the following zones:\n");
        for (Zone zone : zones) {
            message.append("Zone: ").append(zone.getName())
                    .append(" Height: ").append(zone.getHeight())
                    .append(" LastUpdatedAt: ").append(zone.getLastUpdatedAt())
                    .append("\n");
        }
        return message.toString();
    }

    private void notify(String message) {
//        todo: notify telegram
        System.out.println(message);
    }
}
