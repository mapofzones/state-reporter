package com.mapofzones.statereporter.services.checkers.ibcdata;

import com.mapofzones.statereporter.common.dto.CheckStatus;
import com.mapofzones.statereporter.data.entities.IbcChannel;
import com.mapofzones.statereporter.data.entities.IbcClient;
import com.mapofzones.statereporter.data.entities.IbcConnection;
import com.mapofzones.statereporter.data.entities.Zone;
import com.mapofzones.statereporter.data.repositories.IbcChannelRepository;
import com.mapofzones.statereporter.data.repositories.IbcClientRepository;
import com.mapofzones.statereporter.data.repositories.IbcConnectionRepository;
import com.mapofzones.statereporter.data.repositories.ZoneRepository;
import com.mapofzones.statereporter.services.checkers.Checker;
import com.mapofzones.statereporter.services.checkers.ibcdata.ibcclient.LcdClient;
import com.mapofzones.statereporter.services.checkers.ibcdata.ibcclient.dto.IbcData;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Slf4j
public class IbcDataChecker implements Checker {

    private final ZoneRepository zoneRepository;
    private final IbcClientRepository ibcClientRepository;
    private final IbcConnectionRepository ibcConnectionRepository;
    private final IbcChannelRepository ibcChannelRepository;

    private final LcdClient lcdClient;

    public IbcDataChecker(ZoneRepository zoneRepository,
                          IbcClientRepository ibcClientRepository,
                          IbcConnectionRepository ibcConnectionRepository,
                          IbcChannelRepository ibcChannelRepository,
                          LcdClient lcdClient) {
        this.zoneRepository = zoneRepository;
        this.ibcClientRepository = ibcClientRepository;
        this.ibcConnectionRepository = ibcConnectionRepository;
        this.ibcChannelRepository = ibcChannelRepository;
        this.lcdClient = lcdClient;
    }

    @Override
    public CheckStatus check() {

        log.info("Ready to get zones");
        List<DiffMessage> diffMessageList = new ArrayList<>();
        List<Zone> zones = new ArrayList<>(zoneRepository.findAll());

        log.info("Ready to check");
        for (Zone zone : zones) {
            List<IbcClient> clients = ibcClientRepository.getAllByIbcClientId_Zone(zone.getChainId());
            List<IbcConnection> connections = ibcConnectionRepository.getAllByIbcConnectionId_Zone(zone.getChainId());
            List<IbcChannel> channels = ibcChannelRepository.getAllByIbcChannelId_Zone(zone.getChainId());

            DiffMessage diffMessage = new DiffMessage(zone.getChainId());

            if (!(clients.isEmpty() && connections.isEmpty() && channels.isEmpty())) {

                String aliveAddressWithHightestBlock = zoneRepository.findLcdAddressWithHightestBlockByChainId(zone.getChainId());

                if (aliveAddressWithHightestBlock != null && !aliveAddressWithHightestBlock.isEmpty()) {
                    IbcData ibcData = lcdClient.getIbcData(zone.getChainId(), aliveAddressWithHightestBlock);
                    compareIbcData(clients, connections, channels, ibcData, diffMessage);
                }
            }

            diffMessageList.add(diffMessage);
        }
        return new CheckStatus(buildMessage(diffMessageList));
    }

    private void compareIbcData(List<IbcClient> clients, List<IbcConnection> connections, List<IbcChannel> channels, IbcData ibcData, DiffMessage diffMessage) {
        compareClients(clients, ibcData, diffMessage);
        compareConnections(connections, ibcData, diffMessage);
        compareChannels(channels, ibcData, diffMessage);
    }

    private void compareClients(List<IbcClient> clients, IbcData ibcData, DiffMessage diffMessage) {
        for (IbcClient dbClient : clients) {
            for (IbcData.Client ibcClient : ibcData.getClients()) {
                if (dbClient.getIbcClientId().getClientId().equals(ibcClient.getClientId())) {
                    if (!dbClient.getChainId().equals(ibcClient.getChainId()))
                        diffMessage.getClient().getEntries().put(dbClient.toString(), ibcClient.toString());
                    break;
                }
            }
        }

        for (IbcData.Client ibcClient : ibcData.getClients()) {
            boolean foundClient = false;
            for (IbcClient dbClient : clients) {
                if (ibcClient.getClientId().equals(dbClient.getIbcClientId().getClientId())) {
                    foundClient = true;
                    break;
                }
            }
            if (!foundClient) {
                diffMessage.getClient().getMissingInDB().add(ibcClient.toString());
            }
        }
    }

