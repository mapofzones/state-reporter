package com.mapofzones.statereporter.data.repositories;

import com.mapofzones.statereporter.data.constants.QueryConstants;
import com.mapofzones.statereporter.data.entities.Zone;
import com.mapofzones.statereporter.data.entities.ZoneKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZoneRepository extends JpaRepository<Zone, ZoneKey> {
    @Query(value = QueryConstants.GET_ZONES, nativeQuery = true)
    List<Zone> getZones();
}
