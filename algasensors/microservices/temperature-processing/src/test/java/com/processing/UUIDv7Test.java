package com.processing;

import com.processing.common.UUIDGenerator;
import com.processing.common.UUIDv7Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class UUIDv7Test {

    @Test
    void shouldGenerateUUIDv7() {

        /*UUID uuid1 = UUIDGenerator.generateTimeBasedUUID();
        UUID uuid2 = UUIDGenerator.generateTimeBasedUUID();
        UUID uuid3 = UUIDGenerator.generateTimeBasedUUID();
        UUID uuid4 = UUIDGenerator.generateTimeBasedUUID();

        System.out.println(">>> UUID1: " + uuid1);
        System.out.println(">>> UUID2: " + uuid2);
        System.out.println(">>> UUID3: " + uuid3);
        System.out.println(">>> UUID4: " + uuid4);

        System.out.println(">>> UUID1: " + UUIDv7Utils.extractOffsetDateTime(uuid1));
        System.out.println(">>> UUID2: " + UUIDv7Utils.extractOffsetDateTime(uuid2));
        System.out.println(">>> UUID3: " + UUIDv7Utils.extractOffsetDateTime(uuid3));
        System.out.println(">>> UUID4: " + UUIDv7Utils.extractOffsetDateTime(uuid4));*/

        UUID uuid = UUIDGenerator.generateTimeBasedUUID();

        OffsetDateTime uuidDateTime = UUIDv7Utils.extractOffsetDateTime(uuid).truncatedTo(ChronoUnit.MINUTES);
        OffsetDateTime currentDateTime = OffsetDateTime.now().truncatedTo(ChronoUnit.MINUTES);

        Assertions.assertEquals(uuidDateTime, currentDateTime);
    }
}
