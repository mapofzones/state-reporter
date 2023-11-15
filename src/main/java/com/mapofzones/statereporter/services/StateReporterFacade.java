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
    private final Checker unupdatedPriceChecker;
    private final INotifier notifier;

    public StateReporterFacade(@Qualifier("unupdatedZoneChecker") Checker unupdatedZoneChecker,
                               @Qualifier("ibcDataChecker") Checker ibcDataChecker,
                               @Qualifier("chainIdChecker") Checker chainIdChecker,
                               @Qualifier("unupdatedPriceChecker") Checker unupdatedPriceChecker,
                               @Lazy INotifier notifier) {
        this.unupdatedZoneChecker = unupdatedZoneChecker;
        this.ibcDataChecker = ibcDataChecker;
        this.chainIdChecker = chainIdChecker;
        this.unupdatedPriceChecker = unupdatedPriceChecker;
        this.notifier = notifier;
    }

    @Transactional
    public void notifyAboutUnupdatedZones() {

        log.info("Start checking unupdated zones...");
        CheckStatus status = unupdatedZoneChecker.check();
        log.info("UnupdatedZoneChecker has been finished!!!");

        log.info("Start sending message from unupdatedZoneChecker...");
        sendMessageIfStatusIsNotOk(status);
        log.info("Message has been sent from unupdatedZoneChecker");
    }

    @Transactional
    public void notifyAboutIbcDataChanged() {

        log.info("Start checking IBC data zones...");
        CheckStatus status = ibcDataChecker.check();
        log.info("ibcDataChecker has been finished!!!");

        log.info("Start sending message from ibcDataChecker...");
        sendMessageIfStatusIsNotOk(status);
        log.info("Message has been sent from ibcDataChecker");
    }

    @Transactional
    public void notifyAboutNewChainId() {

        log.info("Start checking chainIds...");
        CheckStatus status = chainIdChecker.check();
        log.info("ChainIdChecker has been finished!!!");

        log.info("Start sending message from ChainIdChecker...");
        sendMessageIfStatusIsNotOk(status);
        log.info("Message has been sent from ChainIdChecker");
    }

    @Transactional
    public void notifyAboutUnupdatedPrices() {

        log.info("Start checking prices...");
        CheckStatus status = unupdatedPriceChecker.check();
        log.info("UnupdatedPriceChecker has been finished!!!");

        log.info("Start sending message from UnupdatedPriceChecker...");
        sendMessageIfStatusIsNotOk(status);
        log.info("Message has been sent from UnupdatedPriceChecker");
    }

    private synchronized void sendMessageIfStatusIsNotOk(CheckStatus status) {
        if (!status.getIsOk() && !status.getMessage().isBlank()) {
            notifier.sendMessage(status.getMessage());
        }
    }
}
