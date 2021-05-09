package com.mapofzones.zoneheightchecker.processor;

import com.mapofzones.zoneheightchecker.data.entities.Zone;
import com.mapofzones.zoneheightchecker.data.repositories.ZoneRepository;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class Processor {
    private final ZoneRepository zoneRepository;

    public Processor(ZoneRepository zoneRepository) {
        this.zoneRepository = zoneRepository;
    }

    public void doScript() {
        System.out.println("Starting...");

        List<Zone> zones = new ArrayList<>();
        System.out.println("ready to get zones");
        zones.addAll(zoneRepository.getZones());
        for (Zone zone : zones) {
            System.out.println(zone);
        }
        System.out.println("ready to check timestamps");
        checkTimestamps(zones);
        System.out.println("ready to notify if needed");
        callNotifier();
        System.out.println("Finished!");
        System.out.println("---------------");
    }

    private void checkTimestamps(List<Zone> zones) {
        for (Zone zone : zones) {
//            todo: check timestamps
        }
    }

    private void callNotifier() {
//        todo: create messages
//        todo: notify if message exists
    }
}
