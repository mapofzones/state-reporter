package com.mapofzones.statereporter.data.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "IBC_CONNECTIONS")
public class IbcConnection {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Embeddable
    public static class IbcConnectionId implements Serializable {

        @Column(name = "ZONE")
        private String zone;

        @Column(name = "CONNECTION_ID")
        private String connectionId;
    }

    @EmbeddedId
    private IbcConnectionId ibcConnectionId;

    @Column(name = "CLIENT_ID")
    private String clientId;

    @Override
    public String toString() {
        return "{" +
                "ibcConnectionId=" + ibcConnectionId +
                ", clientId=" + clientId +
                '}';
    }
}
