package com.device.domain.model;

import io.hypersistence.tsid.TSID;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SensorId {

    private TSID value;

    // Alt + insert para gerar de forma rápida
    public SensorId(TSID value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    public SensorId(Long value) {
        Objects.requireNonNull(value);
        this.value = TSID.from(value);
    }

    public SensorId(String value) {
        Objects.requireNonNull(value);
        this.value = TSID.from(value);
    }

    // Alt + insert para gerar de forma rápida
    @Override
    public String toString() {
        return value.toString();
    }
}
