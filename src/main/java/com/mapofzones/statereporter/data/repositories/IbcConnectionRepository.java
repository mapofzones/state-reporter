package com.mapofzones.statereporter.data.repositories;

import com.mapofzones.statereporter.data.entities.IbcConnection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IbcConnectionRepository extends JpaRepository<IbcConnection, IbcConnection.IbcConnectionId> {

    List<IbcConnection> getAllByIbcConnectionId_Zone(String zone);

}
