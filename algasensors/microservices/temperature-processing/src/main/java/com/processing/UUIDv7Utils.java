package com.processing;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.UUID;

public class UUIDv7Utils {

    // construtor privado
    private UUIDv7Utils() {
    }

    // método para extrair o timestamp do UUID
    public static OffsetDateTime extractOffsetDateTime(UUID uuid) {

        // verifica se o uuid for um valor nulo, retorna nulo para não ter exceção
        if (uuid == null) {
            return null;
        }

        // extrai os bites do timestamp
        long timestamp = uuid.getMostSignificantBits() >>> 16;

        return OffsetDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
    }
}
