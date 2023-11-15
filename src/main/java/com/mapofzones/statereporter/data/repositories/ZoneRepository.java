package com.mapofzones.statereporter.data.repositories;

import com.mapofzones.statereporter.data.dto.LatestPriceDTO;
import com.mapofzones.statereporter.data.entities.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ZoneRepository extends JpaRepository<Zone, String> {

    @Query(value = "SELECT zn.lcd_addr from zones z " +
            "JOIN zone_nodes zn on z.chain_id = zn.zone and z.chain_id = zone " +
            "    WHERE z.chain_id = ?1 " +
            "        AND zn.last_block_height is not null " +
            "        AND zn.is_lcd_addr_active = true order by zn.last_block_height DESC LIMIT 1", nativeQuery = true)
    String findLcdAddressWithHightestBlockByChainId(String chainId);

    List<Zone> findAllByCreatedAfter(LocalDateTime dateTime);

    @Query(value = 
        " SELECT " + 
            " zone, " + 
            " base_denom AS baseDenom, " + 
            " max(datetime) AS latestPriceUpdate " + 
        " FROM " + 
            " token_prices " + 
            " INNER JOIN zones ON token_prices.zone = zones.chain_id and token_prices.base_denom = zones.base_token_denom " + 
        " WHERE " + 
            " zones.is_mainnet = TRUE " + 
        " GROUP BY " + 
            " zone, base_denom ", nativeQuery = true)
    List<LatestPriceDTO> findLatestPrices();
    

}
