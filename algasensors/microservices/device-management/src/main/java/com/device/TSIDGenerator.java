package com.device;

import io.hypersistence.tsid.TSID;

import java.util.Optional;

public class TSIDGenerator {

    private static final TSID.Factory tsidFactory;

    static {
        //System.setProperty("tsid.node", "7");
        //System.setProperty("tsid.node.count", "32");

        Optional.ofNullable(System.getenv("tsid.node"))
                .ifPresent(tsidnode -> System.setProperty("tsid.node", tsidnode));

        Optional.ofNullable(System.getenv("tsid.node.count"))
                .ifPresent(tsidnodecount -> System.setProperty("tsid.node.count", tsidnodecount));

        tsidFactory = TSID.Factory.builder().build();
    }

    // construtor privado
    private TSIDGenerator() {
    }

    // método para gerar TSID
    public static TSID generateTSID() {
        return tsidFactory.generate();
    }
}
