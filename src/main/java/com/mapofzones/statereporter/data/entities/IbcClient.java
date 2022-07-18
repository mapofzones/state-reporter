package com.mapofzones.statereporter.data.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "IBC_CLIENTS")
public class IbcClient {


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Embeddable
    public static class IbcClientId implements Serializable {

        @Column(name = "ZONE")
        private String zone;

        @Column(name = "CLIENT_ID")
        private String clientId;
    }

    @EmbeddedId
    private IbcClient.IbcClientId ibcClientId;

    @Column(name = "CHAIN_ID")
    private String chainId;

    @Override
    public String toString() {
        return "{" +
                "ibcClientId=" + ibcClientId.getClientId() +
                ", chainId=" + chainId +
                '}';
    }
}
