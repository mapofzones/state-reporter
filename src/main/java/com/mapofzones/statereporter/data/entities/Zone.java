package com.mapofzones.statereporter.data.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "zones", schema = "public")
public class Zone {
    @Id
    @NonNull
    @Column(name = "chain_id")
    private String chainId;

//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name = "chain_id", referencedColumnName = "chain_id")
//    private List<IbcClient> clientList;

}
