package com.mapofzones.statereporter.services.checkers.ibcclient.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConnectionDto {

    @JsonIgnore
    private boolean isSuccess;

    @JsonProperty("pagination")
    private Pagination pagination;

    @JsonProperty("connections")
    private List<Connection> connections;

    public ConnectionDto(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Connection {

        @JsonProperty("id")
        private String id;

        @JsonProperty("client_id")
        private String clientId;
    }
}
