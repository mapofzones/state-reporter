package com.mapofzones.statereporter.data.repositories;

import com.mapofzones.statereporter.common.constants.QueryConstants;
import com.mapofzones.statereporter.data.entities.ZoneBlocksLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZoneBlocksLogRepository extends JpaRepository<ZoneBlocksLog, String> {

    @Query(value = QueryConstants.GET_ZONES, nativeQuery = true)
    List<ZoneBlocksLog> getZones();
}
