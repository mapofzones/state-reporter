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
    private String name;

    @Column(name = "last_processed_block")
    @NonNull
    private Integer height;

    @Column(name = "last_updated_at")
    @NonNull
    private Timestamp lastUpdatedAt;

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
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
        return name.equals(zone1.name) && height.equals(zone1.height) && lastUpdatedAt.equals(zone1.lastUpdatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, height, lastUpdatedAt);
    }

    @Override
    public String toString() {
        return "Zone{" +
                "zone='" + name + '\'' +
                ", height=" + height +
                ", lastUpdatedAt=" + lastUpdatedAt +
                '}';
    }
}
