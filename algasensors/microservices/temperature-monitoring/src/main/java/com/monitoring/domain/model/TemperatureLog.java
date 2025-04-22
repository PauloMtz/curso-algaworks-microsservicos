package com.monitoring.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TemperatureLog {

    // value é palavra reservada
    // no id, o nome value foi trocado por id
    // no value, foi colcado entre aspas duplas para manter o nome e não dar conflito

    @Id
    @AttributeOverride(name = "value", column = @Column(name = "id", columnDefinition = "uuid"))
    private TemperatureLogId id;

    @Column(name = "\"value\"")
    private Double value;

    private OffsetDateTime registeredAt;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "sensor_id", columnDefinition = "bigint"))
    private SensorId sensorId;
}
