package com.mapofzones.statereporter.data.repositories;

import com.mapofzones.statereporter.data.entities.IbcChannel;
import com.mapofzones.statereporter.data.entities.IbcClient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IbcChannelRepository extends JpaRepository<IbcChannel, IbcChannel.IbcChannelId> {

    List<IbcChannel> getAllByIbcChannelId_Zone(String zone);

}