    private void compareConnections(List<IbcConnection> connections, IbcData ibcData, DiffMessage diffMessage) {
        for (IbcConnection dbConnection : connections) {
            for (IbcData.Connection ibcConnection : ibcData.getConnections()) {
                if (dbConnection.getIbcConnectionId().getConnectionId().equals(ibcConnection.getConnectionId())) {
                    if (!dbConnection.getClientId().equals(ibcConnection.getClientId()))
                        diffMessage.getConnections().getEntries().put(dbConnection.toString(), ibcConnection.toString());
                    break;
                }
            }
        }

        for (IbcData.Connection ibcConnection : ibcData.getConnections()) {
            boolean foundConnection = false;
            for (IbcConnection dbConnection : connections) {
                if (ibcConnection.getConnectionId().equals(dbConnection.getIbcConnectionId().getConnectionId())) {
                    foundConnection = true;
                    break;
                }
            }
            if (!foundConnection) {
                diffMessage.getConnections().getMissingInDB().add(ibcConnection.toString());
            }
        }
    }

    private void compareChannels(List<IbcChannel> channels, IbcData ibcData, DiffMessage diffMessage) {
        for (IbcChannel dbChannel : channels) {
            for (IbcData.Channel ibcChannel : ibcData.getChannels()) {
                if (dbChannel.getIbcChannelId().getChannelId().equals(ibcChannel.getChannelId())) {
                    if (!dbChannel.getConnectionId().equals(ibcChannel.getConnectionId())
                            || dbChannel.getCounterpartyChannelId() != null
                            && !dbChannel.getCounterpartyChannelId().equals(ibcChannel.getCounterpartyChannelId())
                            || !dbChannel.getIsOpened().equals(ibcChannel.getIsOpened()))
                        diffMessage.getConnections().getEntries().put(dbChannel.toString(), ibcChannel.toString());
                    break;
                }
            }
        }

        for (IbcData.Channel ibcChannel : ibcData.getChannels()) {
            boolean foundChannel = false;
            for (IbcChannel dbChannel : channels) {
                if (ibcChannel.getChannelId().equals(dbChannel.getIbcChannelId().getChannelId())) {
                    foundChannel = true;
                    break;
                }
            }
            if (!foundChannel) {
                diffMessage.getChannel().getMissingInDB().add(ibcChannel.toString());
            }
        }
    }

    private String buildMessage(List<DiffMessage> diffMessages) {
        String startOfMessage = "❗ATTENTION❗<Inconsistencies has been found</b>\n";
        StringBuilder message = new StringBuilder(startOfMessage);

        for (DiffMessage diffMessage : diffMessages) {
            message.append(diffMessage.buildMessage());
        }
        return message.toString();
    }

    @Data
    private static class DiffMessage {

        private final String TAB = " ";
        private final String TAB_X2 = TAB + TAB;
        private final String TAB_X3 = TAB_X2 + TAB;
        private final String DELIMITER = " -> ";

        private StringBuilder message;

        private String zone;
        private Diff client;
        private Diff connections;
        private Diff channel;

        public DiffMessage(String zone) {
            this.zone = zone;
            this.client = new Diff();
            this.connections = new Diff();
            this.channel = new Diff();
        }

        public String buildMessage() {
            StringBuilder message = new StringBuilder();

            if (!isEmpty()) {
                message.append("Zone: ").append(zone).append("\n");

                buildMessage(message, client, "Clients");
                buildMessage(message, connections, "Connections");
                buildMessage(message, channel, "Channels");
            }

            return message.toString();
        }

        private void buildMessage(StringBuilder message, Diff diff, String name) {
            if (!diff.isEmpty()) {
                boolean entityNameIsShowed = false;
                if (!diff.getEntries().isEmpty()) {
                    message.append(TAB).append(name).append(":").append("\n");
                    message.append(TAB_X2).append("Doesn't match:").append("\n");
                    diff.getEntries().forEach((key, value) -> message.append(TAB_X3).append("DB:").append(key).append(DELIMITER).append("IBC:").append(value).append("\n"));
                    entityNameIsShowed = true;
                }

                if (!diff.getMissingInDB().isEmpty()) {

                    if (!entityNameIsShowed) {
                        message.append(TAB).append(name).append(":").append("\n");
                    }

                    message.append(TAB_X2).append("Not in DB:").append("\n");
                    diff.getMissingInDB().forEach(value -> message.append(TAB_X3).append(value).append("\n"));
                }
            }
        }

        public boolean isEmpty() {
            return client.isEmpty() && connections.isEmpty() && channel.isEmpty();
        }

        @Data
        private static class Diff {
            private Map<String, String> entries;
            private List<String> missingInDB;

            public Diff() {
                this.entries = new TreeMap<>();
                this.missingInDB = new ArrayList<>();
            }

            public boolean isEmpty() {
                return entries.isEmpty() && missingInDB.isEmpty();
            }
        }
    }
}
