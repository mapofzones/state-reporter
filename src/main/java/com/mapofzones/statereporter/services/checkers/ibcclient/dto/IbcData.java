package com.mapofzones.statereporter.services.checkers.ibcclient.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class IbcData {

    private String zone;
    private String lcdAddress;
    private List<Client> clients;
    private List<Connection> connections;
    private List<Channel> channels;
    private boolean isSuccess;

    public IbcData(String zone, String lcdAddress) {
        this.zone = zone;
        this.lcdAddress = lcdAddress;
        this.isSuccess = false;
        this.clients = new ArrayList<>();
        this.connections = new ArrayList<>();
        this.channels = new ArrayList<>();
    }

    @Data
    public static class Client {

        private String clientId;
        private String chainId;

        @Override
        public String toString() {
            return "{" +
                    "ibcClientId=" + clientId +
                    ", chainId=" + chainId +
                    '}';
        }

    }

    @Data
    public static class Connection {

        private String connectionId;
        private String clientId;

        @Override
        public String toString() {
            return "{" +
                    "ibcConnectionId=" + connectionId +
                    ", clientId=" + clientId +
                    '}';
        }

    }

    @Data
    public static class Channel {

        private String channelId;
        private String connectionId;
        private String counterpartyChannelId;
        private Boolean isOpened;

        @Override
        public String toString() {
            return "{" +
                    "ibcChannelId=" + channelId +
                    ", connectionId=" + connectionId +
                    ", counterpartyChannelId=" + counterpartyChannelId +
                    ", isOpened=" + isOpened +
                    '}';
        }
    }

}
