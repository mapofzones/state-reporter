package com.mapofzones.statereporter.data.repositories;

import com.mapofzones.statereporter.data.entities.IbcClient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IbcClientRepository extends JpaRepository<IbcClient, IbcClient.IbcClientId> {

    List<IbcClient> getAllByIbcClientId_Zone(String zone);

}
