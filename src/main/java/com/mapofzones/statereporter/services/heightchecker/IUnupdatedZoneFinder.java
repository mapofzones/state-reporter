package com.mapofzones.statereporter.services.heightchecker;

import com.mapofzones.statereporter.common.dto.CheckStatus;

public interface IUnupdatedZoneFinder {

    CheckStatus findUnupdatedZones();

}
