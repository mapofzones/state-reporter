package com.mapofzones.statereporter.services.checkers;

import com.mapofzones.statereporter.common.dto.CheckStatus;

public interface Checker {

    CheckStatus check();

}
