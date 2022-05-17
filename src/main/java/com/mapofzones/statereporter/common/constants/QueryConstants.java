package com.mapofzones.statereporter.common.constants;

public interface QueryConstants {
    String GET_ZONES = "" +
            "select\n" +
            "    zone,\n" +
            "    last_processed_block,\n" +
            "    last_updated_at\n" +
            "from\n" +
            "    blocks_log";
}
