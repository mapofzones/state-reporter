package com.mapofzones.statereporter.scheduler;

import com.mapofzones.statereporter.services.StateReporterFacade;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class GetDataScheduler {

    private final StateReporterFacade stateReporterFacade;

    public GetDataScheduler(StateReporterFacade stateReporterFacade) {
        this.stateReporterFacade = stateReporterFacade;
    }

    @Scheduled(fixedDelayString = "#{unupdatedZoneCheckerProperties.syncTime}")
    public void callNotifyAboutUnupdatedZone() {
        stateReporterFacade.notifyAboutUnupdatedZones();
    }

//    @Scheduled(fixedDelayString = "#{ibcDataCheckerProperties.syncTime}")
//    public void callNotifyAboutIbcDataChanged() {
//        stateReporterFacade.notifyAboutIbcDataChanged();
//    }
//
//    @Scheduled(fixedDelayString = "#{chainIdCheckerProperties.syncTime}")
//    public void callNotifyAboutChainIdChanged() {
//        stateReporterFacade.notifyAboutNewChainId();
//    }

}
