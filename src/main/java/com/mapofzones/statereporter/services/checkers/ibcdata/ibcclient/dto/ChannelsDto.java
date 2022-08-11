package com.mapofzones.statereporter.services.checkers.ibcdata.ibcclient.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChannelsDto {

    @JsonIgnore
    private boolean isSuccess;

    @JsonProperty("channels")
    private List<Channel> channels;

    @JsonProperty("pagination")
    private Pagination pagination;

    public ChannelsDto(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Channel {

        @JsonProperty("channel_id")
        private String channelId;

        @JsonProperty("connection_hops")
        private List<String> connections;

        @JsonProperty("counterparty")
        private Counterparty counterparty;

        @JsonProperty("state")
        private String state;

        @Data
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class Counterparty {
            @JsonProperty("channel_id")
            private String channelId;
        }

    }
}
