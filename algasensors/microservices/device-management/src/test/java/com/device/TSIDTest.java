package com.device;

import io.hypersistence.tsid.TSID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class TSIDTest {

    @Test
    public void shouldGenerateTSID() {
        //TSID tsid = TSID.fast(); // falou não utilizar dessa forma -> dá o mesmo resultado no console
        //TSID tsid = TSID.Factory.getTsid();

        // chama a classe implementada
        TSID tsid = TSIDGenerator.generateTSID();
        /*System.out.println(">>> String: " + tsid);
        System.out.println(">>> Long: " + tsid.toLong());
        System.out.println(">>> Timestamp: " + tsid.getInstant());*/

        Assertions.assertEquals(tsid.getInstant().truncatedTo(ChronoUnit.MINUTES),
                Instant.now().truncatedTo(ChronoUnit.MINUTES));
    }
}
