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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "IBC_CHANNELS")
public class IbcChannel {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Embeddable
    public static class IbcChannelId implements Serializable {

        @Column(name = "ZONE")
        private String zone;

        @Column(name = "CHANNEL_ID")
        private String channelId;
    }

    @EmbeddedId
    private IbcChannelId ibcChannelId;

    @Column(name = "CONNECTION_ID")
    private String connectionId;

    @Column(name = "COUNTERPARTY_CHANNEL_ID")
    private String counterpartyChannelId;

    @Column(name = "is_opened")
    private Boolean isOpened;

    @Override
    public String toString() {
        return "{" +
                "ibcChannelId=" + ibcChannelId.getChannelId() +
                ", connectionId=" + connectionId +
                ", counterpartyChannelId=" + counterpartyChannelId +
                ", isOpened=" + isOpened +
                '}';
    }
}
