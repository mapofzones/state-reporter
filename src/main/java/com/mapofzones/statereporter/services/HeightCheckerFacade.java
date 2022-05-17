package com.mapofzones.statereporter.services;

import com.mapofzones.statereporter.common.dto.CheckStatus;
import com.mapofzones.statereporter.services.heightchecker.IUnupdatedZoneFinder;
import com.mapofzones.statereporter.services.notifier.INotifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class HeightCheckerFacade {

    private final IUnupdatedZoneFinder unupdatedZoneFinder;
    private final INotifier notifier;

    public HeightCheckerFacade(IUnupdatedZoneFinder unupdatedZoneFinder,
                               @Lazy INotifier notifier) {
        this.unupdatedZoneFinder = unupdatedZoneFinder;
        this.notifier = notifier;
    }

    public void notifyAboutUnupdatedZones() {
        CheckStatus status = unupdatedZoneFinder.findUnupdatedZones();
        log.info(status.toString());
        if (status.getIsOk()) {
            notifier.sendMessage(status.getMessage());
        }
    }
}
