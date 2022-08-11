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
public class ClientDto {

    @JsonIgnore
    private boolean isSuccess;

    @JsonProperty("pagination")
    private Pagination pagination;

    @JsonProperty("client_states")
    private List<ClientStates> clientStates;

    public ClientDto(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ClientStates {

        @JsonProperty("client_state")
        private ClientState clientState;

        @JsonProperty("client_id")
        private String clientId;

        @Data
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class ClientState {

            @JsonProperty("chain_id")
            private String chainId;

        }
    }
}
