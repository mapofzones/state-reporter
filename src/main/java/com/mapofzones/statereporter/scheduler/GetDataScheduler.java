package com.mapofzones.statereporter.scheduler;

import com.mapofzones.statereporter.services.HeightCheckerFacade;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class GetDataScheduler {

    private final HeightCheckerFacade heightCheckerFacade;

    public GetDataScheduler(HeightCheckerFacade heightCheckerFacade) {
        this.heightCheckerFacade = heightCheckerFacade;
    }

    @Scheduled(fixedDelayString = "${checker.sync-time}", initialDelay = 10000)
    public void callDownloader() {
        heightCheckerFacade.notifyAboutUnupdatedZones();
    }
}
