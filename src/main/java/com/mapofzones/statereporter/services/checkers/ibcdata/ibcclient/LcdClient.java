package com.mapofzones.statereporter.services.checkers.ibcdata.ibcclient;

import com.mapofzones.statereporter.common.properties.EndpointProperties;
import com.mapofzones.statereporter.services.checkers.ibcdata.ibcclient.dto.ChannelsDto;
import com.mapofzones.statereporter.services.checkers.ibcdata.ibcclient.dto.ClientDto;
import com.mapofzones.statereporter.services.checkers.ibcdata.ibcclient.dto.ConnectionDto;
import com.mapofzones.statereporter.services.checkers.ibcdata.ibcclient.dto.IbcData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Optional;

@Slf4j
public class LcdClient {

    private final RestTemplate lcdClientRestTemplate;
    private final EndpointProperties endpointProperties;

    public LcdClient(RestTemplate lcdClientRestTemplate,
                     EndpointProperties endpointProperties) {
        this.lcdClientRestTemplate = lcdClientRestTemplate;
        this.endpointProperties = endpointProperties;
    }

    public IbcData getIbcData(String chainId, String lcdAddress) {
        IbcData ibcData = new IbcData(chainId, lcdAddress);
        if (lcdAddress != null && !lcdAddress.isBlank()) {
            try {
                log.debug("Find clients: {}", chainId);
                findClients(ibcData, "");
                log.debug("Find connections: {}", chainId);
                findConnections(ibcData, "");
                log.debug("Find channels: {}", chainId);
                findChannels(ibcData, "");
                ibcData.setSuccess(true);
            } catch (Exception e) {
                ibcData.setSuccess(false);
            }
        }
        return ibcData;
    }

    private void findClients(IbcData ibcData, String nextPageKey) throws RestClientException {
        URI uri = URI.create(String.format(ibcData.getLcdAddress() + endpointProperties.getLcd().getClients(), nextPageKey));

        Optional<ClientDto> receivedClientDto = Optional.ofNullable(lcdClientRestTemplate.getForEntity(uri, ClientDto.class).getBody());
        receivedClientDto.ifPresent(clientDto -> clientDto.setSuccess(true));
        ClientDto clientDto = receivedClientDto.orElse(new ClientDto(false));

        if (!clientDto.getClientStates().isEmpty()) {

            for (ClientDto.ClientStates currentClientState : clientDto.getClientStates()) {
                IbcData.Client client = new IbcData.Client();
                client.setClientId(currentClientState.getClientId());
                client.setChainId(currentClientState.getClientState().getChainId());

                ibcData.getClients().add(client);
            }

            if (clientDto.getPagination().getNextKey() != null)
                findClients(ibcData, clientDto.getPagination().getNextKey());
        }
    }

    private void findConnections(IbcData ibcData, String nextPageKey) throws RestClientException {
        URI uri = URI.create(String.format(ibcData.getLcdAddress() + endpointProperties.getLcd().getConnections(), nextPageKey));

        Optional<ConnectionDto> receivedConnectionDto = Optional.ofNullable(lcdClientRestTemplate.getForEntity(uri, ConnectionDto.class).getBody());
        receivedConnectionDto.ifPresent(connectionDto -> connectionDto.setSuccess(true));
        ConnectionDto connectionDto = receivedConnectionDto.orElse(new ConnectionDto(false));

        if (!connectionDto.getConnections().isEmpty()) {

            for (ConnectionDto.Connection currentConnection : connectionDto.getConnections()) {
                IbcData.Connection connection = new IbcData.Connection();
                connection.setConnectionId(currentConnection.getId());
                connection.setClientId(currentConnection.getClientId());

                ibcData.getConnections().add(connection);
            }

            if (connectionDto.getPagination().getNextKey() != null) {
                findConnections(ibcData, connectionDto.getPagination().getNextKey());
            }
        }
    }

    private void findChannels(IbcData ibcData, String nextPageKey) throws RestClientException {
        URI uri = URI.create(String.format(ibcData.getLcdAddress() + endpointProperties.getLcd().getChannels(), nextPageKey));

        Optional<ChannelsDto> receivedChannelsDto = Optional.ofNullable(lcdClientRestTemplate.getForEntity(uri, ChannelsDto.class).getBody());
        receivedChannelsDto.ifPresent(clientDto -> clientDto.setSuccess(true));
        ChannelsDto channelsDto = receivedChannelsDto.orElse(new ChannelsDto(false));

        if (!channelsDto.getChannels().isEmpty()) {

            for (ChannelsDto.Channel currentChannel : channelsDto.getChannels()) {
                IbcData.Channel channel = new IbcData.Channel();
                channel.setChannelId(currentChannel.getChannelId());
                channel.setConnectionId(currentChannel.getConnections().get(0));
                channel.setCounterpartyChannelId(currentChannel.getCounterparty().getChannelId());
                channel.setIsOpened(currentChannel.getState().equals("STATE_OPEN"));

                ibcData.getChannels().add(channel);
            }

            if (channelsDto.getPagination().getNextKey() != null) {
                findChannels(ibcData, channelsDto.getPagination().getNextKey());
            }
        }
    }
}
