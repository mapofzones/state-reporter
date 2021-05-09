package com.mapofzones.zoneheightchecker.data.entities;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@IdClass(ZoneKey.class)
@Table(name = "blocks_log", schema = "public")
public class Zone {
    @Id
    @Column(name = "zone", unique = true)
    @NonNull
    private String zone;

    @Column(name = "last_processed_block")
    @NonNull
    private Integer height;

    @Column(name = "last_updated_at")
    @NonNull
    private Timestamp lastUpdatedAt;

    @NonNull
    public String getZone() {
        return zone;
    }

    public void setZone(@NonNull String zone) {
        this.zone = zone;
    }

    @NonNull
    public Integer getHeight() {
        return height;
    }

    public void setHeight(@NonNull Integer height) {
        this.height = height;
    }

    @NonNull
    public Timestamp getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(@NonNull Timestamp lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Zone zone1 = (Zone) o;
        return zone.equals(zone1.zone) && height.equals(zone1.height) && lastUpdatedAt.equals(zone1.lastUpdatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zone, height, lastUpdatedAt);
    }

    @Override
    public String toString() {
        return "Zone{" +
                "zone='" + zone + '\'' +
                ", height=" + height +
                ", lastUpdatedAt=" + lastUpdatedAt +
                '}';
    }
}
