package com.mapofzones.statereporter.services.checkers.chainid;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class ChainIdUtils {

    public static Map<String, List<String>> turnChainIdsToMap(List<String> chainIds) {
        Map<String, List<String>> chainIdsMap = new HashMap<>();

        for (String chainId : chainIds) {
            List<String> listInMap = new ArrayList<>();
            String chainIdWithoutVersion = getChainNameWithoutVersion(chainId);

            if (getChainNameWithoutVersion(chainId).equals("?")) {
                log.warn("Zone not recognized: {}", chainId);
            } else if (chainIdsMap.containsKey(chainIdWithoutVersion))
                chainIdsMap.get(chainIdWithoutVersion).add(chainId);
            else {
                listInMap.add(chainId);
                chainIdsMap.put(chainIdWithoutVersion, listInMap);
            }
        }
        return chainIdsMap;
    }

    public static String getChainNameWithoutVersion(String chainId) {
        if (isChainNameWithoutVersion(chainId)) {
            return chainId;
        } else if (isChainVersionSeparate(chainId)) {
            return chainId.replaceAll("\\d", "");
        } else if (isChainVersionThroughUnderline(chainId)) {
            if (chainId.substring(chainId.lastIndexOf('_')).contains("-")) {
                return chainId.substring(0, chainId.lastIndexOf('-'));
            } else return chainId.substring(0, chainId.lastIndexOf('_'));
        } else if (isChainVersionThroughDash(chainId)) {
            return chainId.substring(0, chainId.lastIndexOf('-'));
        } else return "?";
    }

    private static boolean isChainNameWithoutVersion(String chainId) {
        return chainId.matches("^(([a-zA-Z0-9-]+)([a-zA-Z]{2,})|([a-zA-Z]{0,2}))$");
    }

    private static boolean isChainVersionSeparate(String chainId) {
        return chainId.matches("^([a-zA-Z0-9-]+)(([a-zA-Z])([0-9]{1,5}))$");
    }

    private static boolean isChainVersionThroughUnderline(String chainId) {
        return chainId.matches("^([a-zA-Z0-9-_]+)(_)([a-zA-Z0-9-_]+)$");
    }

    private static boolean isChainVersionThroughDash(String chainId) {
        return chainId.matches("^([a-zA-Z0-9-]+)(-)(([a-zA-Z]?)|([0-9]{0,5})|([0-9]{0,5}[a-zA-Z]?)|(([0-9]{0,5}[a-zA-Z]?).([0-9]{0,5}[a-zA-Z]?)))$");
    }

}
