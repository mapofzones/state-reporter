package com.mapofzones.statereporter.services.checkers.chainid;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChainIdCheckerTest {

    @Test
    void getChainNameWithoutVersionTest() {
        String s1 = "akashnet-2";
        String s2 = "alteredcarbon";
        String s3 = "BASE_1";
        String s4 = "bitsong-2b";
        String s5 = "band-laozi-testnet4";
        String s6 = "evmos_9000-1";
        String s7 = "evmos_9001-1";
        String s8 = "sifchain-testnet-042-ibc";
        String s9 = "PubKeySecp256k1{03DB0F159D76AFB435B986F1FA0ADFEC0FC86BDF98F6D279E501D6BA9214010218}";
        String s10 = "yet-another-chain";
        String s11 = "checkers";
        String s12 = "genesis_29-2";

        assertEquals(ChainIdUtils.getChainNameWithoutVersion(s1), "akashnet");
        assertEquals(ChainIdUtils.getChainNameWithoutVersion(s2), "alteredcarbon");
        assertEquals(ChainIdUtils.getChainNameWithoutVersion(s3), "BASE");
        assertEquals(ChainIdUtils.getChainNameWithoutVersion(s4), "bitsong");
        assertEquals(ChainIdUtils.getChainNameWithoutVersion(s5), "band-laozi-testnet");
        assertEquals(ChainIdUtils.getChainNameWithoutVersion(s6), "evmos_9000");
        assertEquals(ChainIdUtils.getChainNameWithoutVersion(s7), "evmos_9001");
        assertEquals(ChainIdUtils.getChainNameWithoutVersion(s8), "sifchain-testnet-042-ibc");
        assertEquals(ChainIdUtils.getChainNameWithoutVersion(s9), "?");
        assertEquals(ChainIdUtils.getChainNameWithoutVersion(s10), "yet-another-chain");
        assertEquals(ChainIdUtils.getChainNameWithoutVersion(s11), "checkers");
        assertEquals(ChainIdUtils.getChainNameWithoutVersion(s12), "genesis_29");

    }
}