package com.mapofzones.statereporter.data.repositories;

import com.mapofzones.statereporter.data.entities.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ZoneRepository extends JpaRepository<Zone, String> {

    @Query(value = "SELECT zn.lcd_addr from zones z " +
            "JOIN zone_nodes zn on z.chain_id = zn.zone and z.chain_id = zone " +
            "    WHERE z.chain_id = ?1 " +
            "        AND zn.last_block_height is not null " +
            "        AND zn.is_lcd_addr_active = true order by zn.last_block_height DESC LIMIT 1", nativeQuery = true)
    String findLcdAddressWithHightestBlockByChainId(String chainId);

}
