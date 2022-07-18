package com.mapofzones.statereporter.services;

import com.mapofzones.statereporter.common.dto.CheckStatus;
import com.mapofzones.statereporter.services.checkers.Checker;
import com.mapofzones.statereporter.services.notifier.INotifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class StateReporterFacade {


    private final Checker unupdatedZoneChecker;
    private final Checker ibcDataChecker;
    private final Checker chainIdChecker;
    private final INotifier notifier;

    public StateReporterFacade(@Qualifier("unupdatedZoneChecker") Checker unupdatedZoneChecker,
                               @Qualifier("ibcDataChecker") Checker ibcDataChecker,
                               @Qualifier("chainIdChecker") Checker chainIdChecker,
                               @Lazy INotifier notifier) {
        this.unupdatedZoneChecker = unupdatedZoneChecker;
        this.ibcDataChecker = ibcDataChecker;
        this.chainIdChecker = chainIdChecker;
        this.notifier = notifier;
    }

    @Transactional
    public void notifyAboutUnupdatedZones() {
        CheckStatus status = unupdatedZoneChecker.check();
        sendMessageIfStatusIsNotOk(status);
    }

    @Transactional
    public void notifyAboutIbcDataChanged() {
        CheckStatus status = ibcDataChecker.check();
        sendMessageIfStatusIsNotOk(status);
    }

    @Transactional
    public void notifyAboutNewChainId() {
        CheckStatus status = chainIdChecker.check();
        sendMessageIfStatusIsNotOk(status);
    }

    private synchronized void sendMessageIfStatusIsNotOk(CheckStatus status) {
        log.info(status.toString());
        if (!status.getIsOk() && !status.getMessage().isBlank()) {
            notifier.sendMessage(status.getMessage());
        }
    }
}
