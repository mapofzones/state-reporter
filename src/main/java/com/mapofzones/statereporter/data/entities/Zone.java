package com.mapofzones.statereporter.data.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "chainId")
@Entity
@Table(name = "zones", schema = "public")
public class Zone {
    @Id
    @NonNull
    @Column(name = "chain_id")
    private String chainId;

    @Column(name = "added_at")
    private LocalDateTime created;

}
