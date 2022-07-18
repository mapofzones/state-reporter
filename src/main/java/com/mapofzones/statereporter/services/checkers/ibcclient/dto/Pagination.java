package com.mapofzones.statereporter.services.checkers.ibcclient.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Pagination {

    @JsonProperty("next_key")
    private String nextKey;

    @JsonProperty("total")
    private String total;

}
